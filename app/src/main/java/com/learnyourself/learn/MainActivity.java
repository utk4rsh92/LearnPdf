package com.learnyourself.learn;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
  private RecyclerView recyclerView;
  private LearnAdapter learnAdapter;
  private RecyclerView.LayoutManager layoutManager;
  private ProgressDialog progressDialog;
    private List<LearnData> learnData;
    private RequestQueue mQueue;
    ArrayList<String> file_name ;

    TextView tvname;
    TextView tvurl;
    int counter = 0;
    String  st_status ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdff);
        file_name = new ArrayList<>();

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mQueue = Volley.newRequestQueue(this);
        learnData = new ArrayList<>();
        redfile();
    }

    private void jsonParse() {

        String url = "https://api.myjson.com/bins/a5dyk";
        //        //https://api.myjson.com/bins/131j6c

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("country");
                            for (int i = 0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                //https://www.nhc.noaa.gov/pdf/84velden.pdf
                                String st_url = jsonObject.getString("url");
                                String[] splited = st_url.split("/pdf/");
                                String st_new = splited[1];
//                                st_new= st_new.replaceAll(".pdf","");
                                Toast.makeText(MainActivity.this, ""+st_new, Toast.LENGTH_SHORT).show();

                                if(file_name.contains(st_new)){
                                    st_status = "1";
                                }else {
                                    st_status = "0";

                                }
                                LearnData learata = new LearnData(jsonObject.getString("name"),jsonObject.getString("url"),st_status,st_new);
                                learnData.add(learata);

                            }
                            LearnAdapter vegAdapter = new LearnAdapter(learnData,MainActivity.this);
                            recyclerView.setAdapter(vegAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );

        mQueue.add(request);

    }
    public void redfile(){
        //String yourFilePath = context.getFilesDir() + "/" + "hello.txt";
        try {
            String path = "/storage/emulated/0/pdf";
            Log.d("Files", "Path: " + path);
            File directory = new File(path);
            File[] files = directory.listFiles();
            Log.d("Files", "Size: " + files.length);
            for (int i = 0; i < files.length; i++) {
                Log.d("Files", "FileName:" + files[i].getName());
                file_name.add(files[i].getName()/*+".pdf"*/);
            }
            Toast.makeText(this, "" + file_name.toString(), Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.getMessage();
        }

        jsonParse();
    }

}
