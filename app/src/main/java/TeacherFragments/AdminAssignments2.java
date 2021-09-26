package TeacherFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.betterlearn.DashboardProfile;
import com.example.betterlearn.R;

import TeacherAssignments.NewAssignment;


public class AdminAssignments2 extends Fragment implements View.OnClickListener {

    Button myButton;
    Button myButton1;


    public AdminAssignments2() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.fragment_admin_assignments2, container, false);

        container.clearDisappearingChildren();



        myButton = (Button) root.findViewById(R.id.addnewASS);
        myButton1 = (Button) root.findViewById(R.id.allASS);

        myButton.setOnClickListener(this);
        myButton1.setOnClickListener(this);








        return root;

    }

    @Override
    public void onClick(View view) {

        Fragment fragment;

        if(view==myButton) {

            fragment = new NewAssignment();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.fragmentContainerASS, fragment);
            ft.commit();
        }

        if(view==myButton1) {

            //for all assignments


        }

    }
}