package dane.com.gyl.dane;

import android.os.Vibrator;
import android.app.Activity;
import dane.com.gyl.dane.Utilities;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class JuegoImagenesActivity extends ActionBarActivity implements View.OnTouchListener{

    static TextView t;
    static HashMap<String, Object> pares = new HashMap<String, Object>();
    static MediaPlayer mp1 = null;
    static MediaPlayer mp2 = null;
    static String letras = null;
    static String letra_actual = null;
    static int ejercicio = 0;
    static String origen = null;
    static Boolean patron1YaJugado = false; //Para el juego de imágenes. Indica si las 1eras 3 imágenes ya fueron mostradas.
    static Boolean patron2YaJugado = false; //Para el juego de imágenes. Indica si las 2das 3 imágenes ya fueron mostradas.
    static Boolean patron3YaJugado = false; //Para el juego de imágenes. Indica si las 3eras 3 imágenes ya fueron mostradas.
    static Boolean patron4YaJugado = false; //Para el juego de imágenes. Indica si las 4tas 3 imágenes ya fueron mostradas.
    static Boolean patron5YaJugado = false; //Para el juego de imágenes. Indica si las 5tas 3 imágenes ya fueron mostradas.
    static ImageButton botonAvanzar = null;
    static ImageButton botonHome = null;
    static Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juego_imagenes);

        botonAvanzar = (ImageButton) findViewById(R.id.botonAvanzar);
        botonAvanzar.setVisibility(View.INVISIBLE);
        context = getApplicationContext();
        botonHome = (ImageButton) findViewById(R.id.botonHome);
        //Recuperar parámetros.
        Bundle b = getIntent().getExtras();
        letras = b.getString("letras");
        letra_actual = b.getString("letra");
        ejercicio = b.getInt("ejercicio");
        origen = b.getString("origen");
        patron1YaJugado = b.getBoolean("patron1YaJugado");
        patron2YaJugado = b.getBoolean("patron2YaJugado");
        patron3YaJugado = b.getBoolean("patron3YaJugado");
        patron4YaJugado = b.getBoolean("patron4YaJugado");
        patron5YaJugado = b.getBoolean("patron5YaJugado");
        //Leer las dimensiones de la pantalla en uso
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        int screenHeight = metrics.heightPixels; //1920 en Samsung S4
        int screenWidth = metrics.widthPixels;
        View view = this.findViewById(android.R.id.content);


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

        TextView titulo = (TextView) findViewById(R.id.titulo);
        //Iluminar letra y ejercicio en banner superior.
        switch(letra_actual){
            case "m": t = (TextView) findViewById(R.id.letra1); break;
            case "n": t = (TextView) findViewById(R.id.letra2); titulo.setText(R.string.descripcion_imagenes2); break;
            case "p": t = (TextView) findViewById(R.id.letra3); titulo.setText(R.string.descripcion_imagenes); break;
            case "l": t = (TextView) findViewById(R.id.letra4); titulo.setText(R.string.descripcion_imagenes2); break;
            case "d": t = (TextView) findViewById(R.id.letra1); titulo.setText(R.string.descripcion_imagenes); break;
            case "t": t = (TextView) findViewById(R.id.letra2); titulo.setText(R.string.descripcion_imagenes); break;
            case "s": t = (TextView) findViewById(R.id.letra3); titulo.setText(R.string.descripcion_imagenes); break;
        }
        t.setTextColor(Color.parseColor("#F3E624"));
        switch(ejercicio) {
            case 1: t = (TextView) findViewById(R.id.ejercicio_1); break;
            case 2: t = (TextView) findViewById(R.id.ejercicio_2); break;
            case 3: t = (TextView) findViewById(R.id.ejercicio_3); break;
        }
        //Toast.makeText(getApplicationContext(),"ejercicio: "+ejercicio+", letra: "+letra_actual, Toast.LENGTH_LONG).show();
        t.setTextColor(Color.parseColor("#F3E624"));

        //Definir sonidos.
        mp2 = MediaPlayer.create(this, R.raw.misc049);

        ImageView imagen1 = (ImageView) findViewById(R.id.imagen1);
        ImageView imagen2 = (ImageView) findViewById(R.id.imagen2);
        ImageView imagen3 = (ImageView) findViewById(R.id.imagen3);
        ImageView letra = (ImageView) findViewById(R.id.letra);


        ArrayList<Integer> correctImages = new ArrayList<Integer>();
        ArrayList<Integer> allImages = new ArrayList<Integer>();
        for(int i=1;i<=60;i++){
            allImages.add(i);
        }
        t = (TextView) findViewById(R.id.resultado);
        ArrayList<Integer> candidateImages = new ArrayList<Integer>();

        imagen1.getLayoutParams().width = screenWidth/2 - 30;
        imagen1.getLayoutParams().height = screenWidth/2 - 30;
        imagen2.getLayoutParams().width = screenWidth/2 - 30;
        imagen2.getLayoutParams().height = screenWidth/2 - 30;
        imagen3.getLayoutParams().width = screenWidth/2 - 30;
        imagen3.getLayoutParams().height = screenWidth/2 - 30;

        titulo.setTextSize(Utilities.convertFromDp(40, view));
        letra.getLayoutParams().height = (int) ((screenHeight/2 - 30) * .3);

        titulo.setText(titulo.getText() + " " + letra_actual.toUpperCase()+ ".");

        List<Integer> wrongOnes = new ArrayList<Integer>();
        wrongOnes = allImages;
        int correctOne = 0;
        Random aleatorio = new Random();
        int nroAleatorio = 0;
        wrongOnes = new ArrayList<Integer>();
        ArrayList patronesSinJugar = new ArrayList();
        if(!patron1YaJugado){ patronesSinJugar.add(0); }
        if(!patron2YaJugado){ patronesSinJugar.add(1); }
        if(!patron3YaJugado){ patronesSinJugar.add(2); }
        if(!patron4YaJugado){ patronesSinJugar.add(3); }
        if(!patron5YaJugado){ patronesSinJugar.add(4); }
        nroAleatorio = (int) patronesSinJugar.get(aleatorio.nextInt(patronesSinJugar.size()));
        switch(letra_actual) {
            case("m"):
                switch(nroAleatorio) {
                    case 1: correctImages.add(1); wrongOnes.add(2); wrongOnes.add(4); patron2YaJugado = true; break; //Moto - Calabaza - Puerta
                    case 2: correctImages.add(34); wrongOnes.add(22); wrongOnes.add(42); patron3YaJugado = true; break; //Muñeca - Jugo Naranja - Reloj
                    case 3: correctImages.add(28); wrongOnes.add(26); wrongOnes.add(23); patron4YaJugado = true; break; //Mariposa - Lapiz - Limón
                    case 4: correctImages.add(30); wrongOnes.add(21); wrongOnes.add(49); patron5YaJugado = true; break; //Mesa - Gato - Taza
                    case 0: correctImages.add(33); wrongOnes.add(52); wrongOnes.add(54); patron1YaJugado = true; break; //Mono - Tenedor - Toalla
                }
                letra.setImageResource(R.drawable.m_amarilla); break;
            case("n"):
                switch(nroAleatorio) {
                    case 1: correctImages.add(6); wrongOnes.add(3); wrongOnes.add(5); patron2YaJugado = true; break; //sartén – lapicera - fideos
                    case 2: correctImages.add(24); wrongOnes.add(66); wrongOnes.add(67); patron3YaJugado = true; break; //cuaderno – león – bebé de juguete
                    case 3: correctImages.add(68); wrongOnes.add(69); wrongOnes.add(70); patron4YaJugado = true; break; //víbora- camión - remera
                    case 4: correctImages.add(71); wrongOnes.add(72); wrongOnes.add(73); patron5YaJugado = true; break; //elefante – cinturón - jarra
                    case 0: correctImages.add(74); wrongOnes.add(75); wrongOnes.add(76); patron1YaJugado = true; break; //avión – ojota - vestido
                }
                letra.setImageResource(R.drawable.n_amarilla); break;
            case("p"):
                switch(nroAleatorio) {
                    case 1: correctImages.add(35); wrongOnes.add(31); wrongOnes.add(41); patron2YaJugado = true; break; //Pan - Microondas - Oso Peluche
                    case 2: correctImages.add(40); wrongOnes.add(16); wrongOnes.add(50); patron3YaJugado = true; break; //Pino - Galletas - Teléfono
                    case 3: correctImages.add(39); wrongOnes.add(58); wrongOnes.add(51); patron4YaJugado = true; break; //Pijama - Zapatilla - TV
                    case 4: correctImages.add(38); wrongOnes.add(12); wrongOnes.add(13); patron5YaJugado = true; break; //Perro - Campera - Guitarra
                    case 0: correctImages.add(37); wrongOnes.add(8); wrongOnes.add(9); patron1YaJugado = true; break; //Pelota - Flor - Banana
                }
                letra.setImageResource(R.drawable.p_amarillo); break;
            case("l"):
                switch(nroAleatorio) {
                    case 1: correctImages.add(32); wrongOnes.add(65); wrongOnes.add(10); patron2YaJugado = true; break; //Miel - Nube - Cuchara
                    case 2: correctImages.add(11); wrongOnes.add(36); wrongOnes.add(46); patron3YaJugado = true; break; //Caracol - Pato - Silla
                    case 3: correctImages.add(7); wrongOnes.add(27); wrongOnes.add(14); patron4YaJugado = true; break; //Arbol - Manzana - Auto
                    case 4: correctImages.add(62); wrongOnes.add(61); wrongOnes.add(63); patron5YaJugado = true; break; //Papel - Bicicleta - Jirafa
                    case 0: correctImages.add(47); wrongOnes.add(25); wrongOnes.add(64); patron1YaJugado = true; break; //Sol - Hipopótamo - Libro
                }
                letra.setImageResource(R.drawable.l_amarilla); break;
            case("d"):
                switch(nroAleatorio) {
                    case 1: correctImages.add(59); wrongOnes.add(77); wrongOnes.add(78); patron2YaJugado = true; break; //dados – crayón – chupete
                    case 2: correctImages.add(15); wrongOnes.add(79); wrongOnes.add(80); patron3YaJugado = true; break; //conejo – zanahoria - delantal
                    case 3: correctImages.add(19); wrongOnes.add(58); wrongOnes.add(26); patron4YaJugado = true; break; //dinosaurio – zapatillas - limón
                    case 4: correctImages.add(20); wrongOnes.add(81); wrongOnes.add(14); patron5YaJugado = true; break; //pantalón – durazno – auto
                    case 0: correctImages.add(18); wrongOnes.add(82); wrongOnes.add(83); patron1YaJugado = true; break; //frutilla – dientes - hamaca
                }
                letra.setImageResource(R.drawable.d_amarilla); break;
            case("t"):
                switch(nroAleatorio) {
                    case 1: correctImages.add(55); wrongOnes.add(28); wrongOnes.add(41); patron2YaJugado = true; break; //tobogán – mariposa – oso de peluche
                    case 2: correctImages.add(53); wrongOnes.add(84); wrongOnes.add(60); patron3YaJugado = true; break; //anteojos – tijera – pantuflas
                    case 3: correctImages.add(49); wrongOnes.add(27); wrongOnes.add(85); patron4YaJugado = true; break; //taza – manzana – bermuda
                    case 4: correctImages.add(86); wrongOnes.add(87); wrongOnes.add(23); patron5YaJugado = true; break; //tractor – pepino – lápiz
                    case 0: correctImages.add(56); wrongOnes.add(29); wrongOnes.add(17); patron1YaJugado = true; break; //cartera – tomate - delfín
                }
                letra.setImageResource(R.drawable.t_amarilla); break;
            case("s"):
                //nroAleatorio = aleatorio.nextInt(5);
                switch(nroAleatorio) {
                    case 1: correctImages.add(45); wrongOnes.add(88); wrongOnes.add(35); patron2YaJugado = true; break; //calesita -  sapo – pan
                    case 2: correctImages.add(44); wrongOnes.add(54); wrongOnes.add(8); patron3YaJugado = true; break; //sandía – toalla – flor
                    case 3: correctImages.add(43); wrongOnes.add(21); wrongOnes.add(30); patron4YaJugado = true; break; //sal (salero) – gato – mesa
                    case 4: correctImages.add(48); wrongOnes.add(89); wrongOnes.add(90); patron5YaJugado = true; break; //jabón – ballena - sopa
                    case 0: correctImages.add(46); wrongOnes.add(70); wrongOnes.add(57); patron1YaJugado = true; break; //remera – torta - silla
                }
                letra.setImageResource(R.drawable.s_amarilla); break;
        }
        //Definir una única imagen correcta.
        Collections.shuffle(correctImages);
        correctOne = correctImages.get(0);

        //Definir imágenes incorrectas.
        wrongOnes.removeAll(correctImages);
        Collections.shuffle(wrongOnes);

        //Definir imagenes candidatas a mostrarse.
        candidateImages.add(correctOne);
        for(int i=1;i<3;i++){
            candidateImages.add(wrongOnes.get(i-1));
        }
        Collections.shuffle(candidateImages);

        imagen1.setImageResource(traerImagen(candidateImages.get(0)));
        imagen1.setTag(candidateImages.get(0));
        if(candidateImages.get(0) == correctOne){
            pares.put(letra_actual, imagen1);
        }
        imagen2.setImageResource(traerImagen(candidateImages.get(1)));
        imagen2.setTag(candidateImages.get(1));
        if(candidateImages.get(1) == correctOne){
            pares.put(letra_actual, imagen2);
        }
        imagen3.setImageResource(traerImagen(candidateImages.get(2)));
        imagen3.setTag(candidateImages.get(2));
        if(candidateImages.get(2) == correctOne){
            pares.put(letra_actual, imagen3);
        }

        //Toast.makeText(getApplicationContext(),images.toString()+" "+images.get(1).toString(), Toast.LENGTH_LONG).show();

        //Listeners drag and drop.

        imagen1.setOnTouchListener((View.OnTouchListener) this);
        imagen2.setOnTouchListener((View.OnTouchListener) this);
        imagen3.setOnTouchListener((View.OnTouchListener) this);

        findViewById(R.id.letra).setOnDragListener(new MiDrag());

        botonAvanzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirProxima();
            }
        });

        botonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                origen = null;
                abrirProxima();
            }
        });
    }

    /*@Override
    public void onBackPressed() {
    }*/

    private int traerImagen(int n){
        switch(n){
            case 1: return R.drawable.moto; //M
            case 2: return R.drawable.zapallo; //M
            case 3: return R.drawable.lapicera; //N
            case 4: return R.drawable.puerta; //M
            case 5: return R.drawable.fideos; //N
            case 6: return R.drawable.sarten; //N
            case 7: return R.drawable.arbol; //L
            case 8: return R.drawable.flor; //P
            case 9: return R.drawable.banana; //P
            case 10: return R.drawable.cuchara; //L
            case 11: return R.drawable.caracol; //L
            case 12: return R.drawable.campera; //P
            case 13: return R.drawable.guitarra; //P
            case 14: return R.drawable.auto; //L
            case 15: return R.drawable.delantal; //D
            case 16: return R.drawable.galletas; //P
            case 17: return R.drawable.delfin; //T
            case 18: return R.drawable.dientes; //D
            case 19: return R.drawable.dinosaurio; //D
            case 20: return R.drawable.durazno; //D
            case 21: return R.drawable.gato; //M
            case 22: return R.drawable.jugo_de_naranja; //M
            case 23: return R.drawable.lapiz; //M
            case 24: return R.drawable.leon; //N
            case 25: return R.drawable.libro; //L
            case 26: return R.drawable.limon; //M
            case 27: return R.drawable.manzana; //L
            case 28: return R.drawable.mariposa; //M
            case 29: return R.drawable.cartera; //T
            case 30: return R.drawable.mesa; //M
            case 31: return R.drawable.microondas; //P
            case 32: return R.drawable.miel; //L
            case 33: return R.drawable.mono; //M
            case 34: return R.drawable.muneca; //M
            case 35: return R.drawable.pan; //S
            case 36: return R.drawable.pato; //L
            case 37: return R.drawable.pelota; //P
            case 38: return R.drawable.perro; //P
            case 39: return R.drawable.pijama; //P
            case 40: return R.drawable.pino; //P
            case 41: return R.drawable.oso_de_peluche; //P
            case 42: return R.drawable.reloj; //M
            case 43: return R.drawable.salero; //S
            case 44: return R.drawable.sandia; //S
            case 45: return R.drawable.sapo; //S
            case 46: return R.drawable.silla; //L
            case 47: return R.drawable.sol; //L
            case 48: return R.drawable.sopa; //S
            case 49: return R.drawable.taza; //M
            case 50: return R.drawable.telefono; //P
            case 51: return R.drawable.tv; //P
            case 52: return R.drawable.tenedor; //M
            case 53: return R.drawable.tijera; //T
            case 54: return R.drawable.toallas; //S
            case 55: return R.drawable.tobogan; //T
            case 56: return R.drawable.tomate;  //T
            case 57: return R.drawable.torta; //S
            case 58: return R.drawable.zapatillas; //P, D
            case 59: return R.drawable.dados; //D
            case 60: return R.drawable.pantuflas; //T
            case 61: return R.drawable.bicicleta; //L
            case 62: return R.drawable.papel; //L
            case 63: return R.drawable.jirafa; //L
            case 64: return R.drawable.hipopotamo; //L
            case 65: return R.drawable.nube; //L
            case 66: return R.drawable.cuaderno; //N
            case 67: return R.drawable.bebe_de_juguete; //N
            case 68: return R.drawable.camion; //N
            case 69: return R.drawable.vibora; //N
            case 70: return R.drawable.remera; //N
            case 71: return R.drawable.cinturon; //N
            case 72: return R.drawable.elefante; //N
            case 73: return R.drawable.jarra; //N
            case 74: return R.drawable.avion; //N
            case 75: return R.drawable.ojotas; //N
            case 76: return R.drawable.vestido; //N
            case 77: return R.drawable.crayon; //D
            case 78: return R.drawable.chupete; //D
            case 79: return R.drawable.conejo; //D
            case 80: return R.drawable.zanahoria; //D
            case 81: return R.drawable.pantalon; //D
            case 82: return R.drawable.frutillas; //D
            case 83: return R.drawable.hamaca; //D
            case 84: return R.drawable.anteojos; //T
            case 85: return R.drawable.bermuda; //T
            case 86: return R.drawable.tractor; //T
            case 87: return R.drawable.pepino; //T
            case 88: return R.drawable.calesita; //S
            case 89: return R.drawable.jabon; //S
            case 90: return R.drawable.ballena; //S
        }
        return R.drawable.calabaza;
    }

    private int traerSonido(int n){
        switch(n){
            case 1: return R.raw.sonido_moto;
            case 2: return R.raw.sonido_zapallo;
            case 3: return R.raw.sonido_lapicera;
            case 4: return R.raw.sonido_puerta;
            case 5: return R.raw.sonido_fideos;
            case 6: return R.raw.sonido_sarten;
            case 7: return R.raw.sonido_arbol;
            case 8: return R.raw.sonido_flor;
            case 9: return R.raw.sonido_banana;
            case 10: return R.raw.sonido_cuchara;
            case 11: return R.raw.sonido_caracol;
            case 12: return R.raw.sonido_campera;
            case 13: return R.raw.sonido_guitarra;
            case 14: return R.raw.sonido_auto;
            case 15: return R.raw.sonido_delantal;
            case 16: return R.raw.sonido_galletas;
            case 17: return R.raw.sonido_delfin;
            case 18: return R.raw.sonido_dientes;
            case 19: return R.raw.sonido_dinosaurio;
            case 20: return R.raw.sonido_durazno;
            case 21: return R.raw.sonido_gato;
            case 22: return R.raw.sonido_jugo_de_naranja;
            case 23: return R.raw.sonido_lapiz;
            case 24: return R.raw.sonido_leon;
            case 25: return R.raw.sonido_libro;
            case 26: return R.raw.sonido_limon;
            case 27: return R.raw.sonido_manzana;
            case 28: return R.raw.sonido_mariposa;
            case 29: return R.raw.sonido_cartera;
            case 30: return R.raw.sonido_mesa;
            case 31: return R.raw.sonido_microondas;
            case 32: return R.raw.sonido_miel;
            case 33: return R.raw.sonido_mono;
            case 34: return R.raw.sonido_muneca;
            case 35: return R.raw.sonido_pan;
            case 36: return R.raw.sonido_pato;
            case 37: return R.raw.sonido_pelota;
            case 38: return R.raw.sonido_perro;
            case 39: return R.raw.sonido_pijama;
            case 40: return R.raw.sonido_pino;
            case 41: return R.raw.sonido_oso_de_peluche;
            case 42: return R.raw.sonido_reloj;
            case 43: return R.raw.sonido_salero;
            case 44: return R.raw.sonido_sandia;
            case 45: return R.raw.sonido_sapo;
            case 46: return R.raw.sonido_silla;
            case 47: return R.raw.sonido_sol;
            case 48: return R.raw.sonido_sopa;
            case 49: return R.raw.sonido_taza;
            case 50: return R.raw.sonido_telefono;
            case 51: return R.raw.sonido_televisor;
            case 52: return R.raw.sonido_tenedor;
            case 53: return R.raw.sonido_tijera;
            case 54: return R.raw.sonido_toalla;
            case 55: return R.raw.sonido_tobogan;
            case 56: return R.raw.sonido_tomate;
            case 57: return R.raw.sonido_torta;
            case 58: return R.raw.sonido_zapatillas;
            case 59: return R.raw.sonido_dados;
            case 60: return R.raw.sonido_pantuflas;
            case 61: return R.raw.sonido_bicicleta;
            case 62: return R.raw.sonido_papel;
            case 63: return R.raw.sonido_jirafa;
            case 64: return R.raw.sonido_hipopotamo;
            case 65: return R.raw.sonido_nube;
            case 66: return R.raw.sonido_cuaderno;
            case 67: return R.raw.sonido_bebe_de_juguete;
            case 68: return R.raw.sonido_camion;
            case 69: return R.raw.sonido_vibora;
            case 70: return R.raw.sonido_remera;
            case 71: return R.raw.sonido_cinturon;
            case 72: return R.raw.sonido_elefante;
            case 73: return R.raw.sonido_jarra;
            case 74: return R.raw.sonido_avion;
            case 75: return R.raw.sonido_ojota;
            case 76: return R.raw.sonido_vestido;
            case 77: return R.raw.sonido_crayon;
            case 78: return R.raw.sonido_chupete;
            case 79: return R.raw.sonido_conejo;
            case 80: return R.raw.sonido_zanahoria;
            case 81: return R.raw.sonido_pantalon;
            case 82: return R.raw.sonido_frutilla;
            case 83: return R.raw.sonido_hamaca;
            case 84: return R.raw.sonido_anteojos;
            case 85: return R.raw.sonido_bermuda;
            case 86: return R.raw.sonido_tractor;
            case 87: return R.raw.sonido_pepino;
            case 88: return R.raw.sonido_calesita;
            case 89: return R.raw.sonido_jabon;
            case 90: return R.raw.sonido_ballena;
        }
        return 0;
    }
    public boolean onTouch(View objeto, MotionEvent evento){
        if(evento.getAction() == MotionEvent.ACTION_DOWN){
            JuegoImagenesActivity.t.setText("");
            ClipData datos = ClipData.newPlainText("","");
            View.DragShadowBuilder objeto_sombra = new View.DragShadowBuilder(objeto);
            objeto.startDrag(datos, objeto_sombra, objeto, 0);
            int imageIndex = (Integer) objeto_sombra.getView().getTag();
            if(traerSonido(imageIndex)>0){
                //Toast.makeText(getApplicationContext(),"on touch "+imageIndex+" "+objeto_sombra.getView().getTag(), Toast.LENGTH_LONG).show();
                //Anular sonidos previos.
                if (mp1!=null && mp1.isPlaying()) {
                    mp1.stop();
                    mp1.release();
                }
                //Definir sonido.
                mp1 = MediaPlayer.create(this, traerSonido(imageIndex));
                mp1.setVolume((float) 0.3, (float) 0.3);
                //mp1.start();
            }
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_juego_imagenes, menu);
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
        Bundle b = new Bundle();
        if(patron1YaJugado && patron2YaJugado && patron3YaJugado && patron4YaJugado && patron5YaJugado) {
            if(origen==null || origen.equals("")) {
                intent = new Intent(this,MenuActivity.class);
            } else{
                intent = new Intent(this, ElegirJuegoActivity.class);
            }
            b.putString("letras", letras);
            b.putString("letra", letra_actual);
            b.putString("origen", null);
            b.putInt("ejercicio", 0);
            intent.putExtras(b);
        }else{
            if(origen==null || origen.equals("")) {
                intent = new Intent(this,MenuActivity.class);
                ejercicio = 0; origen = null;
            } else {
                intent = new Intent(this, JuegoImagenesActivity.class);
                b.putBoolean("patron1YaJugado", patron1YaJugado);
                b.putBoolean("patron2YaJugado", patron2YaJugado);
                b.putBoolean("patron3YaJugado", patron3YaJugado);
                b.putBoolean("patron4YaJugado", patron4YaJugado);
                b.putBoolean("patron5YaJugado", patron5YaJugado);
                ejercicio++;
            }
            b.putString("origen", origen);
            b.putString("letras", letras);
            b.putString("letra", letra_actual);
            b.putInt("ejercicio", ejercicio);
            intent.putExtras(b);
        }
        startActivity(intent);
    }
}

final class MiDrag extends JuegoImagenesActivity implements View.OnDragListener{

    public boolean onDrag(View contenedor_nuevo, DragEvent evento){
        switch (evento.getAction()){
            case DragEvent.ACTION_DRAG_ENTERED:
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                break;
            case DragEvent.ACTION_DROP:
                View objeto = (View) evento.getLocalState();
                if(JuegoImagenesActivity.pares.containsValue(objeto)){
                    //Toast.makeText(context, "¡CORRECTO!", Toast.LENGTH_SHORT).show();
                    if (mp2.isPlaying()) {
                        mp2.stop();
                        mp2.release();
                    }
                    mp2.setVolume((float) 1, (float) 1);
                    //mp2.start();
                    objeto.setBackgroundResource(0);
                    objeto.setVisibility(View.INVISIBLE);
                    JuegoImagenesActivity.botonAvanzar.setVisibility(View.VISIBLE);
                }else{
                    Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                    if(v.hasVibrator()){
                        // Vibrar por 500 milisegundos
                        v.vibrate(500);
                    }
                }
                break;
            default:
                break;
        }
        return true;
    }

}

