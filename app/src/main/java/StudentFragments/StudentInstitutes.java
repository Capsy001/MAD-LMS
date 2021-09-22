package StudentFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.betterlearn.R;


public class StudentInstitutes extends Fragment implements View.OnClickListener {

    View myButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View myView = inflater.inflate(R.layout.fragment_student_institutes, container, false);
        myButton = (View) myView.findViewById(R.id.assign2);

        myButton.setOnClickListener(this);

        // Inflate the layout for this fragment

        return myView;
    }

    @Override
    public void onClick(View view) {

        Fragment fragment;

        if(view==myButton) {

            fragment = new StudentInstitutesPage();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

    }


}