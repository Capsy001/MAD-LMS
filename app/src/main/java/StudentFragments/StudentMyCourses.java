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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.betterlearn.ContentAdapter;
import com.example.betterlearn.ListAdapterInstitutes;
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

import models.Content;
import models.Institute;


public class StudentMyCourses extends Fragment {

    public static FragmentManager fragmentManager;

    FirebaseFirestore fStore=FirebaseFirestore.getInstance();
    FirebaseAuth fAuth=FirebaseAuth.getInstance();

    public ArrayList<Content> contentArray=new ArrayList<Content>() {};//list of contents

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment

        View root= inflater.inflate(R.layout.fragment_student_my_courses, container, false);

        fragmentManager=getFragmentManager();

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






        //get contents for selected course
        Spinner courses_spinner= root.findViewById(R.id.coursesMC);

        courses_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                contentArray.clear();
                ContentAdapter listAdapterContent=new ContentAdapter(getActivity(),contentArray);
                ListView listView=root.findViewById(R.id.listviewSMC);
                listView.setAdapter(listAdapterContent);


                String selected_inst=institute_spinner.getSelectedItem().toString();
                String selected_crs=courses_spinner.getSelectedItem().toString();

                CollectionReference contentReference=fStore.collection("content");

                contentReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){

                            contentArray.clear();
                            List<DocumentSnapshot> listDocument = task.getResult().getDocuments();

                            for(DocumentSnapshot document : listDocument){

                                //getting content


                                if(document.get("institute").toString().equals(selected_inst)
                                && document.get("course").toString().equals(selected_crs)) {

                                    String institute = document.get("institute").toString();
                                    String course = document.get("course").toString();
                                    String contentID = document.get("contentID").toString();
                                    String description = document.get("description").toString();
                                    String downloadURL = document.get("downloadURL").toString();
                                    String link_description = document.get("link_description").toString();
                                    String title = document.get("title").toString();
                                    String type = document.get("type").toString();


                                    Content newContent = new Content(contentID, course, description, institute, link_description, title, type, downloadURL);

                                    contentArray.add(newContent);
                                    //add all object items to a list

                                    listviewAdd(root);

                                }

                            }




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


    public void listviewAdd(View myView){


        ContentAdapter listAdapterContent=new ContentAdapter(getActivity(),contentArray);
        ListView listView=myView.findViewById(R.id.listviewSMC);
        listView.setAdapter(listAdapterContent);


    }

}