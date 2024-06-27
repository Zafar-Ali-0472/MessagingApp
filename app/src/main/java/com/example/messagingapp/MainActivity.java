package com.example.messagingapp;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private EditText messageBox;
    private ImageButton resetButton;
    private static final String FILE_NAME = "message.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        messageBox = findViewById(R.id.editTextText);
        resetButton = findViewById(R.id.imageButton2);
        setupResetButtonListener(); // Call setupResetButtonListener() method

        loadSavedText(); // Load saved text when the app starts

        // Display toast message
        Toast.makeText(this, "Loading Saved Text", Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onPause() {
        super.onPause();
        saveTextToInternalStorage(); // Save text when app goes into background

        // Display toast message
        Toast.makeText(this, "Text Saved Successfully.. ", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadSavedText(); // Load saved text when app resumes
    }

    private void saveTextToInternalStorage() {
        String text = messageBox.getText().toString();
        FileOutputStream fos = null;
        try {
            fos = openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(text.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadSavedText() {
        FileInputStream fis = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            fis = openFileInput(FILE_NAME);
            InputStreamReader inputStreamReader = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            messageBox.setText(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void setupResetButtonListener() {
        resetButton.setOnClickListener(view -> messageBox.setText(""));
    }
}
