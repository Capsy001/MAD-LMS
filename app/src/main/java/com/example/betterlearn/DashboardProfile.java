package com.example.betterlearn;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class DashboardProfile extends Fragment {


    public static final String TAG = "TAG";
    TextView name;
    TextView email;
    TextView accType;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading . .");
        dialog.show();

        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_dashboard_profile, container, false);



        name= (TextView)rootView.findViewById(R.id.name_profile);
        email= (TextView)rootView.findViewById(R.id.email_profile);
        accType= (TextView)rootView.findViewById(R.id.accounttype_profile);
        TextView inst_crs=rootView.findViewById(R.id.profile_inst_crs);
        TextView prof1=rootView.findViewById(R.id.prof1);
        TextView prof2=rootView.findViewById(R.id.prof2);
        TextView prof3=rootView.findViewById(R.id.prof3);

        List<TextView> profs = new ArrayList<TextView>() {};
        profs.add(prof1);
        profs.add(prof2);
        profs.add(prof3);


        fAuth= FirebaseAuth.getInstance();  //initialize firebase authentication instance
        fStore= FirebaseFirestore.getInstance();    ////initialize firebase database instance
        userID= fAuth.getCurrentUser().getUid();    //get current user's UserID


        DocumentReference documentReference=fStore.collection("users").document(userID);    //document reference for current user's document

        //ddd
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {





                if (task.isSuccessful()) {
                    DocumentSnapshot value = task.getResult();

                    name.setText(value.getString("name").toString());
                    email.setText(value.getString("email").toString());

                    if(value.getBoolean("type").booleanValue()==false) {
                        accType.setText("Teacher");
                        inst_crs.setText("Institutes");

                        CollectionReference institutes=fStore.collection("institutes");
                        institutes.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {



                                    if(task.isSuccessful()) {
                                        List<DocumentSnapshot> listDocument = task.getResult().getDocuments();

                                        List<String> items = new ArrayList<String>() {};
                                        for (DocumentSnapshot document : listDocument) {

                                            //selecting user's institutes
                                            if (document.get("UserID").toString().equals(userID)) {


                                                String institute = document.get("InstituteName").toString();
                                                items.add(institute);

                                            }
                                        }

                                        int i=0;
                                        for(String inst : items){

                                            profs.get(i).setText(inst);

                                            i++;

                                            if (i >= 3){
                                                break;
                                            }


                                        }
                                    }




                            }
                        });

                    }
                    else if(value.getBoolean("type").booleanValue()==true) {
                        accType.setText("Student");
                        inst_crs.setText("Courses");


                        CollectionReference institutes=fStore.collection("enrolled")
                                .document(userID).collection("courses");


                        institutes.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {



                                if(task.isSuccessful()) {
                                    List<DocumentSnapshot> listDocument = task.getResult().getDocuments();

                                    List<String> items = new ArrayList<String>() {};
                                    for (DocumentSnapshot document : listDocument) {

                                        //selecting user's courses

                                            String institute = document.get("course").toString();
                                            items.add(institute);

                                    }

                                    int i=0;
                                    for(String crs : items){

                                        profs.get(i).setText(crs);

                                        i++;

                                        if (i >= 3){
                                            break;
                                        }


                                    }
                                }




                            }
                        });




                    }

                    if (value.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + value.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }


            }
        });
        //dddd


        dialog.dismiss();



        return rootView;


    }




}