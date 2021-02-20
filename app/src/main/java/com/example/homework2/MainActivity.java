package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private Button button_getStarted;
    private ImageView imageView_beer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_getStarted = findViewById(R.id.button_getStarted);
        imageView_beer = findViewById(R.id.imageView_home);
        try {
            InputStream ims = getAssets().open("clip.png");
            Drawable d = Drawable.createFromStream(ims, null);
            imageView_beer.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }

        button_getStarted.setOnClickListener(v -> launchNextActivity(v));
    }

    public void launchNextActivity(View view){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }
}