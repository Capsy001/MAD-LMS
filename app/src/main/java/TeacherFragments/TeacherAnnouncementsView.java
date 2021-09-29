package TeacherFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.betterlearn.R;

import StudentFragments.StudentInstitutes;
import StudentFragments.StudentInstitutesPage;
import models.Announcement;
import models.Institute;


public class TeacherAnnouncementsView extends Fragment implements View.OnClickListener  {
    Button Update_announcement;
    Announcement ancmnt2 = new Announcement();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_teacher_announcements_view, container, false);
        Bundle bundle=getArguments();
        Announcement Announce1=(Announcement) bundle.getSerializable("selected_institute1");
        ancmnt2=Announce1;
        TextView title = myView.findViewById(R.id.textViewtitle);
        title.setText(Announce1.getTitle());

        TextView description = myView.findViewById(R.id.textViewdescription);
        description.setText(Announce1.getDescription());

        Update_announcement = (Button) myView.findViewById(R.id.editbtn_annc);
        Update_announcement.setOnClickListener(this);

        return myView;

    }

    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("key",ancmnt2); // Put anything what you want

        TeacherAnnouncementsEdit fragment2 = new TeacherAnnouncementsEdit();
        fragment2.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.defaultDashboard, fragment2)
                .commit();
    }


}





/*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();


        userID=fAuth.getCurrentUser().getUid();
        DocumentReference documentReference= fStore.collection("users").document(userID);

        //ddd
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot value = task.getResult();



                    if(value.getBoolean("type").booleanValue()==false) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.tabsView, new TabsTeacher())
                                .commit();
                    }
                    else if(value.getBoolean("type").booleanValue()==true) {
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.tabsView, new TabsStudent())
                                .commit();
                    }

                    if (value.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + value.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        //dddd

        Button logout=findViewById(R.id.logout_dashboard);


    }*/



/*    @Override
    public void onClick(View view) {

        Fragment fragment;

        if(view==myButton) {

            fragment = new Annoucement();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

    }
} */


/*    public void onClick(View view) {
        Fragment fragment;
        if(view==Add_announcement) {

            fragment = new view();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
        }

        if(view==Add_announcement_text) {

            fragment = new view();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();


        }
    } */