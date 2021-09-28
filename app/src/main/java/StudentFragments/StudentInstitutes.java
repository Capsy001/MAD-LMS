package StudentFragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.betterlearn.ListAdapterInstitutes;
import com.example.betterlearn.R;
import com.example.betterlearn.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
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

import models.Institute;


public class StudentInstitutes extends Fragment implements View.OnClickListener {

    public static FragmentManager fragmentManager;

    public static final String TAG="TAG";
    FirebaseAuth fAuth=FirebaseAuth.getInstance();
    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    String userID=fAuth.getCurrentUser().getUid();

    public ArrayList<Institute> items=new ArrayList<Institute>() {};//list of institutes

    ActivityMainBinding binding;

    ProgressDialog dialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View myView = inflater.inflate(R.layout.fragment_student_institutes, container, false);
        // Inflate the layout for this fragment

        fragmentManager=getFragmentManager();

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.show();

        CollectionReference collectionReference=fStore.collection("institutes");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if (task.isSuccessful()){
                    items.clear();
                    List<DocumentSnapshot> listDocument = task.getResult().getDocuments();


                    for(DocumentSnapshot document : listDocument){

                        //getting institutes

                        String institute=document.get("InstituteName").toString();
                        String user_id=document.get("UserID").toString();
                        String image=document.get("downloadURL").toString();

                        Institute newInstitute=new Institute(institute, user_id, image);


                        items.add(newInstitute);
                        //add all object items to a list

                        listviewAdd(myView);

                    }
                }
            }
        });



        return myView;
    }


    public void listviewAdd(View myView){


        if(items.isEmpty()){
            Log.d(TAG, "yooooooooooo");
        }

        ListAdapterInstitutes listAdapterInstitutes=new ListAdapterInstitutes(getActivity(),items);
        ListView listView=myView.findViewById(R.id.listViewSI);
        listView.setAdapter(listAdapterInstitutes);

        dialog.dismiss();
        Log.d(TAG, "yhhhhhhhhhhhhhh");

    }

    @Override
    public void onClick(View view) {



    }

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}