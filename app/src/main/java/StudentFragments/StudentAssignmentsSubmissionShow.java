package StudentFragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.betterlearn.Announcement_Adapter;
import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Announcement;
import models.Assigments;


public class StudentAssignmentsSubmissionShow extends Fragment implements View.OnClickListener {

    Button submit;
    Assigments Assigments2 = new Assigments();
    private FirebaseFirestore db;
    String userID;
    private EditText SubmissionLink;
    private String rSubmissionLink;

    FirebaseAuth fAuth= FirebaseAuth.getInstance();
    FirebaseFirestore fStore=FirebaseFirestore.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View myView = inflater.inflate(R.layout.fragment_student_assignment_submission_show, container, false);
        Bundle bundle=getArguments();
        Assigments Assigments1=(Assigments) bundle.getSerializable("key");
        Assigments2 = Assigments1;
        TextView title = myView.findViewById(R.id.textView39);
        title.setText(Assigments1.getTitle());

        TextView description = myView.findViewById(R.id.textView12);
        description.setText(Assigments1.getDescription());

        TextView deadline = myView.findViewById(R.id.textView22);
        deadline.setText(Assigments1.getDeadline());

        SubmissionLink = myView.findViewById(R.id.editTextTextPersonName2);

        submit = (Button) myView.findViewById(R.id.button9);
        submit.setOnClickListener(this);


        return myView;

        // Inflate the layout for this fragment


    }


    @Override
    public void onClick(View view) {
        userID=fAuth.getCurrentUser().getUid();

        rSubmissionLink = SubmissionLink.getText().toString();
        if (TextUtils.isEmpty(rSubmissionLink)) {
            SubmissionLink.setError("Please enter Submission Link!.");
        }else{
            DocumentReference documentReference= fStore.collection("Submission").document();
            Map<String, Object> assign= new HashMap<>();
            assign.put("user", userID);
            assign.put("owner", Assigments2.getUser());
            assign.put("course", Assigments2.getCourse());
            assign.put("institutes", Assigments2.getInstitutes());
            assign.put("submissions", rSubmissionLink);

            documentReference.set(assign).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getActivity(), "Submission Submitted!.", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Error!.", Toast.LENGTH_SHORT).show();
                }
            });}
        //database document reference

    }


}


