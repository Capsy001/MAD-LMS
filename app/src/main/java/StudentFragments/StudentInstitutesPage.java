package StudentFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Institute;


public class StudentInstitutesPage extends Fragment {

    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    FirebaseAuth fAuth=FirebaseAuth.getInstance();




    View myButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_student_institutes_page, container, false);

        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading . .");
        dialog.show();

        Bundle bundle=getArguments();
        Institute institute4=(Institute) bundle.getSerializable("selected_institute");

        ImageView img=root.findViewById(R.id.imageAIP);
        TextView institute_name=root.findViewById(R.id.instituteAIP);
        Button enroll=root.findViewById(R.id.enrollBtnAIP);
        EditText enrollKey=root.findViewById(R.id.enrollKeyAIP);


        institute_name.setText(institute4.getInstituteName());
        Glide.with(img.getContext())
                .load(institute4.getImage_url())
                .centerCrop()
                .into(img);

        String owner_id=institute4.getUser_id();

        CollectionReference collectionReference =fStore.collection("courses");
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()) {
                    List<DocumentSnapshot> listDocument = task.getResult().getDocuments();

                    List<String> items = new ArrayList<String>() {};
                    for(DocumentSnapshot document : listDocument){


                        if(document.get("userID").toString().equals(institute4.getUser_id())

                        && document.get("institute").toString().equals(institute4.getInstituteName())) {

                            String course = document.get("coursename").toString();
                            items.add(course);
                            //getting data to insert into spinner

                        }
                    }


                    //setting up the spinner
                    Spinner courses=root.findViewById(R.id.coursesAIP);

                    ArrayAdapter itemsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected, items);
                    itemsAdapter.setDropDownViewResource(R.layout.spinner_dropdown);

                    courses.setAdapter(itemsAdapter);
                    dialog.dismiss();




                }//if task successfull
            }
        });




        DocumentReference documentReference=fStore.collection("enrolled").document(fAuth.getCurrentUser().getUid()).collection("courses").document();
        //to upload


        //on click enroll-> get info and upload
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog2=new ProgressDialog(getActivity());

                dialog2.setMessage("Loading . .");
                dialog2.show();


                Spinner courses=root.findViewById(R.id.coursesAIP);
                EditText enrollKey=root.findViewById(R.id.enrollKeyAIP);

                String eKey=enrollKey.getText().toString();


                CollectionReference keyGet= fStore.collection("courses");

                keyGet.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        //
                        List<DocumentSnapshot> listDocument = task.getResult().getDocuments();

                        List<String> items = new ArrayList<String>() {};
                        for(DocumentSnapshot document : listDocument){

                            //selecting user's institutes
                            if(document.get("userID").toString().equals(institute4.getUser_id())
                               && document.get("institute").toString().equals(institute4.getInstituteName())
                               && document.get("enrollmentkey").toString().equals(eKey)
                            )
                            {



                                Map<String, Object> content = new HashMap<>();
                                content.put("course", courses.getSelectedItem().toString());
                                content.put("owner_id", owner_id);
                                content.put("institute", institute4.getInstituteName());

                                documentReference.set(content).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull @NotNull Task<Void> task) {

                                        enrollKey.getText().clear();
                                        dialog2.dismiss();

                                        Toast.makeText(getActivity(), "Enrolled Successfully!", Toast.LENGTH_SHORT).show();
                                    }
                                });



                            }else{
                                dialog2.dismiss();

                            }
                        }
                        //for



                    }
                });





            }
        });



        return root;
    }

}