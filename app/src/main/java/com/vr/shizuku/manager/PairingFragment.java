package com.vr.shizuku.manager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PairingFragment extends Fragment {
    public static boolean isPaired = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pairing, container, false);

        EditText etPort = view.findViewById(R.id.etPort);
        EditText etCode = view.findViewById(R.id.etCode);
        Button btnPair = view.findViewById(R.id.btnPair);
        TextView tvPairStatus = view.findViewById(R.id.tvPairStatus);

        btnPair.setOnClickListener(v -> {
            String port = etPort.getText().toString().trim();
            String code = etCode.getText().toString().trim();

            if (port.isEmpty() || code.isEmpty()) {
                Toast.makeText(getContext(), "Enter port and code", Toast.LENGTH_SHORT).show();
                return;
            }

            // Simulate successful Shizuku/ADB pairing hook
            isPaired = true;
            tvPairStatus.setText("Status: Successfully Paired via Port " + port);
            Toast.makeText(getContext(), "Device Paired Successfully!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }
}
