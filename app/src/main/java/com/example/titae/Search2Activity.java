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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Search2Activity extends AppCompatActivity {

    SearchData searchData = new SearchData();

    Button ba_btn;
    Button bf_btn;
    Button bs_btn;

    Button ta_btn;
    Button tc_btn;
    Button tl_btn;

    Button ca_btn;
    Button cre_btn;
    Button cra_btn;

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

        ca_btn = (Button) findViewById(R.id.calmethode_all);
        cre_btn = (Button) findViewById(R.id.calmethode_reg);
        cra_btn = (Button) findViewById(R.id.calmethode_rand);

        region = (Spinner) findViewById(R.id.region);
        period = (Spinner) findViewById(R.id.period);
        amount = (EditText) findViewById(R.id.amount);

        sub_btn = (Button) findViewById(R.id.submission);


        /*
        Initialize searchDate
         */
        searchData.setFinancialSphere("all");
        ba_btn.setBackgroundColor(0x969797);
        searchData.setTarget("all");
        ta_btn.setBackgroundColor(0x969797);
        searchData.setCalMethod("all");
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

        ca_btn.setOnClickListener(btnOnClickListner);
        cra_btn.setOnClickListener(btnOnClickListner);
        cre_btn.setOnClickListener(btnOnClickListner);

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

                case R.id.calmethode_all:
                    searchData.setCalMethod("all");
                    ca_btn.setBackgroundColor(0x969797);
                    cra_btn.setBackgroundColor(0xd6d7d7);
                    cre_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.calmethode_rand:
                    searchData.setCalMethod("rand");
                    cra_btn.setBackgroundColor(0x969797);
                    ca_btn.setBackgroundColor(0xd6d7d7);
                    cre_btn.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.calmethode_reg:
                    searchData.setCalMethod("rand");
                    cre_btn.setBackgroundColor(0x969797);
                    cra_btn.setBackgroundColor(0xd6d7d7);
                    ca_btn.setBackgroundColor(0xd6d7d7);
                    break;
            }
        }
    }

    /*
    값 변경
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



//    void region_popup() {
//        final List<String> ListItems = new ArrayList<>();
//        //차후 수정
//        ListItems.add("서울");
//        ListItems.add("강원");
//        ListItems.add("경기");
//        ListItems.add("충청");
//        ListItems.add("경상");
//        ListItems.add("전라");
//        ListItems.add("광역");
//        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);
//
//        final List SelectedItems = new ArrayList();
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("지역 선택");
//        builder.setMultiChoiceItems(items, null,
//                new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which,
//                                        boolean isChecked) {
//                        if (isChecked) {
//                            //사용자가 체크한 경우 리스트에 추가
//                            SelectedItems.add(which);
//                        } else if (SelectedItems.contains(which)) {
//                            //이미 리스트에 들어있던 아이템이면 제거
//                            SelectedItems.remove(Integer.valueOf(which));
//                        }
//                    }
//                });
//        builder.setPositiveButton("Ok",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        String msg = "";
//                        for (int i = 0; i < SelectedItems.size(); i++) {
//                            int index = (int) SelectedItems.get(i);
//
//                            msg = msg + "\n" + (i + 1) + " : " + ListItems.get(index);
//                        }
//                        Toast.makeText(getApplicationContext(),
//                                "Total " + SelectedItems.size() + " Items Selected.\n" + msg, Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
//        builder.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//        builder.show();
//    }
//
//    void period_popup() {
//        final List<String> ListItems = new ArrayList<>();
//        //차후 수정
//        ListItems.add("6개월");
//        ListItems.add("1년");
//        ListItems.add("2년");
//        final CharSequence[] items = ListItems.toArray(new String[ListItems.size()]);
//
//        final List SelectedItems = new ArrayList();
//
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("저축 기간 선택");
//        builder.setMultiChoiceItems(items, null,
//                new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which,
//                                        boolean isChecked) {
//                        if (isChecked) {
//                            //사용자가 체크한 경우 리스트에 추가
//                            SelectedItems.add(which);
//                        } else if (SelectedItems.contains(which)) {
//                            //이미 리스트에 들어있던 아이템이면 제거
//                            SelectedItems.remove(Integer.valueOf(which));
//                        }
//                    }
//                });
//        builder.setPositiveButton("Ok",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//                        String msg = "";
//                        for (int i = 0; i < SelectedItems.size(); i++) {
//                            int index = (int) SelectedItems.get(i);
//
//                            msg = msg + "\n" + (i + 1) + " : " + ListItems.get(index);
//                        }
//                        Toast.makeText(getApplicationContext(),
//                                "Total " + SelectedItems.size() + " Items Selected.\n" + msg, Toast.LENGTH_LONG)
//                                .show();
//                    }
//                });
//        builder.setNegativeButton("Cancel",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//        builder.show();
//    }
}
