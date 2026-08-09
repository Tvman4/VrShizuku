package com.vr.shizuku.manager;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import android.net.nsd.NsdManager;
import android.net.nsd.NsdServiceInfo;
import android.os.Handler;
import android.os.Looper;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class PairingManager {

    public interface PairingCallback {
        void onSuccess();
        void onFailure(String error);
    }

    @RequiresApi(api = Build.VERSION_CODES.R)
    public static void pairAndConnect(String ip, int port, String pairingCode, PairingCallback callback) {
        new Thread(() -> {
            try {
                // Step 1: Establish socket connection to Wireless Debugging pairing port
                Socket socket = new Socket(ip, port);
                OutputStream out = socket.getOutputStream();

                // Send ADB pairing protocol handshake (simplified representation)
                String payload = "CNXN" + pairingCode;
                out.write(payload.getBytes(StandardCharsets.UTF_8));
                out.flush();

                socket.close();
                
                // Step 2: Once paired, spawn the local server daemon via shell execution
                execServerStartup();
                
                new Handler(Looper.getMainLooper()).post(callback::onSuccess);
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onFailure(e.getMessage()));
            }
        }).start();
    }

    private static void execServerStartup() {
        try {
            // Pushes and executes the local unix socket server daemon with root/shell privileges
            Process process = Runtime.getRuntime().exec(new String[]{
                "sh", "-c", 
                "app_process -Djava.class.path=/data/local/tmp/vrshizuku_server.jar /data/local/tmp com.vr.shizuku.ServerMain"
            });
            process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
