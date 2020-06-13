package com.example.titae;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Search2DepositActivity extends AppCompatActivity {

    SearchData searchData = new SearchData();

    Button ba_btn;
    Button bf_btn;
    Button bs_btn;

    Button ta_btn;
    Button tc_btn;
    Button tl_btn;

    Button ca_btn;
    Button cs_btn;
    Button cc_btn;

    Spinner region;

    Button sub_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2_deposit);

        ba_btn = (Button) findViewById(R.id.bank_all);
        bf_btn = (Button) findViewById(R.id.bank_first);
        bs_btn = (Button) findViewById(R.id.bank_second);

        ta_btn = (Button) findViewById(R.id.target_all);
        tc_btn = (Button) findViewById(R.id.target_common);
        tl_btn = (Button) findViewById(R.id.target_limit);

        ca_btn = (Button) findViewById(R.id.calmethod_all);
        cs_btn = (Button) findViewById(R.id.calmethod_simple);
        cc_btn = (Button) findViewById(R.id.calmethod_compound);

        region = (Spinner) findViewById(R.id.region);

        sub_btn = (Button) findViewById(R.id.submission);


        /*
        Initialize searchDate
         */
        searchData.setFinancialSphere("all");
        ba_btn.setBackgroundColor(0x969797);
        searchData.setTarget("all");
        ta_btn.setBackgroundColor(0x969797);
        searchData.setCalmethod("all");
        ca_btn.setBackgroundColor(0x969797);
        searchData.setAmount(0);

        //수정해야하는 부분
        searchData.setPeriod(1);
        searchData.setRegion("서울");


        //지역 선택 스피너 처리
        region.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 0)
                {
                    //서울 일 때 경우
                    searchData.setRegion("서울");
                }
                else if(position ==1)
                {
                    //경기일때
                    searchData.setRegion("경기");
                }
                //기타 등등 처리
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        /*
        버튼 액션
         */
        BtnOnClickListner btnOnClickListner = new BtnOnClickListner();
        ba_btn.setOnClickListener(btnOnClickListner);
        bf_btn.setOnClickListener(btnOnClickListner);
        bs_btn.setOnClickListener(btnOnClickListner);

        ta_btn.setOnClickListener(btnOnClickListner);
        tc_btn.setOnClickListener(btnOnClickListner);
        tl_btn.setOnClickListener(btnOnClickListner);


        /*
        SearchActivity로 이동
         */
        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button click (to SearchAct)", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Search2DepositActivity.this, SearchDepositActivity.class);

                //button 값 입력 받아 SearchActivity로 전송
                intent.putExtra("SEARCH_DATA", searchData);
                startActivity(intent);
            }
        });
    }

    /*
    버튼 액션 처리 변수 값 알맞게 수정 추천
     */
    class BtnOnClickListner implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.bank_all:
                    searchData.setFinancialSphere("all");
                    ba_btn.setBackgroundColor(0x969797);
                    bf_btn.setBackgroundColor(0xd6d7d7);
                    bs_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.bank_first:
                    searchData.setFinancialSphere("first");
                    bf_btn.setBackgroundColor(0x969797);
                    ba_btn.setBackgroundColor(0xd6d7d7);
                    bs_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.bank_second:
                    searchData.setFinancialSphere("second");
                    bs_btn.setBackgroundColor(0x969797);
                    bf_btn.setBackgroundColor(0xd6d7d7);
                    ba_btn.setBackgroundColor(0xd6d7d7);
                    break;

                case R.id.target_all:
                    searchData.setTarget("all");
                    ta_btn.setBackgroundColor(0x969797);
                    tc_btn.setBackgroundColor(0xd6d7d7);
                    tl_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.target_common:
                    searchData.setTarget("common");
                    tc_btn.setBackgroundColor(0x969797);
                    ta_btn.setBackgroundColor(0xd6d7d7);
                    tl_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.target_limit:
                    searchData.setTarget("limit");
                    tl_btn.setBackgroundColor(0x969797);
                    tc_btn.setBackgroundColor(0xd6d7d7);
                    ta_btn.setBackgroundColor(0xd6d7d7);
                    break;

                case R.id.calmethod_all:
                    searchData.setCalmethod("all");
                    ca_btn.setBackgroundColor(0x969797);
                    cs_btn.setBackgroundColor(0xd6d7d7);
                    cc_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.calmethod_simple:
                    searchData.setCalmethod("simple");
                    cc_btn.setBackgroundColor(0x969797);
                    ca_btn.setBackgroundColor(0xd6d7d7);
                    cs_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.calmethod_compound:
                    searchData.setCalmethod("compound");
                    cc_btn.setBackgroundColor(0x969797);
                    cs_btn.setBackgroundColor(0xd6d7d7);
                    ca_btn.setBackgroundColor(0xd6d7d7);
                    break;

            }
        }
    }

    /*
    값 변경
     */



}
