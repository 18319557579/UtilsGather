package com.example.uioperate.shortcus;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.uioperate.R;

public class ShortcutsOtherActivity extends AppCompatActivity {

    private TextView tvCenterContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcuts_other);

        tvCenterContent = findViewById(R.id.tv_center_content);

        if (getIntent() != null) {
            int value = getIntent().getIntExtra("EXTRA_KEY", -1);
            tvCenterContent.setText(value + "这个页面");
        }
    }
}