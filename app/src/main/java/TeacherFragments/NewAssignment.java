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
import com.google.firebase.firestore.QueryDocumentSnapshot;
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
        View root= inflater.inflate(R.layout.fragment_new_assignments, container, false);

        fAuth= FirebaseAuth.getInstance();
        //firestore databse initialize
        fStore=FirebaseFirestore.getInstance();
        userID=fAuth.getCurrentUser().getUid();

        ///////////////////////////////////////////////////////////////
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.show();


        CollectionReference subjectsRef = fStore.collection("institutes");
        Spinner spinner = (Spinner) root.findViewById(R.id.spinnerASS);
        List<String> subjects = new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, subjects);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        subjectsRef.whereEqualTo("UserID", userID).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

        dialog.dismiss();

        Spinner institute_spinner= root.findViewById(R.id.spinnerASS);
        institute_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected=institute_spinner.getSelectedItem().toString();

                CollectionReference subjectsRef = fStore.collection("courses");
                Spinner spinner = (Spinner) root.findViewById(R.id.spinner2ASS);
                List<String> subjects = new ArrayList<>();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, subjects);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);
                subjectsRef.whereEqualTo("institute", selected).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String subject = document.getString("coursename");
                                subjects.add(subject);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //---Retrieve institutes
        ///////////////////////////////////////////////////////////////

        Button b = (Button) root.findViewById(R.id.button7);
        b.setOnClickListener(this);

        return root;

    }


    @Override
    public void onClick(View v){

        EditText Title = (EditText) getView().findViewById(R.id.editTextTextPersonName);
        EditText Description = (EditText) getView().findViewById(R.id.editTextTextMultiLine);
        EditText Deadline = (EditText) getView().findViewById(R.id.editTextDate2);
        Spinner Institutes = (Spinner) getView().findViewById(R.id.spinnerASS);
        Spinner Course = (Spinner) getView().findViewById(R.id.spinner2ASS);

        String rTitle=Title.getText().toString().trim();
        String rDescription=Description.getText().toString();
        String rDeadline=Deadline.getText().toString();
        String rInstitutes=Institutes.getSelectedItem().toString();
        String rCourse=Course.getSelectedItem().toString();

        if(TextUtils.isEmpty(rTitle)){
            Title.setError("Title should not be empty");
            return;

        }

        if(TextUtils.isEmpty(rDescription)){
            Description.setError("Description should not be empty");
            return;

        }
        if(TextUtils.isEmpty(rDeadline)){
            Deadline.setError("Deadline should not be empty");
            return;

        }
        if(TextUtils.isEmpty(rInstitutes)){
            Deadline.setError("Deadline should not be empty");
            return;

        }
        if(TextUtils.isEmpty(rCourse)){
            Deadline.setError("Deadline should not be empty");
            return;

        }else{
            userID=fAuth.getCurrentUser().getUid();

            //database document reference
            DocumentReference documentReference= fStore.collection("Assigments").document();   //Collection="Assigments"

            Map<String, Object> announce= new HashMap<>();
            announce.put("title", rTitle);
            announce.put("description", rDescription);
            announce.put("deadline", rDeadline);
            announce.put("course", rCourse);
            announce.put("institute", rInstitutes);
            announce.put("user", userID);

            documentReference.set(announce).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG, "User Assigment created for ");
                    Title.getText().clear();
                    Description.getText().clear();
                    Deadline.getText().clear();
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


}