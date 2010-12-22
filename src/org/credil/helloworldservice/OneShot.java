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
import android.content.BroadcastReceiver;
import android.widget.Toast;

public class OneShot extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, R.string.one_shot_received,
                        Toast.LENGTH_SHORT).show();
//	Log.i("oneshot", intent.getAction());
    }
}

