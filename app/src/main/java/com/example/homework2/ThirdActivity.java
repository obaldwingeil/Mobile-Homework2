package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ThirdActivity extends AppCompatActivity {

    private ArrayList<Beer> beerList;
    private RecyclerView recyclerView;

    private TextView textView_number;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        recyclerView = findViewById(R.id.recyclerView_beer);
        textView_number = findViewById(R.id.textView_number);
        beerList = new ArrayList<>();

        Intent intent = getIntent();
        try {
            JSONArray beerJSON = new JSONArray(intent.getStringExtra("data"));
            for(int i = 0; i < beerJSON.length(); i++){
                JSONObject beerObject = beerJSON.getJSONObject(i);
                Beer beer = new Beer(
                      beerObject.getString("name"),
                      beerObject.getString("image_url"),
                      beerObject.getString("description"),
                      beerObject.toString()
                );
                beerList.add(beer);
            }
            String found = "We found " + beerList.size() + " results";
            textView_number.setText(found);
            BeerAdapter adapter = new BeerAdapter(beerList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
