package com.example.peng.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mListView;
    private static String URL="http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView= (ListView) findViewById(R.id.lv_main);
        new NewsAsyncTask().execute(URL);
    }
    class NewsAsyncTask extends AsyncTask<String,Void,List<NewsBean>>{

        private List<NewsBean> getJsonData(String url){
            List<NewsBean> newsBeenList=new ArrayList<NewsBean>();
            try {
                String jsonString=readStream(new URL(url).openStream());
              //  Log.d("xys",jsonString);
                JSONObject jsonObject;
                NewsBean newsBean;
                jsonObject=new JSONObject(jsonString);
                JSONArray jsonArray=jsonObject.getJSONArray("data");
                for(int i=0;i<jsonArray.length();i++){
                    jsonObject=jsonArray.getJSONObject(i);
                    newsBean=new NewsBean();
                    newsBean.newsIconUrl=jsonObject.getString("picSmall");
                    newsBean.newsTitle=jsonObject.getString("name");
                    newsBean.newsContent=jsonObject.getString("description");
                    newsBeenList.add(newsBean);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return newsBeenList;
        }
        private String readStream(InputStream is){
            InputStreamReader isr;
            String result="";
            try {
                String line="";
                isr=new InputStreamReader(is,"utf-8");
                BufferedReader br=new BufferedReader(isr);
                while((line=br.readLine())!=null){
                    result+=line;
                }
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected List<NewsBean> doInBackground(String... strings) {
            return getJsonData(strings[0]);
        }

        @Override
        protected void onPostExecute(List<NewsBean> newsBeen) {
            super.onPostExecute(newsBeen);
            NewsAdapter adapter=new NewsAdapter(MainActivity.this,newsBeen);
            mListView.setAdapter(adapter);
        }
    }
}
