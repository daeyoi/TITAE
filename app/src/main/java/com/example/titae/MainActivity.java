package com.example.titae;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button MTSbtn = (Button) findViewById(R.id.main_to_search_btn);
        Button MTCbtn = (Button) findViewById(R.id.main_to_compare_btn);
        Button MTCabtn = (Button) findViewById(R.id.main_to_calc_btn);

        //검색 버튼 클릭 시
        MTSbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_search_popup();
            }
        });

        //비교 버튼 클릭 시
        MTCbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_compare_popup();
            }
        });
    }

    void select_search_popup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.selection_deposit_savings, null);
        builder.setView(view);
        final Button deposit = (Button) view.findViewById(R.id.select_deposit);
        final Button savings = (Button) view.findViewById(R.id.select_savings);

        final AlertDialog dialog = builder.create();

        //적금 버튼 처리
        savings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button click (to Search2Act)", Toast.LENGTH_LONG).show();
                Intent myintent = new Intent(MainActivity.this, Search2Activity.class);
                startActivity(myintent);
//                finish();
            }
        });

        dialog.show();

    }

    void select_compare_popup() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.selection_deposit_savings, null);
        builder.setView(view);
        final Button deposit = (Button) view.findViewById(R.id.select_deposit);
        final Button savings = (Button) view.findViewById(R.id.select_savings);

        final AlertDialog dialog = builder.create();

        //적금 버튼 처리
        savings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button click (to CompareAct)", Toast.LENGTH_LONG).show();
                Intent myintent = new Intent(MainActivity.this, CompareActivity.class);
                startActivity(myintent);
//                finish();
            }
        });

        dialog.show();

    }
}