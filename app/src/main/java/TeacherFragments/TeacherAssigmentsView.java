package TeacherFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import models.Announcement;
import models.Assigments;
import models.Submission;


public class TeacherAssigmentsView extends Fragment implements View.OnClickListener  {
    Button Update_assigments;
    Button Show_submission;
    Button remove;
    Assigments Assigments2 = new Assigments();
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_teacher_assigments_view, container, false);
        Bundle bundle=getArguments();
        Assigments Assigments1=(Assigments) bundle.getSerializable("Show_Assigments");
        Assigments2=Assigments1;
        TextView title = myView.findViewById(R.id.textViewtitle);
        title.setText(Assigments1.getTitle());

        TextView description = myView.findViewById(R.id.textViewdescription);
        description.setText(Assigments1.getDescription());

        TextView deadline = myView.findViewById(R.id.textViewdeadline);
        deadline.setText(Assigments1.getDeadline());

        db = FirebaseFirestore.getInstance();
        Update_assigments = (Button) myView.findViewById(R.id.editbtn_assign);
        Update_assigments.setOnClickListener(this);

        remove = (Button) myView.findViewById(R.id.dltbtn_assign);
        remove.setOnClickListener(this);

        Show_submission = (Button) myView.findViewById(R.id.show_submissionbtn);
        Show_submission.setOnClickListener(this);

        return myView;

    }

    @Override
    public void onClick(View view) {
        if (view==Update_assigments){
            Bundle bundle = new Bundle();
            bundle.putSerializable("key",Assigments2); // Put anything what you want

            TeacherAssigmentsEdit fragment2 = new TeacherAssigmentsEdit();
            fragment2.setArguments(bundle);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.defaultDashboard, fragment2)
                    .commit();
        }else if (view==remove){
            deleteCourse(Assigments2);
        }else if (view==Show_submission){
            Bundle bundle = new Bundle();
            bundle.putSerializable("key",Assigments2); // Put anything what you want

            AdminSubmission fragment2 = new AdminSubmission();
            fragment2.setArguments(bundle);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.defaultDashboard, fragment2)
                    .commit();
        }

    }

    private void deleteCourse(Assigments Assigment) {
        // below line is for getting the collection
        // where we are storing our courses.
        db.collection("Assigments").
                // after that we are getting the document
                // which we have to delete.
                        document(Assigment.getKey()).

                // after passing the document id we are calling
                // delete method to delete this document.
                        delete().
                // after deleting call on complete listener
                // method to delete this data.
                        addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // inside on complete method we are checking
                        // if the task is success or not.
                        if (task.isSuccessful()) {
                            // this method is called when the task is success
                            // after deleting we are starting our MainActivity.
                            Toast.makeText(getActivity(), "Assigment has been deleted from Databse.", Toast.LENGTH_SHORT).show();

                            AdminAssignments fragment2 = new AdminAssignments();

                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.defaultDashboard, fragment2)
                                    .commit();

                        } else {
                            // if the delete operation is failed
                            // we are displaying a toast message.
                            Toast.makeText(getActivity(), "Fail to delete the Assigment. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}



