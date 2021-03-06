package fi.bunnyfeet.servicemonitor.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;


public class MainActivity extends Activity {

    private CheckResponseReceiver checkResponseReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            FragmentManager fm = getFragmentManager();
            FragmentTransaction tranny = fm.beginTransaction();
            ServiceFragment sf = new ServiceFragment();
            tranny.add(R.id.top, sf, "servicelist");
            tranny.commit();

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        Intent intent = new Intent(this, CheckService.class);
        startService(intent);
    }

    public void addService(View view) {
        ServiceListItem service = new ServiceListItem("Google", "http://google.com");
        try {
            ServiceFragment sf = (ServiceFragment)getFragmentManager().findFragmentByTag("servicelist");
            sf.addItem(service);
            sf.getAdapter().notifyDataSetChanged();
            Log.d("yo", sf.getItems().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onResume() {
        super.onResume();
        if (checkResponseReceiver == null) {
            checkResponseReceiver = new CheckResponseReceiver();
        }
       LocalBroadcastManager.getInstance(this).registerReceiver(checkResponseReceiver, new IntentFilter(CheckService.CHECK));
    }
    protected void onPause() {
        super.onPause();
        if (checkResponseReceiver != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(checkResponseReceiver);
        }
    }
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(checkResponseReceiver);
        super.onDestroy();
    }

    private class CheckResponseReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("receiver", intent.getStringExtra("url") + ": " + intent.getStringExtra("status"));
        }
    }

}
