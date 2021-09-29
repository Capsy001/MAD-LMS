package StudentFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.betterlearn.Announcement_Adapter;
import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import models.Announcement;


public class StudentAnnouncements extends Fragment  {
    public static FragmentManager fragmentManager;
    GridView coursesGV;
    ArrayList<Announcement> dataModalArrayList;
    FirebaseFirestore db;
    FirebaseFirestore fStore;
    String rInstitutes;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_student_announcements, container, false);
        // Inflate the layout for this fragment
        coursesGV = myView.findViewById(R.id.std_announcemets_sec);
        dataModalArrayList = new ArrayList<>();


        fragmentManager=getFragmentManager();

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();
        // here we are calling a method
        // to load data in our list view.

        GridView gridview = (GridView) myView.findViewById(R.id.std_announcemets_sec);

        CollectionReference subjectsRef = db.collection("institutes");
        Spinner b = (Spinner) myView.findViewById(R.id.spinner1123);
        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        b.setAdapter(adapter);
        subjectsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        String subject = document.getString("InstituteName");
                        subjects.add(subject);
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        });


        b.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapter, View v, int i, long lng) {
                dataModalArrayList.clear();
                gridview.setAdapter(null);
                Spinner Institutes = (Spinner) getView().findViewById(R.id.spinner1123);
                rInstitutes=Institutes.getSelectedItem().toString();
                rInstitutes =  adapter.getItemAtPosition(i).toString();

                fStore=FirebaseFirestore.getInstance();
                db.collection("announcements").whereEqualTo("institutes", rInstitutes).get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                // after getting the data we are calling on success method
                                // and inside this method we are checking if the received
                                // query snapshot is empty or not.
                                if (!queryDocumentSnapshots.isEmpty()) {
                                    // if the snapshot is not empty we are hiding our
                                    // progress bar and adding our data in a list.
                                    List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                                    for (DocumentSnapshot d : list) {

                                        // after getting this list we are passing
                                        // that list to our object class.
                                        Announcement dataModal = d.toObject(Announcement.class);
                                        dataModal.setKey(d.getId());
                                        final String TAG = "MyActivity";
                                        Log.d(TAG,d.getId());
                                        // after getting data from Firebase
                                        // we are storing that data in our array list
                                        dataModalArrayList.add(dataModal);
                                    }
                                    // after that we are passing our array list to our adapter class.
                                    Announcement_Adapter adapter = new Announcement_Adapter(getActivity(), dataModalArrayList);

                                    // after passing this array list
                                    // to our adapter class we are setting
                                    // our adapter to our list view.
                                    coursesGV.setAdapter(adapter);
                                } else {
                                    // if the snapshot is empty we are displaying a toast message.
                                    Toast.makeText(getActivity(), "No Announcements in Database", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // we are displaying a toast message
                        // when we get any error from Firebase.
                        Toast.makeText(getActivity(), "Fail to load data..", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                dataModalArrayList.clear();
                gridview.setAdapter(null);
                loadDatainGridView();
            }
        });

        return myView;

    }

    private void loadDatainGridView() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        //firestore databse initialize


        fStore=FirebaseFirestore.getInstance();
        db.collection("announcements").get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // after getting the data we are calling on success method
                        // and inside this method we are checking if the received
                        // query snapshot is empty or not.
                        if (!queryDocumentSnapshots.isEmpty()) {
                            // if the snapshot is not empty we are hiding our
                            // progress bar and adding our data in a list.
                            List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                            for (DocumentSnapshot d : list) {

                                // after getting this list we are passing
                                // that list to our object class.
                                Announcement dataModal = d.toObject(Announcement.class);

                                // after getting data from Firebase
                                // we are storing that data in our array list
                                dataModalArrayList.add(dataModal);
                            }
                            // after that we are passing our array list to our adapter class.
                            Announcement_Adapter adapter = new Announcement_Adapter(getActivity(), dataModalArrayList);

                            // after passing this array list
                            // to our adapter class we are setting
                            // our adapter to our list view.
                            coursesGV.setAdapter(adapter);
                        } else {
                            // if the snapshot is empty we are displaying a toast message.
                            Toast.makeText(getActivity(), "No Announcements in Database", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // we are displaying a toast message
                // when we get any error from Firebase.
                Toast.makeText(getActivity(), "Fail to load data..", Toast.LENGTH_SHORT).show();
            }
        });
    }



}



/*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();


        userID=fAuth.getCurrentUser().getUid();
        DocumentReference documentReference= fStore.collection("users").document(userID);

        //ddd
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot value = task.getResult();



                    if(value.getBoolean("type").booleanValue()==false) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.tabsView, new TabsTeacher())
                                .commit();
                    }
                    else if(value.getBoolean("type").booleanValue()==true) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.tabsView, new TabsStudent())
                                .commit();
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

        Button logout=findViewById(R.id.logout_dashboard);


    }*/



/*    @Override
    public void onClick(View view) {

        Fragment fragment;

        if(view==myButton) {

            fragment = new Annoucement();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

    }
} */


/*    public void onClick(View view) {
        Fragment fragment;
        if(view==Add_announcement) {

            fragment = new view();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

        if(view==Add_announcement_text) {

            fragment = new view();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();


        }
    } */