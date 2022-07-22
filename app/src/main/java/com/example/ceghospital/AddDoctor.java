package com.example.ceghospital;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AddDoctor extends AppCompatActivity {
    EditText name;
    EditText spec;
    EditText phone;
    EditText email,num;
    RadioGroup r;

    Button sub,ret;
    FirebaseAuth A;
    public static String mail="",pass="";
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.add_doctor);
        name=findViewById(R.id.Doctorid);
        spec=findViewById(R.id.Specilation);
        phone=findViewById(R.id.contactPhone);
        email=findViewById(R.id.email25);
         r=findViewById(R.id.grp);
        num=findViewById(R.id.num);
        sub=findViewById(R.id.accept);
        ret=findViewById(R.id.returna);
        email.setText(mail);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LinearProgressIndicator a=findViewById(R.id.bar1);
                a.setVisibility(View.VISIBLE);
                if(name.getText().toString().isEmpty()||spec.getText().toString().isEmpty()||num.getText().toString().isEmpty()||phone.getText().toString().isEmpty()||email.getText().toString().isEmpty())
                {
                    Snackbar.make(view,"No Blanks are allowed",Snackbar.LENGTH_LONG).show();
                    return;
                }
                Doctor D=new Doctor(name.getText().toString(),spec.getText().toString(),num.getText().toString(),phone.getText().toString(),email.getText().toString());
                FirebaseDoctor f=new FirebaseDoctor();
                finalcreate(mail,pass,view);
                f.update(D);
                MainActivity.D=D;
                a.setVisibility(View.INVISIBLE);
                Snackbar.make(view,"created successfully",10000);
                Intent i=new Intent(AddDoctor.this,DoctorActivity.class);
                startActivity(i);


            }
        });
        ret.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(AddDoctor.this,NewUser.class);
                startActivity(i);
            }
        });


    }
    public boolean finalcreate(String mail,String pass,final View v) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {



                } else {
                    Toast.makeText(getApplicationContext(), "User could not be created", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
        return true;
    }
}
