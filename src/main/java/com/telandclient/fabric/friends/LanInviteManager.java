package com.telandclient.fabric.friends;

import com.telandclient.fabric.TelandFabric;
import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.net.*;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * LAN davet sistemi.
 * Host: rastgele 6 haneli kod üretir, belirlenen portta TCP dinler.
 * Guest: kodu girer → host portuna bağlanır → MC'ye localhost:port olarak ekler.
 */
public class LanInviteManager {

    private static ServerSocket serverSocket;
    private static int hostPort = 0;
    private static String inviteCode = null;
    private static final ExecutorService POOL = Executors.newCachedThreadPool(r -> {
        Thread t = new Thread(r, "teland-lan");
        t.setDaemon(true);
        return t;
    });

    public static boolean isHosting() {
        return serverSocket != null && !serverSocket.isClosed();
    }

    /** LAN sunucusu başlat, davet kodu döndür */
    public static String startHosting() {
        try {
            serverSocket = new ServerSocket(0); // OS boş port atar
            hostPort = serverSocket.getLocalPort();
            inviteCode = String.format("%06d", new Random().nextInt(999999));

            TelandFabric.LOGGER.info("[TelandClient-LAN] Sunucu başlatıldı. Port: {} Kod: {}", hostPort, inviteCode);

            POOL.submit(() -> {
                while (!serverSocket.isClosed()) {
                    try {
                        Socket client = serverSocket.accept();
                        POOL.submit(() -> handleGuest(client));
                    } catch (IOException ignored) {}
                }
            });

            return inviteCode;
        } catch (IOException e) {
            TelandFabric.LOGGER.error("[TelandClient-LAN] Sunucu başlatılamadı", e);
            return "HATA";
        }
    }

    public static void stopHosting() {
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException ignored) {}
        serverSocket = null;
        inviteCode = null;
        hostPort = 0;
    }

    /** Verilen kodla host'a bağlan */
    public static void joinByCode(String code) {
        // Format: "PORT:KOD" veya sadece 6 haneli kod (aynı LAN)
        // Basit: kodu lokal ağda broadcast → bul → bağlan
        // Şimdilik: kullanıcı "IP:PORT" formatında girer
        String[] parts = code.split(":");
        if (parts.length == 2) {
            String ip   = parts[0].trim();
            String port = parts[1].trim();
            // MC multiplayer'a ekle
            MinecraftClient mc = MinecraftClient.getInstance();
            mc.execute(() -> {
                TelandFabric.LOGGER.info("[TelandClient-LAN] Bağlanılıyor: {}:{}", ip, port);
                // MC'yi doğrudan bu adrese bağla
                mc.world.disconnect();
                net.minecraft.client.network.ServerAddress addr =
                    net.minecraft.client.network.ServerAddress.parse(ip + ":" + port);
                net.minecraft.client.gui.screen.ConnectScreen.connect(
                    null, mc,
                    net.minecraft.client.network.ServerAddress.parse(ip + ":" + port),
                    new net.minecraft.client.network.ServerInfo("LAN Arkadaş", ip + ":" + port, net.minecraft.client.network.ServerInfo.ServerType.OTHER),
                    false, null
                );
            });
        }
    }

    private static void handleGuest(Socket guest) {
        try (BufferedReader in  = new BufferedReader(new InputStreamReader(guest.getInputStream()));
             PrintWriter   out = new PrintWriter(guest.getOutputStream(), true)) {
            String received = in.readLine();
            if (inviteCode != null && inviteCode.equals(received)) {
                out.println("OK:" + hostPort);
                TelandFabric.LOGGER.info("[TelandClient-LAN] Misafir bağlandı: {}", guest.getInetAddress());
            } else {
                out.println("ERR:Geçersiz kod");
            }
        } catch (IOException ignored) {}
    }

    public static String getInviteCode() { return inviteCode; }
    public static int    getHostPort()   { return hostPort; }
}
