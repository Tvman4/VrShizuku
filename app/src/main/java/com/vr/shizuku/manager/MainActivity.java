package com.vr.shizuku.manager;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Links this activity to activity_main.xml so it renders UI instead of a white screen
        setContentView(R.layout.activity_main); 
    }
}
