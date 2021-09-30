package StudentFragments;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

import models.Assigments;
import models.Submission;


public class StudentSubmissionShow extends Fragment implements View.OnClickListener {

    View myButton;
    Submission Assigments2 = new Submission();
    final String[] user1 = new String[1];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View myView = inflater.inflate(R.layout.fragment_admin_submission_show, container, false);
        Bundle bundle=getArguments();
        Submission Assigments1=(Submission) bundle.getSerializable("Show_Submission");
        Assigments2=Assigments1;


        FirebaseFirestore db;
        db = FirebaseFirestore.getInstance();

        db.collection("users").document(Assigments1.getUser()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Map<String, Object> map = document.getData();
                        for (Map.Entry<String, Object> entry : map.entrySet()) {
                            if (entry.getKey().equals("name")) {

                                user1[0] =entry.getValue().toString();
                                TextView deadline = myView.findViewById(R.id.submission_link);
                                deadline.setText(user1[0]);
                                Log.d("TAG", user1[0]);
                            }
                        }
                    }
                }
            }
        });

        TextView title = myView.findViewById(R.id.submission_title);
        title.setText(Assigments1.getCourse());

        TextView description = myView.findViewById(R.id.institutes_title123);
        description.setText(Assigments1.getInstitutes());

        TextView submission = myView.findViewById(R.id.submission_link2);
        submission.setText(Assigments1.getSubmissions());




        return myView;

        // Inflate the layout for this fragment


    }


    @Override
    public void onClick(View view) {

        if(view==myButton) {

            Bundle bundle = new Bundle();
            bundle.putSerializable("key",Assigments2); // Put anything what you want

            StudentAssignmentsSubmissionShow fragment2 = new StudentAssignmentsSubmissionShow();
            fragment2.setArguments(bundle);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.defaultDashboard, fragment2)
                    .commit();
        }

    }

    public String getuser(String UserId){


        return user1[0];
    }
}


