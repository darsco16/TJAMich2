package com.darsco.tjamich;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class consulta_acdos extends AppCompatActivity {

    Button btnBuscarAcdos;
    EditText etExpediente;
    TextView txtExpediente;
    TextView txtArea;

    private ListView lvLista;
    private AdaptadorAcdos adaptadorAcdos;
    ListView listaAcuerdos;
    ProgressDialog pd;

    String resultadoTotalAcdos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_acdos);

        btnBuscarAcdos = (Button)findViewById(R.id.btnBuscarAcdos);
        etExpediente = (EditText)findViewById(R.id.etExpediente);
        txtArea = (TextView)findViewById(R.id.txtArea);
        txtExpediente = (TextView)findViewById(R.id.txtExpediente);
        listaAcuerdos = (ListView) findViewById(R.id.lvLista);

        btnBuscarAcdos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isOnline()) {
                    //String consulta = "http://tjamich.gob.mx/consultarAcdos.php?accion=acdos&expediente="+etExpediente.getText();
                    String consulta2 = "https://tjamich.gob.mx/consultarAcdos.php?accion=exp&expediente=" + etExpediente.getText();
                    String consulta3 = "https://tjamich.gob.mx/consultarAcdos.php?accion=area&expediente=" + etExpediente.getText();
                    EnviarRecibirExp(consulta2);
                    EnviarRecibirArea(consulta3);
                    EjecutarTodo();
                }
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(btnBuscarAcdos.getWindowToken(), 0);
                    etExpediente.setText("");

            }
        });
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnected()) {
            return true;
        }
        showAlertDialog(consulta_acdos.this, "Conexion a Internet",
                "Tu dispositivo no tiene conexión a internet, los acuerdos no se podrán visualizar.", false);
        return false;
    }

    public void showAlertDialog(Context context, String title, String message, Boolean status) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog.show();
    }
    public void EnviarRecibirArea(String URL){
        //Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarArea(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }
    public void CargarArea(JSONArray ja){
        ArrayList<String> lista = new ArrayList<>();
        try {
            lista.add(ja.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        txtArea.setText(lista.get(0));
    }

    public void EnviarRecibirExp(String URL){

        //Toast.makeText(getApplicationContext(), ""+URL, Toast.LENGTH_SHORT).show();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                response = response.replace("][",",");
                if (response.length()>0){
                    try {
                        JSONArray ja = new JSONArray(response);
                        Log.i("sizejson",""+ja.length());
                        CargarExpe(ja);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);
    }

    public void CargarExpe(JSONArray ja){
        ArrayList<String> lista = new ArrayList<>();
        try {
            lista.add(ja.getString(0));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        txtExpediente.setText(lista.get(0));
    }

    private String ConectarAcdos(){

        int respuesta = 0;
        StringBuilder stringBuilder = null;
        String linea = "";
        URL url = null;

        try{
            url = new URL("https://tjamich.gob.mx/consultarAcdos.php?accion=acdos&expediente="+etExpediente.getText());

            HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
            respuesta = conexion.getResponseCode();

            stringBuilder = new StringBuilder();

            if(respuesta == HttpURLConnection.HTTP_OK){

                InputStream inputStream = new BufferedInputStream(conexion.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                while((linea = bufferedReader.readLine()) != null){
                    stringBuilder.append(linea + "\n");
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return resultadoTotalAcdos = stringBuilder.toString();
    }

    public ArrayList<Entidad> ObtenerJson(String result){
        try{
            ArrayList<Entidad> acuerdos = new ArrayList<Entidad>();
            JSONArray jsonArray = new JSONArray(result);
            Entidad pb;

            for(int i = 0; i < jsonArray.length(); i++){
                pb = new Entidad();

                //pb.setCodigoproducto(jsonArray.getJSONObject(i).getInt("idproducto"));
                pb.setTitulo(jsonArray.getJSONObject(i).getString("FechaPublicacion"));
                pb.setContenido(jsonArray.getJSONObject(i).getString("ExtractoSinFormato"));
                //pb.setImfFoto(jsonArray.getJSONObject(i).getString("imagen2"));

                //Agregas a tu lista el nuevo objeto
                acuerdos.add(pb);

            }
            //Devuelves el listado
            return acuerdos;

        }catch (Exception e){
            e.printStackTrace();
        }

        return null;

    }

    protected class HiloCargarImagen extends AsyncTask<String, Void, Object> {

        @Override
        protected Object doInBackground(String... strings) {
            ConectarAcdos();
            return null;
        }

        protected void onPostExecute(Object result){
            pd.dismiss();

            ArrayList<Entidad> productoBean = ObtenerJson(resultadoTotalAcdos);
            if (productoBean != null){
                AdaptadorAcdos adaptadorAcdos = new AdaptadorAcdos(consulta_acdos.this, productoBean);
                listaAcuerdos.setAdapter(adaptadorAcdos);
            }

        }
    }

    private void EjecutarTodo(){
        pd = ProgressDialog.show(this, "Cargando acuerdos", "Espere por favor...", true, false);
        new consulta_acdos.HiloCargarImagen().execute();
    }
}
