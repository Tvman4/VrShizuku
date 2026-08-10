package com.vr.shizuku.manager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatus;
    private static final String GTAG_PACKAGE = "com.AnotherAxiom.GorillaTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnPair = findViewById(R.id.btnPair);
        Button btnPatchGtag = findViewById(R.id.btnPatchGtag);
        tvStatus = findViewById(R.id.tvStatus);

        btnPair.setOnClickListener(v -> startWirelessPairing());
        btnPatchGtag.setOnClickListener(v -> patchGorillaTag());
    }

    private void startWirelessPairing() {
        tvStatus.setText("Status: Initializing wireless pairing...");
        Toast.makeText(this, "Starting Shizuku Pairing...", Toast.LENGTH_SHORT).show();
        // Add your PairingManager logic here to handle wireless debugging connection
    }

    private void patchGorillaTag() {
        tvStatus.setText("Status: Checking for Gorilla Tag...");
        
        if (isAppInstalled(GTAG_PACKAGE)) {
            tvStatus.setText("Status: Gorilla Tag found. Applying patch...");
            // Logic to modify files or inject mods into Gorilla Tag directory via Shizuku/root permissions
            Toast.makeText(this, "Gorilla Tag patch sequence initiated.", Toast.LENGTH_LONG).show();
        } else {
            tvStatus.setText("Status: Gorilla Tag not installed.");
            Toast.makeText(this, "Error: Gorilla Tag (com.AnotherAxiom.GorillaTag) is not installed on this device.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isAppInstalled(String packageName) {
        try {
            getPackageManager().getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
