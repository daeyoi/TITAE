package com.example.titae;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class SearchDepositActivity extends AppCompatActivity {
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
    private static final String TAG_Calmethod = "calmethod";
    String sql_msg ;
    JSONArray products = null;
    String mJsonString;

    Button ret_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_deposit);
        Log.d("main","start1");
        Intent intent = getIntent();
        SearchData searchData = (SearchData)intent.getSerializableExtra("SEARCH_DATA");
        if(searchData == null)
        {
            Toast.makeText(getApplicationContext(), "intent error", Toast.LENGTH_LONG).show();
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
        tv3.setText("적립방식: " + searchData.getCalmethod() + "  ");
        tv4.setText("지역: " + searchData.getRegion() + "  ");
        tv5.setText("적립금액: " + searchData.getAmount() + "  ");
        tv6.setText("저축기간: " + searchData.getPeriod() + "  ");



        mRecyclerView = findViewById(R.id.search_list) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.

        mAdapter = new SearchRecyclerAdapter(getApplicationContext(), mList) ;
        mRecyclerView.setAdapter(mAdapter) ;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;

        //임시
        //addItem("은행1","뫄뫄아이템1",0.01F);
        //addItem("은행2","뫄뫄2",0.2F);
        //addItem("은행3","뫄뫄아이템5",0.03F);
        //addItem("은행4","뫄뫄4",0.4F);
        Log.d("main","start");

        //리사이클러 뷰 클릭 이벤트 : 상세 페이지  이동


        if (searchData.getFinancialSphere().equals("all")){
            if (searchData.getTarget().equals("all")){
                if (searchData.getCalmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname";
                }
                else if (searchData.getCalmethod().equals("simple")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where calmethod=\"단리\"";
                }
                else if (searchData.getCalmethod().equals("compound")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where calmethod=\"복리\"";
                }
            }

            else if (searchData.getTarget().equals("common")){
                if (searchData.getCalmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where target=\"서민전용\"";
                }
                else if(searchData.getCalmethod().equals("simple")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where target=\"서민전용\"and calmethod=\"단리\"";
                }
                else if(searchData.getCalmethod().equals("compound")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where target=\"서민전용\"and calmethod=\"복리\"";
                }
            }

            else if (searchData.getTarget().equals("limit")){
                if (searchData.getCalmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where target=\"일부제한\"";
                }
                else if(searchData.getCalmethod().equals("simple")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where target=\"일부제한\"and calmethod=\"단리\"";
                }
                else if(searchData.getCalmethod().equals("compound")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where target=\"일부제한\"and calmethod=\"복리\"";
                }
            }
        }

        else if (searchData.getFinancialSphere().equals("first")){
            if (searchData.getTarget().equals("all")){
                if (searchData.getCalmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"";
                }
                else if (searchData.getCalmethod().equals("simple")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and calmethod=\"단리\"";
                }
                else if (searchData.getCalmethod().equals("compound")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and calmethod=\"복리\"";
                }
            }

            else if (searchData.getTarget().equals("common")){
                if (searchData.getCalmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"서민전용\"";
                }
                else if(searchData.getCalmethod().equals("simple")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"서민전용\"and calmethod=\"단리\"";
                }
                else if(searchData.getCalmethod().equals("compound")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"서민전용\"and calmethod=\"복리\"";
                }
            }

            else if (searchData.getTarget().equals("limit")){
                if (searchData.getCalmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"일부제한\"";
                }
                else if(searchData.getCalmethod().equals("simple")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"일부제한\"and calmethod=\"단리\"";
                }
                else if(searchData.getCalmethod().equals("compound")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"은행\"and target=\"일부제한\"and calmethod=\"복리\"";
                }
            }
        }

        else if (searchData.getFinancialSphere().equals("second")){
            if (searchData.getTarget().equals("all")){
                if (searchData.getCalmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"";
                }
                else if (searchData.getCalmethod().equals("simple")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\" and calmethod=\"단리\"";
                }
                else if (searchData.getCalmethod().equals("compound")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\" and calmethod=\"복리\"";
                }
            }

            else if (searchData.getTarget().equals("common")){
                if (searchData.getCalmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"";
                }
                else if(searchData.getCalmethod().equals("simple")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"and target=\"서민전용\"and calmethod=\"단리\"";
                }
                else if(searchData.getCalmethod().equals("compound")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"and target=\"서민전용\"and calmethod=\"복리\"";
                }
            }

            else if (searchData.getTarget().equals("limit")){
                if (searchData.getCalmethod().equals("all")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"and target=\"일부제한\"";
                }
                else if(searchData.getCalmethod().equals("simple")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"and target=\"일부제한\"and calmethod=\"단리\"";
                }
                else if(searchData.getCalmethod().equals("compound")){
                    sql_msg="sql_msg=select * from titae.deposit as s join titae.bank as b on s.bankname = b.bankname where financialsphere=\"저축은행\"and target=\"일부제한\"and calmethod=\"복리\"";
                }
            }
        }

        String myIP = "172.30.1.7";
        getData asyncgetData = new getData();
        asyncgetData.execute("http://"+myIP+"/getjson.php" ); //onPreExecute -> doInBackground -> onPostExecute 순으로 실행됨 본인 아이피주소 넣으면됨
        Log.d("main","start3");

        //검색화면으로 돌아가기
        ret_btn = (Button) findViewById(R.id.return_button);

        ret_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "button click (return Search2Act)", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SearchDepositActivity.this, Search2DepositActivity.class);

                startActivity(intent);
                finish();
            }
        });

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
                String calmethod = c.getString(TAG_Calmethod);
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



