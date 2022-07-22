package com.example.ceghospital;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Patient P ;
    public static Doctor D ;
    public static String k;
    FirebaseAuth auth;
    Button b1, b3;
    EditText txt;

    public MainActivity()
    {
        P = new Patient();
        D = new Doctor();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressBar p;
        b1 = (Button) findViewById(R.id.btDocteur);

        b3 = (Button) findViewById(R.id.btPatient);

        auth = FirebaseAuth.getInstance();


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.login_doctor, null);

                final EditText mEmail = (EditText) mView.findViewById(R.id.email25);
                final EditText mPassword = (EditText) mView.findViewById(R.id.password);

                Button mLogin = (Button) mView.findViewById(R.id.login);
                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mEmail.getText().toString().equals("") || mPassword.getText().toString().equals("")) {
                            Snackbar.make(v, "Blank fields not allowed", 3000).show();

                        } else {


                            if (mPassword.getText().toString().contains("doc$246")) {
                                auth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            new FirebaseDatabaseHelperDoctor().readPatients(new FirebaseDatabaseHelperDoctor.DataStatus() {
                                                @Override
                                                public void DataIsLoaded(List<Doctor> doctor, List<String> keys) {
                                                    for (Doctor x : doctor) {
                                                        if (mEmail.getText().toString().contains(x.getEmail())) {
                                                            D = x;

                                                            Intent i = new Intent(getApplicationContext(), DoctorActivity.class);
                                                            startActivity(i);

                                                            Toast.makeText(getApplicationContext(), "User logged in successfully", Toast.LENGTH_SHORT).show();
                                                            return;
                                                        }
                                                    }
                                                    Toast.makeText(getApplicationContext(), "Network Issue", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                        } else {
                                            Toast.makeText(getApplicationContext(), "User could not be logged in", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(getApplicationContext(), "Your are not a Doctor", Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                });

                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();

                dialog.show();

            }


        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.login_patient, null);

                final EditText mEmail = (EditText) mView.findViewById(R.id.email25);
                final EditText mPassword = (EditText) mView.findViewById(R.id.password);
                Button mLogin = (Button) mView.findViewById(R.id.login);
                Button mInscription = (Button) mView.findViewById(R.id.inscription);

                //connecter et verification des champs

                mLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (mEmail.getText().toString().equals("") || mPassword.getText().toString().equals("")) {
                            Toast.makeText(getApplicationContext(), "Blank fields not allowed", Toast.LENGTH_SHORT).show();
                        } else {
                            auth.signInWithEmailAndPassword(mEmail.getText().toString(), mPassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        new FirebaseDatabaseHelper().readPatients(new FirebaseDatabaseHelper.DataStatus() {
                                            @Override
                                            public void DataIsLoaded(List<Patient> patients, List<String> keys) {
                                                for (Patient x : patients) {
                                                    if (mEmail.getText().toString().contains(x.getEmail())) {
                                                        P = x;
                                                        Toast.makeText(getApplicationContext(), "User logged in successfully", Toast.LENGTH_SHORT).show();
                                                        Intent i = new Intent(getApplicationContext(), PatientActivity.class);
                                                        startActivity(i);
                                                        return;
                                                    }
                                                }
                                                Toast.makeText(getApplicationContext(), "Network Failure", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void DataIsUpdated() {

                                            }

                                            @Override
                                            public void DataIsDeleted() {

                                            }
                                        });


                                    } else {
                                        Toast.makeText(getApplicationContext(), "User could not be logged in", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }

                    }
                });

                //inscription des patient

                mInscription.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent i = new Intent(getApplicationContext(), NewUser.class);
                        startActivity(i);

                    }
                });


                mBuilder.setView(mView);
                AlertDialog dialog = mBuilder.create();
                dialog.show();

            }


        });


    }
    public void fun() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("Mynoti", "My", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = MainActivity.this.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this, "1")
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Notification title")
                .setContentText("Content text")
                .setAutoCancel(true);

        // Obtain NotificationManager system service in order to show the notification
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(MainActivity.this);
        managerCompat.notify(1, mBuilder.build());
    }


}

