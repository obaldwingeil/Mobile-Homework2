package com.example.homework2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity {

    private ConstraintLayout constraintLayout;
    private EditText editText_name;
    private EditText editText_from;
    private EditText editText_to;
    private Switch switch_high;
    private Button button_results;

    private String api_root = "https://api.punkapi.com/v2/beers";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        constraintLayout = findViewById(R.id.constraint_second);
        editText_name = findViewById(R.id.editText_name);
        editText_from = findViewById(R.id.editText_from);
        editText_to = findViewById(R.id.editText_to);
        switch_high = findViewById(R.id.switch_high);
        button_results = findViewById(R.id.button_results);

        button_results.setOnClickListener(v -> loadResults(v));
    }

    public void loadResults(View view){
        String api_call = api_root;
        boolean name = false;
        boolean high = false;
        Toast toast = Toast.makeText(this, "Incorrect Date Format", Toast.LENGTH_SHORT);

        if(!editText_name.getText().toString().equals("")){
            api_call += "?beer_name=" + editText_name.getText().toString();
            name = true;
        }
        if(switch_high.isChecked()){
            if(name){
                api_call += "&abv_gt=3.99";
            }
            else{
                api_call += "?abv_gt=3.99";
            }
            high = true;
        }

        String from = editText_from.getText().toString();
        String to = editText_to.getText().toString();
        Boolean ready = false;
        Log.d("from", from);
        Log.d("to", to);
        Boolean from_digits = true;
        Boolean to_digits = true;
        for(int i = 0; i < from.length(); i++){
            if(!Character.isDigit(from.charAt(i)) && i != 2){
                from_digits = false;
            }
        }
        for(int i = 0; i < to.length(); i++){
            if(!Character.isDigit(to.charAt(i)) && i != 2){
                to_digits = false;
            }
        }
        if(!from.equals("") && !to.equals("")){
            if(from.length() == 7 && to.length() == 7 && from.charAt(2) == '/' && to.charAt(2) == '/' && from_digits && to_digits){
                int from_month = Integer.parseInt(from.substring(0, 2));
                int from_year = Integer.parseInt(from.substring(3));
                int to_month = Integer.parseInt(to.substring(0,2));
                int to_year = Integer.parseInt(to.substring(3));
                if(from_month > 0 && from_month < 13 && to_month > 0 && to_month < 13 && (from_year < to_year || (from_year == to_year && from_month < to_month))){
                    if (high || name) {
                        api_call += "&brewed_after=" + from_month + "-" + from_year;
                    } else {
                        api_call += "?brewed_after=" + from_month + "-" + from_year;
                    }
                    api_call += "&brewed_before=" + to_month + "-" + to_year;
                    ready = true;
                }
                else {
                    toast.show();
                    Log.d("toast", "should have gone");
                }
            }
            else {
                toast.show();
                Log.d("toast", "should have gone");
            }
        }
        else if(!from.equals("")){
            if(from.length() == 7 && from.charAt(2) == '/' && from_digits) {
                Log.d("from", from);
                int from_month = Integer.parseInt(from.substring(0, 2));
                String month_str = "";
                if (from_month < 10){
                    month_str = "0" + from_month;
                }
                else{
                    month_str = from_month + "";
                }
                int from_year = Integer.parseInt(from.substring(3));
                if (from_month > 0 && from_month < 13) {
                    if (high || name) {
                        api_call += "&brewed_after=" + month_str + "-" + from_year;
                    } else {
                        api_call += "?brewed_after=" + month_str + "-" + from_year;
                    }
                    ready = true;
                }
                else {
                    toast.show();
                    Log.d("toast", "should have gone");
                }
            }
            else {
                toast.show();
                Log.d("toast", "should have gone");
            }
        }
        else if(!to.equals("")){
            if(to.length() == 7 && to.charAt(2) == '/' && to_digits) {
                int to_month = Integer.parseInt(to.substring(0, 2));
                String month_str = "";
                if (to_month < 10){
                    month_str = "0" + to_month;
                }
                else{
                    month_str = to_month + "";
                }
                int to_year = Integer.parseInt(to.substring(3));
                if (to_month > 0 && to_month < 13) {
                    if (high || name) {
                        api_call += "&brewed_before=" + month_str + "-" + to_year;
                    } else {
                        api_call += "?brewed_before=" + month_str + "-" + to_year;
                    }
                    ready = true;
                }
                else {
                    toast.show();
                    Log.d("toast", "should have gone");
                }
            }
            else {
                toast.show();
                Log.d("toast", "should have gone");
            }
        }
        else{
            ready = true;
        }
        if(ready){
            Log.d("ready", "good to go!");
            client.get(api_call, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("api", new String(responseBody));
                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    intent.putExtra("data", new String(responseBody));
                    startActivity(intent);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Log.e("api error", new String (responseBody));
                }
            });
        }
        Log.d("api", api_call);
    }
}
