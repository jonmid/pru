package com.jonmid.tallerasynctask;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ProgressBar cargador;
    Button boton;
    TextView texto;
    List<Post> mysPost;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cargador = (ProgressBar) findViewById(R.id.cargador);
        boton = (Button) findViewById(R.id.boton);
        texto = (TextView) findViewById(R.id.texto);

        linearLayout = (LinearLayout) findViewById(R.id.linearlayout);
    }

    public Boolean isOnLine(){
        ConnectivityManager connec = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connec.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnectedOrConnecting()){
            return true;
        }else {
            return false;
        }
    }

    public void onClickButton(View view){
        if (isOnLine()){
            MyTask task = new MyTask();
            task.execute("https://jsonplaceholder.typicode.com/posts");
        }else {
            Toast.makeText(this, "Sin conexión", Toast.LENGTH_SHORT).show();
        }
    }

    public void cargarDatos(){
        if (mysPost != null){
            for (Post post:mysPost){

                TextView myItem = new TextView(this);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(0, 0, 0, 30);
                myItem.setLayoutParams(params);

                myItem.setBackgroundColor(Color.parseColor("#FF4081"));
                myItem.setTextColor(Color.WHITE);

                myItem.setText(post.getTitle());
                linearLayout.addView(myItem);

                //texto.append(post.getTitle()+"\n");
            }
        }
    }

    private class MyTask extends AsyncTask<String, String, String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cargador.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            String content = null;
            try {
                content = HttpManager.getData(params[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                mysPost = JsonParser.parse(s);
            } catch (Exception e) {
                e.printStackTrace();
            }
            cargarDatos();

            cargador.setVisibility(View.GONE);
        }
    }
}
