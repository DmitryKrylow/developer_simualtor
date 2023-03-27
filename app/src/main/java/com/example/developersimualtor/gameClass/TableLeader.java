package com.example.developersimualtor.gameClass;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.developersimualtor.MainActivity;
import com.example.developersimualtor.R;
import com.example.developersimualtor.person.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class TableLeader extends ListActivity {

    List<User> arrayList_user = new ArrayList<>();
    private ArrayList<HashMap<String, Object>> arrayList_map = new ArrayList<>();
    private HashMap<String, Object> map = new HashMap<>();
    private SimpleAdapter adapter;
    ListView listView;
    Button back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboards);
        listView = findViewById(android.R.id.list);
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new PutUser().execute("");
        new ShowUser().execute("");
    }
    class PutUser extends AsyncTask<String, String, String> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            try {
                RequestBody body = new FormBody.Builder()
                        .add("nickname", MainActivity.getPlayer().getName())
                        .add("level", String.valueOf(MainActivity.getPlayer().getLvl()))
                        .add("email",FirebaseAuth.getInstance().getCurrentUser().getEmail())
                        .build();
                Request request = new Request.Builder()
                        .url("https://74973c01ce05.ngrok.io/putUser")
                        .post(body)
                        .build();
                Response response = client.newCall(request).execute();
            } catch (Exception e) {
                return e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }
    class ShowUser extends AsyncTask<String, String, String> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... params) {
            OkHttpClient client = new OkHttpClient();
            try {
                Request request = new Request.Builder()
                        .url("https://74973c01ce05.ngrok.io/getAllUser").build();
                Response response = client.newCall(request).execute();
                String json =  response.body().string();
                Gson gson = new Gson();
                arrayList_user = stringToArray(json, User[].class);
                int i = 1;
                for(User user : arrayList_user){
                    map = new HashMap<>();
                    if(user.email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                        map.put("nickname", String.format("Имя: %s (Вы), %d место", user.nickname,i));
                    }else {
                        map.put("nickname", String.format("Имя: %s, %d место", user.nickname,i));
                    }
                    i++;
                    map.put("level",String.format("Уровень: %d",user.level));
                    arrayList_map.add(map);
                }
                Log.d("user_array",arrayList_user.toString());

            } catch (Exception e) {
                Log.d("SHOW_ERR", e.getMessage());
            }
            return null;
        }

        public <T> List<T> stringToArray(String s, Class<T[]> clazz) {
            T[] arr = new Gson().fromJson(s, clazz);
            return Arrays.asList(new Gson().fromJson(s, clazz));
        }

        @Override
        protected void onPostExecute(String result) {
            adapter = new SimpleAdapter(TableLeader.this, arrayList_map, R.layout.maket,
                    new String[]{"nickname","level"},
                    new int[]{android.R.id.text1,android.R.id.text2});
            listView.setAdapter(adapter);
            super.onPostExecute(result);
        }
    }


}
