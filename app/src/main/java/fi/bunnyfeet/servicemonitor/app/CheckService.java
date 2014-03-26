package fi.bunnyfeet.servicemonitor.app;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.UnknownHostException;

public class CheckService extends Service {
    public static String CHECK = "fi.bunnyfeet.servicemonitor.CHECK";
    public static int OK = 1;
    public static int DOWN = 2;
    public static int BAD_HOST = 3;

    public CheckService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service", "onstartcommand");
        if (intent.getStringExtra("url") != null) {
            checkUrl(intent.getStringExtra("url"));
        }
        else {
            checkUrl("http://bunnyfeet.fi");
        }

        return START_STICKY;
    }

    public int checkUrl(String url) {
        int status = -1;

        new Checker(this).execute(url);

        return status;
    }

    private class Checker extends AsyncTask<String, Integer, Integer> {
        private Context checkerContext;

        public Checker(Context context) {
            checkerContext = context;
        }

        protected Integer doInBackground(String... urls) {
            int status = -1;

            HttpGet req = new HttpGet(urls[0]);
            HttpClient client = new DefaultHttpClient();
            try {
                HttpResponse response = client.execute(req);
                Log.d("response", "response code: " + response.getStatusLine().getStatusCode());

                int responseCode = response.getStatusLine().getStatusCode();

                Intent intent = new Intent(CHECK);
                intent.putExtra("url", urls[0]);
                intent.putExtra("status", "status: " + responseCode);
                LocalBroadcastManager.getInstance(checkerContext).sendBroadcast(intent);

                return responseCode;
            }
            catch(UnknownHostException e) {
                return CheckService.BAD_HOST;
            }
            catch (Exception e) {
                Log.d("fail", e.toString());
                return -1;
            }
        }

    }
}
