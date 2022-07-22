package com.example.ceghospital;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;

import androidx.annotation.RequiresApi;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PatientActivity extends AppCompatActivity   {

    private static final boolean TODO = true;
    FirebaseAuth auth ;
    Button addpatient, buttonRDV;

    FirebaseDatabase database ;
    DatabaseReference ref ;
    Patient patient ;
    int y=0;


    public static String patientid;

    EditText nom ;
    EditText prenom ;
    EditText cin ;
    EditText adress ;
    EditText tel ;
    EditText email;
    EditText age ;
    Button addrdvv;
    TextView pa,sp,nn,na;
    private DrawerLayout drawer;
    ProgressBar px;
    final static List<RDV> finalTem = new ArrayList<>();
    final static List<String> keyz=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_patient);

        px=findViewById(R.id.pb);
        pa=findViewById(R.id.doctorName1);
        sp=findViewById(R.id.spec);

        auth = FirebaseAuth.getInstance();

        new FirebaseDatabaseHelperRDV().readRDV(new FirebaseDatabaseHelperRDV.DataStatus() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void DataIsLoaded(ArrayList<RDV> appointments, List<String> keys) {
                if(appointments==null)return;
                if(appointments.size()==0){
                    Toast.makeText( getApplicationContext(), "No appointments were made", Toast.LENGTH_SHORT).show();
                    return;
                }
                finalTem.clear();
                keyz.clear();
                for(int i=0;i<appointments.size();i++) {
                    if(appointments.get(i)==null){
                       // Toast.makeText( getApplicationContext(), "help", Toast.LENGTH_SHORT).show();
                        continue;
                    }
                    if(appointments.get(i).getEmail()==null) {
                       // Toast.makeText( getApplicationContext(), "help1", Toast.LENGTH_SHORT).show();
                       continue;
                    }
                    if (appointments.get(i)!=null&&appointments.get(i).getEmail().equals(MainActivity.P.getEmail())) {
                        if ("-1".equals(appointments.get(i).getLastName())) {

                        } else if (appointments!=null&&appointments.get(i).isStatus()) {
                            y++;
                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                            LocalDateTime now = LocalDateTime.now();
                            LocalDateTime t = LocalDateTime.parse((appointments.get(i).getLastName()), dtf);
                            long p = t.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                            setAlarm(p);
                            appointments.get(i).setLastName("-1");
                            new FirebaseDatabaseHelperRDV().alarmset(keys.get(i), appointments.get(i));
                        }
                        if(appointments.get(i)!=null) {
                            finalTem.add(appointments.get(i));
                            keyz.add(keys.get(i));
                        }

                    }
                }
            }

            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });




        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());



        TextView textViewDate = findViewById(R.id.text_view_date);
        textViewDate.setText(currentDate);

        if(y!=0)
        {
            Snackbar.make(getCurrentFocus(),"Appointments were fixed and alarms were setted. Check your appointment list",4000);
        }
        addrdvv = (Button) findViewById( R.id.buttonPredreRendezVous );
        addrdvv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent( PatientActivity.this, AddRDV.class );
                startActivity(intent);

            }
        } );

        buttonRDV = (Button) findViewById( R.id.buttonConsulterListeRendezVous );
        buttonRDV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConsulterListRDV.id=0;

                Intent intent3 = new Intent( PatientActivity.this,ConsulterListRDV.class );
                startActivity(intent3);
            }
        } );

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        drawer = findViewById( R.id.draw_layout );


        pa.setText(MainActivity.P.getFirstName());
        sp.setText(MainActivity.P.getAge()+" years");


        px.setVisibility(View.INVISIBLE);



        }
    private void setAlarm(long time) {
        AlarmManager am = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
        Intent i = new Intent(this.getApplicationContext(), MyBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast( this.getApplicationContext(), 24567577, i, 0);
        am.set(AlarmManager.RTC_WAKEUP,time,pi);
        Toast.makeText( getApplicationContext(), "Alarm is set", Toast.LENGTH_SHORT).show();
    }



    public void signout(View v)
    {
        auth.signOut();
        finish();
        Intent i = new Intent( this,MainActivity.class );
        startActivity(i);
    }



    @Override
    public void onBackPressed() {
        finish();
        Intent i = new Intent( this,MainActivity.class );
        startActivity(i);
    }

}
