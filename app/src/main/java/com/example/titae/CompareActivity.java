package com.example.titae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CompareActivity extends AppCompatActivity implements OnItemClick {
    RecyclerView mRecyclerView = null ;
    CompareRecyclerAdapter mAdapter = null ;
    ArrayList<SearchRecyclerItem> mList = new ArrayList<SearchRecyclerItem>();

    //ArrayList<SearchRecyclerItem> mDeposits = new ArrayList<SearchRecyclerItem>(); //변경
    // ArrayList<SearchRecyclerItem> mSavings = new ArrayList<SearchRecyclerItem>();  // 변경

    private static final String TAG_RESULTS = "results";
    private static final String TAG_Bankname = "bankname";
    private static final String TAG_Productname = "productname";
    private static final String TAG_Rate = "rate";
    private static final String TAG_Description = "description";
    String sql_msg ;
    JSONArray products = null;
    String mJsonString;

    Button btn_6;
    Button btn_12;
    Button btn_24;
    Button btn_36;
    Button calc_btn;

    EditText amount;

    int period;
    int amount_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        Log.d("compare","start1");

        //정의
        btn_6 = (Button) findViewById(R.id.button_6);
        btn_12 = (Button) findViewById(R.id.button_12);
        btn_24 = (Button) findViewById(R.id.button_24);
        btn_36 = (Button) findViewById(R.id.button_36);
        calc_btn = (Button) findViewById(R.id.calc_btn);

        mRecyclerView = findViewById(R.id.compare_list) ;
        amount = (EditText) findViewById(R.id.amount);

         /*
        Initialize calc data
         */
        btn_6.setBackgroundColor(0x969797);
        period = 6;
        amount_data = 300000;
        amount.setText("300000");


        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.

        mAdapter = new  CompareRecyclerAdapter(getApplicationContext(), mList, this) ;
        mRecyclerView.setAdapter(mAdapter) ;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        //구분선 추가
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(mRecyclerView.getContext(),new LinearLayoutManager(this).getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);


        //swipe 구현
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

        addItem("임시1","임시 메뉴", 1.0F);
        addItem("임시2","임시 메뉴", 2.9F);
        addItem("임시3","임시 메뉴", 3.5F);
        addItem("임시4","임시 메뉴", 3.5F);
        addItem("임시5","임시 메뉴", 3.5F);
        addItem("임시6","임시 메뉴", 3.5F);
        addItem("임시7","임시 메뉴", 3.5F);

        Log.d("main","start");

//        sql_msg = "sql_msg=select * from titae.deposit"; //쿼리문 전달
//
//        String myIP = "192.168.0.8";
//        SearchActivity.getData asyncgetData = new SearchActivity.getData();
//        asyncgetData.execute("http://"+myIP+"/getjson.php" ); //onPreExecute -> doInBackground -> onPostExecute 순으로 실행됨 본인 아이피주소 넣으면됨
//        Log.d("main","start3");

        //금액 입력 실시간 처리
        amount.addTextChangedListener(textWatcher);

        //버튼 클릭
        CompareActivity.BtnOnClickListner btnOnClickListner = new CompareActivity.BtnOnClickListner();
        btn_6.setOnClickListener(btnOnClickListner);
        btn_12.setOnClickListener(btnOnClickListner);
        btn_24.setOnClickListener(btnOnClickListner);
        btn_36.setOnClickListener(btnOnClickListner);
        calc_btn.setOnClickListener(btnOnClickListner);
    }



    public void addItem(String bankName, String productName, float rate) {
        SearchRecyclerItem item = new SearchRecyclerItem();

        item.setBankName(bankName);
        item.setProductName(productName);
        item.setRate(rate);
        //item.setAmount(amount);

        mList.add(item);
    }

    protected void MakeItemList() {
        try {

            JSONObject jsonObject = new JSONObject(mJsonString);
            products = jsonObject.getJSONArray(TAG_RESULTS);

            for(int i = 0; i<products.length(); i++){

                JSONObject c = products.getJSONObject(i);
                String bankname = c.getString(TAG_Bankname);
                String productname = c.getString(TAG_Productname);
                float rate = Float.parseFloat(c.getString(TAG_Rate));
                String description = c.getString(TAG_Description);
                Log.d("makelist",description);
                addItem(bankname,productname,rate);

            }

            mAdapter.notifyDataSetChanged() ;
        }catch(Exception e)
        {
            Log.d("makelist error","error");
        }
    }


    private class getData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            mJsonString = result;
            MakeItemList();


        }

        @Override
        protected String doInBackground(String... params) {
            String url_server = params[0];
            BufferedReader br = null;

            Log.d("url",url_server);

            try {

                URL url = new URL(url_server);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                con.setRequestMethod("POST");
                con.setDoOutput(true); // 서버로 쓰기 모드 지정
                con.connect();


                //php서버로 데이터 전송
                OutputStream outs =con.getOutputStream();
                outs.write(sql_msg.getBytes("UTF-8"));
                outs.flush();
                outs.close();

                StringBuilder sb = new StringBuilder();
                br= new BufferedReader(new InputStreamReader(con.getInputStream()));
                String json;


                while ((json = br.readLine()) != null) {
                    sb.append(json + "\n");
                }



                Log.d("try","error3");
                return sb.toString().trim();
            } catch (Exception e) {
                Log.e("catch","error");
                return null;
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

                case R.id.calc_btn:
                    mAdapter.notifyItemRangeChanged(0,mAdapter.getItemCount(), "calc_amount");
                    break;
            }
        }
    }

    @Override
    public int getPeriod (){
        // value this data you receive when increment() / decrement() called
        return period;
    }
    @Override
    public int getAmount (){
        // value this data you receive when increment() / decrement() called
        return amount_data;
    }

    //recyclerview swipe 구현
    ItemTouchHelper.SimpleCallback simpleItemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            // remove item from adapter
            final int position = viewHolder.getAdapterPosition();
            mList.remove(position);
            mAdapter.notifyItemRemoved(position);
        }


        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

    };

}

