package StudentFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.betterlearn.R;


public class StudentAssignmentsShow extends Fragment implements View.OnClickListener {

    View myButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View myView = inflater.inflate(R.layout.fragment_student_assignment_show, container, false);
        myButton = (View) myView.findViewById(R.id.button8);
        myButton.setOnClickListener(this);

        return myView;

        // Inflate the layout for this fragment


    }


    @Override
    public void onClick(View view) {

        Fragment fragment;

        if(view==myButton) {

            fragment = new StudentAssignmentsSubmissionShow();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

    }
}


