package com.example.titae;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class ResultActivity extends AppCompatActivity {

    TextView bank;
    TextView product;
    TextView rate;
    TextView target;
    TextView res_method;
    TextView rec_amount;
    TextView interest;
    TextView etc;
    Button btn_home;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

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
