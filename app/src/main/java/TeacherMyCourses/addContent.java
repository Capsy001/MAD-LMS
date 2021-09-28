package TeacherMyCourses;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betterlearn.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.app.Activity.RESULT_OK;


public class addContent extends Fragment {


    public static final String TAG = "TAG";
    FirebaseAuth fAuth= FirebaseAuth.getInstance();
    FirebaseFirestore fStore= FirebaseFirestore.getInstance();

    ProgressDialog dialog2;

    String selected_institute;
    String selected_course;
    String content_id=null;

    Uri pdf=null;


    public addContent() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View root= inflater.inflate(R.layout.fragment_add_content, container, false);


        //clear overlapping fragments
        container.clearDisappearingChildren();

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.show();

        //clear radio button selection
        RadioGroup radioGroup=root.findViewById(R.id.radioGroup);
        radioGroup.clearCheck();

        //Hide button and edit text until radio button checked
        Button select=root.findViewById(R.id.selectAD);
        select.setVisibility(View.GONE);

        EditText link_description=root.findViewById(R.id.link_descriptionAD);
        link_description.setVisibility(View.GONE);




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
                    Spinner institutes=root.findViewById(R.id.instituteAD);

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







        //get course content to upload to database
        Button addContent               =root.findViewById(R.id.addAD);//ADD button
        select                          =root.findViewById(R.id.selectAD);
        EditText title                  =root.findViewById(R.id.titleAD);
        EditText title_description      =root.findViewById(R.id.title_descriptionAD);
        RadioButton link_radio          =root.findViewById(R.id.linkAD);
        RadioButton pdf_radio           =root.findViewById(R.id.pdfAD);
        RadioButton description_radio   =root.findViewById(R.id.descriptionAD);
        link_description                =root.findViewById(R.id.link_descriptionAD);
        radioGroup                      =root.findViewById(R.id.radioGroup);


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pdf_radio.isChecked()) {

                    DocumentReference content_increment=fStore.collection("ids").document("contents");

                    content_increment.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            content_id=documentSnapshot.get("contentID").toString();
                        }
                    });


                    Intent galleryIntent = new Intent();
                    galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

                    // redirected to choose pdf
                    galleryIntent.setType("application/pdf");
                    startActivityForResult(galleryIntent, 1);


                }
            }
        });




        pdf_radio.setChecked(true);
        select.setVisibility(View.VISIBLE);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

                Button select=root.findViewById(R.id.selectAD);
                EditText link_description=root.findViewById(R.id.link_descriptionAD);

                if(link_radio.isChecked())
                {
                    link_description.setHint("Link");
                    select.setVisibility(View.GONE);
                    link_description.setVisibility(View.VISIBLE);
                }
                else if(pdf_radio.isChecked())
                {

                    select.setVisibility(View.VISIBLE);
                    link_description.setVisibility(View.GONE);

                }
                else if(description_radio.isChecked())
                {
                    link_description.setHint("Text");
                    select.setVisibility(View.GONE);
                    link_description.setVisibility(View.VISIBLE);

                }


            }
        });


        addContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Button select=root.findViewById(R.id.selectAD);
                EditText link_description=root.findViewById(R.id.link_descriptionAD);

                if(link_radio.isChecked())
                {
                    if(link_description.getText().toString().isEmpty()){

                        link_description.setError("Link Cannot be empty!");
                        return;

                    }

                }
                else if(pdf_radio.isChecked())
                {

                    if(pdf==null){
                        //alert box
                        new AlertDialog.Builder(getActivity())
                                .setTitle("Error!")
                                .setMessage("You need to select a PDF file!")
                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        return;

                                    }
                                })

                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setIcon(android.R.drawable.stat_sys_warning)
                                .show();
                        //alert
                    }

                }
                else if(description_radio.isChecked())
                {
                    if(link_description.getText().toString().isEmpty()){

                        link_description.setError("Text Cannot be empty!");
                        return;

                    }

                }

                String titleAD=title.getText().toString();
                String title_descriptionAD=title_description.getText().toString();

                if(TextUtils.isEmpty(titleAD)){
                    title.setError("Title cannot be empty!");
                    return;
                }

                if(TextUtils.isEmpty(title_descriptionAD)){
                    title_description.setError("Description cannot be empty!");
                    return;
                }

                if(pdf_radio.isChecked()) {

                    String titleString = title.getText().toString();
                    String descriptionString = title_description.getText().toString();

                    DocumentReference addCourseDB = fStore.collection("content").document();

                    Map<String, Object> content = new HashMap<>();
                    content.put("title", titleString);
                    content.put("description", descriptionString);
                    content.put("institute", selected_institute);
                    content.put("course", selected_course);
                    content.put("contentID", content_id);
                    content.put("type", "pdf");
                    content.put("link_description", "null");

                    addCourseDB.set(content).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {

                            //increment content id
                            DocumentReference content_increment=fStore.collection("ids").document("contents");
                            int x=Integer.parseInt(content_id);
                            x++;

                            content_id=String.valueOf(x);

                            Map<String, Object> content_id_incremented = new HashMap<>();
                            content_id_incremented.put("contentID", content_id);

                            content_increment.set(content_id_incremented);


                            title.getText().clear();
                            title_description.getText().clear();


                            //alert box
                            new AlertDialog.Builder(getActivity())
                                    .setTitle("Success!")
                                    .setMessage("Content Added!")
                                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {


                                        }
                                    })

                                    // A null listener allows the button to dismiss the dialog and take no further action.
                                    .setIcon(android.R.drawable.checkbox_on_background)
                                    .show();
                            //alert
                        }
                    });





                }

                if(link_radio.isChecked() || description_radio.isChecked()) {

                    DocumentReference content_increment=fStore.collection("ids").document("contents");

                    content_increment.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            String cid=null;
                            cid=documentSnapshot.get("contentID").toString();



                            String titleString = title.getText().toString();
                            String descriptionString = title_description.getText().toString();
                            String link_desc = link_description.getText().toString();

                            String type = null;
                            if(link_radio.isChecked()){
                                type="link";
                            }
                            else if(description_radio.isChecked()){
                                type="text";
                            }

                            DocumentReference addCourseDB = fStore.collection("content").document();

                            Toast.makeText(getActivity(), "In increment "+content_id,Toast.LENGTH_SHORT).show();

                            Map<String, Object> content = new HashMap<>();
                            content.put("title", titleString);
                            content.put("description", descriptionString);
                            content.put("institute", selected_institute);
                            content.put("course", selected_course);
                            content.put("contendID", cid);
                            content.put("type", type);
                            content.put("link_description", link_desc);

                            addCourseDB.set(content).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    //increment content id
                                    DocumentReference content_increment=fStore.collection("ids").document("contents");
                                    content_increment.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                        @Override
                                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                                            String cid=documentSnapshot.get("contentID").toString();

                                            int x=Integer.parseInt(cid);
                                            x++;

                                            cid=String.valueOf(x);



                                            Map<String, Object> content_id_incremented = new HashMap<>();
                                            content_id_incremented.put("contentID", cid);

                                            //increment contentID
                                            content_increment.set(content_id_incremented);
                                        }
                                    });




                                    title.getText().clear();
                                    title_description.getText().clear();
                                    link_description.getText().clear();


                                    //alert box
                                    new AlertDialog.Builder(getActivity())
                                            .setTitle("Success!")
                                            .setMessage("Content Added!")
                                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {


                                                }
                                            })

                                            // A null listener allows the button to dismiss the dialog and take no further action.
                                            .setIcon(android.R.drawable.checkbox_on_background)
                                            .show();
                                    //alert
                                }
                            });

                        }   //increment on success
                    });


                }   //if link_radio


            }
        });










        return root;
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
                    Spinner courses=root.findViewById(R.id.courseAD);

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        StorageReference fStorage= FirebaseStorage.getInstance().getReference();
        String user_ID= fAuth.getCurrentUser().getUid();

        if(resultCode==RESULT_OK)
        {
            ProgressDialog dialog3=new ProgressDialog(getActivity());
            dialog3.setMessage("Uploading . .");
            dialog3.show();

            pdf=data.getData();

            final StorageReference filepath = fStorage.child( "users/"+user_ID+"/pdf/"+content_id+".pdf" );





            filepath.putFile(pdf).continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        // After uploading is done it progress
                        // dialog box will be dismissed
                        dialog3.dismiss();
                        Uri uri = task.getResult();
                        String myurl;
                        myurl = uri.toString();
                        Toast.makeText(getActivity(), "Uploaded Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        dialog3.dismiss();
                        Toast.makeText(getActivity(), "UploadedFailed", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }

    }



