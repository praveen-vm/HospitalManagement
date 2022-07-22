package com.example.ceghospital;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ConsulterListRDV extends AppCompatActivity {


   private RecyclerView recyclerView ;
    public static int id=-1;  //0 patient 1 doctor
    public  static Context con=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.listrdv);

        recyclerView = (RecyclerView) findViewById( R.id.recyclerViewRDV );

        new FirebaseDatabaseHelperRDV().readRDV( new FirebaseDatabaseHelperRDV.DataStatus() {
            @Override
            public void DataIsLoaded(ArrayList<RDV> appointments, List<String> keys) {

                if(id==0)new RecyclerView_ConfigRDV().setConfig( recyclerView, ConsulterListRDV.this,PatientActivity.finalTem, PatientActivity.keyz,id);
                 else new RecyclerView_ConfigRDV().setConfig( recyclerView, ConsulterListRDV.this, appointments, keys,id);
            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });
    }
  /*  private void setAlarm(long time) {
        //getting the alarm manager
        Context mContext=con;
        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(mContext, MyBroadcastReceiver.class);

        //creating a pending intent using the intent
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 1, i, 0);
        am.set(AlarmManager.RTC_WAKEUP,time,pi);
        Toast.makeText(mContext, "Alarm is set", Toast.LENGTH_SHORT).show();
    }*/
}
