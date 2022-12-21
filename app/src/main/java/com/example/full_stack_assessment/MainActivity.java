package com.example.full_stack_assessment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


/**
 * Main Activity class holds the logic for the forecast btn
 * The forecast btn starts the ForecastActivity
 */
public class MainActivity extends AppCompatActivity {
    Button forecastButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ToDO: Create the forecast button listener to transition to the next activity

        forecastButton = (Button)findViewById(R.id.forecast_button);
        forecastButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent switchActivityIntent = new Intent(MainActivity.this, ForecastActivity.class);
                startActivity(switchActivityIntent);
            }
        });
    }
}
