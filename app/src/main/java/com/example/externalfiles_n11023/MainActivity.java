package com.example.externalfiles_n11023;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The type Main activity.
 *
 * @author      Tal Weintraub <tv0823@bs.amalnet.k12.il>
 * @version	    1
 * @since		20/3/2025
 * short description:
 *      read text from file and put it in a textView or get text from the user and displays it and add it to the external file.
 */
public class MainActivity extends AppCompatActivity {
    /**
     * The textEt.
     */
    EditText textEt;

    /**
     * The displayTv.
     */
    TextView displayTv;

    /**
     * The FILENAME.
     */
    private final String FILENAME = "exttest.txt";

    /**
     * The REQUEST_CODE_PERMISSION.
     */
    private int REQUEST_CODE_PERMISSION = 1984;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textEt = findViewById(R.id.textEt);
        displayTv = findViewById(R.id.displayTv);

        String textFromFile = getFileText();
        if(isExternalStorageAvailable() && checkPermission()) {
            displayTv.setText(textFromFile);
        } else {
            requestPermission();
        }
    }

    public boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission to access external storage granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission to access external storage NOT granted", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Gets text from user and add it to the external file and displays it in TextView.
     *
     * @param view the view
     */
    public void saveBtn(View view) {
        if(!isExternalStorageAvailable()) {
            Toast.makeText(this, "External storage not available", Toast.LENGTH_SHORT).show();
            return;
        }
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file, true);
            writer.write(textEt.getText().toString());
            writer.close();

            displayTv.setText(getFileText());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Resets text in external file and displays it in a textView.
     *
     * @param view the view
     */
    public void resetBtn(View view) {
        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileWriter writer = new FileWriter(file);
            writer.write("");
            writer.close();

            textEt.setText("");
            displayTv.setText("");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the text from user and closes the app.
     *
     * @param view the view
     */
    public void exitApp(View view) {
        saveBtn(view);
        finish();;
    }

    /**
     * Gets text from external file.
     * @return the external file text.
     */
    public String getFileText() {
        String text = "";

        try {
            File externalDir = Environment.getExternalStorageDirectory();
            File file = new File(externalDir, FILENAME);
            file.getParentFile().mkdirs();
            FileReader reader = new FileReader(file);
            BufferedReader bR = new BufferedReader(reader);
            StringBuilder sB = new StringBuilder();
            String line = bR.readLine();
            while (line != null) {
                sB.append(line+'\n');
                line = bR.readLine();
            }
            bR.close();
            reader.close();
            text = sB.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return text;
    }

    /**
     * Creates the options menu on screen
     *
     * @param menu the menu
     * @return ture
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     * Checks if the clicked menuItem is R.id.menuCred
     *
     * @param item a menuItem
     * @return ture
     */
    @Override
    public boolean onOptionsItemSelected(@Nullable MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menuCred) {
            Intent si = new Intent(this, CreditsActivity.class);
            startActivity(si);
        }
        return true;
    }
}