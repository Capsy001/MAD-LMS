package TeacherFragments;

public class admin_g {
}
/*package TeacherFragments;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainer;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.betterlearn.R;

import StudentFragments.StudentMyCourses;
import TeacherMyCourses.addContent;
import TeacherMyCourses.addCourse;
import TeacherMyCourses.addInstitute;

public class AdminCourse extends Fragment implements View.OnClickListener {

    Button addCourse;
    Button addInstitute;
    Button addContent;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        View v1 = inflater.inflate(R.layout.admin_course,container,false);
        container.clearDisappearingChildren();

       // Button
        addCourse= v1.findViewById(R.id.newcourseAI);
        addInstitute= v1.findViewById(R.id.addinstituteAI);
        addContent= v1.findViewById(R.id.addcontentAI);

        addCourse.setOnClickListener(this);
        addInstitute.setOnClickListener(this);
        addContent.setOnClickListener(this);

        return v1;
    }

    @Override
    public void onClick(View view) {

        FragmentContainerView fl = getView().findViewById(R.id.containerAI);
        fl.removeAllViews();

        Fragment fragment;

        if(view==addCourse) {

            fragment = new addCourse();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.containerAI, fragment);
            ft.commit();
        }

        if(view==addInstitute) {

            fragment = new addInstitute();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.containerAI, fragment);
            ft.commit();
        }

        if(view==addContent) {

            fragment = new addContent();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.containerAI, fragment);
            ft.commit();
        }


    }
}*/