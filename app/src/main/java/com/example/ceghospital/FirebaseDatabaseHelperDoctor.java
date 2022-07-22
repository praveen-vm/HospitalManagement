package com.example.ceghospital;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelperDoctor {

    FirebaseAuth auth ;
    FirebaseUser user ;
    FirebaseDatabase database ;
    DatabaseReference databaseReference ;

   private  Doctor doctor ;
    private ArrayList<Doctor> doctors = new ArrayList<>(  );
    private ArrayAdapter<Patient> adapter ;

    public interface DataStatus {
        void DataIsLoaded(List<Doctor> doctor, List<String> keys);
    }

    public FirebaseDatabaseHelperDoctor() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference( "Doctor");

    }

    public void readPatients( final DataStatus dataStatus){
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                doctors.clear();
                List<String> keys = new ArrayList<>( );
                for (DataSnapshot keyNode : dataSnapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    Doctor patient = keyNode.getValue(Doctor.class);
                    doctors.add( patient );
                }

                dataStatus.DataIsLoaded(doctors, keys );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }


}
