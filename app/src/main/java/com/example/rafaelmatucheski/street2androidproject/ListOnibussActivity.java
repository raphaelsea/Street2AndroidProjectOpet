package com.example.rafaelmatucheski.street2androidproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ListOnibussActivity extends AppCompatActivity {

    private List<Onibus> onibuses;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_onibuss);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent cadastroOnibus = new Intent(ListOnibussActivity.this, InsertOnibussActivity.class);
                startActivity(cadastroOnibus);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        loadOnibus();
    }

    public void loadOnibus() {
        if (isConnected())
            new ListOnibussActivity.DownloadFromMyAPI().execute();
        else
            Toast.makeText(this, "Verifique a sua conexão com a internet ...", Toast.LENGTH_SHORT).show();
    }

    private boolean isConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    private class DownloadFromMyAPI extends AsyncTask<Void, Void, String> {
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {

            progress = new ProgressDialog(ListOnibussActivity.this);
            progress.setMessage("Fazendo download dos dados ...");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setProgress(0);
            progress.show();

        }

        @Override
        protected String doInBackground(Void... params) {
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://street2.pe.hu/selectOnibus.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                int test = urlConnection.getResponseCode();

                String result = Util.webToString(urlConnection.getInputStream());

                return result;
            } catch (Exception e) {
                Log.e("Error", "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            List<Onibus> onibuscad = Util.convertJSONtoCadastroOnibus(s);
            if (onibuscad != null) {
                ArrayAdapter<Onibus> onibusAdapter = new OnibusAdapter(ListOnibussActivity.this, R.layout.onibus_item, onibuscad);
                ListView listaOnibus = (ListView) findViewById(R.id.listaOnibuss);
                listaOnibus.setAdapter(onibusAdapter);
            }
            progress.dismiss();
        }
    }
}