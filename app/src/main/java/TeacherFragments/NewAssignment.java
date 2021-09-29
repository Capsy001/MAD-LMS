package TeacherFragments;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Announcement;

public class NewAssignment extends Fragment  implements View.OnClickListener  {

    Button Add_announcement;
    TextView Add_announcement_text;
    GridView coursesGV;
    ArrayList<Announcement> dataModalArrayList;
    FirebaseFirestore db;
    String userID;

    public static final String TAG="TAG";
    FirebaseAuth fAuth= FirebaseAuth.getInstance();
    FirebaseFirestore fStore=FirebaseFirestore.getInstance();

    ProgressDialog dialog2;

    String selected_institute;
    String selected_course;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_new_assignment, container, false);


        ///////////////////////////////////////////////////////////////
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.show();


        //getting institute list from the database
        String userID= fAuth.getCurrentUser().getUid();

        //Database collection reference
        CollectionReference collectionReference= fStore.collection("institutes");

        //Retrieve institutes
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    List<DocumentSnapshot> listDocument = task.getResult().getDocuments();

                    List<String> items=new ArrayList<String>() {};
                    for(DocumentSnapshot document : listDocument){

                        //selecting user's institutes
                        if(document.get("UserID").toString().equals(userID)) {

                            String institute = document.get("InstituteName").toString();
                            items.add(institute);
                            //getting data to insert into spinner

                        }

                    }

                    //setting up the spinner
                    Spinner institutes=root.findViewById(R.id.spinnerASS);

                    ArrayAdapter itemsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected, items);
                    itemsAdapter.setDropDownViewResource(R.layout.spinner_dropdown);


                    institutes.setAdapter(itemsAdapter);

                    dialog.dismiss();

                    //set selected listener
                    institutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            dialog2 = new ProgressDialog(getActivity());
                            dialog2.setMessage("Loading..");
                            dialog2.show();

                            //get selected spinner item
                            selected_institute=institutes.getSelectedItem().toString();
                            Toast.makeText(getActivity(), selected_institute+" selected!" , Toast.LENGTH_SHORT).show();

                            secondSpinner(root);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        //---Retrieve institutes
        ///////////////////////////////////////////////////////////////

        return root;

    }


    @Override
    public void onClick(View v){
        //firebase Authentication initialize
        fAuth=FirebaseAuth.getInstance();
        //firestore databse initialize
        fStore=FirebaseFirestore.getInstance();

        EditText Title = (EditText) getView().findViewById(R.id.editTextTextPersonName3);
        EditText Description = (EditText) getView().findViewById(R.id.editTextTextMultiLine3);
        Spinner Institutes = (Spinner) getView().findViewById(R.id.institute);
        Log.d(TAG, "newAnnouncement:" + Institutes.getSelectedItem().toString());

        String rTitle=Title.getText().toString().trim();
        String rDescription=Description.getText().toString();
        String rCourse=Institutes.getSelectedItem().toString();

        if(TextUtils.isEmpty(rTitle)){
            Title.setError("Title should not be empty");
            return;

        }

        if(TextUtils.isEmpty(rDescription)){
            Title.setError("Title should not be empty");
            return;

        }else{
            userID=fAuth.getCurrentUser().getUid();

            //database document reference
            DocumentReference documentReference= fStore.collection("Assigments").document();   //Collection="Assigments"

            Map<String, Object> announce= new HashMap<>();
            announce.put("title", rTitle);
            announce.put("description", rDescription);
            announce.put("Course", rCourse);
            announce.put("user", userID);

            documentReference.set(announce).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG, "User Assigment created for ");
                    Title.getText().clear();
                    Description.getText().clear();
                    Toast.makeText(getActivity(), "Assigments Created", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "OnFailure: "+e.toString());
                    Toast.makeText(getActivity(), "Error!.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }





    ///-----------------------------------------------------------
    //Custom function to setup second spinner
    private void secondSpinner(View root) {

        //Database collection reference 2
        CollectionReference collectionReference2= fStore.collection("courses");

        //Retrieve courses
        collectionReference2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){

                    List<DocumentSnapshot> listDocument2 = task.getResult().getDocuments();

                    List<String> items2=new ArrayList<String>() {};
                    for(DocumentSnapshot document : listDocument2){

                        if(selected_institute.equals(document.get("institute"))) {



                            String course = document.get("coursename").toString();
                            items2.add(course);
                            //getting data to insert into spinner

                        }


                    }

                    if(items2.isEmpty())
                    {
                        //alert box
                        new AlertDialog.Builder(getActivity())
                                .setTitle("No Courses!")
                                .setMessage("Selected institute has no Courses. Add some Courses!")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        //alert
                    }

                    //setting up the spinner
                    Spinner courses=root.findViewById(R.id.spinner2ASS);

                    ArrayAdapter itemsAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected, items2);//items3
                    itemsAdapter2.setDropDownViewResource(R.layout.spinner_dropdown);


                    courses.setAdapter(itemsAdapter2);
                    dialog2.dismiss();



                    //set selected listener
                    courses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                            //get selected spinner item
                            selected_course=courses.getSelectedItem().toString();
                            Toast.makeText(getActivity(), selected_course+" selected!" , Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        //---Retrieve courses



    }
    ///-----------------------------------------------------------
}