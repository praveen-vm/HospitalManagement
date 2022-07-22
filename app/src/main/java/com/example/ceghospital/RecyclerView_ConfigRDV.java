package com.example.ceghospital;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RecyclerView_ConfigRDV {

    private Context mContext ;
    private RDVAdapter rdvAdapter;
    private int id=-1;



    private void setAlarm(long time) {
        AlarmManager am = (AlarmManager) mContext.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(mContext, MyBroadcastReceiver.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pi = PendingIntent.getBroadcast(mContext, 24567577, i, 0);
        am.set(AlarmManager.RTC_WAKEUP,time,pi);
        Toast.makeText(mContext, "Alarm is set", Toast.LENGTH_SHORT).show();
    }
    public void setConfig(RecyclerView recyclerView, Context context, List<RDV> appoint, List<String> keys,int k){
        mContext = context;
        rdvAdapter = new RDVAdapter( appoint, keys );
        id=k;
        recyclerView.setLayoutManager( new LinearLayoutManager( context ) );
        recyclerView.setAdapter( rdvAdapter );
    }

    class RDVItemView extends RecyclerView.ViewHolder {

        private TextView patientname;
        private TextView age;
        private TextView issue;
        private TextView details;
        private TextView date,time,status,docname,docspec;
        private Button b;
        String key;


        public RDVItemView(ViewGroup parent) {
            super( LayoutInflater.from( mContext ).
                    inflate( R.layout.rdv_items, parent, false ) );


           patientname = (TextView) itemView.findViewById( R.id.patientname );
            age = (TextView) itemView.findViewById( R.id.age1 );
            issue = (TextView) itemView.findViewById( R.id.issue );
            details = (TextView) itemView.findViewById( R.id.details);
            date = (TextView) itemView.findViewById( R.id.date1 );
            time = (TextView) itemView.findViewById( R.id.time1 );
            status=(TextView) itemView.findViewById( R.id.status);
            docname=(TextView) itemView.findViewById( R.id.docname);
            docspec=(TextView) itemView.findViewById( R.id.docsep);
            b=(Button) itemView.findViewById(R.id.accepts);



        }

        public void bind(final RDV appoitment, final String key) {

            patientname.setText(appoitment.getFirstName());
            age.setText(appoitment.getAge()+" years old");
            issue.setText(appoitment.getIssue());
            details.setText(appoitment.getDetail());
            date.setText(appoitment.getDateRDV());
            time.setText(appoitment.getHour());
            if(appoitment.isStatus())
            {
                status.setText("Accepted");
                status.setTextColor(Color.GREEN);
                docname.setText(appoitment.getDoc());
                docspec.setText(appoitment.getSpec());

            }
            else {
                status.setText("Waiting");
                status.setTextColor(Color.RED);
                docname.setText("NotSelected Yet");
                docspec.setText("NotSelected Yet");
            }
            b.setEnabled(true);
            b.setVisibility(View.VISIBLE);
            if(id==0||appoitment.isStatus()){
                b.setEnabled(false);
                b.setVisibility(View.INVISIBLE);
            }
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(mContext);
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.O)
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:

                                     DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
                                    LocalDateTime now = LocalDateTime.now();
                                    LocalDateTime t= LocalDateTime.parse(appoitment.getLastName(),dtf);
                                    long p=t.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()- now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
                                    setAlarm(p);
                                    Snackbar.make(mContext,v,Long.toString(p),3000).show();
                                    RDV temp=new RDV(appoitment,MainActivity.D.getName(),MainActivity.D.getSpecilication(),true);
                                    new FirebaseDatabaseHelperRDV().upDateRDV(key, temp, new FirebaseDatabaseHelperRDV.DataStatus() {
                                        @Override
                                        public void DataIsLoaded(ArrayList<RDV> appointments, List<String> keys) {
                                            Snackbar.make(mContext,v,"Appointment Added Success",1000).show();
                                        }

                                        @Override
                                        public void DataIsUpdated() {

                                        }

                                        @Override
                                        public void DataIsDeleted() {

                                        }
                                    });
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    builder.setMessage("Are you sure?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }
            });
            this.key=key;


        }
    }

    class RDVAdapter extends RecyclerView.Adapter<RDVItemView>{

            private List<RDV> appoitmentList;
            private  List<String> keys;

            public RDVAdapter(List<RDV> appoitmentList, List<String> keys) {
                this.appoitmentList = appoitmentList;
                this.keys = keys;
            }


            @NonNull
            @Override
            public RDVItemView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new RDVItemView( viewGroup );
            }

            @Override
            public void onBindViewHolder(@NonNull RDVItemView rdvItemView, int i) {
                rdvItemView.bind( appoitmentList.get( i ), keys.get( i ) );
            }

            @Override
            public int getItemCount() {
                return appoitmentList.size();
            }
        }

}
