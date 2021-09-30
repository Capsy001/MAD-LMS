package StudentFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.betterlearn.R;

import TeacherFragments.TeacherAssigmentsEdit;
import models.Assigments;


public class StudentAssignmentsShow extends Fragment implements View.OnClickListener {

    View myButton;
    Assigments Assigments2 = new Assigments();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View myView = inflater.inflate(R.layout.fragment_student_assignment_show, container, false);
        Bundle bundle=getArguments();
        Assigments Assigments1=(Assigments) bundle.getSerializable("Show_Submission");
        Assigments2=Assigments1;


        TextView title = myView.findViewById(R.id.textView39);
        title.setText(Assigments1.getTitle());

        TextView description = myView.findViewById(R.id.textView12);
        description.setText(Assigments1.getDescription());

        TextView deadline = myView.findViewById(R.id.textView23);
        deadline.setText(Assigments1.getDeadline());

        return myView;

        // Inflate the layout for this fragment


    }


    @Override
    public void onClick(View view) {


    }
}


