package com.example.ceghospital;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.List;

public class ConsulterListPatients extends AppCompatActivity {


   private RecyclerView recyclerView ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.listpatient);

        recyclerView = (RecyclerView) findViewById( R.id.recyclerViewPatients );

        new FirebaseDatabaseHelper().readPatients( new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Patient> patients, List<String> keys) {
                LinearProgressIndicator P=findViewById(R.id.linearpatient);
                P.setVisibility(View.VISIBLE);
                new RecyclerView_Config().setConfig( recyclerView, ConsulterListPatients.this, patients, keys);
                P.setVisibility(View.INVISIBLE);
            }



            @Override
            public void DataIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        } );


    }
}
