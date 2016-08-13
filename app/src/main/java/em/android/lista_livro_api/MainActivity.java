package em.android.lista_livro_api;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private ListView lista;
    private EditText texto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        lista = (ListView) findViewById(R.id.lista);
        texto = (EditText) findViewById(R.id.editText);
    }

    public void buscar (View v){
        String filtro = texto.getText().toString();
        new TwitterTask().execute(filtro);

    }

    private class TwitterTask extends AsyncTask<String, Void, String[]>{

        ProgressDialog dialog;

        @Override
        protected void onPreExecute(String[] result) {
            if (result !=null){
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(),
                android.R.layout.simple_list_item_1, result);
                lista.setAdapter(adapter);
            }
            dialog.dismiss();

        }

        @Override
        protected String[] doInBackground(String... params) {
            try{
                String filtro = params[0];
                if (TextUtils.isEmpty(filtro)){
                    return null;
                }
                String urlTwitter="http://twitter.com/search.json?q=";
                String url = Uri.parse(urlTwitter + filtro).toString();
                String conteudo = HTTPUtils.acessar(url);

                // armazena os tweets em formato de array
                JSONObject jsonObject = new JSONObject(conteudo);
                JSONArray resultados = jsonObject.getJSONArray("results");

                //armazena dentro de uma array para passar para a listview

                String[] tweets = new String[resultados.length()];

                for(int i = 0; i < resultados.length(); i++){
                    JSONObject tweet = resultados.getJSONObject(i){
                        String texto = tweet.getString("text");
                        String usuario = tweet.getString("from_user");
                        tweets[i] = usuario + " - " + texto;
                    }
                    return tweets;
                }


            }catch (Exception e){
                throw new RuntimeException(e);
            }

            return new String[0];
        }
    }

}
