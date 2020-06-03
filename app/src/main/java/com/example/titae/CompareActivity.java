package com.example.titae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class CompareActivity extends AppCompatActivity {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);
        Log.d("compare","start1");
//        Intent intent = getIntent();
//        SearchData searchData = (SearchData)intent.getSerializableExtra("SEARCH_DATA");
//        if(searchData == null)
//        {
//            Toast.makeText(getApplicationContext(), "Tq", Toast.LENGTH_LONG).show();
//        }

//        TextView tv1 = (TextView)findViewById(R.id.tv1);
//        TextView tv2 = (TextView)findViewById(R.id.tv2);
//        TextView tv3 = (TextView)findViewById(R.id.tv3);
//        TextView tv4 = (TextView)findViewById(R.id.tv4);
//        TextView tv5 = (TextView)findViewById(R.id.tv5);
//        TextView tv6 = (TextView)findViewById(R.id.tv6);
//
//        tv1.setText("금융권역: " + searchData.getFinancialSphere() + "  ");
//        tv2.setText("가입대상: " + searchData.getTarget() + "  ");
//        tv3.setText("적립방식: " + searchData.getCalMethod() + "  ");
//        tv4.setText("지역: " + searchData.getRegion() + "  ");
//        tv5.setText("적립금액: " + searchData.getAmount() + "  ");
//        tv6.setText("저축기간: " + searchData.getPeriod() + "  ");

        mRecyclerView = findViewById(R.id.compare_list) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.

        mAdapter = new  CompareRecyclerAdapter(mList) ;
        mRecyclerView.setAdapter(mAdapter) ;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        addItem("임시1","임시 메뉴", 2.9F);
        addItem("임시2","임시 메뉴", 2.9F);
        addItem("임시3","임시 메뉴", 2.9F);

        Log.d("main","start");
//        sql_msg = "sql_msg=select * from titae.deposit"; //쿼리문 전달
//
//        String myIP = "192.168.0.8";
//        SearchActivity.getData asyncgetData = new SearchActivity.getData();
//        asyncgetData.execute("http://"+myIP+"/getjson.php" ); //onPreExecute -> doInBackground -> onPostExecute 순으로 실행됨 본인 아이피주소 넣으면됨
//        Log.d("main","start3");
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

}

