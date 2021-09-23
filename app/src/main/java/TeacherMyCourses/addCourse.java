package TeacherMyCourses;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.betterlearn.R;


public class addCourse extends Fragment {

    public addCourse() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (container != null) {
            container.removeAllViews();
        }

        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_add_course2, container, false);

        container.clearDisappearingChildren();

        Spinner institutes=root.findViewById(R.id.spinnerAC);





        return root;
    }
}