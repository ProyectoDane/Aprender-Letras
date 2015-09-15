package dane.com.gyl.dane;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by bpogorelc on 13/02/2015.
 */
public class PaintView extends View {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    //private HashMap paths = new HashMap();
    private List<Path> paths_m = new ArrayList<Path>();
    private List<Path> paths_n = new ArrayList<Path>();
    private List<Path> paths_p = new ArrayList<Path>();
    private List<Path> paths_l = new ArrayList<Path>();
    private List<Path> paths_d = new ArrayList<Path>();
    private List<Path> paths_t = new ArrayList<Path>();
    private List<Path> paths_s = new ArrayList<Path>();
    private Paint mPaint; private Paint lPaint;
    private static final int TOUCH_TOLERANCE_DP = 24;
    private static final int BACKGROUND = 0xff1c95c7;
    private ArrayList<ArrayList<Point>> points_m = new ArrayList<ArrayList<Point>>();
    private ArrayList<ArrayList<Point>> points_n = new ArrayList<ArrayList<Point>>();
    private ArrayList<ArrayList<Point>> points_p = new ArrayList<ArrayList<Point>>();
    private ArrayList<ArrayList<Point>> points_l = new ArrayList<ArrayList<Point>>();
    private ArrayList<ArrayList<Point>> points_d = new ArrayList<ArrayList<Point>>();
    private ArrayList<ArrayList<Point>> points_t = new ArrayList<ArrayList<Point>>();
    private ArrayList<ArrayList<Point>> points_s = new ArrayList<ArrayList<Point>>();
    private List<Integer> lastPointIndex_m = new ArrayList<Integer>();
    private List<Integer> lastPointIndex_n = new ArrayList<Integer>();
    private List<Integer> lastPointIndex_p = new ArrayList<Integer>();
    private List<Integer> lastPointIndex_l = new ArrayList<Integer>();
    private List<Integer> lastPointIndex_d = new ArrayList<Integer>();
    private List<Integer> lastPointIndex_t = new ArrayList<Integer>();
    private List<Integer> lastPointIndex_s = new ArrayList<Integer>();
    private int mTouchTolerance;
    private List<Boolean> isPathStarted_m = new ArrayList<Boolean>();
    private List<Boolean> isPathStarted_n = new ArrayList<Boolean>();
    private List<Boolean> isPathStarted_p = new ArrayList<Boolean>();
    private List<Boolean> isPathStarted_l = new ArrayList<Boolean>();
    private List<Boolean> isPathStarted_d = new ArrayList<Boolean>();
    private List<Boolean> isPathStarted_t = new ArrayList<Boolean>();
    private List<Boolean> isPathStarted_s = new ArrayList<Boolean>();

    private int screenWidth;
    private int screenHeight;
    private Point p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,p15,p16,p17,p18,p19,p20,p21,p22,p23,p24,p25,p26,p27,p28,p29,p30,p31,p32,p33;
    int x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12,x13,x14,x15,x16,x17,x18,x19,x20,x21,x22,x23,x24,x25,x26,x27,x28,x29;
    int y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,y11,y12,y13,y14,y15,y16,y17,y18,y19,y20,y21,y22,y23,y24,y25,y26,y27,y28,y29;

    public PaintView(Context context, AttributeSet attrs) {
        super(context, attrs);
        //Leer las dimensiones de la pantalla en uso
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        screenHeight = metrics.heightPixels;
        screenWidth = metrics.widthPixels;

        mCanvas = new Canvas();
        //Agregar cuatro trazos a la letra M.
        paths_m.add(new Path()); paths_m.add(new Path()); paths_m.add(new Path()); paths_m.add(new Path());
        //Agregar tres trazos a la letra N.
        paths_n.add(new Path()); paths_n.add(new Path()); paths_n.add(new Path());
        //Agregar dos trazos a la letra P.
        paths_p.add(new Path()); paths_p.add(new Path());
        //Agregar dos trazos a la letra L.
        paths_l.add(new Path()); paths_l.add(new Path());
        //Agregar dos trazos a la letra D.
        paths_d.add(new Path()); paths_d.add(new Path());
        //Agregar dos trazos a la letra T.
        paths_t.add(new Path()); paths_t.add(new Path());
        //Agregar un trazo a la letra S.
        paths_s.add(new Path());

        mPaint = new Paint();
        lPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.argb(255,241,229,43));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth((int)(screenWidth *.084));//85 12 por default
        mTouchTolerance = dp2px(TOUCH_TOLERANCE_DP);

        lPaint.setStrokeWidth(5);
        lPaint.setColor(Color.WHITE);
        lPaint.setAntiAlias(true);
        lPaint.setDither(true);
        lPaint.setStyle(Paint.Style.STROKE);
        lPaint.setStrokeJoin(Paint.Join.ROUND);
        lPaint.setStrokeCap(Paint.Cap.ROUND);

        //Inicializar índices de últimos puntos visitados.
        lastPointIndex_m.add(0); lastPointIndex_m.add(0); lastPointIndex_m.add(0); lastPointIndex_m.add(0);
        lastPointIndex_n.add(0); lastPointIndex_n.add(0); lastPointIndex_n.add(0);
        lastPointIndex_l.add(0); lastPointIndex_l.add(0);
        lastPointIndex_p.add(0); lastPointIndex_p.add(0);
        lastPointIndex_d.add(0); lastPointIndex_d.add(0);
        lastPointIndex_t.add(0); lastPointIndex_t.add(0);
        lastPointIndex_s.add(0);

        //Inicializar banderas con los trayectos iniciados.
        isPathStarted_m.add(false); isPathStarted_m.add(false); isPathStarted_m.add(false); isPathStarted_m.add(false);
        isPathStarted_n.add(false); isPathStarted_n.add(false); isPathStarted_n.add(false);
        isPathStarted_l.add(false); isPathStarted_l.add(false);
        isPathStarted_p.add(false); isPathStarted_p.add(false);
        isPathStarted_d.add(false); isPathStarted_d.add(false);
        isPathStarted_t.add(false); isPathStarted_t.add(false);
        isPathStarted_s.add(false);

        //Definir puntos en función de la letra actual.
        colocarPuntos();
    }


    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);
        clear();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawColor(BACKGROUND);
        canvas.drawBitmap(mBitmap, 0, 0, null);
        if(JuegoDibujoActivity.letra_actual.equals("m")) {
            //Dibujar los cuatro trazos de la M.
            canvas.drawPath(paths_m.get(0), mPaint);
            if (lastPointIndex_m.get(0) == 2) {
                canvas.drawPath(paths_m.get(1), mPaint);
            }
            if (lastPointIndex_m.get(1) == 2) {
                canvas.drawPath(paths_m.get(2), mPaint);
            }
            if (lastPointIndex_m.get(2) == 2) {
                canvas.drawPath(paths_m.get(3), mPaint);
            }
            //Dibujar los puntos de la M.
            for (Point point : points_m.get(0)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            for (Point point : points_m.get(1)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            for (Point point : points_m.get(2)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            for (Point point : points_m.get(3)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            //Si aún no se recorrieron puntos en M, mostrar primera flecha roja.
            if (lastPointIndex_m.get(0) != 2){
                colocarFlecha(mCanvas, "M", 1, Color.RED);
            }
        }
        if(JuegoDibujoActivity.letra_actual.equals("n")) {
            //Dibujar los tres trazos de la N.
            canvas.drawPath(paths_n.get(0), mPaint);
            if (lastPointIndex_n.get(0) == 2) {
                canvas.drawPath(paths_n.get(1), mPaint);
            }
            if (lastPointIndex_n.get(1) == 2) {
                canvas.drawPath(paths_n.get(2), mPaint);
            }
            //Dibujar los puntos de la N.
            for (Point point : points_n.get(0)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            for (Point point : points_n.get(1)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            for (Point point : points_n.get(2)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            //Si aún no se recorrieron puntos en N, mostrar primera flecha roja.
            if (lastPointIndex_n.get(0) != 2){
                colocarFlecha(mCanvas, "N", 1, Color.RED);
            }
        }
        if(JuegoDibujoActivity.letra_actual.equals("p")) {
            //Dibujar los dos trazos de la P.
            canvas.drawPath(paths_p.get(0), mPaint);
            if (lastPointIndex_p.get(0) == 2) {
                canvas.drawPath(paths_p.get(1), mPaint);
            }
            //Dibujar los puntos de la P.
            for (Point point : points_p.get(0)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            for (Point point : points_p.get(1)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            //Si aún no se recorrieron puntos en P, mostrar primera flecha roja.
            if (lastPointIndex_p.get(0) != 2){
                colocarFlecha(mCanvas, "P", 1, Color.RED);
            }
        }
        if(JuegoDibujoActivity.letra_actual.equals("l")) {
            //Dibujar los dos trazos de la L.
            canvas.drawPath(paths_l.get(0), mPaint);
            if (lastPointIndex_l.get(0) == 2) {
                canvas.drawPath(paths_l.get(1), mPaint);
            }
            //Dibujar los puntos de la L.
            for (Point point : points_l.get(0)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            for (Point point : points_l.get(1)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            //Si aún no se recorrieron puntos en L, mostrar primera flecha roja.
            if (lastPointIndex_l.get(0) != 2){
                colocarFlecha(mCanvas, "L", 1, Color.RED);
            }
        }
        if(JuegoDibujoActivity.letra_actual.equals("d")) {
            //Dibujar los dos trazos de la D.
            canvas.drawPath(paths_d.get(0), mPaint);
            if (lastPointIndex_d.get(0) == 2) {
                canvas.drawPath(paths_d.get(1), mPaint);
            }
            //Dibujar los puntos de la D.
            for (Point point : points_d.get(0)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            for (Point point : points_d.get(1)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            //Si aún no se recorrieron puntos en D, mostrar primera flecha roja.
            if (lastPointIndex_d.get(0) != 2){
                colocarFlecha(mCanvas, "D", 1, Color.RED);
            }
        }
        if(JuegoDibujoActivity.letra_actual.equals("t")) {
            //Dibujar los dos trazos de la T.
            canvas.drawPath(paths_t.get(0), mPaint);
            if (lastPointIndex_t.get(0) == 2) {
                canvas.drawPath(paths_t.get(1), mPaint);
            }
            //Dibujar los puntos de la T.
            for (Point point : points_t.get(0)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            for (Point point : points_t.get(1)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            //Si aún no se recorrieron puntos en T, mostrar primera flecha roja.
            if (lastPointIndex_t.get(0) != 2){
                colocarFlecha(mCanvas, "T", 1, Color.RED);
            }
        }
        if(JuegoDibujoActivity.letra_actual.equals("s")) {
            //Dibujar el único trazo de la S.
            canvas.drawPath(paths_s.get(0), mPaint);
            //Dibujar los puntos de la S.
            for (Point point : points_s.get(0)) {
                canvas.drawPoint(point.x, point.y, mPaint);
            }
            //Si aún no se recorrieron puntos en S, mostrar primera flecha roja.
            if (lastPointIndex_s.get(0) != 2){
                colocarFlecha(mCanvas, "S", 1, Color.RED);
            }
        }

        //Dibujar contornos de letras.
        crearContornos(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(JuegoDibujoActivity.letra_actual.equals("m")) {
                    if(lastPointIndex_m.get(2) == 2){
                        touch_start(x, y, 3, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        if(lastPointIndex_m.get(1) == 2){
                            touch_start(x, y, 2, JuegoDibujoActivity.letra_actual.charAt(0));
                        } else {
                            if(lastPointIndex_m.get(0) == 2){
                                touch_start(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                            } else {
                                touch_start(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                            }
                        }
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("n")) {
                    if(lastPointIndex_n.get(1) == 2){
                        touch_start(x, y, 2, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        if(lastPointIndex_n.get(0) == 2){
                            touch_start(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                        } else {
                            touch_start(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                        }
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("p")) {
                    if(lastPointIndex_p.get(0) == 2){
                        touch_start(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_start(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("l")) {
                    if(lastPointIndex_l.get(0) == 2){
                        touch_start(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_start(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("d")) {
                    if(lastPointIndex_d.get(0) == 2){
                        touch_start(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_start(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("t")) {
                    if(lastPointIndex_t.get(0) == 2){
                        touch_start(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_start(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("s")) {
                    touch_start(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                if(JuegoDibujoActivity.letra_actual.equals("m")) {
                    if(lastPointIndex_m.get(2) == 2){
                        touch_move(x, y, 3, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        if(lastPointIndex_m.get(1) == 2){
                            touch_move(x, y, 2, JuegoDibujoActivity.letra_actual.charAt(0));
                        } else {
                            if(lastPointIndex_m.get(0) == 2){
                                touch_move(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                            } else {
                                touch_move(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                            }
                        }
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("n")) {
                    if(lastPointIndex_n.get(1) == 2){
                        touch_move(x, y, 2, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        if(lastPointIndex_n.get(0) == 2){
                            touch_move(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                        } else {
                            touch_move(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                        }
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("p")) {
                    if(lastPointIndex_p.get(0) == 2){
                        touch_move(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_move(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("l")) {
                    if(lastPointIndex_l.get(0) == 2){
                        touch_move(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_move(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("d")) {
                    if(lastPointIndex_d.get(0) == 2){
                        touch_move(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_move(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("t")) {
                    if(lastPointIndex_t.get(0) == 2){
                        touch_move(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_move(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("s")) {
                    touch_move(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if(JuegoDibujoActivity.letra_actual.equals("m")) {
                    //Si se soltó el trazo en el 3° punto, mostrar en rojo flecha 4 y blanquear la 3.
                    if(lastPointIndex_m.get(2) == 2){
                        colocarFlecha(mCanvas, "M", 3, Color.WHITE);
                        colocarFlecha(mCanvas, "M", 4, Color.RED);
                        touch_up(x, y, 3, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {//Si se soltó el trazo en el 2° punto, mostrar en rojo flecha 3 y blanquear la 2.
                        if(lastPointIndex_m.get(1) == 2){
                            colocarFlecha(mCanvas, "M", 2, Color.WHITE);
                            colocarFlecha(mCanvas, "M", 3, Color.RED);
                            touch_up(x, y, 2, JuegoDibujoActivity.letra_actual.charAt(0));
                        } else {//Si se soltó el trazo en el 1° punto, mostrar en rojo flecha 2 y blanquear la 1.
                            if(lastPointIndex_m.get(0) == 2){
                                colocarFlecha(mCanvas, "M", 1, Color.WHITE);
                                colocarFlecha(mCanvas, "M", 2, Color.RED);
                                touch_up(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                            } else {
                                touch_up(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                            }
                        }
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("n")) {
                    if(lastPointIndex_n.get(1) == 2){
                        colocarFlecha(mCanvas, "N", 2, Color.WHITE);
                        colocarFlecha(mCanvas, "N", 3, Color.RED);
                        touch_up(x, y, 2, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        if(lastPointIndex_n.get(0) == 2){
                            colocarFlecha(mCanvas, "N", 1, Color.WHITE);
                            colocarFlecha(mCanvas, "N", 2, Color.RED);
                            touch_up(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                        } else {
                            touch_up(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                        }
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("p")) {
                    if(lastPointIndex_p.get(0) == 2){
                        colocarFlecha(mCanvas, "P", 1, Color.WHITE);
                        colocarFlecha(mCanvas, "P", 2, Color.RED);
                        touch_up(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_up(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("l")) {
                    if(lastPointIndex_l.get(0) == 2){
                        colocarFlecha(mCanvas, "L", 1, Color.WHITE);
                        colocarFlecha(mCanvas, "L", 2, Color.RED);
                        touch_up(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_up(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("d")) {
                    if(lastPointIndex_d.get(0) == 2){
                        colocarFlecha(mCanvas, "D", 1, Color.WHITE);
                        colocarFlecha(mCanvas, "D", 2, Color.RED);
                        touch_up(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_up(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("t")) {
                    if(lastPointIndex_t.get(0) == 2){
                        colocarFlecha(mCanvas, "T", 1, Color.WHITE);
                        colocarFlecha(mCanvas, "T", 2, Color.RED);
                        touch_up(x, y, 1, JuegoDibujoActivity.letra_actual.charAt(0));
                    } else {
                        touch_up(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                    }
                }
                if(JuegoDibujoActivity.letra_actual.equals("s")) {
                    touch_up(x, y, 0, JuegoDibujoActivity.letra_actual.charAt(0));
                }
                invalidate();
                break;
        }
        return true;
    }

    private void touch_start(float x, float y, int nroTrazo, char letra) {
        switch(letra){
            case 'm':
                if (checkPoint(x, y, lastPointIndex_m.get(nroTrazo), points_m.get(nroTrazo))) {
                    paths_m.get(nroTrazo).reset();
                    // El usuario comenzó desde el punto inicial del trayecto, por eso se permite el trazado de líneas.
                    if(JuegoDibujoActivity.fin == 0) {
                        isPathStarted_m.set(nroTrazo, true);
                    }
                } else {
                    //El usuario comenzó desde cualquier punto que no pertenece al conjunto del trayecto actual.
                    isPathStarted_m.set(nroTrazo, false);
                }
                break;
            case 'n':
                if (checkPoint(x, y, lastPointIndex_n.get(nroTrazo), points_n.get(nroTrazo))) {
                    paths_n.get(nroTrazo).reset();
                    // El usuario comenzó desde el punto inicial del trayecto, por eso se permite el trazado de líneas.
                    if(JuegoDibujoActivity.fin == 0) {
                        isPathStarted_n.set(nroTrazo, true);
                    }
                } else {
                    //El usuario comenzó desde cualquier punto que no pertenece al conjunto del trayecto actual.
                    isPathStarted_n.set(nroTrazo, false);
                }
                break;
            case 'p':
                if (checkPoint(x, y, lastPointIndex_p.get(nroTrazo), points_p.get(nroTrazo))) {
                    paths_p.get(nroTrazo).reset();
                    // El usuario comenzó desde el punto inicial del trayecto, por eso se permite el trazado de líneas.
                    if(JuegoDibujoActivity.fin == 0) {
                        isPathStarted_p.set(nroTrazo, true);
                    }
                } else {
                    //El usuario comenzó desde cualquier punto que no pertenece al conjunto del trayecto actual.
                    isPathStarted_p.set(nroTrazo, false);
                }
                break;
            case 'l':
                if (checkPoint(x, y, lastPointIndex_l.get(nroTrazo), points_l.get(nroTrazo))) {
                    paths_l.get(nroTrazo).reset();
                    // El usuario comenzó desde el punto inicial del trayecto, por eso se permite el trazado de líneas.
                    if(JuegoDibujoActivity.fin == 0) {
                        isPathStarted_l.set(nroTrazo, true);
                    }
                } else {
                    //El usuario comenzó desde cualquier punto que no pertenece al conjunto del trayecto actual.
                    isPathStarted_l.set(nroTrazo, false);
                }
                break;
            case 'd':
                if (checkPoint(x, y, lastPointIndex_d.get(nroTrazo), points_d.get(nroTrazo))) {
                    paths_d.get(nroTrazo).reset();
                    // El usuario comenzó desde el punto inicial del trayecto, por eso se permite el trazado de líneas.
                    if(JuegoDibujoActivity.fin == 0) {
                        isPathStarted_d.set(nroTrazo, true);
                    }
                } else {
                    //El usuario comenzó desde cualquier punto que no pertenece al conjunto del trayecto actual.
                    isPathStarted_d.set(nroTrazo, false);
                }
                break;
            case 't':
                if (checkPoint(x, y, lastPointIndex_t.get(nroTrazo), points_t.get(nroTrazo))) {
                    paths_t.get(nroTrazo).reset();
                    // El usuario comenzó desde el punto inicial del trayecto, por eso se permite el trazado de líneas.
                    if(JuegoDibujoActivity.fin == 0) {
                        isPathStarted_t.set(nroTrazo, true);
                    }
                } else {
                    //El usuario comenzó desde cualquier punto que no pertenece al conjunto del trayecto actual.
                    isPathStarted_t.set(nroTrazo, false);
                }
                break;
            case 's':
                if (checkPoint(x, y, lastPointIndex_s.get(nroTrazo), points_s.get(nroTrazo))) {
                    paths_s.get(nroTrazo).reset();
                    // El usuario comenzó desde el punto inicial del trayecto, por eso se permite el trazado de líneas.
                    if(JuegoDibujoActivity.fin == 0) {
                        isPathStarted_s.set(nroTrazo, true);
                    }
                } else {
                    //El usuario comenzó desde cualquier punto que no pertenece al conjunto del trayecto actual.
                    isPathStarted_s.set(nroTrazo, false);
                }
                break;
        }

    }

    /** draw line with finger move
     *
     * @param x the coordinate x of the point touched
     * @param y the coordinate y of the point touched
     */
    private void touch_move(float x, float y, int nroTrazo, char letra) {
        switch(letra){
            case 'm':
                if (isPathStarted_m.get(nroTrazo)) {
                    paths_m.get(nroTrazo).reset();
                    Point p = points_m.get(nroTrazo).get(lastPointIndex_m.get(nroTrazo));
                    paths_m.get(nroTrazo).moveTo(p.x, p.y);
                    if (checkPoint(x, y, lastPointIndex_m.get(nroTrazo) + 1, points_m.get(nroTrazo))) {
                        p = points_m.get(nroTrazo).get(lastPointIndex_m.get(nroTrazo) + 1);
                        paths_m.get(nroTrazo).lineTo(p.x, p.y);
                        mCanvas.drawPath(paths_m.get(nroTrazo), mPaint);
                        paths_m.get(nroTrazo).reset();
                        lastPointIndex_m.set(nroTrazo, lastPointIndex_m.get(nroTrazo) + 1);
                    } else {
                        paths_m.get(nroTrazo).lineTo(x, y);
                    }
                }
                break;
            case 'n':
                if (isPathStarted_n.get(nroTrazo)) {
                    paths_n.get(nroTrazo).reset();
                    Point p = points_n.get(nroTrazo).get(lastPointIndex_n.get(nroTrazo));
                    paths_n.get(nroTrazo).moveTo(p.x, p.y);
                    if (checkPoint(x, y, lastPointIndex_n.get(nroTrazo) + 1, points_n.get(nroTrazo))) {
                        p = points_n.get(nroTrazo).get(lastPointIndex_n.get(nroTrazo) + 1);
                        paths_n.get(nroTrazo).lineTo(p.x, p.y);
                        mCanvas.drawPath(paths_n.get(nroTrazo), mPaint);
                        paths_n.get(nroTrazo).reset();
                        lastPointIndex_n.set(nroTrazo, lastPointIndex_n.get(nroTrazo) + 1);
                    } else {
                        paths_n.get(nroTrazo).lineTo(x, y);
                    }
                }
                break;
            case 'p':
                if (isPathStarted_p.get(nroTrazo)) {
                    paths_p.get(nroTrazo).reset();
                    Point p = points_p.get(nroTrazo).get(lastPointIndex_p.get(nroTrazo));
                    paths_p.get(nroTrazo).moveTo(p.x, p.y);
                    if (checkPoint(x, y, lastPointIndex_p.get(nroTrazo) + 1, points_p.get(nroTrazo))) {
                        p = points_p.get(nroTrazo).get(lastPointIndex_p.get(nroTrazo) + 1);
                        paths_p.get(nroTrazo).lineTo(p.x, p.y);
                        mCanvas.drawPath(paths_p.get(nroTrazo), mPaint);
                        paths_p.get(nroTrazo).reset();
                        lastPointIndex_p.set(nroTrazo, lastPointIndex_p.get(nroTrazo) + 1);
                    } else {
                        paths_p.get(nroTrazo).lineTo(x, y);
                    }
                }
                break;
            case 'l':
                if (isPathStarted_l.get(nroTrazo)) {
                    paths_l.get(nroTrazo).reset();
                    Point p = points_l.get(nroTrazo).get(lastPointIndex_l.get(nroTrazo));
                    paths_l.get(nroTrazo).moveTo(p.x, p.y);
                    if (checkPoint(x, y, lastPointIndex_l.get(nroTrazo) + 1, points_l.get(nroTrazo))) {
                        p = points_l.get(nroTrazo).get(lastPointIndex_l.get(nroTrazo) + 1);
                        paths_l.get(nroTrazo).lineTo(p.x, p.y);
                        mCanvas.drawPath(paths_l.get(nroTrazo), mPaint);
                        paths_l.get(nroTrazo).reset();
                        lastPointIndex_l.set(nroTrazo, lastPointIndex_l.get(nroTrazo) + 1);
                    } else {
                        paths_l.get(nroTrazo).lineTo(x, y);
                    }
                }
                break;
            case 'd':
                if (isPathStarted_d.get(nroTrazo)) {
                    paths_d.get(nroTrazo).reset();
                    Point p = points_d.get(nroTrazo).get(lastPointIndex_d.get(nroTrazo));
                    paths_d.get(nroTrazo).moveTo(p.x, p.y);
                    if (checkPoint(x, y, lastPointIndex_d.get(nroTrazo) + 1, points_d.get(nroTrazo))) {
                        p = points_d.get(nroTrazo).get(lastPointIndex_d.get(nroTrazo) + 1);
                        paths_d.get(nroTrazo).lineTo(p.x, p.y);
                        mCanvas.drawPath(paths_d.get(nroTrazo), mPaint);
                        paths_d.get(nroTrazo).reset();
                        lastPointIndex_d.set(nroTrazo, lastPointIndex_d.get(nroTrazo) + 1);
                    } else {
                        paths_d.get(nroTrazo).lineTo(x, y);
                    }
                }
                break;
            case 't':
                if (isPathStarted_t.get(nroTrazo)) {
                    paths_t.get(nroTrazo).reset();
                    Point p = points_t.get(nroTrazo).get(lastPointIndex_t.get(nroTrazo));
                    paths_t.get(nroTrazo).moveTo(p.x, p.y);
                    if (checkPoint(x, y, lastPointIndex_t.get(nroTrazo) + 1, points_t.get(nroTrazo))) {
                        p = points_t.get(nroTrazo).get(lastPointIndex_t.get(nroTrazo) + 1);
                        paths_t.get(nroTrazo).lineTo(p.x, p.y);
                        mCanvas.drawPath(paths_t.get(nroTrazo), mPaint);
                        paths_t.get(nroTrazo).reset();
                        lastPointIndex_t.set(nroTrazo, lastPointIndex_t.get(nroTrazo) + 1);
                    } else {
                        paths_t.get(nroTrazo).lineTo(x, y);
                    }
                }
                break;
            case 's':
                if (isPathStarted_s.get(nroTrazo)) {
                    paths_s.get(nroTrazo).reset();
                    Point p = points_s.get(nroTrazo).get(lastPointIndex_s.get(nroTrazo));
                    paths_s.get(nroTrazo).moveTo(p.x, p.y);
                    if (checkPoint(x, y, lastPointIndex_s.get(nroTrazo) + 1, points_s.get(nroTrazo))) {
                        p = points_s.get(nroTrazo).get(lastPointIndex_s.get(nroTrazo) + 1);
                        paths_s.get(nroTrazo).lineTo(p.x, p.y);
                        mCanvas.drawPath(paths_s.get(nroTrazo), mPaint);
                        paths_s.get(nroTrazo).reset();
                        lastPointIndex_s.set(nroTrazo, lastPointIndex_s.get(nroTrazo) + 1);
                    } else {
                        paths_s.get(nroTrazo).lineTo(x, y);
                    }
                }
                break;
        }
    }

    /**
     * Draws line.
     */
    private void touch_up(float x, float y, int nroTrazo, char letra) {
        switch(letra) {
            case 'm':
                paths_m.get(nroTrazo).reset();
                if (checkPoint(x, y, lastPointIndex_m.get(nroTrazo) + 1, points_m.get(nroTrazo)) && isPathStarted_m.get(nroTrazo)) {
                    // move finished at valid point so draw whole line
                    // start point
                    Point p = points_m.get(nroTrazo).get(lastPointIndex_m.get(nroTrazo));
                    paths_m.get(nroTrazo).moveTo(p.x, p.y);
                    // end point
                    p = points_m.get(nroTrazo).get(lastPointIndex_m.get(nroTrazo) + 1);
                    paths_m.get(nroTrazo).lineTo(p.x, p.y);
                    mCanvas.drawPath(paths_m.get(nroTrazo), mPaint);
                    paths_m.get(nroTrazo).reset();
                    // increment point index
                    lastPointIndex_m.set(nroTrazo, lastPointIndex_m.get(nroTrazo) + 1);
                    isPathStarted_m.set(nroTrazo, false);
                }
                break;
            case 'n':
                paths_n.get(nroTrazo).reset();
                if (checkPoint(x, y, lastPointIndex_n.get(nroTrazo) + 1, points_n.get(nroTrazo)) && isPathStarted_n.get(nroTrazo)) {
                    // move finished at valid point so draw whole line
                    // start point
                    Point p = points_n.get(nroTrazo).get(lastPointIndex_n.get(nroTrazo));
                    paths_n.get(nroTrazo).moveTo(p.x, p.y);
                    // end point
                    p = points_n.get(nroTrazo).get(lastPointIndex_n.get(nroTrazo) + 1);
                    paths_n.get(nroTrazo).lineTo(p.x, p.y);
                    mCanvas.drawPath(paths_n.get(nroTrazo), mPaint);
                    paths_n.get(nroTrazo).reset();
                    // increment point index
                    lastPointIndex_n.set(nroTrazo, lastPointIndex_n.get(nroTrazo) + 1);
                    isPathStarted_n.set(nroTrazo, false);
                }
                break;
            case 'p':
                paths_p.get(nroTrazo).reset();
                if (checkPoint(x, y, lastPointIndex_p.get(nroTrazo) + 1, points_p.get(nroTrazo)) && isPathStarted_p.get(nroTrazo)) {
                    // move finished at valid point so draw whole line
                    // start point
                    Point p = points_p.get(nroTrazo).get(lastPointIndex_p.get(nroTrazo));
                    paths_p.get(nroTrazo).moveTo(p.x, p.y);
                    // end point
                    p = points_p.get(nroTrazo).get(lastPointIndex_p.get(nroTrazo) + 1);
                    paths_p.get(nroTrazo).lineTo(p.x, p.y);
                    mCanvas.drawPath(paths_p.get(nroTrazo), mPaint);
                    paths_p.get(nroTrazo).reset();
                    // increment point index
                    lastPointIndex_p.set(nroTrazo, lastPointIndex_p.get(nroTrazo) + 1);
                    isPathStarted_p.set(nroTrazo, false);
                }
                break;
            case 'l':
                paths_p.get(nroTrazo).reset();
                if (checkPoint(x, y, lastPointIndex_l.get(nroTrazo) + 1, points_l.get(nroTrazo)) && isPathStarted_l.get(nroTrazo)) {
                    // move finished at valid point so draw whole line
                    // start point
                    Point p = points_l.get(nroTrazo).get(lastPointIndex_l.get(nroTrazo));
                    paths_l.get(nroTrazo).moveTo(p.x, p.y);
                    // end point
                    p = points_l.get(nroTrazo).get(lastPointIndex_l.get(nroTrazo) + 1);
                    paths_l.get(nroTrazo).lineTo(p.x, p.y);
                    mCanvas.drawPath(paths_l.get(nroTrazo), mPaint);
                    paths_l.get(nroTrazo).reset();
                    // increment point index
                    lastPointIndex_l.set(nroTrazo, lastPointIndex_l.get(nroTrazo) + 1);
                    isPathStarted_l.set(nroTrazo, false);
                }
                break;
            case 'd':
                paths_d.get(nroTrazo).reset();
                if (checkPoint(x, y, lastPointIndex_d.get(nroTrazo) + 1, points_d.get(nroTrazo)) && isPathStarted_d.get(nroTrazo)) {
                    // move finished at valid point so draw whole line
                    // start point
                    Point p = points_d.get(nroTrazo).get(lastPointIndex_d.get(nroTrazo));
                    paths_d.get(nroTrazo).moveTo(p.x, p.y);
                    // end point
                    p = points_d.get(nroTrazo).get(lastPointIndex_d.get(nroTrazo) + 1);
                    paths_d.get(nroTrazo).lineTo(p.x, p.y);
                    mCanvas.drawPath(paths_d.get(nroTrazo), mPaint);
                    paths_d.get(nroTrazo).reset();
                    // increment point index
                    lastPointIndex_d.set(nroTrazo, lastPointIndex_d.get(nroTrazo) + 1);
                    isPathStarted_d.set(nroTrazo, false);
                }
                break;
            case 't':
                paths_t.get(nroTrazo).reset();
                if (checkPoint(x, y, lastPointIndex_t.get(nroTrazo) + 1, points_t.get(nroTrazo)) && isPathStarted_t.get(nroTrazo)) {
                    // move finished at valid point so draw whole line
                    // start point
                    Point p = points_t.get(nroTrazo).get(lastPointIndex_t.get(nroTrazo));
                    paths_t.get(nroTrazo).moveTo(p.x, p.y);
                    // end point
                    p = points_t.get(nroTrazo).get(lastPointIndex_t.get(nroTrazo) + 1);
                    paths_t.get(nroTrazo).lineTo(p.x, p.y);
                    mCanvas.drawPath(paths_t.get(nroTrazo), mPaint);
                    paths_t.get(nroTrazo).reset();
                    // increment point index
                    lastPointIndex_t.set(nroTrazo, lastPointIndex_t.get(nroTrazo) + 1);
                    isPathStarted_t.set(nroTrazo, false);
                }
                break;
            case 's':
                paths_s.get(nroTrazo).reset();
                if (checkPoint(x, y, lastPointIndex_s.get(nroTrazo) + 1, points_s.get(nroTrazo)) && isPathStarted_s.get(nroTrazo)) {
                    // move finished at valid point so draw whole line
                    // start point
                    Point p = points_s.get(nroTrazo).get(lastPointIndex_s.get(nroTrazo));
                    paths_s.get(nroTrazo).moveTo(p.x, p.y);
                    // end point
                    p = points_s.get(nroTrazo).get(lastPointIndex_s.get(nroTrazo) + 1);
                    paths_s.get(nroTrazo).lineTo(p.x, p.y);
                    mCanvas.drawPath(paths_s.get(nroTrazo), mPaint);
                    paths_s.get(nroTrazo).reset();
                    // increment point index
                    lastPointIndex_s.set(nroTrazo, lastPointIndex_s.get(nroTrazo) + 1);
                    isPathStarted_s.set(nroTrazo, false);
                }
                break;
        }
    }

    /**
     * Sets paint
     *
     * @param paint
     */
    public void setPaint(Paint paint) {
        this.mPaint = paint;
    }

    /**
     * Returns image as bitmap
     *
     * @return
     */
    public Bitmap getBitmap() {
        return mBitmap;
    }

    /**
     * Clears canvas
     */
    public void clear() {
        mBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        mBitmap.eraseColor(BACKGROUND);
        mCanvas.setBitmap(mBitmap);
        invalidate();
    }

    /**
     * Checks if user touch point with some tolerance
     */
    private boolean checkPoint(float x, float y, int pointIndex, List<Point>arrayPoints) {
        if (pointIndex == arrayPoints.size()) {
            if ((JuegoDibujoActivity.letra_actual.equals("m") && (lastPointIndex_m.get(3) == points_m.get(3).size()-1)) ||
                    (JuegoDibujoActivity.letra_actual.equals("n") && (lastPointIndex_n.get(2) == points_n.get(2).size()-1)) ||
                    (JuegoDibujoActivity.letra_actual.equals("p") && (lastPointIndex_p.get(1) == points_p.get(1).size()-1)) ||
                    (JuegoDibujoActivity.letra_actual.equals("l") && (lastPointIndex_l.get(1) == points_l.get(1).size()-1)) ||
                    (JuegoDibujoActivity.letra_actual.equals("d") && (lastPointIndex_d.get(1) == points_d.get(1).size()-1)) ||
                    (JuegoDibujoActivity.letra_actual.equals("t") && (lastPointIndex_t.get(1) == points_t.get(1).size()-1)) ||
                    (JuegoDibujoActivity.letra_actual.equals("s") && (lastPointIndex_s.get(0) == points_s.get(0).size()-1))){
                //Si se soltó el trazo en el último punto de la M, blanquear la flecha 4.
                if (JuegoDibujoActivity.letra_actual.equals("m")){
                    colocarFlecha(mCanvas, "M", 4, Color.WHITE);
                }
                //Si se soltó el trazo en el último punto de la N, blanquear la flecha 3.
                if (JuegoDibujoActivity.letra_actual.equals("n")){
                    colocarFlecha(mCanvas, "N", 3, Color.WHITE);
                }
                //Si se soltó el trazo en el último punto de la P, blanquear la flecha 2.
                if (JuegoDibujoActivity.letra_actual.equals("p")){
                    colocarFlecha(mCanvas, "P", 2, Color.WHITE);
                }
                //Si se soltó el trazo en el último punto de la L, blanquear la flecha 2.
                if (JuegoDibujoActivity.letra_actual.equals("l")){
                    colocarFlecha(mCanvas, "L", 2, Color.WHITE);
                }
                //Si se soltó el trazo en el último punto de la D, blanquear la flecha 2.
                if (JuegoDibujoActivity.letra_actual.equals("d")){
                    colocarFlecha(mCanvas, "D", 2, Color.WHITE);
                }
                //Si se soltó el trazo en el último punto de la T, blanquear la flecha 2.
                if (JuegoDibujoActivity.letra_actual.equals("t")){
                    colocarFlecha(mCanvas, "T", 2, Color.WHITE);
                }
                //Si se soltó el trazo en el último punto de la S, blanquear la flecha 1.
                if (JuegoDibujoActivity.letra_actual.equals("s")){
                    colocarFlecha(mCanvas, "S", 1, Color.WHITE);
                }
                if (JuegoDibujoActivity.fin == 0) {
                    JuegoDibujoActivity.fin = 1;
                    JuegoDibujoActivity.button.setVisibility(View.VISIBLE);
                    isPathStarted_m.set(3, false);
                    isPathStarted_n.set(2, false);
                    isPathStarted_p.set(1, false);
                    isPathStarted_l.set(1, false);
                    isPathStarted_d.set(1, false);
                    isPathStarted_t.set(1, false);
                    isPathStarted_s.set(0, false);
                    JuegoDibujoActivity.mp.setVolume((float) 1, (float) 1);
                    JuegoDibujoActivity.mp.start();
                }
                return false;
            }
        }
        if(pointIndex < arrayPoints.size()){
            Point point = arrayPoints.get(pointIndex);
            //EDIT changed point.y to point.x in the first if statement
            if (x > (point.x - mTouchTolerance) && x < (point.x + mTouchTolerance)) {
                if (y > (point.y - mTouchTolerance) && y < (point.y + mTouchTolerance)) {
                    return true;
                }
            }
        }

        return false;
    }

    public List<Point> getPoints() {
        return points_m.get(0);
        //return mPoints;
    }

    public void setPoints(List<Point> points) {
        //this.points_m.set(0, points);
        //this.mPoints = points;
    }

    private int dp2px(int dp) {
        Resources r = getContext().getResources();
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
        return (int) px;
    }

    private void crearContornos(Canvas canvas){
        int x1,x2,x3,x4,x5,x6,x7,x8,x9,x10,x11,x12,x13,x14,x15,x16,x17,x18,x19,x20,x21,x22,x23,x24,x25,x26,x27,x28,x29;
        int y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,y11,y12,y13,y14,y15,y16,y17,y18,y19,y20,y21,y22,y23,y24,y25,y26,y27,y28,y29;
        int x_1,x_2,x_3,x_4;
        int y_1,y_2,y_3,y_4;
        double dx = 0.02, dy = 0.01;
        switch(JuegoDibujoActivity.letra_actual) {
            case ("m"):
                dx = 0.03; dy = 0.03;
                x1 = (int) (screenWidth * (0.180-dx)); y1 = (int) (screenHeight * (0.710+dy));
                x2 = (int) (screenWidth * (0.180-dx)); y2 = (int) (screenHeight * (0.190-dy)); canvas.drawLine(x1, y1, x2, y2, lPaint);
                x3 = (int) (screenWidth * (0.220+dx)); y3 = (int) (screenHeight * (0.190-dy)); canvas.drawLine(x2, y2, x3, y3, lPaint);
                x4 = (int) (screenWidth * (0.500)); y4 = (int) (screenHeight * (0.665-2*dy)); canvas.drawLine(x3, y3, x4, y4, lPaint);
                x5 = (int) (screenWidth * (0.780-dx)); y5 = (int) (screenHeight * (0.190-dy)); canvas.drawLine(x4, y4, x5, y5, lPaint);
                x6 = (int) (screenWidth * (0.820+dx)); y6 = (int) (screenHeight * (0.190-dy)); canvas.drawLine(x5, y5, x6, y6, lPaint);
                x7 = (int) (screenWidth * (0.820+dx)); y7 = (int) (screenHeight * (0.710+dy)); canvas.drawLine(x6, y6, x7, y7, lPaint);
                x8 = (int) (screenWidth * (0.780-dx)); y8 = (int) (screenHeight * (0.710+dy)); canvas.drawLine(x7, y7, x8, y8, lPaint);
                x9 = (int) (screenWidth * (0.780-dx)); y9 = (int) (screenHeight * (0.260+4*dy)); canvas.drawLine(x8, y8, x9, y9, lPaint);
                x10 = (int) (screenWidth * (0.515+dx)); y10 = (int) (screenHeight * (0.710+dy)); canvas.drawLine(x9, y9, x10, y10, lPaint);
                x11 = (int) (screenWidth * (0.485-dx)); y11 = (int) (screenHeight * (0.710+dy)); canvas.drawLine(x10, y10, x11, y11, lPaint);
                x12 = (int) (screenWidth * (0.220+dx)); y12 = (int) (screenHeight * (0.260+4*dy)); canvas.drawLine(x11, y11, x12, y12, lPaint);
                x13 = (int) (screenWidth * (0.220+dx)); y13 = (int) (screenHeight * (0.710+dy)); canvas.drawLine(x12, y12, x13, y13, lPaint);
                canvas.drawLine(x13, y13, x1, y1, lPaint);
                break;
            case ("n"):
                dx = 0.03; dy = 0.03;
                x1 = (int) (screenWidth * (0.180-dx)); y1 = (int) (screenHeight * (0.710+dy));
                x2 = (int) (screenWidth * (0.180-dx)); y2 = (int) (screenHeight * (0.190-dy)); canvas.drawLine(x1, y1, x2, y2, lPaint);
                x3 = (int) (screenWidth * (0.220+dx)); y3 = (int) (screenHeight * (0.190-dy)); canvas.drawLine(x2, y2, x3, y3, lPaint);
                x4 = (int) (screenWidth * (0.780-dx)); y4 = (int) (screenHeight * (0.670-dy*2)); canvas.drawLine(x3, y3, x4, y4, lPaint);
                x5 = (int) (screenWidth * (0.780-dx)); y5 = (int) (screenHeight * (0.190-dy)); canvas.drawLine(x4, y4, x5, y5, lPaint);
                x6 = (int) (screenWidth * (0.820+dx)); y6 = (int) (screenHeight * (0.190-dy)); canvas.drawLine(x5, y5, x6, y6, lPaint);
                x7 = (int) (screenWidth * (0.820+dx)); y7 = (int) (screenHeight * (0.710+dy)); canvas.drawLine(x6, y6, x7, y7, lPaint);
                x8 = (int) (screenWidth * (0.780-dx)); y8 = (int) (screenHeight * (0.710+dy)); canvas.drawLine(x7, y7, x8, y8, lPaint);
                x9 = (int) (screenWidth * (0.220+dx)); y9 = (int) (screenHeight * (0.230+dy*2)); canvas.drawLine(x8, y8, x9, y9, lPaint);
                x10 = (int) (screenWidth * (0.220+dx)); y10 = (int) (screenHeight * (0.710+dy)); canvas.drawLine(x9, y9, x10, y10, lPaint);
                canvas.drawLine(x10, y10, x1, y1, lPaint);
                break;
            case ("p"):
                dx = 0.05; dy = 0.03;
                /*Pi: Puntos exteriores del contorno.   Qi: Puntos interiores del contorno.*/
                //Parte inicial.
                /*P1*/x_1 = (int) (screenWidth * (0.35 - dx)); y_1 = (int) (screenHeight * (0.70 + dy));
                /*P2*/x_2 = (int) (screenWidth * (0.35 - dx)); y_2 = (int) (screenHeight * (0.200 - dy));
                canvas.drawLine(x_1, y_1, x_2, y_2, lPaint);
                /*Q1*/x_3 = (int) (screenWidth * (0.35 + dx)); y_3 = (int) (screenHeight * (0.452 - dy));
                /*Q2*/x_4 = (int) (screenWidth * (0.35 + dx)); y_4 = (int) (screenHeight * (0.200 + dy));
                canvas.drawLine(x_3, y_3, x_4, y_4, lPaint);
                /*P3*/x_3 = (int) (screenWidth * (0.35 + dx)); y_3 = (int) (screenHeight * (0.455 + dy));
                /*P4*/x_4 = (int) (screenWidth * (0.35 + dx)); y_4 = (int) (screenHeight * (0.70 + dy));
                canvas.drawLine(x_3, y_3, x_4, y_4, lPaint);
                /*P5*/x_1 = (int) (screenWidth * (0.35 + dx)); y_1 = (int) (screenHeight * (0.70 + dy));
                /*P6*/x_2 = (int) (screenWidth * (0.35 - dx)); y_2 = (int) (screenHeight * (0.70 + dy));
                canvas.drawLine(x_1, y_1, x_2, y_2, lPaint);

                //Curva P superior.
                x_1 = (int) (screenWidth * (-0.05 - dx));
                x_2 = (int) (screenWidth * (0.65 + dx));
                y_1 = (int) (screenHeight * (0.20 - dy));
                y_2 = (int) (screenHeight * (0.458 + dy));
                RectF rectF = new RectF(x_1, y_1, x_2, y_2);
                canvas.drawArc(rectF, -90, (float) 165.2, false, lPaint);

                //Curva P inferior.
                x_1 = (int) (screenWidth * (0.10 - dx));
                x_2 = (int) (screenWidth * (0.555 + dx));
                y_1 = (int) (screenHeight * (0.255 - dy));
                y_2 = (int) (screenHeight * (0.399 + dy));
                rectF = new RectF(x_1, y_1, x_2, y_2);
                canvas.drawArc(rectF, (float) -74.6, (float) 149.8, false, lPaint);
                break;
            case ("l"):
                dx = 0.03; dy = 0.02;
                x1 = (int) (screenWidth * (0.180-dx)); y1 = (int) (screenHeight * (0.190-dy));
                x2 = (int) (screenWidth * (0.220+dx)); y2 = (int) (screenHeight * (0.190-dy));
                canvas.drawLine(x1, y1, x2, y2, lPaint);
                x3 = (int) (screenWidth * (0.220+dx)); y3 = (int) (screenHeight * (0.690-dy));
                canvas.drawLine(x2, y2, x3, y3, lPaint);
                x4 = (int) (screenWidth * (0.820+dx)); y4 = (int) (screenHeight * (0.690-dy));
                canvas.drawLine(x3, y3, x4, y4, lPaint);
                x5 = (int) (screenWidth * (0.820+dx)); y5 = (int) (screenHeight * (0.710+dy));
                canvas.drawLine(x4, y4, x5, y5, lPaint);
                x6 = (int) (screenWidth * (0.180-dx)); y6 = (int) (screenHeight * (0.710+dy));
                canvas.drawLine(x5, y5, x6, y6, lPaint);
                canvas.drawLine(x6, y6, x1, y1, lPaint);
                break;
            case ("d"):
                /*Pi: Puntos exteriores del contorno.   Qi: Puntos interiores del contorno.*/
                //Parte inicial.
                /*P1*/x_1 = (int) (screenWidth * (0.276 - dx)); y_1 = (int) (screenHeight * (0.714 + dy));
                /*P2*/x_2 = (int) (screenWidth * (0.276 - dx)); y_2 = (int) (screenHeight * (0.190 - dy));
                canvas.drawLine(x_1, y_1, x_2, y_2, lPaint);
                /*Q1*/x_3 = (int) (screenWidth * (0.324 + dx)); y_3 = (int) (screenHeight * (0.675 - dy));
                /*Q2*/x_4 = (int) (screenWidth * (0.324 + dx)); y_4 = (int) (screenHeight * (0.225 + dy));
                canvas.drawLine(x_3, y_3, x_4, y_4, lPaint);
                x_1 = (int) (screenWidth * (-0.2 - dx));
                x_2 = (int) (screenWidth * (0.72 + dx));
                y_1 = (int) (screenHeight * (0.19 - dy));
                y_2 = (int) (screenHeight * (0.715 + dy));
                rectF = new RectF(x_1, y_1, x_2, y_2);

                canvas.drawArc(rectF, -90, (float) 180.5, false, lPaint);
                x_1 = (int) (screenWidth * (-0.2 - dx));
                x_2 = (int) (screenWidth * (0.63 + dx));
                y_1 = (int) (screenHeight * (0.235 - dy));
                y_2 = (int) (screenHeight * (0.665 + dy));
                rectF = new RectF(x_1, y_1, x_2, y_2);
                canvas.drawArc(rectF, (float) -73, (float) 146, false, lPaint);

                break;
            case ("t"):
                dx = 0.05; dy = 0.027;
                x1 = (int) (screenWidth * (0.2-dx)); y1 = (int) (screenHeight * (0.2-dy));
                x2 = (int) (screenWidth * (0.8+dx)); y2 = (int) (screenHeight * (0.2-dy)); canvas.drawLine(x1, y1, x2, y2, lPaint);
                x3 = (int) (screenWidth * (0.8+dx)); y3 = (int) (screenHeight * (0.2+dy)); canvas.drawLine(x2, y2, x3, y3, lPaint);
                x4 = (int) (screenWidth * (0.5+dx)); y4 = (int) (screenHeight * (0.2+dy)); canvas.drawLine(x3, y3, x4, y4, lPaint);
                x5 = (int) (screenWidth * (0.5+dx)); y5 = (int) (screenHeight * (0.7+dy)); canvas.drawLine(x4, y4, x5, y5, lPaint);
                x6 = (int) (screenWidth * (0.5-dx)); y6 = (int) (screenHeight * (0.7+dy)); canvas.drawLine(x5, y5, x6, y6, lPaint);
                x7 = (int) (screenWidth * (0.5-dx)); y7 = (int) (screenHeight * (0.2+dy)); canvas.drawLine(x6, y6, x7, y7, lPaint);
                x8 = (int) (screenWidth * (0.2-dx)); y8 = (int) (screenHeight * (0.2+dy)); canvas.drawLine(x7, y7, x8, y8, lPaint);
                canvas.drawLine(x8, y8, x1, y1, lPaint);
                break;
            case ("s"):
                dx = 0.05; dy = 0.027;
                /*Pi: Puntos exteriores del contorno.   Qi: Puntos interiores del contorno.*/
                //Parte inicial.
                /*P1*/x_1 = (int) (screenWidth * (0.80 + dx)); y_1 = (int) (screenHeight * (0.20 - dy));
                /*P2*/x_2 = (int) (screenWidth * (0.465)); y_2 = (int) (screenHeight * (0.20 - dy));
                canvas.drawLine(x_1, y_1, x_2, y_2, lPaint);
                /*Q1*/x_3 = (int) (screenWidth * (0.80 + dx)); y_3 = (int) (screenHeight * (0.20 + dy));
                /*Q2*/x_4 = (int) (screenWidth * (0.450)); y_4 = (int) (screenHeight * (0.20 + dy));
                canvas.drawLine(x_3, y_3, x_4, y_4, lPaint);
                //P se une con Q
                canvas.drawLine(x_3, y_3, x_1, y_1, lPaint);

                x_1 = (int) (screenWidth * (0.200 - dx));
                x_2 = (int) (screenWidth * (0.735 + dx));
                y_1 = (int) (screenHeight * (0.200 - dy));
                y_2 = (int) (screenHeight * (0.450 + dy));
                rectF = new RectF(x_1, y_1, x_2, y_2);
                canvas.drawArc(rectF, -90, (float) -180.5, false, lPaint);
                x_1 = (int) (screenWidth * (0.298 - dx));
                x_2 = (int) (screenWidth * (0.690 + dx));
                y_1 = (int) (screenHeight * (0.253 - dy));
                y_2 = (int) (screenHeight * (0.397 + dy));
                rectF = new RectF(x_1, y_1, x_2, y_2);
                canvas.drawArc(rectF, (float)-95.5, (float)-178, false, lPaint);
                x_1 = (int) (screenWidth * (0.208 - dx));
                x_2 = (int) (screenWidth * (0.790 + dx));
                y_1 = (int) (screenHeight * (0.452 - dy));
                y_2 = (int) (screenHeight * (0.700 + dy));
                rectF = new RectF(x_1, y_1, x_2, y_2);
                canvas.drawArc(rectF, -90, (float) 180, false, lPaint);
                x_1 = (int) (screenWidth * (0.208 - dx));
                x_2 = (int) (screenWidth * (0.704 + dx));
                y_1 = (int) (screenHeight * (0.504 - dy));
                y_2 = (int) (screenHeight * (0.646 + dy));
                rectF = new RectF(x_1, y_1, x_2, y_2);
                canvas.drawArc(rectF, -90, (float) 180, false, lPaint);

                x_2 = (int) (screenWidth * (0.50)); y_2 = (int) (screenHeight * (0.700 + dy));
                x_4 = (int) (screenWidth * (0.50)); y_4 = (int) (screenHeight * (0.700 - dy));
                x_1 = (int) (screenWidth * (0.20 - dx)); y_1 = (int) (screenHeight * (0.700 + dy));
                canvas.drawLine(x_2, y_2, x_1, y_1, lPaint);
                x_3 = (int) (screenWidth * (0.20 - dx)); y_3 = (int) (screenHeight * (0.700 - dy));
                canvas.drawLine(x_4, y_4, x_3, y_3, lPaint);
                canvas.drawLine(x_3, y_3, x_1, y_1, lPaint);

                break;
        }
    }

    private void colocarPuntos(){
        int x; int y;
        ArrayList<Point> lista = null;
        double dy;
        switch(JuegoDibujoActivity.letra_actual){
            case("m"):
                dy = 0.017;
                x = (int) (screenWidth * (0.20)); y = (int) (screenHeight * (0.20-dy)); p1 = new Point(x, y);
                x = (int) (screenWidth * (0.20)); y = (int) (screenHeight * (0.45)); p2 = new Point(x, y);
                x = (int) (screenWidth * (0.20)); y = (int) (screenHeight * (0.70+dy)); p3 = new Point(x, y);
                x = (int) (screenWidth * (0.35)); y = (int) (screenHeight * (0.45)); p4 = new Point(x, y);
                x = (int) (screenWidth * (0.50)); y = (int) (screenHeight * (0.70+dy)); p5 = new Point(x, y);
                x = (int) (screenWidth * (0.65)); y = (int) (screenHeight * (0.45)); p6 = new Point(x, y);
                x = (int) (screenWidth * (0.80)); y = (int) (screenHeight * (0.20-dy)); p7 = new Point(x, y);
                x = (int) (screenWidth * (0.80)); y = (int) (screenHeight * (0.45)); p8 = new Point(x, y);
                x = (int) (screenWidth * (0.80)); y = (int) (screenHeight * (0.70+dy)); p9 = new Point(x, y);

                lista = new ArrayList<Point>(); lista.add(p1); lista.add(p2); lista.add(p3); points_m.add(lista);
                lista = new ArrayList<Point>(); lista.add(p1); lista.add(p4); lista.add(p5); points_m.add(lista);
                lista = new ArrayList<Point>(); lista.add(p5); lista.add(p6); lista.add(p7); points_m.add(lista);
                lista = new ArrayList<Point>(); lista.add(p7); lista.add(p8); lista.add(p9); points_m.add(lista);
                break;
            case("n"):
                dy = 0.017;
                x = (int) (screenWidth * (0.20)); y = (int) (screenHeight * (0.70+dy)); p1 = new Point(x, y);
                x = (int) (screenWidth * (0.20)); y = (int) (screenHeight * (0.45)); p2 = new Point(x, y);
                x = (int) (screenWidth * (0.20)); y = (int) (screenHeight * (0.20-dy)); p3 = new Point(x, y);
                x = (int) (screenWidth * (0.50)); y = (int) (screenHeight * (0.45)); p4 = new Point(x, y);
                x = (int) (screenWidth * (0.80)); y = (int) (screenHeight * (0.70+dy)); p5 = new Point(x, y);
                x = (int) (screenWidth * (0.80)); y = (int) (screenHeight * (0.45)); p6 = new Point(x, y);
                x = (int) (screenWidth * (0.80)); y = (int) (screenHeight * (0.20-dy)); p7 = new Point(x, y);

                lista = new ArrayList<Point>(); lista.add(p3); lista.add(p2); lista.add(p1); points_n.add(lista);
                lista = new ArrayList<Point>(); lista.add(p3); lista.add(p4); lista.add(p5); points_n.add(lista);
                lista = new ArrayList<Point>(); lista.add(p5); lista.add(p6); lista.add(p7); points_n.add(lista);
                break;
            case("p"):
                x1 = (int) (screenWidth * (0.35)); y1 = (int) (screenHeight * (0.7));
                //x2 = (int) (screenWidth * (0.35)); y2 = (int) (screenHeight * (0.7));
                x3 = (int) (screenWidth * (0.35)); y3 = (int) (screenHeight * (0.200));
                //x4 = (int) (screenWidth * (0.40)); y4 = (int) (screenHeight * (0.202));
                x5 = (int) (screenWidth * (0.455)); y5 = (int) (screenHeight * (0.209));
                //x6 = (int) (screenWidth * (0.50)); y6 = (int) (screenHeight * (0.210));
                x7 = (int) (screenWidth * (0.55)); y7 = (int) (screenHeight * (0.234));
                //x8 = (int) (screenWidth * (0.57)); y8 = (int) (screenHeight * (0.230));
                x9 = (int) (screenWidth * (0.62)); y9 = (int) (screenHeight * (0.272));
                //x10 = (int) (screenWidth * (0.62)); y10 = (int) (screenHeight * (0.266));
                x11 = (int) (screenWidth * (0.653)); y11 = (int) (screenHeight * (0.328));
                //x12 = (int) (screenWidth * (0.65)); y12 = (int) (screenHeight * (0.325));
                //x14 = (int) (screenWidth * (0.62)); y14 = (int) (screenHeight * (0.384));
                x13 = (int) (screenWidth * (0.62)); y13 = (int) (screenHeight * (0.384));
                //x16 = (int) (screenWidth * (0.57)); y16 = (int) (screenHeight * (0.420));
                x15 = (int) (screenWidth * (0.55)); y15 = (int) (screenHeight * (0.422));
                //x18 = (int) (screenWidth * (0.5)); y18 = (int) (screenHeight * (0.440));
                x17 = (int) (screenWidth * (0.455)); y17 = (int) (screenHeight * (0.449));
                //x20 = (int) (screenWidth * (0.4)); y20 = (int) (screenHeight * (0.448));
                x19 = (int) (screenWidth * (0.35)); y19 = (int) (screenHeight * (0.458));

                p1 = new Point(x1, y1); p3 = new Point(x3, y3); p5 = new Point(x5, y5); p7 = new Point(x7, y7);
                p9 = new Point(x9, y9); p11 = new Point(x11, y11); p13 = new Point(x13, y13); p15 = new Point(x15, y15);
                p17 = new Point(x17, y17); p19 = new Point(x19, y19); p21 = new Point(x21, y21);

                lista = new ArrayList<Point>(); lista.add(p3); lista.add(p19); lista.add(p1); points_p.add(lista);
                lista = new ArrayList<Point>(); lista.add(p3); lista.add(p5); lista.add(p7);
                lista.add(p9); lista.add(p11); lista.add(p13);
                lista.add(p15); lista.add(p17); lista.add(p19);
                points_p.add(lista);
                break;
            case("l"):
                x = (int) (screenWidth * (0.20)); y = (int) (screenHeight * (0.20)); p1 = new Point(x, y);
                x = (int) (screenWidth * (0.20)); y = (int) (screenHeight * (0.45)); p2 = new Point(x, y);
                x = (int) (screenWidth * (0.20)); y = (int) (screenHeight * (0.70)); p3 = new Point(x, y);
                x = (int) (screenWidth * (0.50)); y = (int) (screenHeight * (0.70)); p4 = new Point(x, y);
                x = (int) (screenWidth * (0.80)); y = (int) (screenHeight * (0.70)); p5 = new Point(x, y);

                lista = new ArrayList<Point>(); lista.add(p1); lista.add(p2); lista.add(p3); points_l.add(lista);
                lista = new ArrayList<Point>(); lista.add(p3); lista.add(p4); lista.add(p5); points_l.add(lista);
                break;
            case("d"):
                x1 = (int) (screenWidth * (0.30)); y1 = (int) (screenHeight * (0.697)); p1 = new Point(x1, y1);
                x2 = (int) (screenWidth * (0.30)); y2 = (int) (screenHeight * (0.45)); p2 = new Point(x2, y2);
                x3 = (int) (screenWidth * (0.30)); y3 = (int) (screenHeight * (0.205)); p3 = new Point(x3, y3);
                x5 = (int) (screenWidth * (0.41)); y5 = (int) (screenHeight * (0.220)); p5 = new Point(x5, y5);
                x7 = (int) (screenWidth * (0.50)); y7 = (int) (screenHeight * (0.247)); p7 = new Point(x7, y7);
                x9 = (int) (screenWidth * (0.58)); y9 = (int) (screenHeight * (0.284)); p9 = new Point(x9, y9);
                x11 = (int) (screenWidth * (0.64)); y11 = (int) (screenHeight * (0.330)); p11 = new Point(x11, y11);
                x13 = (int) (screenWidth * (0.677)); y13 = (int) (screenHeight * (0.382)); p13 = new Point(x13, y13);
                x15 = (int) (screenWidth * (0.695)); y15 = (int) (screenHeight * (0.433)); p15 = new Point(x15, y15);
                x17 = (int) (screenWidth * (0.695)); y17 = (int) (screenHeight * (0.471)); p17 = new Point(x17, y17);
                x19 = (int) (screenWidth * (0.677)); y19 = (int) (screenHeight * (0.520)); p19 = new Point(x19, y19);
                x21 = (int) (screenWidth * (0.64)); y21 = (int) (screenHeight * (0.572)); p21 = new Point(x21, y21);
                x23 = (int) (screenWidth * (0.58)); y23 = (int) (screenHeight * (0.618)); p23 = new Point(x23, y23);
                x25 = (int) (screenWidth * (0.50)); y25 = (int) (screenHeight * (0.655)); p25 = new Point(x25, y25);
                x27 = (int) (screenWidth * (0.41)); y27 = (int) (screenHeight * (0.682)); p27 = new Point(x27, y27);
                x29 = (int) (screenWidth * (0.30)); y29 = (int) (screenHeight * (0.697)); p29 = new Point(x29, y29);

                lista = new ArrayList<Point>(); lista.add(p3); lista.add(p2); lista.add(p1); points_d.add(lista);
                lista = new ArrayList<Point>(); lista.add(p3); lista.add(p5); lista.add(p7);
                lista.add(p9); lista.add(p11); lista.add(p13);
                lista.add(p15); lista.add(p17); lista.add(p19);
                lista.add(p21); lista.add(p23); lista.add(p25);
                lista.add(p27); lista.add(p29); points_d.add(lista);
                break;
            case("t"):
                x = (int) (screenWidth * (0.50)); y = (int) (screenHeight * (0.20)); p1 = new Point(x, y);
                x = (int) (screenWidth * (0.50)); y = (int) (screenHeight * (0.70)); p2 = new Point(x, y);
                x = (int) (screenWidth * (0.20)); y = (int) (screenHeight * (0.20)); p3 = new Point(x, y);
                x = (int) (screenWidth * (0.80)); y = (int) (screenHeight * (0.20)); p4 = new Point(x, y);
                x = (int) (screenWidth * (0.50)); y = (int) (screenHeight * (0.45)); p5 = new Point(x, y);

                lista = new ArrayList<Point>(); lista.add(p1); lista.add(p5); lista.add(p2); points_t.add(lista);
                lista = new ArrayList<Point>(); lista.add(p3); lista.add(p1); lista.add(p4); points_t.add(lista);
                break;
            case("s"):
                x1 = (int) (screenWidth * (0.80)); y1 = (int) (screenHeight * (0.200)); p1 = new Point(x1, y1);
                x3 = (int) (screenWidth * (0.45)); y3 = (int) (screenHeight * (0.202)); p3 = new Point(x3, y3);
                x5 = (int) (screenWidth * (0.37)); y5 = (int) (screenHeight * (0.210)); p5 = new Point(x5, y5);
                x7 = (int) (screenWidth * (0.29)); y7 = (int) (screenHeight * (0.232)); p7 = new Point(x7, y7);
                x9 = (int) (screenWidth * (0.23)); y9 = (int) (screenHeight * (0.266)); p9 = new Point(x9, y9);
                x11 = (int) (screenWidth * (0.20)); y11 = (int) (screenHeight * (0.325)); p11 = new Point(x11, y11);
                x13 = (int) (screenWidth * (0.23)); y13 = (int) (screenHeight * (0.384)); p13 = new Point(x13, y13);
                x15 = (int) (screenWidth * (0.29)); y15 = (int) (screenHeight * (0.418)); p15 = new Point(x15, y15);
                x17 = (int) (screenWidth * (0.37)); y17 = (int) (screenHeight * (0.440)); p17 = new Point(x17, y17);
                x19 = (int) (screenWidth * (0.45)); y19 = (int) (screenHeight * (0.448)); p19 = new Point(x19, y19);

                lista = new ArrayList<Point>(); lista.add(p1); lista.add(p3); lista.add(p5);
                lista.add(p7); lista.add(p9); lista.add(p11);
                lista.add(p13); lista.add(p15); lista.add(p17);
                lista.add(p19);

                x1 = (int) (screenWidth * (0.53)); y1 = (int) (screenHeight * (0.452)); p1 = new Point(x1, y1);
                x3 = (int) (screenWidth * (0.61)); y3 = (int) (screenHeight * (0.460)); p3 = new Point(x3, y3);
                x5 = (int) (screenWidth * (0.69)); y5 = (int) (screenHeight * (0.480)); p5 = new Point(x5, y5);
                x7 = (int) (screenWidth * (0.75)); y7 = (int) (screenHeight * (0.507)); p7 = new Point(x7, y7);
                x9 = (int) (screenWidth * (0.79)); y9 = (int) (screenHeight * (0.550)); p9 = new Point(x9, y9);
                x11 = (int) (screenWidth * (0.79)); y11 = (int) (screenHeight * (0.600)); p11 = new Point(x11, y11);
                x13 = (int) (screenWidth * (0.75)); y13 = (int) (screenHeight * (0.643)); p13 = new Point(x13, y13);
                x15 = (int) (screenWidth * (0.69)); y15 = (int) (screenHeight * (0.670)); p15 = new Point(x15, y15);
                x17 = (int) (screenWidth * (0.61)); y17 = (int) (screenHeight * (0.690)); p17 = new Point(x17, y17);
                x19 = (int) (screenWidth * (0.53)); y19 = (int) (screenHeight * (0.698)); p19 = new Point(x19, y19);
                x20 = (int) (screenWidth * (0.20)); y20 = (int) (screenHeight * (0.700)); p20 = new Point(x20, y20);

                lista.add(p1); lista.add(p3); lista.add(p5);
                lista.add(p7); lista.add(p9); lista.add(p11);
                lista.add(p13); lista.add(p15); lista.add(p17);
                lista.add(p19); lista.add(p20); points_s.add(lista);
                break;
        }
    }
    private void colocarFlecha(Canvas canvas, String letra, int numero, int color){
        int x1,x2,x3,x4;
        int y1,y2,y3,y4;
        double inclinacionX, inclinacionY, inclinacion;
        lPaint.setColor(color);
        switch(letra) {
            case "M":
                inclinacionX = 0.500 - 0.190; //180
                inclinacionY = 0.665 - 0.180; //190
                inclinacion = inclinacionY / inclinacionX;
                switch (numero) {
                    case 1:
                        x1 = (int) (screenWidth * (0.180) * 0.7);
                        y1 = (int) (screenHeight * (0.190));
                        x2 = (int) (screenWidth * (0.180) * 0.7);
                        y2 = (int) (screenHeight * (0.260));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("1", (int) (x1 * 0.7), y1, lPaint);
                        x3 = (int) (screenWidth * ((0.180 - 0.02) * 0.7));
                        y3 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * ((0.180 + 0.02) * 0.7));
                        y1 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                    case 2:
                        x1 = (int) (screenWidth * (0.250) * 1.2); //220
                        y1 = (int) (screenHeight * (0.190));
                        x2 = (int) (screenWidth * ((0.250 + (0.260 - 0.190) / inclinacion)) * 1.2);
                        y2 = (int) (screenHeight * (0.260));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("2", (int) (x1 * 1.1), y1, lPaint);
                        x3 = (int) (screenWidth * ((0.250 + (0.260 - 0.190) / inclinacion) - 0.02) * 1.2);
                        y3 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * ((0.250 + (0.260 - 0.190) / inclinacion)) * 1.2);
                        y1 = (int) (screenHeight * (0.260 - 0.02));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                    case 3:
                        x1 = (int) (screenWidth * (0.750) * 0.92); //780
                        y1 = (int) (screenHeight * (0.190));
                        x2 = (int) (screenWidth * ((0.750 - (0.260 - 0.190) / inclinacion)) * 0.92);
                        y2 = (int) (screenHeight * (0.260));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("3", (int) (x1 * 0.95), y1, lPaint);
                        //Palito diagonal hacia arriba.
                        x3 = (int) (screenWidth * ((0.750 - (0.260 - 0.190) / inclinacion) + 0.02) * 0.92);//0.95
                        y3 = (int) (screenHeight * (0.190 + 0.01));
                        canvas.drawLine(x3, y3, x1, y1, lPaint);
                        //Palito derecho vertical hacia arriba.
                        x2 = (int) (screenWidth * (0.750) * 0.92);
                        y2 = (int) (screenHeight * (0.190 + 0.02));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                    case 4:
                        x1 = (int) (screenWidth * (0.850) * 1.05);//820
                        y1 = (int) (screenHeight * (0.190));
                        x2 = (int) (screenWidth * (0.850) * 1.05);
                        y2 = (int) (screenHeight * (0.260));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("4", (int) (x1 * 1.05), y1, lPaint);
                        x3 = (int) (screenWidth * ((0.850 - 0.02) * 1.05));
                        y3 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * ((0.850 + 0.02) * 1.05));
                        y1 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                }
                break;
            case "N":
                inclinacionX = 0.780 - 0.180;
                inclinacionY = 0.680 - 0.190;
                inclinacion = inclinacionY / inclinacionX;
                switch(numero){
                    case 1:
                        x1 = (int) (screenWidth * (0.180) * 0.7); y1 = (int) (screenHeight * (0.190));
                        x2 = (int) (screenWidth * (0.180) * 0.7); y2 = (int) (screenHeight * (0.260));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("1", (int)(x1 * 0.7), y1, lPaint);
                        x3 = (int) (screenWidth * ((0.180 - 0.02) * 0.7)); y3 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * ((0.180 + 0.02) * 0.7)); y1 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                    case 2:
                        x1 = (int) (screenWidth * (0.220) * 1.6); y1 = (int) (screenHeight * (0.190));
                        x2 = (int) (screenWidth * ((0.220 + (0.230 - 0.190)/inclinacion)) * 1.5); y2 = (int) (screenHeight * (0.260));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("2", (int)(x1 * 1.3), y1, lPaint);
                        x3 = (int) (screenWidth * ((0.220 + (0.230 - 0.190)/inclinacion) - 0.02) * 1.5 ); y3 = (int) (screenHeight * (0.260 - 0.005));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * ((0.220 + (0.230 - 0.190)/inclinacion) + 0.01) * 1.5 ); y1 = (int) (screenHeight * (0.260 - 0.02));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                    case 3:
                        x1 = (int) (screenWidth * (0.820) * 1.08); y1 = (int) (screenHeight * (0.190));
                        x2 = (int) (screenWidth * (0.820) * 1.08); y2 = (int) (screenHeight * (0.260));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("3", (int)(x1 * 1.05), y1, lPaint);
                        x3 = (int) (screenWidth * ((0.820 - 0.02) * 1.08)); y3 = (int) (screenHeight * (0.190 + 0.01));
                        canvas.drawLine(x3, y3, x2, y1, lPaint);
                        x1 = (int) (screenWidth * ((0.820 + 0.02) * 1.08)); y2 = (int) (screenHeight * (0.190 + 0.01));
                        canvas.drawLine(x1, y2, x2, y1, lPaint);
                        break;
                }
                break;
            case "P":
                switch(numero){
                    case 1:
                        x1 = (int) (screenWidth * (0.40) * 0.7); y1 = (int) (screenHeight * (0.190));
                        x2 = (int) (screenWidth * (0.40) * 0.7); y2 = (int) (screenHeight * (0.260));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("1", (int)(x1 * 0.8), y1, lPaint);
                        x3 = (int) (screenWidth * ((0.400 - 0.02) * 0.7)); y3 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * ((0.400 + 0.02) * 0.7)); y1 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                    case 2:
                        x1 = (int) (screenWidth * (0.300) * 1.2); y1 = (int) (screenHeight * (0.150));
                        x2 = (int) (screenWidth * (0.380) * 1.2); y2 = (int) (screenHeight * (0.160));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("2", (int)(x1), (int) (y1 * 0.88), lPaint);
                        x3 = (int) (screenWidth * (0.380 - 0.02) * 1.2 ); y3 = (int) (screenHeight * (0.160 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * (0.380 - 0.02) * 1.2 ); y1 = (int) (screenHeight * (0.160 + 0.01));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                }
                break;
            case "L":
                switch(numero){
                    case 1:
                        x1 = (int) (screenWidth * (0.180) * 0.7); y1 = (int) (screenHeight * (0.190));
                        x2 = (int) (screenWidth * (0.180) * 0.7); y2 = (int) (screenHeight * (0.260));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("1", (int)(x1 * 0.7), y1, lPaint);
                        x3 = (int) (screenWidth * ((0.180 - 0.02) * 0.7)); y3 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * ((0.180 + 0.02) * 0.7)); y1 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                    case 2:
                        x1 = (int) (screenWidth * (0.180) * 1.2); y1 = (int) (screenHeight * (0.750));
                        x2 = (int) (screenWidth * (0.280) * 1.2); y2 = (int) (screenHeight * (0.750));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("2", (int)(x1), (int) (y1 * 1.06), lPaint);
                        x3 = (int) (screenWidth * (0.280 - 0.02) * 1.2 ); y3 = (int) (screenHeight * (0.750 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * (0.280 - 0.02) * 1.2 ); y1 = (int) (screenHeight * (0.750 + 0.01));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                }
                break;
            case "D":
                switch(numero){
                    case 1:
                        x1 = (int) (screenWidth * (0.33) * 0.7); y1 = (int) (screenHeight * (0.190));
                        x2 = (int) (screenWidth * (0.33) * 0.7); y2 = (int) (screenHeight * (0.260));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("1", (int)(x1 * 0.8), y1, lPaint);
                        x3 = (int) (screenWidth * ((0.330 - 0.02) * 0.7)); y3 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * ((0.330 + 0.02) * 0.7)); y1 = (int) (screenHeight * (0.260 - 0.01));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                    case 2:
                        x1 = (int) (screenWidth * (0.250) * 1.2); y1 = (int) (screenHeight * (0.170));
                        x2 = (int) (screenWidth * (0.350) * 1.2); y2 = (int) (screenHeight * (0.170));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("2", (int)(x1), (int) (y1 * 0.93), lPaint);
                        x3 = (int) (screenWidth * (0.350 - 0.02) * 1.2 ); y3 = (int) (screenHeight * (0.170 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * (0.350 - 0.02) * 1.2 ); y1 = (int) (screenHeight * (0.170 + 0.01));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                }
                break;
            case "T":
                switch(numero){
                    case 1:
                        x1 = (int) (screenWidth * (0.60) * 0.7); y1 = (int) (screenHeight * (0.260));
                        x2 = (int) (screenWidth * (0.60) * 0.7); y2 = (int) (screenHeight * (0.320));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("1", (int)(x1 * 0.92), y1, lPaint);
                        x3 = (int) (screenWidth * ((0.600 - 0.02) * 0.7)); y3 = (int) (screenHeight * (0.320 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * ((0.600 + 0.02) * 0.7)); y1 = (int) (screenHeight * (0.320 - 0.01));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                    case 2:
                        x1 = (int) (screenWidth * (0.180) * 1.2); y1 = (int) (screenHeight * (0.150));
                        x2 = (int) (screenWidth * (0.280) * 1.2); y2 = (int) (screenHeight * (0.150));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        lPaint.setTextSize(50);
                        canvas.drawText("2", (int)(x1), (int) (y1 * 0.93), lPaint);
                        x3 = (int) (screenWidth * (0.280 - 0.02) * 1.2 ); y3 = (int) (screenHeight * (0.150 - 0.01));
                        canvas.drawLine(x3, y3, x2, y2, lPaint);
                        x1 = (int) (screenWidth * (0.280 - 0.02) * 1.2 ); y1 = (int) (screenHeight * (0.150 + 0.01));
                        canvas.drawLine(x1, y1, x2, y2, lPaint);
                        break;
                }
                break;
            case "S":
                x1 = (int) (screenWidth * (0.70) * 1.2); y1 = (int) (screenHeight * (0.150));
                x2 = (int) (screenWidth * (0.60) * 1.2); y2 = (int) (screenHeight * (0.150));
                canvas.drawLine(x1, y1, x2, y2, lPaint);
                lPaint.setTextSize(50);
                canvas.drawText("1", (int)(x1), (int) (y1 * 0.93), lPaint);
                x3 = (int) (screenWidth * (0.60 + 0.02) * 1.2 ); y3 = (int) (screenHeight * (0.150 - 0.01));
                canvas.drawLine(x3, y3, x2, y2, lPaint);
                x1 = (int) (screenWidth * (0.60 + 0.02) * 1.2 ); y1 = (int) (screenHeight * (0.150 + 0.01));
                canvas.drawLine(x1, y1, x2, y2, lPaint);
                break;
        }
        lPaint.setColor(Color.WHITE);
    }
}