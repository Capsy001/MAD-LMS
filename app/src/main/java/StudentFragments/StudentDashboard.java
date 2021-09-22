package StudentFragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.betterlearn.MainActivity;
import com.example.betterlearn.R;
import com.example.betterlearn.TabsStudent;
import com.example.betterlearn.TabsTeacher;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.BreakIterator;

public class StudentDashboard extends AppCompatActivity {

    public static final String TAG="TAG";
    Boolean accType;

    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
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

        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();   //logout from firebase authentication
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });
    }



}