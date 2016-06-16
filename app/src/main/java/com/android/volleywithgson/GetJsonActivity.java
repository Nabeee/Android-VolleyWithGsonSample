package com.android.volleywithgson;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volleywithgson.model.JsonObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GetJsonActivity extends AppCompatActivity implements GetJsonHelper.ResponseCallback {
    private String tag, url;
    private GetJsonHelper getJsonHelper = new GetJsonHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tag = getIntent().getStringExtra("tag");
        url = getIntent().getStringExtra("url");

        if (isObjectJson()) {
            setContentView(R.layout.activity_get_json_object);
            getJsonHelper.getJsonObject(tag, url);
        } else {
            setContentView(R.layout.activity_get_json_array);
            getJsonHelper.getJsonArray(tag, url);
        }
    }

    // GetJsonHelper Callback
    @Override
    public void getResponse(String tag, String responseStr) {
        if (! responseStr.equals("Error")) {
            if (isObjectJson()) {
                Gson gson = new Gson();
                JsonObject jsonObject = gson.fromJson(responseStr, JsonObject.class);
                ((TextView)findViewById(R.id.name)).setText(jsonObject.name);
                ((TextView)findViewById(R.id.email)).setText(jsonObject.email);
                ((TextView)findViewById(R.id.phone_home)).setText(jsonObject.phone.home);
                ((TextView)findViewById(R.id.phone_mobile)).setText(jsonObject.phone.mobile);

            } else {
                Gson gson = new Gson();
                Type arrayListType = new TypeToken<ArrayList<JsonObject>>(){}.getType();
                ArrayList<JsonObject> jsonArray = gson.fromJson(responseStr, arrayListType);

                List<JsonObject> list = new ArrayList<JsonObject>();
                for (JsonObject jsonObject : jsonArray) {
                    list.add(jsonObject);
                }

                JsonArrListAdapter jsonArrListAdapter = new JsonArrListAdapter(getApplicationContext(), list);
                ((ListView)findViewById(R.id.list_view)).setAdapter(jsonArrListAdapter);
            }
        }

    }

    private Boolean isObjectJson() {
        return (tag.equals(Constant.JSON_OBJECT_TAG)) ? true : false;
    }

    class JsonArrListAdapter extends ArrayAdapter<JsonObject> {
        private class ViewHolder {
            TextView nameTextView;
            TextView emailTextView;
            TextView phoneHomeTextView;
            TextView phoneMobileTextView;
        }

        private LayoutInflater inflater;

        public JsonArrListAdapter(Context context, List<JsonObject> objects) {
            super(context, 0, objects);
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.list_item_json_array, parent, false);
                holder = new ViewHolder();
                holder.nameTextView = (TextView) convertView.findViewById(R.id.name);
                holder.emailTextView = (TextView) convertView.findViewById(R.id.email);
                holder.phoneHomeTextView = (TextView) convertView.findViewById(R.id.phone_home);
                holder.phoneMobileTextView = (TextView) convertView.findViewById(R.id.phone_mobile);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                final JsonObject data = getItem(position);
                final String name = data.name;
                final String email = data.email;
                final String phoneHome = data.phone.home;
                final String phoneMobile = data.phone.mobile;

                holder.nameTextView.setText(name);
                holder.emailTextView.setText(email);
                holder.phoneHomeTextView.setText(phoneHome);
                holder.phoneMobileTextView.setText(phoneMobile);
            } catch (Exception e) {
//			throw new RuntimeException(e); //FIXME
                e.printStackTrace();
            }

            return convertView;
        }
    }
}
