package dane.com.gyl.dane;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.location.Criteria;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.Toast;


public class AboutActivity extends ActionBarActivity {
    static Context public_context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        public_context = getApplicationContext();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
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
/*
    @Override
    protected void onResume() {
        super.onResume();
        LocationManager milocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        String provider = milocManager.getBestProvider(criteria, false);
        milocManager.requestLocationUpdates(milocManager.GPS_PROVIDER, 400, 1, this);
    }*/
}