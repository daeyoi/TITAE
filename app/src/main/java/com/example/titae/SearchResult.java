package com.example.titae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;

import android.os.Bundle;
import android.widget.TextView;

import org.jsoup.Jsoup;

public class SearchResult extends AppCompatActivity {

    private TextView title;
    private TextView rate;
    private TextView context;
    private String htmlurl =  "https://obank.kbstar.com/quics?page=C016613#CP"; //파싱할 주소

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);


    }


}


