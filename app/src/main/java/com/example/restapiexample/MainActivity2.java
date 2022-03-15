package com.example.restapiexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.VoiceInteractor;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    ArrayList list = new ArrayList<String>();
    ArrayList img_list = new ArrayList<String>();
    RecyclerView list_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String URL = "https://pokeapi.co/api/v2/pokemon/?limit=898";
        final JSONObject[] req_result = new JSONObject[1];

        list_view = findViewById(R.id.poke_list);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        list_view.setLayoutManager(linearLayoutManager);

        CustomAdapter customAdapter;
        customAdapter = new CustomAdapter(MainActivity2.this, list, img_list);
        list_view.setAdapter(customAdapter);




        TextView txtview;



        JsonObjectRequest objReq = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());

                        JSONArray array = null;
                        JSONArray array_2 = null;

                        try {
                            array = response.getJSONArray("results");
                            for (int i = 0; i < array.length(); i++) {


                                    list.add(array.getJSONObject(i).getString("name"));
                                                               }
                            customAdapter.notifyDataSetChanged();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //Log.e("Rest Response", list.get(1).toString() + "end");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());
                    }
                }
        );

        requestQueue.add(objReq);

        for(int i = 0; i < list.size(); i++){
            img_list.add("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/132.png");

        }

        customAdapter.notifyDataSetChanged();

        customAdapter.notifyDataSetChanged();

    }

    public class CustomAdapter extends RecyclerView.Adapter<com.example.restapiexample.MainActivity2.MyViewHolder>{
        Context context;
        ArrayList pok_list;
        ArrayList image;


        public CustomAdapter(Context context, ArrayList in_list, ArrayList in_list2) {
            this.context = context;
            this.pok_list = in_list;
            this.image = in_list2;
        }
        @NonNull
        @Override
        public MainActivity2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.ind_row, parent, false);
            com.example.restapiexample.MainActivity2.MyViewHolder vh = new com.example.restapiexample.MainActivity2.MyViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull MainActivity2.MyViewHolder holder, int position) {
                holder.txt.setText((CharSequence) pok_list.get(position).toString());
                int pos_2 = position + 1;
                new DownloadImageFromInternet((holder.pok_img)).execute("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/" + pos_2 + ".png");

        }

        @Override
        public int getItemCount() {
            return pok_list.size();
        }
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txt;
            ImageView pok_img;

        public MyViewHolder(@NonNull View itemView) {

            super(itemView);
            txt = (TextView) itemView.findViewById(R.id.pok_name);
            pok_img = (ImageView) itemView.findViewById(R.id.poke_image);

        }
    }

    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;
        public DownloadImageFromInternet(ImageView imageView) {
            this.imageView=imageView;
            Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...",Toast.LENGTH_SHORT).show();
        }
        protected Bitmap doInBackground(String... urls) {
            String imageURL=urls[0];
            Bitmap bimage=null;
            try {
                InputStream in=new java.net.URL(imageURL).openStream();
                bimage= BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }
        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}

