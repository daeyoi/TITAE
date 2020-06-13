package com.example.titae;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ResultDepositActivity extends AppCompatActivity {

    TextView bank;
    TextView product;
    TextView rate;
    TextView target;
    TextView res_method;
    TextView rec_amount;
    TextView interest;
    TextView etc;
    Button btn_home;

    SearchRecyclerItem mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_deposit);
        mData = new SearchRecyclerItem();
        Intent intent = getIntent();
        mData.setBankName(intent.getStringExtra("bank_name"));
////        mData.setFinancialSphere(intent.getStringExtra("financial_sphere"));
////        mData.setRate((Float)intent.getFloatExtra("rate", 0.0f));
////        mData.setRegion(intent.getStringExtra("region"));
        mData.setProductName(intent.getStringExtra("product_name"));
//        mData.setTarget(intent.getStringExtra("target"));
//        mData.setReservingmethod(intent.getStringExtra("reserving_method"));
//        mData.setCalMethod(intent.getStringExtra("cal_method"));


        SearchData searchData = (SearchData)intent.getSerializableExtra("SEARCH_RECYCLER_ADAPTER");
        if(searchData == null)
        {
            Toast.makeText(getApplicationContext(), "intent error", Toast.LENGTH_LONG).show();
        }
        Toast.makeText(getApplicationContext(), mData.getBankName() +  mData.getProductName() , Toast.LENGTH_LONG).show();


        bank = (TextView) findViewById(R.id.bank_name_result_output);
        product = (TextView) findViewById(R.id.product_name_result_output);
        rate = (TextView) findViewById(R.id.rate_result_output);
        target = (TextView) findViewById(R.id.target_result_output);
        res_method = (TextView) findViewById(R.id.reserving_method_result_output);
        rec_amount = (TextView) findViewById(R.id.received_amount_result_output);
        interest = (TextView) findViewById(R.id.interest_result_output);
        etc = (TextView) findViewById(R.id.etc_result);
        btn_home = (Button) findViewById(R.id.btn_hompage);
    }
}
