package com.example.ceghospital;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class EditDeletePatients extends AppCompatActivity {

    FirebaseDatabase database ;
    private DatabaseReference databaseReference ;

    EditText editTextNom ;
    EditText editTextPrenom;
    EditText editTextCin;
    EditText editTextEmail ;
    EditText editTextAge ;
    EditText editTextAdresse ;
    EditText editTextPhone ;

    Button buttonRetour ;
    Button buttonEdit ;
    Button buttonDelete ;

    private String key ;
    private String nom ;
    private String prenom ;
    private String eMail ;
    private String phone ;
    private String cin ;
    private String age ;
    private String adrese ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_delete_patients );

        key = getIntent().getStringExtra( "Key" );
        nom = getIntent().getStringExtra( "LastName" );
        prenom = getIntent().getStringExtra( "FirstName" );
        eMail = getIntent().getStringExtra( "Email" );
        phone = getIntent().getStringExtra( "Phone" );
        age = getIntent().getStringExtra( "Age" );
        cin = getIntent().getStringExtra( "Cin" );
        adrese = getIntent().getStringExtra( "Address" );



        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference( "Patient");

        editTextNom = (EditText) findViewById( R.id.editTextName);
        editTextNom.setText( nom );

        editTextPrenom = (EditText) findViewById( R.id.editTextLastName);
        editTextPrenom.setText( prenom );

        editTextCin = (EditText) findViewById( R.id.editTextCin );
        editTextCin.setText( cin );

        editTextEmail = (EditText) findViewById( R.id.editTextEmail );
        editTextEmail.setText( eMail );

        editTextAge = (EditText) findViewById( R.id.editTextAge );
        editTextAge.setText( age );

        editTextAdresse = (EditText) findViewById( R.id.editTextAddress);
        editTextAdresse.setText( adrese );

        editTextPhone = (EditText) findViewById( R.id.editTextPhone );
        editTextPhone.setText( phone );

        buttonEdit = (Button) findViewById( R.id.buttonEdit );
        buttonDelete = (Button) findViewById( R.id.buttonDelete );
        buttonRetour = (Button) findViewById( R.id.buttonReturn);

        buttonEdit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Patient patient = new Patient( );
                patient.setFirstName( editTextNom.getText().toString() );
                patient.setLastName( editTextPrenom.getText().toString() );
                patient.setEmail( editTextEmail.getText().toString() );
                patient.setAge( editTextAge.getText().toString() );
                patient.setAddress( editTextAdresse.getText().toString() );
                patient.setPhone( editTextPhone.getText().toString() );
                patient.setCin( editTextCin.getText().toString() );
                if(editTextNom.getText().toString().isEmpty()||editTextPrenom.getText().toString().isEmpty()||editTextEmail.getText().toString().isEmpty()||
                        editTextAge.getText().toString().isEmpty()||editTextAdresse.getText().toString().isEmpty() ||editTextPhone.getText().toString().isEmpty()|| editTextCin.getText().toString().isEmpty())
                {
                    Snackbar.make(v,"No Blansk are allowed",3000).show();
                    return;
                }
                new FirebaseDatabaseHelper().upDatePatient( key, patient, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Patient> patients, List<String> keys) {

                    }



                    @Override
                    public void DataIsUpdated() {

                        Toast.makeText( EditDeletePatients.this, "Patient has been updated succesfully ...", Toast.LENGTH_LONG ).show();

                    }

                    @Override
                    public void DataIsDeleted() {

                    }
                } );

            }
        } );

        buttonDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FirebaseDatabaseHelper().deletePatient( key, new FirebaseDatabaseHelper.DataStatus() {
                    @Override
                    public void DataIsLoaded(List<Patient> patients, List<String> Keys) {

                    }


                    @Override
                    public void DataIsUpdated() {

                    }

                    @Override
                    public void DataIsDeleted() {

                        Toast.makeText( EditDeletePatients.this, "Patient has been deleted succesfuly ...", Toast.LENGTH_LONG ).show();
                        finish();return;
                    }
                } );
            }
        } );
        buttonRetour.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( EditDeletePatients.this,ConsulterListPatients.class );
                startActivity(intent);

            }
        } );
    }



}
