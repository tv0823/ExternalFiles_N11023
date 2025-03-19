package com.example.externalfiles_n11023;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * The type Credits activity.
 *
 * @author      Tal Weintraub <tv0823@bs.amalnet.k12.il>
 * @version	    1
 * @since		19/3/2025
 * short description:
 *      Checks the user input for the menu and returns to main activity
 */
public class CreditsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
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
     * Checks if the clicked menuItem is R.id.menuHome and if it is returns to the main activity
     *
     * @param item a menuItem
     * @return ture
     */
    @Override
    public boolean onOptionsItemSelected(@Nullable MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menuHome) {
            finish();
        }

        return true;
    }
}