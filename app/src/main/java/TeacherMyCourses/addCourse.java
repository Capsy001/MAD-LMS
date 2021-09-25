package TeacherMyCourses;

import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betterlearn.R;
import com.example.betterlearn.SignUp;
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


public class addCourse extends Fragment {

    public static final String TAG = "TAG";
    FirebaseAuth fAuth= FirebaseAuth.getInstance();
    FirebaseFirestore fStore= FirebaseFirestore.getInstance();

    String selected_val;


    public addCourse() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        if (container != null) {
            container.removeAllViews();
        }

        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_add_course2, container, false);

        //clear overlapping fragments
        container.clearDisappearingChildren();

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.show();


        //getting institute list from the database
        String userID= fAuth.getCurrentUser().getUid();
        CollectionReference collectionReference= fStore.collection("institutes");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    List<DocumentSnapshot> listDocument = task.getResult().getDocuments();


                    List<String> items=new ArrayList<String>() {};

                    //loop through all documents
                    for(DocumentSnapshot document : listDocument){

                        //selecting user's institutes
                        if(document.get("UserID").toString().equals(userID)){


                            String institute=document.get("InstituteName").toString();
                            items.add(institute);
                            //getting data to insert into spinner

                        }



                    }

                    //setting up the spinner
                    Spinner institutes=root.findViewById(R.id.spinnerAC);
                    TextView display=root.findViewById(R.id.selectedAC);


                    ArrayAdapter itemsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected, items);
                    itemsAdapter.setDropDownViewResource(R.layout.spinner_dropdown);


                    institutes.setAdapter(itemsAdapter);

                    dialog.dismiss();

                    //set selected listener
                    institutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            selected_val=institutes.getSelectedItem().toString();
                            Toast.makeText(getActivity(), selected_val+" selected!" , Toast.LENGTH_SHORT).show();
                            display.setText(selected_val);
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


        Button addCourse=root.findViewById(R.id.addbtnAC);
        EditText courseName=root.findViewById(R.id.coursenameAC);
        EditText enrollKey=root.findViewById(R.id.enrollkeyAC);

        addCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String cName =courseName.getText().toString();
                String eKey =enrollKey.getText().toString();

                if(TextUtils.isEmpty(cName)){
                    courseName.setError("Enter Course Name!");
                    return;

                }

                if(TextUtils.isEmpty(eKey)){
                    enrollKey.setError("Enter Enrollment Key!");
                    return;

                }

                //database document reference
                CollectionReference collectionReference1= fStore.collection("courses");
                DocumentReference documentReference=collectionReference1.document();

                Map<String, Object> course= new HashMap<>();

                course.put("institute", selected_val);
                course.put("coursename", cName);
                course.put("enrollmentkey", eKey);
                course.put("userID", userID);

                Log.d(TAG, "selected value:::::::::::::::"+selected_val);

                documentReference.set(course).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //alert box
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Success!")
                                .setMessage("Course Added. Now add some Content!")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                        EditText editCourseName=getView().findViewById(R.id.coursenameAC);
                                        editCourseName.setText("Add Another Course");

                                        EditText editEnrollKey=getView().findViewById(R.id.enrollkeyAC);
                                        editEnrollKey.setText("EnrollmentKey");


                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(android.R.drawable.checkbox_on_background)
                                .show();
                        //alert
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {

                        Toast.makeText(getActivity(),"Failed to add course!", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });









        return root;
    }


}