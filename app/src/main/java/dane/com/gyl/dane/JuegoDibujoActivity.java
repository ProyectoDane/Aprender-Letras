package dane.com.gyl.dane;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;


public class JuegoDibujoActivity extends ActionBarActivity {
    static String letras = null;
    static String letra_actual = null;
    static int ejercicio = 0;
    static String origen = null;
    static ImageButton button = null;
    static int fin = 0;
    static MediaPlayer mp = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView t = null;
        HashMap<String, Object> pares = new HashMap<String, Object>();
        mp = MediaPlayer.create(this, R.raw.misc049);
        fin = 0;
        //Recuperar parámetros.
        Bundle b = getIntent().getExtras();
        letras = b.getString("letras");
        letra_actual = b.getString("letra");
        ejercicio = b.getInt("ejercicio");
        origen = b.getString("origen");
        //Toast.makeText(getApplicationContext(),"letra:"+letra_actual+", ej:"+ejercicio, Toast.LENGTH_SHORT).show();
        setContentView(R.layout.activity_juego_dibujo);

        //Evaluar qué letras mostrar en el banner superior.
        if(letras.equals("dts")){
            t = (TextView) findViewById(R.id.letra1);
            t.setText("D");
            t = (TextView) findViewById(R.id.letra2);
            t.setText("T");
            t = (TextView) findViewById(R.id.letra3);
            t.setText("S");
            t = (TextView) findViewById(R.id.letra4);
            t.setVisibility(View.INVISIBLE);
        }
        //Toast.makeText(getApplicationContext(), "ejercicio: " + ejercicio + ", letra: " + letra_actual, Toast.LENGTH_LONG).show();
        //Iluminar letra y ejercicio en banner superior.
        switch(letra_actual){
            case "m": t = (TextView) findViewById(R.id.letra1); break;
            case "n": t = (TextView) findViewById(R.id.letra2); break;
            case "p": t = (TextView) findViewById(R.id.letra3); break;
            case "l": t = (TextView) findViewById(R.id.letra4); break;
            case "d": t = (TextView) findViewById(R.id.letra1); break;
            case "t": t = (TextView) findViewById(R.id.letra2); break;
            case "s": t = (TextView) findViewById(R.id.letra3); break;
        }
        t.setTextColor(Color.parseColor("#F3E624"));
        switch(ejercicio) {
            case 1: t = (TextView) findViewById(R.id.ejercicio_1); break;
            case 2: t = (TextView) findViewById(R.id.ejercicio_2); break;
            case 3: t = (TextView) findViewById(R.id.ejercicio_3); break;
        }
        t.setTextColor(Color.parseColor("#F3E624"));

        button=(ImageButton)findViewById(R.id.botonAvanzar);
        button.setVisibility(View.INVISIBLE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"click sobre la imagen", Toast.LENGTH_SHORT).show();
                abrirProxima();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juego_dibujo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        Intent intent = null;
        switch (id){
            case R.id.action_menu:
                intent = new Intent(this,MenuActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                intent = new Intent(this,AyudaActivity.class);
                startActivity(intent);
                break;
            case R.id.exit_app:
                intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void abrirProxima(){
        Intent intent = null;
        //if(origen!=null || letra_actual.equals("l") || letra_actual.equals("s")){
            intent = new Intent(this,MenuActivity.class);
        /*}else {
            intent = new Intent(this,JuegoImagenesActivity.class);
            //Próxima letra.
            switch(letra_actual){
                case "m": letra_actual = "n"; break;
                case "n": letra_actual = "p"; break;
                case "p": letra_actual = "l"; break;
                case "l": letra_actual = "m"; break;
                case "d": letra_actual = "t"; break;
                case "t": letra_actual = "s"; break;
                case "s": letra_actual = "d"; break;
            }*/
            Bundle b = new Bundle();
            b.putString("letras", letras);
            b.putString("letra", letra_actual);
            /*ejercicio=1;
            b.putInt("ejercicio", ejercicio);*/
            intent.putExtras(b);
        //}
        startActivity(intent);
    }

/*    @Override
    public void onBackPressed() {
    }*/
}
