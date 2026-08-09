package com.vr.shizuku.manager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText inputIp, inputPort, inputCode;
    private Button btnPair;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Simple programmatic layout generation for compatibility
        android.widget.LinearLayout layout = new android.widget.LinearLayout(this);
        layout.setOrientation(android.widget.LinearLayout.VERTICAL);
        layout.setPadding(50, 50, 50, 50);

        inputIp = new EditText(this);
        inputIp.setHint("IP Address (e.g., 127.0.0.1)");
        inputIp.setText("127.0.0.1");

        inputPort = new EditText(this);
        inputPort.setHint("Pairing Port");

        inputCode = new EditText(this);
        inputCode.setHint("6-Digit Pairing Code");

        btnPair = new Button(this);
        btnPair.setText("Start VRShizuku Pairing");

        layout.addView(inputIp);
        layout.addView(inputPort);
        layout.addView(inputCode);
        layout.addView(btnPair);

        setContentView(layout);

        btnPair.setOnClickListener(v -> {
            String ip = inputIp.getText().toString();
            int port = Integer.parseInt(inputPort.getText().toString());
            String code = inputCode.getText().toString();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                PairingManager.pairAndConnect(ip, port, code, new PairingManager.PairingCallback() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(MainActivity.INSTANCE != null ? MainActivity.INSTANCE : MainActivity.this, 
                            "Successfully Paired & Daemon Running!", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(String error) {
                        Toast.makeText(MainActivity.this, "Pairing Failed: " + error, Toast.LENGTH_LONG).show();
                    }
                });
            } else {
                Toast.makeText(this, "Requires Android 11+ (Quest 2/3/Pro running Horizon OS)", Toast.LENGTH_LONG).show();
            }
        });
        
        MainActivity.INSTANCE = this;
    }
    
    private static MainActivity INSTANCE;
}
