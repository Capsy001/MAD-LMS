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
import androidx.fragment.app.FragmentTransaction;

import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import StudentFragments.StudentInstitutes;
import StudentFragments.StudentInstitutesPage;
import models.Announcement;
import models.Institute;


public class TeacherAnnouncementsView extends Fragment implements View.OnClickListener  {
    Button Update_announcement;

    Button remove;
    Announcement ancmnt2 = new Announcement();
    private FirebaseFirestore db;

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

        db = FirebaseFirestore.getInstance();
        Update_announcement = (Button) myView.findViewById(R.id.editbtn_annc);
        Update_announcement.setOnClickListener(this);

        remove = (Button) myView.findViewById(R.id.dltbtn_annc);
        remove.setOnClickListener(this);



        return myView;

    }

    @Override
    public void onClick(View view) {
        if (view==Update_announcement){
            Bundle bundle = new Bundle();
            bundle.putSerializable("key",ancmnt2); // Put anything what you want

            TeacherAnnouncementsEdit fragment2 = new TeacherAnnouncementsEdit();
            fragment2.setArguments(bundle);

            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.defaultDashboard, fragment2)
                    .commit();
        }else if (view==remove){
            deleteCourse(ancmnt2);
        }

    }

    private void deleteCourse(Announcement announce) {
        // below line is for getting the collection
        // where we are storing our courses.
        db.collection("announcements").
                // after that we are getting the document
                // which we have to delete.
                        document(announce.getKey()).

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
                            Toast.makeText(getActivity(), "Course has been deleted from Databse.", Toast.LENGTH_SHORT).show();

                            AdminAnnouncement fragment2 = new AdminAnnouncement();

                            getFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.defaultDashboard, fragment2)
                                    .commit();

                        } else {
                            // if the delete operation is failed
                            // we are displaying a toast message.
                            Toast.makeText(getActivity(), "Fail to delete the course. ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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