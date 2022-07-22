package com.example.ceghospital;

import androidx.annotation.NonNull;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    FirebaseAuth auth ;
    FirebaseUser user ;
    FirebaseDatabase database ;
    DatabaseReference databaseReference ;

    private Patient patient ;
    private ArrayList<Patient> patients = new ArrayList<>(  );
    private ArrayAdapter<Patient> adapter ;

    public interface DataStatus {
        void DataIsLoaded(List<Patient> patients, List<String>  keys);
        void DataIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference( "Patient");

    }

    public void readPatients( final DataStatus dataStatus){
        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                patients.clear();
                List<String> keys = new ArrayList<>( );
                for (DataSnapshot keyNode : dataSnapshot.getChildren())
                {
                    keys.add(keyNode.getKey());
                    Patient patient = keyNode.getValue(Patient.class);
                    patients.add( patient );
                }

                dataStatus.DataIsLoaded( patients, keys );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    public void upDatePatient (String key, Patient patient, final DataStatus dataStatus){

        databaseReference.child( key ).setValue( patient )
                .addOnSuccessListener( new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        dataStatus.DataIsUpdated();

                    }
                } );
     }

     public void deletePatient (String key, final DataStatus dataStatus)
     {
         databaseReference.child( key ).setValue( null )
                 .addOnSuccessListener( new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void aVoid) {

                         dataStatus.DataIsDeleted();
                     }
                 } );
     }

}
