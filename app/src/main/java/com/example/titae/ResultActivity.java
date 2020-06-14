package com.example.titae;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG_RESULTS = "results";
    private static final String TAG_Bankname = "bankname";
    private static final String TAG_Productname = "productname";
    private static final String TAG_Rate = "rate";
    private static final String TAG_Target = "target";
    private static final String TAG_Reservingmethod = "reservingmethod";
    //private static final String TAG_Calmethod = "calmethod";
    //private static final String TAG_Description = "description";

    private TextView mTextViewResult;
    ArrayList<HashMap<String, String>> mArrayList;
    ListView mListViewList;
    String mJsonString;

    String searchKeyword1;
    String searchKeyword2;
    JSONArray products = null;

    TextView bank;
    TextView product;
    TextView rate;
    TextView target;
    TextView res_method;
    //TextView cal_method;
    TextView rec_amount;
    TextView interest;
    TextView etc;
    Button btn_home;

    SearchRecyclerItem mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mData = new SearchRecyclerItem();
        Intent intent = getIntent();
        mData = (SearchRecyclerItem) intent.getSerializableExtra("SEARCH_ITEM");

        mData.setBankName(intent.getStringExtra("bank_name"));
        mData.setProductName(intent.getStringExtra("product_name"));


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
        //cal_method = (TextView) findViewById(R.id.cal_method_result_output);
        rec_amount = (TextView) findViewById(R.id.received_amount_result_output);
        interest = (TextView) findViewById(R.id.interest_result_output);
        etc = (TextView) findViewById(R.id.etc_result);
        btn_home = (Button) findViewById(R.id.btn_hompage);
/*
        searchKeyword1 = mData.getBankName();
        searchKeyword2 = mData.getProductName();

        GetData task = new GetData();
        task.execute(searchKeyword1, searchKeyword2);

        mArrayList = new ArrayList<>();
*/
        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://obank.kbstar.com/quics?page=C020702&cc=b061761:b061770&isNew=N&prcode=DP000942"));
                startActivity(intent);
            }
        });
    }
/*
    private class GetData extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(ResultActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            //Log.d(TAG,"response - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String searchKeyword1 = params[0];
            String searchKeyword2 = params[1];

            String serverURL = "http://172.30.1.7/detail.php";
            String postParameters = "bankname=" + searchKeyword1 + "&productname=" + searchKeyword2;

            try {
                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.connect();


                OutputStream outputStream = httpURLConnection.getOutputStream();
                outputStream.write(postParameters.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();


                int responseStatusCode = httpURLConnection.getResponseCode();
                //Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                //Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }

    private void showResult(){
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_RESULTS);

            for(int i=0;i<jsonArray.length();i++) {

                //int i = 0;

                JSONObject item = jsonArray.getJSONObject(i);

                String bankname = item.getString(TAG_Bankname);
                String productname = item.getString(TAG_Productname);
                String rate = item.getString(TAG_Rate);
                String target = item.getString(TAG_Target);
                String reservingmethod = item.getString(TAG_Reservingmethod);
                //String calmethod = item.getString(TAG_Calmethod);
                //String description = item.getString(TAG_Description);

                HashMap<String, String> hashMap = new HashMap<>();

                hashMap.put(TAG_Bankname, bankname);
                hashMap.put(TAG_Productname, productname);
                hashMap.put(TAG_Rate, rate);
                hashMap.put(TAG_Target, target);
                hashMap.put(TAG_Reservingmethod, reservingmethod);
                //hashMap.put(TAG_Calmethod, calmethod);
                //hashMap.put(TAG_Description, description);

                mArrayList.add(hashMap);
            }

            ListAdapter adapter = new SimpleAdapter(
                    ResultActivity.this, mArrayList, R.layout.activity_result,
                    new String[]{TAG_Bankname,TAG_Productname, TAG_Rate,TAG_Target,TAG_Reservingmethod},
                    new int[]{R.id.bank_name_result_output, R.id.product_name_result_output, R.id.rate_result_output,
                            R.id.target_result_output, R.id.reserving_method_result_output}
            );

            mListViewList.setAdapter(adapter);

        } catch (JSONException e) {

            //Log.d(TAG, "showResult : ", e);
        }

    }
*/

}