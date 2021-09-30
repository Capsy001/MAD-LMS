package TeacherFragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import models.Announcement;
import models.Assigments;


public class TeacherAssigmentsEdit extends Fragment implements View.OnClickListener  {
    Button Update_assigments_done;

    private EditText AssigmentsTitle, AssigmentsDescript, AssigmentsDeadline ;
    private EditText title, description, deadline;
    // creating a strings for storing our values from Edittext fields.
    private String rAssigmentsTitle, rAssigmentsDescript, rAssigmentsDeadline;
    Assigments Assigments2 = new Assigments();

    // creating a variable for firebasefirestore.
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_edit_assigments, container, false);
        Bundle bundle=getArguments();
        Assigments Assigments1=(Assigments) bundle.getSerializable("key");
        Assigments2=Assigments1;
        title = myView.findViewById(R.id.editTextTextPersonName3);
        title.setText(Assigments1.getTitle());
        db = FirebaseFirestore.getInstance();
        description = myView.findViewById(R.id.editTextTextMultiLine3);
        description.setText(Assigments1.getDescription());

        deadline = myView.findViewById(R.id.editTextNumber);
        deadline.setText(Assigments1.getDeadline());

        AssigmentsTitle = myView.findViewById(R.id.editTextTextPersonName3);
        AssigmentsDescript = myView.findViewById(R.id.editTextTextMultiLine3);
        AssigmentsDeadline = myView.findViewById(R.id.editTextNumber);

        Update_assigments_done = (Button) myView.findViewById(R.id.button6);
        Update_assigments_done.setOnClickListener(this);

        return myView;

    }

    @Override
    public void onClick(View view) {


        rAssigmentsTitle = AssigmentsTitle.getText().toString();
        rAssigmentsDescript = AssigmentsDescript.getText().toString();
        rAssigmentsDeadline = AssigmentsDeadline.getText().toString();

        // validating the text fields if empty or not.
        if (TextUtils.isEmpty(rAssigmentsTitle)) {
            AssigmentsTitle.setError("Please enter Assigments Title");
        }if (TextUtils.isEmpty(rAssigmentsDescript)) {
            AssigmentsDescript.setError("Please enter Assigments Description");
        }if (TextUtils.isEmpty(rAssigmentsDeadline)) {
            AssigmentsDeadline.setError("Please enter Assigments Deadline");
        }else {
            // calling a method to update our course.
            // we are passing our object class, course name,
            // course description and course duration from our edittext field.
            updateCourses(Assigments2, rAssigmentsTitle, rAssigmentsDescript, rAssigmentsDeadline);
        }

    }

    private void updateCourses(Assigments Assigment, String title, String description, String Deadline) {
        // inside this method we are passing our updated values
        // inside our object class and later on we
        // will pass our whole object to firebase Firestore.
        Assigments updateannunce = new Assigments(title, description,Assigment.getInstitutes(),Assigment.getCourse(), Deadline,Assigment.getUser(),Assigment.getKey());

        // after passing data to object class we are
        // sending it to firebase with specific document id.
        // below line is use to get the collection of our Firebase Firestore.
        db.collection("Assigments").
                // below line is use toset the id of
                // document where we have to perform
                // update operation.
                        document(Assigment.getKey()).

                // after setting our document id we are
                // passing our whole object class to it.
                        set(updateannunce).

                // after passing our object class we are
                // calling a method for on success listener.
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // on successful completion of this process
                        // we are displaying the toast message.
                        AdminAssignments fragment2 = new AdminAssignments();

                        getFragmentManager()
                                .beginTransaction()
                                .replace(R.id.defaultDashboard, fragment2)
                                .commit();

                        Toast.makeText(getActivity(), "Assigment has been updated..", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            // inside on failure method we are
            // displaying a failure message.
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "Fail to update the data..", Toast.LENGTH_SHORT).show();
            }
        });
    }
}




