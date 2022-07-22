package com.example.ceghospital;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class AddRDV extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    FirebaseDatabase database;
    DatabaseReference databaseReferenceRDV;
    FirebaseAuth auth;

    private RDV appointment;
    public static String appointmentId;

    EditText editTextNom;
    EditText editTextPrenom;
    EditText editTextDate;
    EditText editTextHeur;
    EditText editTextPhone;
    EditText editTextEmail;
    Button buttonRetour;
    Button buttonAdd;
    String t;
    RecyclerView recyclerViewRDV;
    private ArrayList<RDV> arrayAdapterRDV = new ArrayList<>();
    private ArrayAdapter<RDV> rdvAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_add_rdv);
        editTextNom = (EditText) findViewById(R.id.editTextLastName);
        editTextDate = (EditText) findViewById(R.id.editTextDate);
        editTextHeur = (EditText) findViewById(R.id.editTextHeur);
        editTextPhone = (EditText) findViewById(R.id.editTextPhone);
        buttonAdd = (Button) findViewById(R.id.buttonAdd);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        buttonRetour = (Button) findViewById(R.id.buttonReturn);
        recyclerViewRDV = (RecyclerView) findViewById(R.id.recyclerViewRDV);

        rdvAdapter = new ArrayAdapter<RDV>(this, android.R.layout.simple_list_item_1, arrayAdapterRDV);

        database = FirebaseDatabase.getInstance();
        databaseReferenceRDV = database.getReference("Appointment");

        appointment = new RDV();

        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment d = new DateFragment();
                d.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        editTextHeur.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddRDV.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        editTextHeur.setText(String.format("%02d",selectedHour) + ":" + String.format("%02d" ,selectedMinute)+":"+"00");
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select time of your appointment");
                mTimePicker.show();

            }
        });


        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddRDV.this, PatientActivity.class);
                startActivity(intent);
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextDate.getText().toString().isEmpty()||editTextHeur.getText().toString().isEmpty()|| editTextNom.getText().toString().isEmpty()||editTextEmail.getText().toString().isEmpty())
                {
                    Snackbar.make(v,"Blank Fields are not allowed",3000).show();
                    return;
                }

                final String id = databaseReferenceRDV.push().getKey();


                Patient y = MainActivity.P;

                RDV appointment1 = new RDV(id, y.getFirstName(),t+" "+editTextHeur.getText().toString(), editTextDate.getText().toString(), editTextHeur.getText().toString(), y.getEmail(), y.getPhone(), editTextNom.getText().toString(), editTextEmail.getText().toString(), y.getAge());
                databaseReferenceRDV.child(id).setValue(appointment1);


                Toast.makeText(AddRDV.this, "Data inserted ...", Toast.LENGTH_LONG).show();


              /*  editTextPrenom.setText( "" );
                editTextDate.setText( "" );
                editTextHeur.setText( "" );
                editTextPhone.setText( "" );
                editTextEmail.setText( "" );*/

                Intent intent1 = new Intent(AddRDV.this, PatientActivity.class);
                startActivity(intent1);

            }
        });





    }
    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);
        DateFormat.getDateInstance(DateFormat.MEDIUM);
        t= String.format("%d/%s/%s", i, String.format("%02d", i1+1), String.format("%02d", i2));
        String s = DateFormat.getDateInstance(DateFormat.MEDIUM).format(c.getTime());
        editTextDate.setText(s);
}
    private void getValuesRDV () {

        appointment.setFirstName(editTextNom.getText().toString());
        appointment.setLastName(editTextPrenom.getText().toString());
        appointment.setDateRDV(editTextDate.getText().toString());
        appointment.setHour(editTextHeur.getText().toString());
        appointment.setTel(editTextPhone.getText().toString());
        appointment.setEmail(editTextEmail.getText().toString());
    }
}



