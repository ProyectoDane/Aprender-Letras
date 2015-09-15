package dane.com.gyl.dane;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class AyudaActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        //Mostrar texto en contenedor.
        String htmlText = " %s ";
        String myData = "<html><body style=\"text-align:justify; font-size:11pt;\"> "+getString(R.string.contenido_ayuda)+" </body></Html>";
        WebView webView = (WebView) findViewById(R.id.text_container);
        webView.loadData(String.format(htmlText, myData), "text/html; charset=UTF-8", "utf-8");
        /*

        */
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ayuda, menu);
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

/*    @Override
    public void onBackPressed() {
    }*/
}
