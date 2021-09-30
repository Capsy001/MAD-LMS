package StudentFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.betterlearn.R;

import models.Assigments;
import models.Submission;


public class StudentSubmissionShow extends Fragment implements View.OnClickListener {

    View myButton;
    Submission Assigments2 = new Submission();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View myView = inflater.inflate(R.layout.fragment_admin_submission_show, container, false);
        Bundle bundle=getArguments();
        Submission Assigments1=(Submission) bundle.getSerializable("Show_Submission");
        Assigments2=Assigments1;


        TextView title = myView.findViewById(R.id.submission_title);
        title.setText(Assigments1.getCourse());

        TextView description = myView.findViewById(R.id.institutes_title);
        description.setText(Assigments1.getInstitute());

        TextView deadline = myView.findViewById(R.id.submission_link);
        deadline.setText(Assigments1.getSubmissions());

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
}


