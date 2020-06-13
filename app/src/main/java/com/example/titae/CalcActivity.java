package com.example.titae;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CalcActivity extends AppCompatActivity {

    Button btn_6;
    Button btn_12;
    Button btn_24;
    Button btn_36;
    Button btn_sim;
    Button btn_com;
    Button calc_btn;

    EditText amount;
    EditText interest;
    int period;
    int calmethod;
    int amount_data;

    float interest_data;

    TextView rec_amount;
    TextView interest_result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);


        btn_6 = (Button) findViewById(R.id.button_6);
        btn_12 = (Button) findViewById(R.id.button_12);
        btn_24 = (Button) findViewById(R.id.button_24);
        btn_36 = (Button) findViewById(R.id.button_36);
        btn_sim = (Button) findViewById(R.id.btn_simple);
        btn_com = (Button) findViewById(R.id.btn_compound);
        calc_btn = (Button) findViewById(R.id.calc_btn);

        amount = (EditText) findViewById(R.id.amount);
        interest = (EditText) findViewById(R.id.interest);
        rec_amount = (TextView) findViewById(R.id.received_amount);
        interest_result = (TextView) findViewById(R.id.interest_result);

        /*
        Initialize calc data
         */
        btn_6.setBackgroundColor(0x969797);
        btn_sim.setBackgroundColor(0x969797);
        period = 6;
        calmethod = 0;
        amount_data = 0;
        interest_data = 0;

        //버튼 액션
        CalcActivity.BtnOnClickListner btnOnClickListner = new CalcActivity.BtnOnClickListner();
        btn_6.setOnClickListener(btnOnClickListner);
        btn_12.setOnClickListener(btnOnClickListner);
        btn_24.setOnClickListener(btnOnClickListner);
        btn_36.setOnClickListener(btnOnClickListner);
        btn_sim.setOnClickListener(btnOnClickListner);
        btn_com.setOnClickListener(btnOnClickListner);
        calc_btn.setOnClickListener(btnOnClickListner);

        //실시간 값 대입
        amount.addTextChangedListener(textWatcher);
        interest.addTextChangedListener(textWatcher_interest);

    }

    /*
       버튼 액션 처리 변수 값 알맞게 수정 추천
        */
    class BtnOnClickListner implements Button.OnClickListener {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_6:
                    period = 6;
                    btn_6.setBackgroundColor(0x969797);
                    btn_12.setBackgroundColor(0xd6d7d7);
                    btn_24.setBackgroundColor(0xd6d7d7);
                    btn_36.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.button_12:
                    period = 12;
                    btn_6.setBackgroundColor(0xd6d7d7);
                    btn_12.setBackgroundColor(0x969797);
                    btn_24.setBackgroundColor(0xd6d7d7);
                    btn_36.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.button_24:
                    period = 24;
                    btn_6.setBackgroundColor(0xd6d7d7);
                    btn_12.setBackgroundColor(0xd6d7d7);
                    btn_24.setBackgroundColor(0x969797);
                    btn_36.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.button_36:
                    period = 36;
                    btn_6.setBackgroundColor(0xd6d7d7);
                    btn_12.setBackgroundColor(0xd6d7d7);
                    btn_24.setBackgroundColor(0xd6d7d7);
                    btn_36.setBackgroundColor(0x969797);
                    break;
                case R.id.btn_simple:
                    calmethod = 0;
                    btn_sim.setBackgroundColor(0x969797);
                    btn_com.setBackgroundColor(0xd6d7d7);
                    break;
                case R.id.btn_compound:
                    calmethod = 1;
                    btn_sim.setBackgroundColor(0xd6d7d7);
                    btn_com.setBackgroundColor(0x969797);
                    break;

                case R.id.calc_btn:
                    if (calmethod == 0) {
                        Toast.makeText(getApplicationContext(), "눌리임" + interest_data + " " + amount_data + " " + period + " " + calmethod, Toast.LENGTH_LONG).show();
                        rec_amount.setText(String.valueOf((amount_data * interest_data * period) / 100 + (period * amount_data)));
                        interest_result.setText(String.valueOf((amount_data * interest_data * period)/ 100));
                    } else if (calmethod == 1) {
                        Toast.makeText(getApplicationContext(), "눌리임" + interest_data + " " + amount_data + " " + period + " " + calmethod, Toast.LENGTH_LONG).show();
                        rec_amount.setText(String.valueOf( Math.pow((amount_data * period) * (1 + interest_data), period / 12)));
                        interest_result.setText(String.valueOf(Math.pow((amount_data * period) * (1 + interest_data), period / 12) - (period * amount_data)));
                    }
                    break;
            }
        }
    }

    /*
   실시간 텍스트 입력 감지
    */
    TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            try {
                amount_data = Integer.parseInt("" + amount.getText());
            } catch (NumberFormatException nfe) {
            }
            Toast.makeText(getApplicationContext(), amount.getText().toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };


    TextWatcher textWatcher_interest = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            try {
                interest_data = Float.parseFloat("" + interest.getText());
            } catch (NumberFormatException nfe) {
            }
            Toast.makeText(getApplicationContext(), interest.getText().toString(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };
}
