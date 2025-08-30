package com.example.musicprofile;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileTextView = findViewById(R.id.profileTextView);

        String profileText = getIntent().getStringExtra("PROFILE_TEXT");
        if (profileText != null) {
            profileTextView.setText(profileText);
        } else {
            profileTextView.setText("Could not load profile.");
        }
    }
}