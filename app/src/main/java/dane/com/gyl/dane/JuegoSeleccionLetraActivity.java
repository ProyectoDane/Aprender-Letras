package dane.com.gyl.dane;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;


public class JuegoSeleccionLetraActivity extends ActionBarActivity {

    static String letras = null;
    static String letra_actual = null;
    static int ejercicio = 0;
    static String origen = null;
    static MediaPlayer mp2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_seleccion_letra);

        //Definición de variables.
        TextView t = null;
        HashMap<String, Object> pares = new HashMap<String, Object>();
        mp2 = MediaPlayer.create(this, R.raw.misc049);
        ImageView letraCorrecta=(ImageView)findViewById(R.id.letra_04);
        ImageView letra=(ImageView)findViewById(R.id.letra);
        TextView titulo = (TextView) findViewById(R.id.titulo);

        //Leer las dimensiones de la pantalla en uso
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels; //1920 en Samsung S4
        int screenWidth = metrics.widthPixels;
        View view = this.findViewById(android.R.id.content);

        ImageButton button = null;

        //Recuperar parámetros.
        Bundle b = getIntent().getExtras();
        letras = b.getString("letras");
        letra_actual = b.getString("letra");
        ejercicio = b.getInt("ejercicio");
        origen = b.getString("origen");

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

        titulo.setTextSize(Utilities.convertFromDp(40, view));
        titulo.setText(titulo.getText()/* + " " + letra_actual.toUpperCase()*/+ ".");

        //Redimensionar letras acorde a resolución.
        /*letra.getLayoutParams().width = (int) ((screenHeight/2 - 30) * .3);
        letra.getLayoutParams().height = (int) ((screenHeight/2 - 30) * .3);*/
        ImageView l = (ImageView) findViewById(R.id.letra_01);
        l.getLayoutParams().width = (int) (screenWidth / 2.5);
        l.getLayoutParams().height = (int) (screenWidth / 2.5);
        l = (ImageView) findViewById(R.id.letra_02);
        l.getLayoutParams().width = (int) (screenWidth / 2.5);
        l.getLayoutParams().height = (int) (screenWidth / 2.5);
        l = (ImageView) findViewById(R.id.letra_03);
        l.getLayoutParams().width = (int) (screenWidth / 2.5);
        l.getLayoutParams().height = (int) (screenWidth / 2.5);
        l = (ImageView) findViewById(R.id.letra_04);
        l.getLayoutParams().width = (int) (screenWidth / 2.5);
        l.getLayoutParams().height = (int) (screenWidth / 2.5);
        /*l = (ImageView) findViewById(R.id.letra_05);
        l.getLayoutParams().width = (int) (screenWidth / 3.5);
        l.getLayoutParams().height = (int) (screenWidth / 3.5);
        l = (ImageView) findViewById(R.id.letra_06);
        l.getLayoutParams().width = (int) (screenWidth / 3.5);
        l.getLayoutParams().height = (int) (screenWidth / 3.5);
        l = (ImageView) findViewById(R.id.letra_07);
        l.getLayoutParams().width = (int) (screenWidth / 3.5);
        l.getLayoutParams().height = (int) (screenWidth / 3.5);
        l = (ImageView) findViewById(R.id.letra_08);
        l.getLayoutParams().width = (int) (screenWidth / 3.5);
        l.getLayoutParams().height = (int) (screenWidth / 3.5);
        l = (ImageView) findViewById(R.id.letra_09);
        l.getLayoutParams().width = (int) (screenWidth / 3.5);
        l.getLayoutParams().height = (int) (screenWidth / 3.5);*/

        //Toast.makeText(getApplicationContext(), "Ejercicio:"+ejercicio, Toast.LENGTH_LONG).show();
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

        //Definir letra a mostrar.
        switch(letra_actual) {
            case("m"):
                letra.setImageResource(R.drawable.m_amarilla); break;
            case("n"):
                letra.setImageResource(R.drawable.n_amarilla); break;
            case("p"):
                letra.setImageResource(R.drawable.p_amarillo); break;
            case("l"):
                letra.setImageResource(R.drawable.l_amarilla); break;
            case("d"):
                letra.setImageResource(R.drawable.d_amarilla); break;
            case("t"):
                letra.setImageResource(R.drawable.t_amarilla); break;
            case("s"):
                letra.setImageResource(R.drawable.s_amarilla); break;
        }
        letra.getLayoutParams().width = (int) (screenWidth / 2.5);
        letra.getLayoutParams().height = (int) (screenWidth / 2.5);

        //Definir letras aleatoriamente sobre la pantalla.
        ArrayList<Integer> allNumbers = new ArrayList<Integer>();
        for(int i=1;i<=4;i++){
            allNumbers.add(i);
        }
        Collections.shuffle(allNumbers);
        ImageView opcion_letra = null;
        ImageView letra_correcta = null;
        for(int i=0;i<=3;i++) {
            switch (i){
                case(0): opcion_letra = (ImageView) findViewById(R.id.letra_01); break;
                case(1): opcion_letra = (ImageView) findViewById(R.id.letra_02); break;
                case(2): opcion_letra = (ImageView) findViewById(R.id.letra_03); break;
                case(3): opcion_letra = (ImageView) findViewById(R.id.letra_04); break;
            }
            switch (letra_actual) {
                case "m":
                    switch (allNumbers.get(i)) {
                        case 1: opcion_letra.setImageResource(R.drawable.letra_m_01); break;
                        case 2: opcion_letra.setImageResource(R.drawable.letra_m_02); break;
                        case 3: opcion_letra.setImageResource(R.drawable.letra_m_03); break;
                        case 4: opcion_letra.setImageResource(R.drawable.letra_m_model); letra_correcta = opcion_letra; break;
                    }
                    break;
                case "n":
                    switch (allNumbers.get(i)) {
                        case 1: opcion_letra.setImageResource(R.drawable.letra_n_01); break;
                        case 2: opcion_letra.setImageResource(R.drawable.letra_n_02); break;
                        case 3: opcion_letra.setImageResource(R.drawable.letra_n_03); break;
                        case 4: opcion_letra.setImageResource(R.drawable.letra_n_model); letra_correcta = opcion_letra; break;
                    }
                    break;
                case "p":
                    switch (allNumbers.get(i)) {
                        case 1: opcion_letra.setImageResource(R.drawable.letra_p_01); break;
                        case 2: opcion_letra.setImageResource(R.drawable.letra_p_02); break;
                        case 3: opcion_letra.setImageResource(R.drawable.letra_p_03); break;
                        case 4: opcion_letra.setImageResource(R.drawable.letra_p_model); letra_correcta = opcion_letra; break;
                    }
                    break;
                case "l":
                    switch (allNumbers.get(i)) {
                        case 1: opcion_letra.setImageResource(R.drawable.letra_l_01); break;
                        case 2: opcion_letra.setImageResource(R.drawable.letra_l_02); break;
                        case 3: opcion_letra.setImageResource(R.drawable.letra_l_03); break;
                        case 4: opcion_letra.setImageResource(R.drawable.letra_l_model); letra_correcta = opcion_letra; break;
                    }
                    break;
                case "d":
                    switch (allNumbers.get(i)) {
                        case 1: opcion_letra.setImageResource(R.drawable.letra_d_01); break;
                        case 2: opcion_letra.setImageResource(R.drawable.letra_d_02); break;
                        case 3: opcion_letra.setImageResource(R.drawable.letra_d_03); break;
                        case 4: opcion_letra.setImageResource(R.drawable.letra_d_model); letra_correcta = opcion_letra; break;
                    }
                    break;
                case "t":
                    switch (allNumbers.get(i)) {
                        case 1: opcion_letra.setImageResource(R.drawable.letra_t_01); break;
                        case 2: opcion_letra.setImageResource(R.drawable.letra_t_02); break;
                        case 3: opcion_letra.setImageResource(R.drawable.letra_t_03); break;
                        case 4: opcion_letra.setImageResource(R.drawable.letra_t_model); letra_correcta = opcion_letra; break;
                    }
                    break;
                case "s":
                    switch (allNumbers.get(i)) {
                        case 1: opcion_letra.setImageResource(R.drawable.letra_s_01); break;
                        case 2: opcion_letra.setImageResource(R.drawable.letra_s_02); break;
                        case 3: opcion_letra.setImageResource(R.drawable.letra_s_03); break;
                        case 4: opcion_letra.setImageResource(R.drawable.letra_s_model); letra_correcta = opcion_letra; break;
                    }
                    break;
            }
        }

        letra_correcta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "¡CORRECTO!", Toast.LENGTH_SHORT).show();
                mp2.setVolume((float) 1, (float) 1);
                mp2.start();
                letraEncontrada();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juego_seleccion_letra, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
/*        if (id == R.id.action_menu) {
            Intent intent = new Intent(this,MenuActivity.class);
            startActivity(intent);
            return true;
        }*/
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

    private void letraEncontrada(){
        //Toast.makeText(getApplicationContext(),"¡CORRECTO!", Toast.LENGTH_SHORT).show();
        ImageButton imageButton=(ImageButton)findViewById(R.id.botonAvanzar);
        imageButton.setVisibility(View.VISIBLE);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirProxima();
            }
        });
    }

    private void abrirProxima(){
        Intent intent = null;
        //if(origen==null){
            intent = new Intent(this,MenuActivity.class);
        //}else {
            Bundle b = new Bundle();
            //intent = new Intent(this,JuegoDibujoActivity.class);
            b.putString("letras", letras);
            b.putString("letra", letra_actual);
            ejercicio++;
            //b.putInt("ejercicio", ejercicio);
            intent.putExtras(b);
        //}
        startActivity(intent);
    }

/*    @Override
    public void onBackPressed() {
    }*/
}
