package com.example.titae;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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
    JSONArray products = null;
    String mJsonString;


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

        TextView tv1 = (TextView)findViewById(R.id.tv1);
        TextView tv2 = (TextView)findViewById(R.id.tv2);
        TextView tv3 = (TextView)findViewById(R.id.tv3);
        TextView tv4 = (TextView)findViewById(R.id.tv4);
        TextView tv5 = (TextView)findViewById(R.id.tv5);
        TextView tv6 = (TextView)findViewById(R.id.tv6);

        tv1.setText("금융권역: " + searchData.getFinancialSphere() + "  ");
        tv2.setText("가입대상: " + searchData.getTarget() + "  ");
        tv3.setText("적립방식: " + searchData.getCalMethod() + "  ");
        tv4.setText("지역: " + searchData.getRegion() + "  ");
        tv5.setText("적립금액: " + searchData.getAmount() + "  ");
        tv6.setText("저축기간: " + searchData.getPeriod() + "  ");

        mRecyclerView = findViewById(R.id.search_list) ;

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.

        mAdapter = new SearchRecyclerAdapter(mList) ;
        mRecyclerView.setAdapter(mAdapter) ;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)) ;



        Log.d("main","start");


        getData asyncgetData = new getData();
        asyncgetData.execute("http://192.168.0.8/getjson.php" ); //onPreExecute -> doInBackground -> onPostExecute 순으로 실행됨
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
            //Log.d("mjson",mJsonString);
            MakeItemList();


        }

        @Override
        protected String doInBackground(String... params) {
            String url_server = params[0];
            String sql ="select * from deposit,savings";
            BufferedReader br = null;

            Log.d("url",url_server);


            try {

                URL url = new URL(url_server);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();

                con.setRequestMethod("POST");
                Log.d("try","error1");
                StringBuilder sb = new StringBuilder();
                br= new BufferedReader(new InputStreamReader(con.getInputStream()));
                String json;
                Log.d("try","error2");
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



