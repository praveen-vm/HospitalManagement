package com.example.ceghospital;

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

public class FirebaseDoctor {
    FirebaseAuth auth ;
    FirebaseUser user ;
    FirebaseDatabase database ;
    DatabaseReference databaseReference ;

    private Doctor doc ;
    private ArrayList<Doctor> doctor = new ArrayList<>();

    public FirebaseDoctor() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference( "Doctor");
    }
    public interface DataStatus {
        void DataView(List<Doctor> doctors, List<String>  keys);
        void DataInsert();
        void DataIsUpdated();
        void DataIsDeleted();
    }
    public void readdoc(final DataStatus Ds){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                doctor.clear();
                List<String> keys = new ArrayList<>( );
                for (DataSnapshot keyNode : dataSnapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                     Doctor patient = keyNode.getValue(Doctor.class);
                    doctor.add( patient );
                }
               Ds.DataView( doctor, keys );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void update(Doctor D)
    {
        String key=databaseReference.push().getKey();
        databaseReference.child(key).setValue(D);
    }

}
