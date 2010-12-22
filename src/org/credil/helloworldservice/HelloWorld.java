package org.credil.helloworldservice;

import org.credil.helloworldservice.R;
import android.app.Activity;
import android.content.Context;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.app.PendingIntent;
import android.app.AlarmManager;
import java.util.Calendar;
import android.content.BroadcastReceiver;

public class HelloWorld extends Activity {
    Toast mToast;

    private static final String LOG_TAG = "HelloWorld";

    /* public HelloWorldServiceInterface hws; */
    public TextView helloBox;
    public ServiceConnection serviceConnection;

    private OnClickListener mOneShotListener = new OnClickListener() {
	public void onClick(View v) {
//		mToast = Toast.makeText(HelloWorld.this, R.string.fuck_toast, Toast.LENGTH_LONG);
//		mToast.show();
		Intent intent = new Intent(HelloWorld.this, OneShot.class);
//		intent.setAction("fuck.action");
//		Log.i("Fuck", "action: " + intent.getAction());
		PendingIntent sender = PendingIntent.getBroadcast(HelloWorld.this,
			0, intent, 0);
/*
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		calendar.add(Calendar.SECOND, 2);

		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), sender);
*/
		try {
			sender.send();
		} catch (PendingIntent.CanceledException e) {
			Log.i("fuck", "Error while send intent");
		}
	}
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hello);

	Button button = (Button)findViewById(R.id.one_shot);
	button.setOnClickListener(mOneShotListener);

	Intent intent = new Intent(HelloWorld.this, OneShot.class);

        helloBox = (TextView)findViewById(R.id.HelloView01);
        helloBox.setText("start");

        /*
        serviceConnection = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.e("HelloWorld", "Service connected");
                hws = HelloWorldServiceInterface.Stub.asInterface(service);
                if(hws != null) {
                    helloBox.setText("connected");
                    Log.e("HelloWorld", "Connected");
                } else {
                    Log.e("HelloWorld", "Failed to connect");
                }
            }  

            public void onServiceDisconnected(ComponentName name) {
                Log.e("HelloWorld","disconnected");
                hws = null;
            }
        };

        // I think it should really be:
        //bindService(ServiceManager.getService("org.credilk..."),
        //            serviceConnection, ???):

        bindService(new Intent("org.credil.helloworldservice.HelloWorldServiceInterface.START_SERVICE"),
                    serviceConnection, Context.BIND_AUTO_CREATE);
        */
        
        // BnServiceManager.getDefault();
        String[] services;
        
        try {
            services = ServiceManager.listServices();
        } catch (RemoteException e) {
            Log.e("HelloWorld", "No list of services.");
            return;
        }

        Log.e("HelloWorld", "services is "+(services.length));
        int i;
        for(i=0; i<services.length; i++) {
            Log.e("HelloWorld", "services["+i+"]="+ services[i]);
        }

        IBinder helloworld = ServiceManager.getService("org.credil.helloworldservice.HelloWorldServiceInterface");
        if(helloworld != null) {
            Log.e(LOG_TAG, "hello "+helloworld.toString());
        } else {
            Log.e(LOG_TAG, "hello service not found ");
        }
    }
}
