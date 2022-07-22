package com.example.ceghospital;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class RecyclerView_Config {

    private Context mContext ;
    private PatientAdapter patientAdapter;

    public void setConfig(RecyclerView recyclerView, Context context, List<Patient> patients, List<String> keys)
    {
        mContext = context;
        patientAdapter = new PatientAdapter(patients, keys);
        recyclerView.setLayoutManager( new LinearLayoutManager( context ) );
        recyclerView.setAdapter( patientAdapter );

    }

    class PatientItemView extends RecyclerView.ViewHolder {

        private TextView textViewNom;
        private TextView textViewPrenom;
        private TextView textViewEmail;
        private TextView textViewPhone;
        private TextView textViewAge ;
        private TextView textViewAdresse ;
        private TextView textViewCin ;

        private String key;

        public PatientItemView(ViewGroup parent) {
            super( LayoutInflater.from( mContext ).
                    inflate( R.layout.patient_list_item, parent, false ) );


            textViewNom = (TextView) itemView.findViewById( R.id.first);
            textViewPrenom = (TextView) itemView.findViewById( R.id.lasts);
            textViewEmail = (TextView) itemView.findViewById( R.id.email25);
            textViewPhone = (TextView) itemView.findViewById( R.id.phone );
            textViewAge = (TextView) itemView.findViewById( R.id.age );
            textViewAdresse = (TextView) itemView.findViewById( R.id.add );
            textViewCin = (TextView) itemView.findViewById( R.id.last );

            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent( mContext, EditDeletePatients.class );
                    intent.putExtra( "Key", key );
                    intent.putExtra( "LastName", textViewNom.getText().toString() );
                    intent.putExtra( "FirstName", textViewPrenom.getText().toString() );
                    intent.putExtra( "Email", textViewEmail.getText().toString() );
                    intent.putExtra( "Phone", textViewPhone.getText().toString() );
                    intent.putExtra( "Age", textViewAge.getText().toString() );
                    intent.putExtra( "Address", textViewAdresse.getText().toString() );
                    intent.putExtra( "Cin", textViewCin.getText().toString() );

                    mContext.startActivity( intent );
                }
            } );

        }

        public void bind(Patient patient, String key) {

            textViewNom.setText( patient.getFirstName() );
            textViewPrenom.setText( patient.getLastName() );
            textViewEmail.setText( patient.getEmail() );
            textViewPhone.setText( patient.getPhone() );
            textViewAge.setText( patient.getAge() );
            textViewAdresse.setText( patient.getAddress() );
            textViewCin.setText( patient.getCin() );
            this.key = key;

        }
    }

    class PatientAdapter extends RecyclerView.Adapter<PatientItemView>{

            private List<Patient> patientList;
            private  List<String> keys;

            public PatientAdapter(List<Patient> patientList, List<String> keys) {
                this.patientList = patientList;
                this.keys = keys;
            }


            @NonNull
            @Override
            public PatientItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new PatientItemView( viewGroup );
            }

            @Override
            public void onBindViewHolder(@NonNull PatientItemView patientItemView, int i) {

                patientItemView.bind( patientList.get( i ), keys.get( i ) );
            }

            @Override
            public int getItemCount() {
                return patientList.size();
            }
        }

}
