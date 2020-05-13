package com.example.titae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    RecyclerView mRecyclerView = null ;
    SearchRecyclerAdapter mAdapter = null ;
    ArrayList<SearchRecyclerItem> mList = new ArrayList<SearchRecyclerItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mRecyclerView = findViewById(R.id.search_list) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new SearchRecyclerAdapter(mList) ;
        mRecyclerView.setAdapter(mAdapter) ;

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;


        // 아이템 추가. 임시로 직접입력
        addItem("A은행",
                "ㄱ상품", 2.80F) ;
        addItem("B은행",
                "ㄴ상품", 2.90F) ;
        addItem("C은행",
                "ㄷ상품", 2.81F) ;
        addItem("D은행",
                "ㄹ상품", 2.94F) ;
        addItem("E은행",
                "ㅁ상품", 2.77F) ;

        mAdapter.notifyDataSetChanged() ;
    }



    public void addItem(String bankName, String productName, float rate) {
        SearchRecyclerItem item = new SearchRecyclerItem();

        item.setBankName(bankName);
        item.setProductName(productName);
        item.setRate(rate);

        mList.add(item);
    }
}
