package com.example.ceghospital;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class NewUser extends AppCompatActivity {

    FirebaseAuth auth;
    EditText eMail, password;
    Button inscription ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.newusersigin);

         eMail = (EditText) findViewById( R.id.email25) ;
         password = (EditText) findViewById( R.id.password ) ;
         inscription = (Button) findViewById( R.id.inscrir)  ;

        auth = FirebaseAuth.getInstance();


    }

    public void  createUser(View v)
    {
        if (eMail.getText().toString().equals( "" ) || password.getText().toString().equals( "" )) {
            Toast.makeText( getApplicationContext(), "Blank not allowed", Toast.LENGTH_SHORT ).show();
        } else {
            String email = eMail.getText().toString();
            final String mpassword = password.getText().toString();
            if(mpassword.contains("doc$246")) {
                AddDoctor.mail=email;
                AddDoctor.pass=mpassword;
                Intent i = new Intent( getApplicationContext(), AddDoctor.class );
                startActivity( i );
            }
            else
            {
                AddPatient.mail=email;
                AddPatient.pass=mpassword;
                Intent i = new Intent( getApplicationContext(), AddPatient.class );
                startActivity( i );
            }


        }
    }

    }

