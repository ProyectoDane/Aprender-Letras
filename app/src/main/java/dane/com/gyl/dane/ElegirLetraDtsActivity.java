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

public class ElegirLetraDtsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegir_letra_dts);

        ImageButton button_m=(ImageButton)findViewById(R.id.imagen_d);
        button_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), getString(R.string.toast_clic), Toast.LENGTH_SHORT).show();
                abrirElegirJuegoD();
            }
        });

        ImageButton button_n=(ImageButton)findViewById(R.id.imagen_t);
        button_n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), getString(R.string.toast_clic), Toast.LENGTH_SHORT).show();
                abrirElegirJuegoT();
            }
        });

        ImageButton button_p=(ImageButton)findViewById(R.id.imagen_s);
        button_p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), getString(R.string.toast_clic), Toast.LENGTH_SHORT).show();
                abrirElegirJuegoS();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_elegir_letra_dts, menu);
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
        switch (id) {
            case R.id.action_menu:
                intent = new Intent(this, MenuActivity.class);
                startActivity(intent);
                break;
            case R.id.help:
                intent = new Intent(this, AyudaActivity.class);
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

    private void abrirElegirJuegoD(){
        Intent intent = new Intent(this,ElegirJuegoActivity.class);
        Bundle b = new Bundle();
        b.putString("letras", "dts");
        b.putString("letra", "d");
        //b.putInt("ejercicio", 1);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void abrirElegirJuegoT(){
        Intent intent = new Intent(this,ElegirJuegoActivity.class);
        Bundle b = new Bundle();
        b.putString("letras", "dts");
        b.putString("letra", "t");
        //b.putInt("ejercicio", 1);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void abrirElegirJuegoS(){
        Intent intent = new Intent(this,ElegirJuegoActivity.class);
        Bundle b = new Bundle();
        b.putString("letras", "dts");
        b.putString("letra", "s");
        //b.putInt("ejercicio", 1);
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
