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


public class MenuActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ImageButton button_mnpl=(ImageButton)findViewById(R.id.imagen_mnpl);
        button_mnpl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), getString(R.string.toast_clic), Toast.LENGTH_SHORT).show();
                //abrirImagenes1();
                abrirElegirLetraMnpl();
            }
        });

        ImageButton button_dts=(ImageButton)findViewById(R.id.imagen_dts);
        button_dts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), getString(R.string.toast_clic), Toast.LENGTH_SHORT).show();
                //abrirImagenes2();
                abrirElegirLetraDts();
            }
        });

        ImageButton button_todas=(ImageButton)findViewById(R.id.imagen_todas);
        button_todas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), getString(R.string.toast_clic), Toast.LENGTH_SHORT).show();
                abrirImagenes3();
            }
        });

        ImageView acercaDe =(ImageView)findViewById(R.id.moreInfo);
        acercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), getString(R.string.toast_clic), Toast.LENGTH_SHORT).show();
                abrirAcercaDe();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.exit_app) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }else{
            if (id == R.id.help) {
                abrirAyuda();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void abrirElegirLetraMnpl(){
        Intent intent = new Intent(this,ElegirLetraMnplActivity.class);
        startActivity(intent);
    }

    private void abrirElegirLetraDts(){
        Intent intent = new Intent(this,ElegirLetraDtsActivity.class);
        startActivity(intent);
    }

/*    private void abrirImagenes1(){
        Intent intent = new Intent(this,JuegoImagenesActivity.class);
        Bundle b = new Bundle();
        b.putString("letras", "mnpl");
        b.putString("letra", "m");
        b.putInt("ejercicio", 1);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void abrirImagenes2(){
        Intent intent = new Intent(this,JuegoImagenesActivity.class);
        Bundle b = new Bundle();
        b.putString("letras", "dts");
        b.putString("letra", "d");
        b.putInt("ejercicio", 1);
        intent.putExtras(b);
        startActivity(intent);
    }*/

    private void abrirImagenes3(){
        Bundle b = new Bundle();
        Random r1 = new Random(); Random r2 = new Random(); Random r3 = new Random();
        int ls = r1.nextInt(2); int l; int e;
        //Toast.makeText(getApplicationContext(), "letras:"+ls, Toast.LENGTH_SHORT).show();
        if(ls == 0){
            b.putString("letras", "mnpl");
            l = r2.nextInt(4);
            //Toast.makeText(getApplicationContext(), "letra:"+l, Toast.LENGTH_SHORT).show();
            switch(l){
                case 0: b.putString("letra", "m"); break;
                case 1: b.putString("letra", "n"); break;
                case 2: b.putString("letra", "p"); break;
                case 3: b.putString("letra", "l"); break;
            }
        }
        if(ls == 1){
            b.putString("letras", "dts");
            l = r2.nextInt(3);
            //Toast.makeText(getApplicationContext(), "letra:"+l, Toast.LENGTH_SHORT).show();
            switch(l){
                case 0: b.putString("letra", "d"); break;
                case 1: b.putString("letra", "t"); break;
                case 2: b.putString("letra", "s"); break;
            }
        }
        e = r3.nextInt(3);
        //Toast.makeText(getApplicationContext(), "ej:"+e, Toast.LENGTH_SHORT).show();
        Intent intent = null;
        switch(e){
            case 0: b.putInt("ejercicio", 1); intent = new Intent(this,JuegoImagenesActivity.class); break;
            case 1: b.putInt("ejercicio", 2); intent = new Intent(this,JuegoSeleccionLetraActivity.class);break;
            case 2: b.putInt("ejercicio", 3); intent = new Intent(this,JuegoDibujoActivity.class);break;
            default: b.putInt("ejercicio", 1); intent = new Intent(this,JuegoImagenesActivity.class);
        }
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

    private void abrirAcercaDe(){
        Intent intent = new Intent(this,AboutActivity.class);
        startActivity(intent);
    }

    /**
     * deshabilita el botón de volver
     */
    @Override
    public void onBackPressed() {
    }

}
