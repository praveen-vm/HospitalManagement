package com.example.ceghospital;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Vibrator;

public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Vibrator vibrator =(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(2000);
            Intent i=new Intent(context,Growth.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
            Uri notific= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
            Ringtone r= RingtoneManager.getRingtone(context,notific);
            r.play();
        }
    }
