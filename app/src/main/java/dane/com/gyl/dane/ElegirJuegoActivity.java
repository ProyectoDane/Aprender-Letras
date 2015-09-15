package dane.com.gyl.dane;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class ElegirJuegoActivity extends ActionBarActivity {

    static String letras = null;
    static String letra_actual = null;
    //static int ejercicio = 0;
    static String origen = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_juego);

        //Recuperar parámetros.
        Bundle b = getIntent().getExtras();
        letras = b.getString("letras");
        letra_actual = b.getString("letra");
        //ejercicio = b.getInt("ejercicio");
        origen = b.getString("origen");

        ImageButton boton_conciencia=(ImageButton)findViewById(R.id.boton_conciencia_fonologica);
        boton_conciencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), getString(R.string.toast_clic), Toast.LENGTH_SHORT).show();
                abrirJuegoImagenes();
            }
        });

        ImageButton boton_percepcion=(ImageButton)findViewById(R.id.boton_percepcion_visual);
        boton_percepcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), getString(R.string.toast_clic), Toast.LENGTH_SHORT).show();
                abrirJuegoSeleccion();
            }
        });

        ImageButton boton_grafo=(ImageButton)findViewById(R.id.boton_grafomotricidad);
        boton_grafo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), getString(R.string.toast_clic), Toast.LENGTH_SHORT).show();
                abrirJuegoDibujo();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_elegir_juego, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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

    private void abrirJuegoImagenes(){
        Intent intent = new Intent(this,JuegoImagenesActivity.class);
        Bundle b = new Bundle();
        b.putString("letras", letras);
        b.putString("letra", letra_actual);
        b.putInt("ejercicio", 1);
        b.putBoolean("patron1YaJugado", false);
        b.putBoolean("patron2YaJugado", false);
        b.putBoolean("patron3YaJugado", false);
        b.putBoolean("patron4YaJugado", false);
        b.putBoolean("patron5YaJugado", false);
        b.putString("origen", "todas");
        intent.putExtras(b);
        startActivity(intent);
    }

    private void abrirJuegoSeleccion(){
        Intent intent = new Intent(this,JuegoSeleccionLetraActivity.class);
        Bundle b = new Bundle();
        b.putString("letras", letras);
        b.putString("letra", letra_actual);
        b.putInt("ejercicio", 2);
        b.putString("origen", "todas");
        intent.putExtras(b);
        startActivity(intent);
    }

    private void abrirJuegoDibujo(){
        Intent intent = new Intent(this,JuegoDibujoActivity.class);
        Bundle b = new Bundle();
        b.putString("letras", letras);
        b.putString("letra", letra_actual);
        b.putInt("ejercicio", 3);
        b.putString("origen", "todas");
        intent.putExtras(b);
        startActivity(intent);
    }




    private void abrirAyuda(){
        Intent intent = new Intent(this,AyudaActivity.class);
        Bundle b = new Bundle();
        b.putString("tipo", "completa");
        intent.putExtras(b);
        startActivity(intent);
    }
}
