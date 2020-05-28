package com.example.titae;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button MTSbtn = (Button)findViewById(R.id.main_to_search_btn);
        MTSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button click (to Search2Act)", Toast.LENGTH_LONG).show();
                Intent myintent = new Intent(MainActivity.this, Search2Activity.class);
                startActivity(myintent);
                finish();
            }
        });
    }

}
