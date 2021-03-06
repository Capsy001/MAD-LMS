package TeacherMyCourses;

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
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.betterlearn.DashboardProfile;
import com.example.betterlearn.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import StudentFragments.StudentDashboard;
import StudentFragments.StudentMyCourses;

public class addInstitute extends Fragment implements View.OnClickListener {

    public static final String TAG="TAG";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageReference;

    ProgressDialog dialog;
    String instituteID;

    Uri imageUri=null;    //URI of the selected Image



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (container != null) {
            container.removeAllViews();
        }

        // Inflate the layout for this fragment
        View myView= inflater.inflate(R.layout.fragment_add_institute, container, false);
        container.clearDisappearingChildren();


        Button selectLogo = myView.findViewById(R.id.selectlogoAI);
        selectLogo.setOnClickListener(this);


        Button addInstitute= myView.findViewById(R.id.addbtnAI);

        addInstitute.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                addInstitute.setEnabled(false);

                if(imageUri==null){
                    Toast.makeText(addInstitute.getContext(), "Select an Image", Toast.LENGTH_SHORT).show();
                    addInstitute.setEnabled(true);
                    return;
                }

                EditText instituteName=getView().findViewById(R.id.institutenameAI);

                String instituteNameAI= instituteName.getText().toString();

                if(TextUtils.isEmpty(instituteNameAI)){
                    instituteName.setError("Institute Name should not be empty");
                    addInstitute.setEnabled(true);
                    return;
                }

                dialog = new ProgressDialog(getActivity());
                dialog.setMessage("Loading..");
                dialog.show();

                fAuth= FirebaseAuth.getInstance();  //Firebase authentication
                fStore= FirebaseFirestore.getInstance();    //Firebase firestore database
                storageReference= FirebaseStorage.getInstance().getReference(); //Firebase storage


                //get instituteID
                DocumentReference institute_ID=fStore.collection("ids").document("institutes");
                institute_ID.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        instituteID= documentSnapshot.get("instituteID").toString();
                    }
                });



                String userID= fAuth.getCurrentUser().getUid();

                //Upload image to cloud storage
                StorageReference fileReference= storageReference.child("users/"+userID+"/institutes/"+instituteNameAI+".jpg");
                fileReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {


                        taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Uri> task) {

                                String generatedFilePath=task.getResult().toString();


                                //Add institute to database
                                DocumentReference documentReference= fStore.collection("institutes").document();

                                Map<String, Object> institute= new HashMap<>();
                                institute.put("InstituteName", instituteNameAI);
                                institute.put("UserID", userID);
                                institute.put("instituteID", instituteID);
                                institute.put("downloadURL", generatedFilePath);


                                //insert to database
                                documentReference.set(institute).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Log.d(TAG, "Institute Created "+instituteNameAI);


                                        //increment instituteID in database
                                        int x=Integer.parseInt(instituteID);
                                        x++;
                                        instituteID=String.valueOf(x);
                                        HashMap<String, Object> instituteID_map=new HashMap<>();
                                        instituteID_map.put("instituteID", instituteID);
                                        institute_ID.set(instituteID_map);





                                        dialog.dismiss();

                                        //alert box
                                        new AlertDialog.Builder(addInstitute.getContext())
                                                .setTitle("Success!")
                                                .setMessage("Institute Created. Now add some Courses!")
                                                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {

                                                        EditText instituteName=getView().findViewById(R.id.institutenameAI);
                                                        instituteName.setHint("Add Another Institute");
                                                        instituteName.getText().clear();
                                                        ImageView imgView=getView().findViewById(R.id.logoimageviewAI);
                                                        imgView.setImageURI(null);
                                                        addInstitute.setEnabled(true);

                                                    }
                                                })

                                                // A null listener allows the button to dismiss the dialog and take no further action.
                                                .setIcon(android.R.drawable.checkbox_on_background)
                                                .show();
                                        //alert
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull  Exception e) {
                                        Log.d(TAG, "OnFailure: "+e.toString());
                                        addInstitute.setEnabled(true);
                                    }
                                });
                            }
                        });

                        Toast.makeText(addInstitute.getContext(), "Image Uploaded", Toast.LENGTH_SHORT).show();










                    }//onsuccess
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull @NotNull Exception e) {
                        Toast.makeText(addInstitute.getContext(), "Failed to upload", Toast.LENGTH_SHORT).show();
                        addInstitute.setEnabled(true);
                    }
                });
            }
        });



        return myView;
    }

    @Override
    public void onClick(View view) {

        Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(openGallery, 1000);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        ImageView logo=getView().findViewById(R.id.logoimageviewAI);

        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                imageUri= data.getData();
                logo.setImageURI(imageUri);

            }
        }
    }
}

