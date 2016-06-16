package com.android.volleywithgson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volleywithgson.model.JsonObject;
import com.google.gson.Gson;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((Button)findViewById(R.id.json_object)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GetJsonActivity.class);
                intent.putExtra("tag", Constant.JSON_OBJECT_TAG);
                intent.putExtra("url", Constant.JSON_OBJECT_URL);
                startActivity(intent);
            }
        });

        ((Button)findViewById(R.id.json_array)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GetJsonActivity.class);
                intent.putExtra("tag", Constant.JSON_ARRAY_TAG);
                intent.putExtra("url", Constant.JSON_ARRAY_URL);
                startActivity(intent);
            }
        });
    }
}
