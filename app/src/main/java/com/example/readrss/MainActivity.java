package com.example.readrss;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listViewtieude;
    ArrayList<String> arrtieude = new ArrayList<>();
    ArrayList<String> arrLink = new ArrayList<>();
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewtieude = findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter(MainActivity.this,android.R.layout.simple_list_item_1,arrtieude);
        listViewtieude.setAdapter(arrayAdapter);
        new readRSS().execute("https://vnexpress.net/rss/thoi-su.rss");
        listViewtieude.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,NewsActivity.class);
                intent.putExtra("linktintuc",arrLink.get(position));
                startActivity(intent);
            }
        });

    }
    public class readRSS extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL (strings[0]);
                URLConnection urlConnection = url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line ="";
                while ((line = bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                bufferedReader.close();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return stringBuilder.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            XMLDOMParser xmldomParser = new XMLDOMParser();

            Document document = xmldomParser.getDocument(s);         // chứa toàn bộ nội dung rss
            NodeList nodeList =document.getElementsByTagName("item");// lấy hết tin tức
            String tieudDe = "";
            for (int i=0;i<nodeList.getLength();i++){
                Element element = (Element) nodeList.item(i); // đọc tất cả phần tử con tron item
                tieudDe = xmldomParser.getValue(element,"title");
                arrtieude.add(tieudDe);
                arrLink.add(xmldomParser.getValue(element,"link"));

            }
            arrayAdapter.notifyDataSetChanged();

            Toast.makeText(MainActivity.this,"Item "+nodeList.getLength(), Toast.LENGTH_SHORT).show();

        }
    }
}
