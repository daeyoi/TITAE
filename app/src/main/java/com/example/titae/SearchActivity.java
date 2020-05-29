package com.example.titae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    RecyclerView mRecyclerView = null ;
    SearchRecyclerAdapter mAdapter = null ;
    ArrayList<SearchRecyclerItem> mList = new ArrayList<SearchRecyclerItem>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //intent 값 전달 받음
       Intent intent = getIntent();
        SearchData searchData = (SearchData)intent.getSerializableExtra("SEARCH_DATA");
        if(searchData == null)
        {
            Toast.makeText(getApplicationContext(), "Tq", Toast.LENGTH_LONG).show();
        }

        // 제대로 intent값이 받아졌는지 확인하는 부분 나중에 삭제
        TextView tv1 = (TextView)findViewById(R.id.tv1);
        TextView tv2 = (TextView)findViewById(R.id.tv2);
        TextView tv3 = (TextView)findViewById(R.id.tv3);
        TextView tv4 = (TextView)findViewById(R.id.tv4);
        TextView tv5 = (TextView)findViewById(R.id.tv5);
        TextView tv6 = (TextView)findViewById(R.id.tv6);

        tv1.setText("금융권역: " + searchData.getFinancialSphere() + "  ");
        tv2.setText("가입대상: " + searchData.getTarget() + "  ");
        tv3.setText("적립방식: " + searchData.getCalMethod() + "  ");
        tv4.setText("지역: " + searchData.getRegion() + "  ");
        tv5.setText("적립금액: " + searchData.getAmount() + "  ");
        tv6.setText("저축기간: " + searchData.getPeriod() + "  ");
        //


        mRecyclerView = findViewById(R.id.search_list) ;
        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        mAdapter = new SearchRecyclerAdapter(mList) ;
        mRecyclerView.setAdapter(mAdapter) ;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;


        /**
         * 이부분에서 쿼리를 받아와서 아이템 추가
         * 아래 부분은 임시로 나중에 삭제
         */

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


        addItem("D은행",
                "ㄹ상품", 2.94F) ;
        addItem("E은행",
                "ㅁ상품", 2.77F) ;
        addItem("D은행",
                "ㄹ상품", 2.94F) ;
        addItem("E은행",
                "ㅁ상품", 2.77F) ;
        addItem("D은행",
                "ㄹ상품", 2.94F) ;
        addItem("E은행",
                "ㅁ상품", 2.77F) ;
        addItem("D은행",
                "ㄹ상품", 2.94F) ;
        addItem("E은행",
                "ㅁ상품", 2.77F) ;
        addItem("D은행",
                "ㄹ상품", 2.94F) ;
        addItem("E은행",
                "ㅁ상품", 2.77F) ;
        addItem("D은행",
                "ㄹ상품", 2.94F) ;
        addItem("E은행",
                "ㅁ상품", 2.77F) ;
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
