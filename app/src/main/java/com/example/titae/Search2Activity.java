package com.example.titae;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Search2Activity extends AppCompatActivity {

    SearchData searchData = new SearchData();

    TabLayout bank_tap;

    Button ba_btn;
    Button bf_btn;
    Button bs_btn;

    Button ta_btn;
    Button tc_btn;
    Button tl_btn;

    Button ra_btn;
    Button rre_btn;
    Button rra_btn;

    Button ca_btn;
    Button cs_btn;
    Button cc_btn;

    Spinner region;
    Spinner period;
    EditText amount;

    Button sub_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search2);

        ba_btn = (Button) findViewById(R.id.bank_all);
        bf_btn = (Button) findViewById(R.id.bank_first);
        bs_btn = (Button) findViewById(R.id.bank_second);

        ta_btn = (Button) findViewById(R.id.target_all);
        tc_btn = (Button) findViewById(R.id.target_common);
        tl_btn = (Button) findViewById(R.id.target_limit);

        ra_btn = (Button) findViewById(R.id.reservingmethod_all);
        rre_btn = (Button) findViewById(R.id.reservingmethod_reg);
        rra_btn = (Button) findViewById(R.id.reservingmethod_rand);

        ca_btn = (Button) findViewById(R.id.calmethod_all);
        cs_btn = (Button) findViewById(R.id.calmethod_simple);
        cc_btn = (Button) findViewById(R.id.calmethod_compound);

        region = (Spinner) findViewById(R.id.region);
        period = (Spinner) findViewById(R.id.period);
        amount = (EditText) findViewById(R.id.amount);

        sub_btn = (Button) findViewById(R.id.submission);


        /*
        Initialize searchDate
         */
        searchData.setFinancialSphere("all");
//        ba_btn.setSelected(true);
        searchData.setTarget("all");
//        ta_btn.setBackgroundColor(0x969797);
        searchData.setReservingmethod("all");
//        ra_btn.setBackgroundColor(0x969797);
        searchData.setCalmethod("all");
//        ca_btn.setBackgroundColor(0x969797);
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
                    searchData.setRegion("전체");
                }
                else if(position ==1)
                {
                    searchData.setRegion("서울");
                }
                else if(position ==2)
                {
                    searchData.setRegion("경기");
                }
                else if(position ==3)
                {
                    searchData.setRegion("충청");
                }
                else if(position ==4)
                {
                    searchData.setRegion("전라");
                }
                else if(position ==5)
                {
                    searchData.setRegion("경상");
                }
                else if(position ==6)
                {
                    searchData.setRegion("인천");
                }
                else if(position ==7)
                {
                    searchData.setRegion("부산");
                }
                else if(position ==8)
                {
                    searchData.setRegion("대구");
                }
                else if(position ==9)
                {
                    searchData.setRegion("울산");
                }
                else if(position ==10)
                {
                    searchData.setRegion("대전");
                }
                else if(position ==11)
                {
                    searchData.setRegion("제주");
                }
                //기타 등등 처리
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //기간 선택 스피너 처리
        period.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position == 0)
                {
                    //6개월 일 때 경우
                    searchData.setPeriod(6);
                }
                else if(position ==1)
                {
                    //1년일 때
                    searchData.setPeriod(12);
                }
                else if(position ==2)
                {
                    //2년일 때
                    searchData.setPeriod(24);
                }
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

        ra_btn.setOnClickListener(btnOnClickListner);
        rra_btn.setOnClickListener(btnOnClickListner);
        rre_btn.setOnClickListener(btnOnClickListner);

        ca_btn.setOnClickListener(btnOnClickListner);
        cc_btn.setOnClickListener(btnOnClickListner);
        cs_btn.setOnClickListener(btnOnClickListner);

        amount.addTextChangedListener(textWatcher);
        /*
        SearchActivity로 이동
         */
        sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button click (to SearchAct)", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Search2Activity.this, SearchActivity.class);

                //button 값 입력 받아 SearchActivity로 전송
                intent.putExtra("SEARCH_DATA", searchData);
                startActivity(intent);
                finish();
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
                    ba_btn.setSelected(true);
                    bf_btn.setSelected(false);
                    bs_btn.setSelected(false);
//                    bf_btn.setBackgroundColor(0xd6d7d7);
//                    bs_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.bank_first:
                    searchData.setFinancialSphere("first");
                    ba_btn.setSelected(false);
                    bf_btn.setSelected(true);
                    bs_btn.setSelected(false);
//                    bf_btn.setBackgroundColor(0x969797);
//                    ba_btn.setBackgroundColor(0xd6d7d7);
//                    bs_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.bank_second:
                    searchData.setFinancialSphere("second");
                    ba_btn.setSelected(false);
                    bf_btn.setSelected(false);
                    bs_btn.setSelected(true);
//                    bs_btn.setBackgroundColor(0x969797);
//                    bf_btn.setBackgroundColor(0xd6d7d7);
//                    ba_btn.setBackgroundColor(0xd6d7d7);
                    break;

                case R.id.target_all:
                    searchData.setTarget("all");
//                    ta_btn.setBackgroundColor(0x969797);
//                    tc_btn.setBackgroundColor(0xd6d7d7);
//                    tl_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.target_common:
                    searchData.setTarget("common");
//                    tc_btn.setBackgroundColor(0x969797);
//                    ta_btn.setBackgroundColor(0xd6d7d7);
//                    tl_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.target_limit:
                    searchData.setTarget("limit");
//                    tl_btn.setBackgroundColor(0x969797);
//                    tc_btn.setBackgroundColor(0xd6d7d7);
//                    ta_btn.setBackgroundColor(0xd6d7d7);
                    break;

                case R.id.reservingmethod_all:
                    searchData.setReservingmethod("all");
//                    ra_btn.setBackgroundColor(0x969797);
//                    rra_btn.setBackgroundColor(0xd6d7d7);
//                    rre_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.reservingmethod_reg:
                    searchData.setReservingmethod("reg");
//                    rra_btn.setBackgroundColor(0x969797);
//                    ra_btn.setBackgroundColor(0xd6d7d7);
//                    rre_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.reservingmethod_rand:
                    searchData.setReservingmethod("rand");
//                    rre_btn.setBackgroundColor(0x969797);
//                    rra_btn.setBackgroundColor(0xd6d7d7);
//                    ra_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.calmethod_all:
                    searchData.setCalmethod("all");
//                    ca_btn.setBackgroundColor(0x969797);
//                    cs_btn.setBackgroundColor(0xd6d7d7);
//                    cc_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.calmethod_simple:
                    searchData.setCalmethod("simple");
//                    cc_btn.setBackgroundColor(0x969797);
//                    ca_btn.setBackgroundColor(0xd6d7d7);
//                    cs_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.calmethod_compound:
                    searchData.setCalmethod("compound");
//                    cc_btn.setBackgroundColor(0x969797);
//                    cs_btn.setBackgroundColor(0xd6d7d7);
//                    ca_btn.setBackgroundColor(0xd6d7d7);
                    break;
            }
        }
    }

    /*
    텍스트 값 변경 감지
     */
    TextWatcher textWatcher = new TextWatcher() {
        String num;
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            try{
                searchData.setAmount(Integer.parseInt("" + amount.getText()));
            } catch(NumberFormatException nfe) {
            }
            Toast.makeText(getApplicationContext(), amount.getText().toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


}
