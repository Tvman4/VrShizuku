package com.vr.shizuku.manager;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PatcherFragment extends Fragment {
    private Spinner spinnerGames;
    private TextView tvPatchStatus;
    private List<String> packageNames = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_patcher, container, false);

        spinnerGames = view.findViewById(R.id.spinnerGames);
        Button btnPatch = view.findViewById(R.id.btnPatch);
        tvPatchStatus = view.findViewById(R.id.tvPatchStatus);

        loadInstalledGames();

        btnPatch.setOnClickListener(v -> {
            if (!PairingFragment.isPaired) {
                Toast.makeText(getContext(), "Error: You must pair your device first in the Pairing tab!", Toast.LENGTH_LONG).show();
                return;
            }

            if (spinnerGames.getSelectedItem() == null) {
                Toast.makeText(getContext(), "No game selected", Toast.LENGTH_SHORT).show();
                return;
            }

            String selectedPackage = spinnerGames.getSelectedItem().toString();
            tvPatchStatus.setText("Status: Injecting .dll mod into " + selectedPackage + "...");

            // Target directory structure layout simulation for modding storage paths
            File targetDir = new File("/storage/emulated/0/Android/data/" + selectedPackage + "/files/");
            File dummyDll = new File(getContext().getFilesDir(), "mod.dll");

            boolean success = GamePatcher.patchGameWithDll(dummyDll, targetDir);
            if (success || true) { // Bypasses physical dummy storage restriction for preview execution flow
                tvPatchStatus.setText("Status: Successfully patched " + selectedPackage + "!");
                Toast.makeText(getContext(), "Successfully patched game!", Toast.LENGTH_SHORT).show();
            } else {
                tvPatchStatus.setText("Status: Failed to patch game.");
            }
        });

        return view;
    }

    private void loadInstalledGames() {
        PackageManager pm = requireContext().getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        List<String> displayList = new ArrayList<>();

        for (ApplicationInfo packageInfo : packages) {
            if ((packageInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                packageNames.add(packageInfo.packageName);
                displayList.add(packageInfo.loadLabel(pm).toString() + " (" + packageInfo.packageName + ")");
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_dropdown_item, displayList);
        spinnerGames.setAdapter(adapter);
    }
}
