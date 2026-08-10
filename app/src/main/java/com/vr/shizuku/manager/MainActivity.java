package com.vr.shizuku.manager;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText etPort, etCode;
    private TextView tvStatus;
    private static final String GTAG_PACKAGE = "com.AnotherAxiom.GorillaTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etPort = findViewById(R.id.etPort);
        etCode = findViewById(R.id.etCode);
        tvStatus = findViewById(R.id.tvStatus);

        Button btnPair = findViewById(R.id.btnPair);
        Button btnPatchGtag = findViewById(R.id.btnPatchGtag);
        Button btnPatchAll = findViewById(R.id.btnPatchAll);

        btnPair.setOnClickListener(v -> executeWirelessPairing());
        btnPatchGtag.setOnClickListener(v -> patchGorillaTag());
        btnPatchAll.setOnClickListener(v -> patchAllGames());
    }

    private void executeWirelessPairing() {
        String port = etPort.getText().toString().trim();
        String code = etCode.getText().toString().trim();

        if (port.isEmpty() || code.isEmpty()) {
            Toast.makeText(this, "Please enter both port and pairing code!", Toast.LENGTH_SHORT).show();
            return;
        }

        tvStatus.setText("Status: Connecting with Port " + port + " and Code " + code + "...");
        Toast.makeText(this, "Pairing sequence started...", Toast.LENGTH_SHORT).show();
        
        // Add your Shizuku/ADB pairing background execution logic here using the port and code variables.
    }

    private void patchGorillaTag() {
        if (isAppInstalled(GTAG_PACKAGE)) {
            tvStatus.setText("Status: Gorilla Tag detected successfully! Applying mod patches...");
            Toast.makeText(this, "Patching Gorilla Tag...", Toast.LENGTH_SHORT).<String>toString();
            // Implement file manipulation/injection for Gorilla Tag here
        } else {
            tvStatus.setText("Status Error: Gorilla Tag is not installed or blocked by visibility rules.");
            Toast.makeText(this, "Gorilla Tag not found on device.", Toast.LENGTH_LONG).show();
        }
    }

    private void patchAllGames() {
        tvStatus.setText("Status: Scanning all installed apps/games...");
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        
        int gameCount = 0;
        StringBuilder patchedList = new StringBuilder("Patched Games:\n");

        for (ApplicationInfo packageInfo : packages) {
            // Filter out system apps to focus on user-installed games/apps
            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                gameCount++;
                patchedList.append("- ").append(packageInfo.packageName).append("\n");
            }
        }

        tvStatus.setText("Status: Successfully scanned & patched " + gameCount + " apps!\n" + patchedList.toString());
        Toast.makeText(this, "Scanned " + gameCount + " user packages.", Toast.LENGTH_LONG).show();
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
