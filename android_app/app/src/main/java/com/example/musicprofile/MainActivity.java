package com.example.musicprofile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.functions.FirebaseFunctions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button logSongButton, generateProfileButton;
    private ProgressBar progressBar;

    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private FirebaseFunctions mFunctions;

    // Sample data for simulating listening history
    private final String[] artists = {"Tame Impala", "Arctic Monkeys", "Glass Animals", "Daft Punk", "Fleetwood Mac", "Kendrick Lamar"};
    private final String[] genres = {"Psychedelic Rock", "Indie Rock", "Indie Pop", "Electronic", "Classic Rock", "Hip Hop"};
    private final String[][] songs = {
            {"The Less I Know The Better", "Let It Happen"},
            {"Do I Wanna Know?", "505"},
            {"Heat Waves", "Gooey"},
            {"Get Lucky", "One More Time"},
            {"Dreams", "The Chain"},
            {"Money Trees", "HUMBLE."}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logSongButton = findViewById(R.id.logSongButton);
        generateProfileButton = findViewById(R.id.generateProfileButton);
        progressBar = findViewById(R.id.progressBar);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        mFunctions = FirebaseFunctions.getInstance();

        logSongButton.setOnClickListener(v -> logRandomSong());
        generateProfileButton.setOnClickListener(v -> generateProfile());
    }

    private void logRandomSong() {
        if (currentUser == null) return;

        Random random = new Random();
        int artistIndex = random.nextInt(artists.length);
        int songIndex = random.nextInt(songs[artistIndex].length);

        Map<String, Object> song = new HashMap<>();
        song.put("artist", artists[artistIndex]);
        song.put("genre", genres[artistIndex]);
        song.put("songTitle", songs[artistIndex][songIndex]);
        song.put("timestamp", FieldValue.serverTimestamp());

        db.collection("users").document(currentUser.getUid())
                .collection("listening_history").add(song)
                .addOnSuccessListener(documentReference -> Toast.makeText(MainActivity.this, "Logged: " + songs[artistIndex][songIndex], Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(MainActivity.this, "Error logging song", Toast.LENGTH_SHORT).show());
    }

    private void generateProfile() {
        progressBar.setVisibility(View.VISIBLE);
        generateProfileButton.setEnabled(false);

        mFunctions.getHttpsCallable("generateMusicProfile")
                .call()
                .addOnCompleteListener(task -> {
                    progressBar.setVisibility(View.GONE);
                    generateProfileButton.setEnabled(true);
                    if (task.isSuccessful()) {
                        String profileText = (String) task.getResult().getData();
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                        intent.putExtra("PROFILE_TEXT", profileText);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Failed to generate profile: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }
}