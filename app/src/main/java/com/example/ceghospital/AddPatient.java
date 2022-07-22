package com.example.ceghospital;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;

public class AddPatient extends AppCompatActivity {

    FirebaseDatabase database ;
    DatabaseReference databaseReference ;

    private Patient patient ;
    public static String patientid,mail="",pass="";

    EditText editTextFirstName;
    EditText editTextLastName;
    EditText editTextCin;
    EditText editTextEmail ;
    EditText editTextAge ;
    EditText editTextAddress;
    EditText editTextPhone ;

    Button Return;
    Button buttonAdd ;
    RecyclerView recyclerViewPatients ;
    private ArrayList<Patient> arrayAdapter = new ArrayList<>(  );
    private ArrayAdapter<Patient> adapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.add_patient);
        if(DoctorActivity.f==1)
        {
            EditText mail=findViewById(R.id.editTextEmail);
            EditText pas=findViewById(R.id.editTextpass);
            mail.setEnabled(true);
            pas.setEnabled(true);
        }
        EditText pas=findViewById(R.id.editTextpass);
        editTextFirstName = (EditText) findViewById( R.id.editTextName);
        editTextLastName = (EditText) findViewById( R.id.editTextLastName);
        editTextCin = (EditText) findViewById( R.id.editTextCin );
        editTextEmail = (EditText) findViewById( R.id.editTextEmail );
        editTextAge = (EditText) findViewById( R.id.editTextAge );
        editTextAddress = (EditText) findViewById( R.id.editTextAddress);
        editTextPhone = (EditText) findViewById( R.id.editTextPhone );
        buttonAdd = (Button) findViewById( R.id.buttonAdd);
        Return = (Button) findViewById( R.id.buttonReturn);
        recyclerViewPatients = (RecyclerView) findViewById( R.id.recyclerViewPatients );
        adapter = new ArrayAdapter<Patient>(this, android.R.layout.simple_list_item_1, arrayAdapter  );



        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference( "Patient");
        editTextEmail.setText(mail);
        pas.setText(pass);
        patient = new Patient( );

        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(DoctorActivity.f==0) {
                    Intent intent = new Intent( AddPatient.this, NewUser.class );
                    startActivity(intent);
                }
                else
                {
                    Intent intent1 = new Intent(AddPatient.this,DoctorActivity.class);
                    startActivity(intent1);
                }
            }
        } );

        buttonAdd.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getValues();

                if (TextUtils.isEmpty( patientid )) {
                    LinearProgressIndicator P=findViewById(R.id.bar);
                    P.setVisibility(View.VISIBLE);
                    String id = databaseReference.push().getKey();
                    Patient patient = new Patient( id,  editTextFirstName.getText().toString(),  editTextLastName.getText().toString(),  editTextAddress.getText().toString(),  editTextPhone.getText().toString(),  editTextEmail.getText().toString(),  editTextAge.getText().toString(),  editTextCin.getText().toString()  );
                    if(editTextAddress.getText().toString().isEmpty()||editTextLastName.getText().toString().isEmpty()||editTextAddress.getText().toString().isEmpty()||editTextPhone.getText().toString().isEmpty()||editTextEmail.getText().toString().isEmpty()||editTextAge.getText().toString().isEmpty()||  editTextCin.getText().toString().isEmpty())
                    {
                        Snackbar.make(v,"No Blanks are allowed",Snackbar.LENGTH_LONG).show();
                        P.setVisibility(View.INVISIBLE);
                        return;
                    }
                    finalcreate(mail, pass);
                    databaseReference.child( id ).setValue( patient );
                    P.setVisibility(View.INVISIBLE);
                    MainActivity.P=patient;
                    Toast.makeText( AddPatient.this, "Data inserted ...", Toast.LENGTH_LONG ).show();
                }
                editTextFirstName.setText( "" );
                editTextLastName.setText( "" );
                editTextAddress.setText( "" );
                editTextCin.setText( "" );
                editTextAge.setText( "" );
                editTextPhone.setText( "" );
                editTextEmail.setText( "" );
                if(DoctorActivity.f==0) {
                    Intent intent1 = new Intent(AddPatient.this, PatientActivity.class);
                    startActivity(intent1);
                }
                else
                {
                    Intent intent1 = new Intent(AddPatient.this,DoctorActivity.class);
                    startActivity(intent1);
                }
            }
        } );
    }

    private void getValues()
    {

        patient.setFirstName(editTextFirstName.getText().toString());
        patient.setLastName(editTextLastName.getText().toString());
        patient.setAddress(editTextAddress.getText().toString());
        patient.setCin(editTextCin.getText().toString());
        patient.setAge(editTextAge.getText().toString());
        patient.setPhone(editTextPhone.getText().toString());
        patient.setEmail(editTextEmail.getText().toString());
    }
    public boolean finalcreate(String mail,String pass) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "User created successfully", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(getApplicationContext(), "User could not be created because user may already exist    ", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        return true;
    }
}
