package TeacherFragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class TeacherEdit extends Fragment implements View.OnClickListener {

    public static final String TAG = "TAG";
    FirebaseAuth fAuth= FirebaseAuth.getInstance();
    FirebaseFirestore fStore= FirebaseFirestore.getInstance();

    ProgressDialog dialog2;

    String newEnroll=null;

    Uri imageUri=null;    //URI of the selected Image

    String selected_institute;
    String selected_course;
    String content_id=null;

    String generatedFilePath=null;

    Button deleteinst;
    Button deletecrs;
    Button changeImg;
    Button editEnroll;



    public TeacherEdit() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root=inflater.inflate(R.layout.fragment_teacher_edit, container, false);

        //clear overlapping fragments
        container.clearDisappearingChildren();

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.show();

        //getting institute list from the database
        String userID= fAuth.getCurrentUser().getUid();

        //Database collection reference
        CollectionReference collectionReference= fStore.collection("institutes");


        //Retrieve institutes
        collectionReference.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    List<DocumentSnapshot> listDocument = task.getResult().getDocuments();

                    List<String> items=new ArrayList<String>() {};
                    for(DocumentSnapshot document : listDocument){

                        //selecting user's institutes
                        if(document.get("UserID").toString().equals(userID)) {

                            String institute = document.get("InstituteName").toString();
                            items.add(institute);
                            //getting data to insert into spinner

                        }
                    }

                    //setting up the spinner
                    Spinner institutes=root.findViewById(R.id.instituteTE);

                    ArrayAdapter itemsAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected, items);
                    itemsAdapter.setDropDownViewResource(R.layout.spinner_dropdown);


                    institutes.setAdapter(itemsAdapter);

                    dialog.dismiss();

                    //set selected listener
                    institutes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                            dialog2 = new ProgressDialog(getActivity());
                            dialog2.setMessage("Loading..");
                            dialog2.show();

                            //get selected spinner item
                            selected_institute=institutes.getSelectedItem().toString();
                            Toast.makeText(getActivity(), selected_institute+" selected!" , Toast.LENGTH_SHORT).show();

                            secondSpinner(root);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        //---Retrieve institutes


        deleteinst=root.findViewById(R.id.deleteInst);
        deletecrs=root.findViewById(R.id.deleteCrs);
        changeImg=root.findViewById(R.id.changeImg);
        editEnroll=root.findViewById(R.id.editEnroll);



        deleteinst.setOnClickListener(this);
        deletecrs.setOnClickListener(this);
        editEnroll.setOnClickListener(this);
        changeImg.setOnClickListener(this);





        return root;


    }

    @Override
    public void onClick(View view) {

        if(view==deleteinst){

            //alert box
            new AlertDialog.Builder(getActivity())
                    .setTitle("Warning!")
                    .setMessage("Are you sure want to delete the selected Institute?")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            CollectionReference institutes=fStore.collection("institutes");


                            Query query=fStore.collection("institutes").whereEqualTo("InstituteName", selected_institute)
                                    .whereEqualTo("UserID", fAuth.getCurrentUser().getUid().toString());


                            //
                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (DocumentSnapshot document : task.getResult()) {
                                            institutes.document(document.getId()).delete();
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                            //





                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //do nothing
                }
            })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            //alert



        }


        if(view==deletecrs){

            //alert box
            new AlertDialog.Builder(getActivity())
                    .setTitle("Warning!")
                    .setMessage("Are you sure want to delete the selected Course?")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                            CollectionReference courses=fStore.collection("courses");


                            Query query=fStore.collection("courses").whereEqualTo("coursename", selected_course)
                                    .whereEqualTo("userID", fAuth.getCurrentUser().getUid().toString());


                            //
                            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (DocumentSnapshot document : task.getResult()) {
                                            courses.document(document.getId()).delete();
                                        }
                                    } else {
                                        Log.d(TAG, "Error getting documents: ", task.getException());
                                    }
                                }
                            });

                            //

                            Fragment fragment;
                            fragment = new TeacherEdit();
                            FragmentManager fm = getFragmentManager();
                            FragmentTransaction ft = fm.beginTransaction();
                            ft.replace(R.id.defaultDashboard, fragment);
                            ft.commit();

                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //do nothing
                }
            })

                    // A null listener allows the button to dismiss the dialog and take no further action.
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            //alert




        }


        if(view==editEnroll){


            /////
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Name");

            final View customLayout = getLayoutInflater().inflate(R.layout.enrollment_dialog, null);
            builder.setView(customLayout);

            builder.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    // send data from the
                    // AlertDialog to the Activity

                    EditText editText = customLayout.findViewById(R.id.dialog_edit);
                    newEnroll=editText.getText().toString();

                    ////////update
                    CollectionReference itemsRef = fStore.collection("courses");
                    Query query = itemsRef.whereEqualTo("coursename", selected_course)
                            .whereEqualTo("userID", fAuth.getCurrentUser().getUid().toString());


                    query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (DocumentSnapshot document : task.getResult()) {
                                    itemsRef.document(document.getId()).update("enrollmentkey", newEnroll);
                                }
                            } else {
                                Log.d(TAG, "Error getting documents: ", task.getException());
                            }
                        }
                    });



                    ///////update

                }

            });
            /////

            builder.show();


        }

        if(view==changeImg){

            Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGallery, 1000);


        }




    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {

        ProgressDialog dial=new ProgressDialog(getActivity());
        dial.setMessage("Loading . .");
        dial.show();



        super.onActivityResult(requestCode, resultCode, data);

        ImageView logo=getView().findViewById(R.id.editImage);

        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                imageUri= data.getData();
                logo.setImageURI(imageUri);

                StorageReference storageReference= FirebaseStorage.getInstance().getReference(); //Firebase storage

                String userID=fAuth.getCurrentUser().getUid().toString();

                //Upload image to cloud storage
                StorageReference fileReference= storageReference.child("users/"+userID+"/institutes/"+selected_institute+".jpg");

                fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Uri> task) {
                                String generatedFilePath=task.getResult().toString();

                                    //Update URL

                                CollectionReference itemsRef = fStore.collection("institutes");
                                Query query = itemsRef.whereEqualTo("InstituteName", selected_institute)
                                        .whereEqualTo("UserID", fAuth.getCurrentUser().getUid().toString());


                                query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            for (DocumentSnapshot document : task.getResult()) {
                                                itemsRef.document(document.getId()).update("downloadURL", generatedFilePath);

                                                ProgressDialog dial=new ProgressDialog(getActivity());
                                                dial.setMessage("Loading . .");
                                                dial.show();

                                                Toast.makeText(getActivity(), "Update completed!",Toast.LENGTH_SHORT).show();

                                                dial.dismiss();


                                            }
                                        } else {
                                            Log.d(TAG, "Error getting documents: ", task.getException());
                                        }
                                    }
                                });





                                    //UpdateURL





                            }
                        });






                    }
                });





            }
        }


        dial.dismiss();
    }






    //Custom function to setup second spinner
    public void secondSpinner(View root){


        //Database collection reference 2
        CollectionReference collectionReference2= fStore.collection("courses");

        //Retrieve courses
        collectionReference2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){

                    List<DocumentSnapshot> listDocument2 = task.getResult().getDocuments();

                    List<String> items2=new ArrayList<String>() {};
                    for(DocumentSnapshot document : listDocument2){

                        if(selected_institute.equals(document.get("institute"))) {



                            String course = document.get("coursename").toString();
                            items2.add(course);
                            //getting data to insert into spinner

                        }


                    }

                    if(items2.isEmpty())
                    {
                        //alert box
                        new AlertDialog.Builder(getActivity())
                                .setTitle("No Courses!")
                                .setMessage("Selected institute has no Courses. Add some Courses!")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                        //alert
                    }

                    //setting up the spinner
                    Spinner courses=root.findViewById(R.id.courseTE);

                    ArrayAdapter itemsAdapter2 = new ArrayAdapter<String>(getActivity(), R.layout.spinner_selected, items2);//items3
                    itemsAdapter2.setDropDownViewResource(R.layout.spinner_dropdown);


                    courses.setAdapter(itemsAdapter2);
                    dialog2.dismiss();



                    //set selected listener
                    courses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {


                            //get selected spinner item
                            selected_course=courses.getSelectedItem().toString();
                            Toast.makeText(getActivity(), selected_course+" selected!" , Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        //---Retrieve courses



}



}