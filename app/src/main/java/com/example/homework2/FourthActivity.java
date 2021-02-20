package com.example.homework2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FourthActivity extends AppCompatActivity {

    private TextView textView_resName;
    private TextView textView_abv;
    private TextView textView_resBrewed;
    private ImageView imageView_resBeer;
    private TextView textView_resDesc;
    private TextView textView_pairings;
    private TextView textView_tips;

    private JSONObject data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        textView_resName = findViewById(R.id.textView_resName);
        textView_abv = findViewById(R.id.textView_abv);
        textView_resBrewed = findViewById(R.id.textView_resBrewed);
        imageView_resBeer = findViewById(R.id.imageView_resBeer);
        textView_resDesc = findViewById(R.id.textView_resDesc);
        textView_pairings = findViewById(R.id.textView_pairings);
        textView_tips = findViewById(R.id.textView_tips);

        Intent intent = getIntent();
        try {
            data = new JSONObject(intent.getStringExtra("data"));
            textView_resName.setText(data.getString("name"));
            textView_abv.setText("ABV: " + data.getString("abv") + "%");
            textView_resBrewed.setText("First Brewed: " + data.getString("first_brewed"));
            Picasso.get().load(data.getString("image_url")).into(imageView_resBeer);
            textView_resDesc.setText("Description: " + data.getString("description"));
            JSONArray foodPairings = data.getJSONArray("food_pairing");
            String pairings = "Food Pairings: ";
            for(int i = 0; i < foodPairings.length(); i++){
                pairings += foodPairings.getString(i);
                if(i != foodPairings.length()-1){
                    pairings += ", ";
                }
            }
            textView_pairings.setText(pairings);
            textView_tips.setText("Brewer's Tips: " + data.getString("brewers_tips"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
