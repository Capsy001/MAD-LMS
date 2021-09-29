package TeacherFragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.betterlearn.R;
import com.google.firebase.firestore.FirebaseFirestore;

import models.Announcement;


public class TeacherAnnouncementsEdit extends Fragment implements View.OnClickListener  {
    Button Update_announcement_done;

    private EditText AnnounceTitle, AnnounceDescript;

    // creating a strings for storing our values from Edittext fields.
    private String rAnnounceTitle, rAnnounceDescript;

    // creating a variable for firebasefirestore.
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_edit_announcement, container, false);
        Bundle bundle=getArguments();
        Announcement Announce1=(Announcement) bundle.getSerializable("key");

        EditText title = myView.findViewById(R.id.editTextTextPersonName3);
        title.setText(Announce1.getTitle());

        EditText description = myView.findViewById(R.id.editTextTextMultiLine3);
        description.setText(Announce1.getDescription());

        AnnounceTitle = myView.findViewById(R.id.editTextTextPersonName3);
        AnnounceDescript = myView.findViewById(R.id.editTextTextMultiLine3);

        Update_announcement_done = (Button) myView.findViewById(R.id.button6);
        Update_announcement_done.setOnClickListener(this);

        return myView;

    }

    @Override
    public void onClick(View view) {


        rAnnounceTitle = AnnounceTitle.getText().toString();
        rAnnounceDescript = AnnounceDescript.getText().toString();

        // validating the text fields if empty or not.
        if (TextUtils.isEmpty(courseName)) {
            AnnounceTitle.setError("Please enter Course Name");
        } else if (TextUtils.isEmpty(courseDescription)) {
            AnnounceDescript.setError("Please enter Course Description");
        }else {
            // calling a method to update our course.
            // we are passing our object class, course name,
            // course description and course duration from our edittext field.
            updateCourses(Announcement, rAnnounceTitle, rAnnounceDescript);
        }

    }

    private void updateCourses(Announcement courses, String courseName, String courseDescription) {
        // inside this method we are passing our updated values
        // inside our object class and later on we
        // will pass our whole object to firebase Firestore.
        Courses updatedCourse = new Courses(courseName, courseDescription, courseDuration);

        // after passing data to object class we are
        // sending it to firebase with specific document id.
        // below line is use to get the collection of our Firebase Firestore.
        db.collection("Courses").
                // below line is use toset the id of
                // document where we have to perform
                // update operation.
                        document(courses.getId()).

                // after setting our document id we are
                // passing our whole object class to it.
                        set(updatedCourse).

                // after passing our object class we are
                // calling a method for on success listener.
                        addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // on successful completion of this process
                        // we are displaying the toast message.
                        Toast.makeText(UpdateCourse.this, "Course has been updated..", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            // inside on failure method we are
            // displaying a failure message.
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateCourse.this, "Fail to update the data..", Toast.LENGTH_SHORT).show();
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