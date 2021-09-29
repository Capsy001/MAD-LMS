package StudentFragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class StudentMyCourses extends Fragment {

    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    FirebaseAuth fAuth=FirebaseAuth.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment

        View root= inflater.inflate(R.layout.fragment_student_my_courses, container, false);

        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading . .");
        dialog.show();

        CollectionReference collectionReference=fStore.collection("enrolled")
                .document(fAuth.getCurrentUser().getUid()).collection("courses");

        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {


                if(task.isSuccessful()){

                    List<DocumentSnapshot> listDocument = task.getResult().getDocuments();

                    List<String> items=new ArrayList<String>() {};
                    List<String> items_dup=new ArrayList<String>() {};

                    for(DocumentSnapshot document : listDocument){

                       String institute= document.get("institute").toString();
                       items_dup.add(institute);

                       // TODO: find a way to remove duplicate institute names in spinner
                        //////////////////////////////////////////////////////////////////

                    }

                    for (String element : items_dup) {

                        // If this element is not present in newList
                        // then add it
                        if (!items.contains(element)) {

                            items.add(element);
                        }
                    }



                    Spinner institute_spinner= root.findViewById(R.id.institutesMC);


                    ArrayAdapter itemsAdapter1 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected, items);
                    itemsAdapter1.setDropDownViewResource(R.layout.spinner_dropdown);



                    institute_spinner.setAdapter(itemsAdapter1);


                    dialog.dismiss();

                }//if task successful
                else{

                    dialog.dismiss();
                    Toast.makeText(getActivity(),"Error!",Toast.LENGTH_SHORT).show();


                }
            }
        });

        Spinner institute_spinner= root.findViewById(R.id.institutesMC);
        institute_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selected=institute_spinner.getSelectedItem().toString();

                CollectionReference collectionReference2=fStore.collection("enrolled")
                        .document(fAuth.getCurrentUser().getUid()).collection("courses");

                collectionReference2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){

                            List<DocumentSnapshot> listDocument = task.getResult().getDocuments();


                            List<String> items2=new ArrayList<String>() {};

                            for(DocumentSnapshot document : listDocument){

                                if(document.get("institute").toString().equals(selected)){

                                    String course= document.get("course").toString();
                                    items2.add(course);

                                }



                            }

                            ArrayAdapter itemsAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected, items2);
                            itemsAdapter2.setDropDownViewResource(R.layout.spinner_dropdown);

                            Spinner courses_spinner= root.findViewById(R.id.coursesMC);
                            courses_spinner.setAdapter(itemsAdapter2);



                        }



                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        return root;
    }



}