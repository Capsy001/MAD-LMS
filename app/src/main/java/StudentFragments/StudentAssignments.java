package StudentFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.betterlearn.Admin_Assigment_Adapter;
import com.example.betterlearn.Assigment_Adapter;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import models.Assigments;


public class StudentAssignments extends Fragment implements View.OnClickListener {

    View myButton;
    GridView assigmnt1;
    ArrayList<Assigments> dataModalArrayList;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    String selected1 = "MAD";
    String rInstitutes;
    public static FragmentManager fragmentManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View myView = inflater.inflate(R.layout.fragment_student_assignments, container, false);



        fragmentManager=getFragmentManager();


        assigmnt1 = myView.findViewById(R.id.admin_assignment_std);
        dataModalArrayList = new ArrayList<>();

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();
        Spinner institute_spinner1= myView.findViewById(R.id.spinner4_std);
        List<String> subjects12 = new ArrayList<>();
        ArrayAdapter<String> adapter12 = new ArrayAdapter(this.getActivity(), android.R.layout.simple_spinner_item, subjects12);
        institute_spinner1.setAdapter(adapter12);
        // here we are calling a method
        // to load data in our list view.
        // Inflate the layout for this fragment
        fAuth= FirebaseAuth.getInstance();
        //firestore databse initialize
        fStore=FirebaseFirestore.getInstance();
        userID=fAuth.getCurrentUser().getUid();


        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading . .");
        dialog.show();

        CollectionReference collectionReference=fStore.collection("enrolled")
                .document(fAuth.getCurrentUser().getUid()).collection("courses");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {


                if(task.isSuccessful()){

                    List<DocumentSnapshot> listDocument = task.getResult().getDocuments();

                    List<String> items=new ArrayList<String>() {};
                    List<String> items_dup=new ArrayList<String>() {};

                    for(DocumentSnapshot document : listDocument){

                        String institute= document.get("institute").toString();
                        items_dup.add(institute);

                        // TODO: find a way to remove duplicate institute names in spinner
                        //////////////////////////////////////////////////////////////////

                    }

                    for (String element : items_dup) {

                        // If this element is not present in newList
                        // then add it
                        if (!items.contains(element)) {

                            items.add(element);
                        }
                    }



                    Spinner institute_spinner= myView.findViewById(R.id.spinner3_std);


                    ArrayAdapter itemsAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected, items);
                    itemsAdapter1.setDropDownViewResource(R.layout.spinner_dropdown);



                    institute_spinner.setAdapter(itemsAdapter1);


                    dialog.dismiss();

                }//if task successful
                else{

                    dialog.dismiss();
                    Toast.makeText(getActivity(),"Error!",Toast.LENGTH_SHORT).show();


                }
            }
        });

        Spinner institute_spinner= myView.findViewById(R.id.spinner3_std);
        institute_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected=institute_spinner.getSelectedItem().toString();

                CollectionReference collectionReference2=fStore.collection("enrolled")
                        .document(fAuth.getCurrentUser().getUid()).collection("courses");

                collectionReference2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            List<DocumentSnapshot> listDocument = task.getResult().getDocuments();


                            List<String> items2=new ArrayList<String>() {};

                            for(DocumentSnapshot document : listDocument){

                                if(document.get("institute").toString().equals(selected)){

                                    String course= document.get("course").toString();
                                    items2.add(course);

                                }



                            }

                            ArrayAdapter itemsAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected, items2);
                            itemsAdapter2.setDropDownViewResource(R.layout.spinner_dropdown);

                            Spinner courses_spinner= myView.findViewById(R.id.spinner4_std);
                            courses_spinner.setAdapter(itemsAdapter2);



                        }



                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        institute_spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView adapter123, View view, int i, long l) {
                dataModalArrayList.clear();
                assigmnt1.setAdapter(null);
                Spinner Institutes = (Spinner) getView().findViewById(R.id.spinner4_std);
                rInstitutes=Institutes.getSelectedItem().toString();
                rInstitutes =  adapter123.getItemAtPosition(i).toString();



                db.collection("Assigments").whereEqualTo("course", rInstitutes).get()
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
                                        Assigments dataModal = d.toObject(Assigments.class);
                                        dataModal.setKey(d.getId());
                                        // after getting data from Firebase
                                        // we are storing that data in our array list
                                        dataModalArrayList.add(dataModal);
                                    }
                                    // after that we are passing our array list to our adapter class.
                                    Assigment_Adapter adapter123 = new Assigment_Adapter(getActivity(), dataModalArrayList);

                                    // after passing this array list
                                    // to our adapter class we are setting
                                    // our adapter to our list view.
                                    assigmnt1.setAdapter(adapter123);
                                } else {
                                    // if the snapshot is empty we are displaying a toast message.
                                    Toast.makeText(getActivity(), "No Assigments in Database" , Toast.LENGTH_SHORT).show();
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

            }
        });



        return myView;

        // Inflate the layout for this fragment


    }


    @Override
    public void onClick(View view) {

        Fragment fragment;

        if(view==myButton) {

            fragment = new StudentAssignmentsShow();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

    }


}


