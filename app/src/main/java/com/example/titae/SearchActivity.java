package com.example.titae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    RecyclerView mRecyclerView = null ;
    SearchRecyclerAdapter mAdapter = null ;
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

    Button tmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Log.d("main","start1");
        Intent intent = getIntent();
        SearchData searchData = (SearchData)intent.getSerializableExtra("SEARCH_DATA");
        if(searchData == null)
        {
            Toast.makeText(getApplicationContext(), "Tq", Toast.LENGTH_LONG).show();
        }

        //잘 넘어왔는지 확인용 (임시)
        TextView tv1 = (TextView)findViewById(R.id.tv1);
        TextView tv2 = (TextView)findViewById(R.id.tv2);
        TextView tv3 = (TextView)findViewById(R.id.tv3);
        TextView tv4 = (TextView)findViewById(R.id.tv4);
        TextView tv5 = (TextView)findViewById(R.id.tv5);
        TextView tv6 = (TextView)findViewById(R.id.tv6);

        tv1.setText("금융권역: " + searchData.getFinancialSphere() + "  ");
        tv2.setText("가입대상: " + searchData.getTarget() + "  ");
        tv3.setText("적립방식: " + searchData.getReservingmethod() + "  ");
        tv4.setText("지역: " + searchData.getRegion() + "  ");
        tv5.setText("적립금액: " + searchData.getAmount() + "  ");
        tv6.setText("저축기간: " + searchData.getPeriod() + "  ");

        //상세 페이지 넘어가는 버튼(임시)
        tmp = (Button)findViewById(R.id.tmp_result);
        tmp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "button click (to ResultAct)", Toast.LENGTH_LONG).show();
                Intent myintent = new Intent(SearchActivity.this, ResultActivity.class);
                startActivity(myintent);
            }
        });

        mRecyclerView = findViewById(R.id.search_list) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.

        mAdapter = new SearchRecyclerAdapter(mList) ;
        mRecyclerView.setAdapter(mAdapter) ;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        Log.d("main","start");


        if (searchData.getFinancialSphere().equals("all")){
            if (searchData.getTarget().equals("all")){
                if (searchData.getReservingmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname";
                }
                else if (searchData.getReservingmethod().equals("reg")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where reservingmethod=\"정액적립식\"";
                }
                else if (searchData.getReservingmethod().equals("rand")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where reservingmethod=\"자유적립식\"";
                }
            }

            else if (searchData.getTarget().equals("common")){
                if (searchData.getReservingmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where target=\"서민전용\"";
                }
                else if(searchData.getReservingmethod().equals("reg")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where target=\"서민전용\"and reservingmethod=\"정액적립식\"";
                }
                else if(searchData.getReservingmethod().equals("rand")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where target=\"서민전용\"and reservingmethod=\"자유적립식\"";
                }
            }

            else if (searchData.getTarget().equals("limit")){
                if (searchData.getReservingmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where target=\"일부제한\"";
                }
                else if(searchData.getReservingmethod().equals("reg")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where target=\"일부제한\"and reservingmethod=\"정액적립식\"";
                }
                else if(searchData.getReservingmethod().equals("rand")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where target=\"일부제한\"and reservingmethod=\"자유적립식\"";
                }
            }
        }

        else if (searchData.getFinancialSphere().equals("first")){
            if (searchData.getTarget().equals("all")){
                if (searchData.getReservingmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"";
                }
                else if (searchData.getReservingmethod().equals("reg")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and reservingmethod=\"정액적립식\"";
                }
                else if (searchData.getReservingmethod().equals("rand")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and reservingmethod=\"자유적립식\"";
                }
            }

            else if (searchData.getTarget().equals("common")){
                if (searchData.getReservingmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"서민전용\"";
                }
                else if(searchData.getReservingmethod().equals("reg")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"서민전용\"and reservingmethod=\"정액적립식\"";
                }
                else if(searchData.getReservingmethod().equals("rand")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"서민전용\"and reservingmethod=\"자유적립식\"";
                }
            }

            else if (searchData.getTarget().equals("limit")){
                if (searchData.getReservingmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"일부제한\"";
                }
                else if(searchData.getReservingmethod().equals("reg")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"일부제한\"and reservingmethod=\"정액적립식\"";
                }
                else if(searchData.getReservingmethod().equals("rand")){
                    sql_msg="sql_msg=select * from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"일부제한\"and reservingmethod=\"자유적립식\"";
                }
            }
        }

        else if (searchData.getFinancialSphere().equals("second")){
            if (searchData.getTarget().equals("all")){
                if (searchData.getReservingmethod().equals("all")){
                    sql_msg="sql_msg=select b.bankname,s.productname,s.rate from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"";
                }
                else if (searchData.getReservingmethod().equals("reg")){
                    sql_msg="sql_msg=select b.bankname,s.productname,s.rate from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\" and reservingmethod=\"정액적립식\"";
                }
                else if (searchData.getReservingmethod().equals("rand")){
                    sql_msg="sql_msg=select b.bankname,s.productname,s.rate from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\" and reservingmethod=\"자유적립식\"";
                }
            }

            else if (searchData.getTarget().equals("common")){
                if (searchData.getReservingmethod().equals("all")){
                    sql_msg="sql_msg=select b.bankname,s.productname,s.rate from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"and target=\"서민전용\"";
                }
                else if(searchData.getReservingmethod().equals("reg")){
                    sql_msg="sql_msg=select b.bankname,s.productname,s.rate from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"and target=\"서민전용\"and reservingmethod=\"정액적립식\"";
                }
                else if(searchData.getReservingmethod().equals("rand")){
                    sql_msg="sql_msg=select b.bankname,s.productname,s.rate from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"and target=\"서민전용\"and reservingmethod=\"자유적립식\"";
                }
            }

            else if (searchData.getTarget().equals("limit")){
                if (searchData.getReservingmethod().equals("all")){
                    sql_msg="sql_msg=select b.bankname,s.productname,s.rate from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"and target=\"일부제한\"";
                }
                else if(searchData.getReservingmethod().equals("reg")){
                    sql_msg="sql_msg=select b.bankname,s.productname,s.rate from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"and target=\"일부제한\"and reservingmethod=\"정액적립식\"";
                }
                else if(searchData.getReservingmethod().equals("rand")){
                    sql_msg="sql_msg=select b.bankname,s.productname,s.rate from titae.savings as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"and target=\"일부제한\"and reservingmethod=\"자유적립식\"";
                }
            }
        }

        String myIP = "172.30.1.53";
        getData asyncgetData = new getData();
        asyncgetData.execute("http://"+myIP+"/getjson.php" ); //onPreExecute -> doInBackground -> onPostExecute 순으로 실행됨 본인 아이피주소 넣으면됨
        Log.d("main","start3");
    }


    public void addItem(String bankName, String productName, float rate) {
        SearchRecyclerItem item = new SearchRecyclerItem();

        item.setBankName(bankName);
        item.setProductName(productName);
        item.setRate(rate);

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



