package TeacherMyCourses;

import android.app.LauncherActivity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class addCourse extends Fragment {

    public static final String TAG = "TAG";
    FirebaseAuth fAuth= FirebaseAuth.getInstance();
    FirebaseFirestore fStore= FirebaseFirestore.getInstance();


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

        //clear overlapping fragments
        container.clearDisappearingChildren();


        //getting institute list from the database
        String userID= fAuth.getCurrentUser().getUid();
        CollectionReference collectionReference= fStore.collection("users").document(userID).collection("institutes");




        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    List<DocumentSnapshot> listDocument = task.getResult().getDocuments();


                    List<String> items=new ArrayList<String>() {
                    };
                    for(DocumentSnapshot document : listDocument){

                        String institute=document.get("InstituteName").toString();
                        items.add(institute);
                        //getting data to insert into spinner

                    }

                    //setting up the spinner
                    Spinner institutes=root.findViewById(R.id.spinnerAC);


                    ArrayAdapter itemsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_dropdown, items);
                    itemsAdapter.setDropDownViewResource(R.layout.spinner_dropdown);


                    institutes.setAdapter(itemsAdapter);


                }else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });










        return root;
    }
}