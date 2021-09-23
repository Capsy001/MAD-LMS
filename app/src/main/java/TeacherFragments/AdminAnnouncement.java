package TeacherFragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.betterlearn.DashboardProfile;
import com.example.betterlearn.R;

import org.w3c.dom.Text;


public class AdminAnnouncement extends Fragment implements View.OnClickListener  {

    Button Add_announcement;
    TextView Add_announcement_text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_admin_announcement, container, false);

        // Inflate the layout for this fragment
        Add_announcement = (Button) myView.findViewById(R.id.add_announcement);
        Add_announcement_text = (TextView) myView.findViewById(R.id.textView36);

        Add_announcement.setOnClickListener(this);
        Add_announcement_text.setOnClickListener(this);

        return myView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment;

        if(view==Add_announcement) {

            fragment = new newAnnouncement();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

        if(view==Add_announcement_text) {

            fragment = new newAnnouncement();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();


        }
    }
}