package com.example.ceghospital;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.util.Calendar;

public class DoctorActivity extends AppCompatActivity  {

    FirebaseAuth auth ;
    FirebaseUser user ;
    Button buttonAddPatient;
    Button buttonConsulterListPatients ;
    Button buttonConsulterListRDV ;
    public static int f=0;


       @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_doctor);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        TextView docname=findViewById(R.id.doctorName);
        TextView spec=findViewById(R.id.specc);
        buttonAddPatient = (Button) findViewById( R.id.buttonAddPatient);
        buttonConsulterListPatients = (Button) findViewById( R.id.buttonConsulterListPatients );
        buttonConsulterListRDV = (Button) findViewById( R.id.buttonConsulterRDV );
           Calendar calendar = Calendar.getInstance();
           String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

           TextView textViewDate = findViewById(R.id.text_view_date);
           textViewDate.setText(currentDate);
           docname.setText(MainActivity.D.getName());
           spec.setText(MainActivity.D.getSpecilication());

           buttonAddPatient.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   f=1;
                   Intent intent = new Intent( DoctorActivity.this, AddPatient.class );
                   startActivity(intent);



               }
           } );
           buttonConsulterListPatients.setOnClickListener( new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent intent1 = new Intent( DoctorActivity.this,ConsulterListPatients.class );
                   startActivity(intent1);

               }
           } );

           buttonConsulterListRDV.setOnClickListener( new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   ConsulterListRDV.id=1;

                   Intent intent3 = new Intent( DoctorActivity.this,ConsulterListRDV.class);
                   startActivity(intent3);
               }
           } );
       }

    public void signout(View v)
    {
        auth.signOut();
        finish();
        Intent i = new Intent( this,MainActivity.class );
        startActivity(i);
    }
}
