package TeacherFragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.betterlearn.R;
import com.example.betterlearn.SignUp;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class newAnnouncement extends Fragment implements View.OnClickListener {

    public static final String TAG="TAG";
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v =  inflater.inflate(R.layout.fragment_new_announcement, container, false);

        Spinner spinner = (Spinner) v.findViewById(R.id.institute);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this.getActivity(), R.array.institutes, R.layout.spinner_selected);
        adapter1.setDropDownViewResource(R.layout.spinner_dropdown);
        spinner.setAdapter(adapter1);

        Button b = (Button) v.findViewById(R.id.button6);
        b.setOnClickListener(this);

        return v;
    }


    @Override
    public void onClick(View v){
        //firebase Authentication initialize
        fAuth=FirebaseAuth.getInstance();
        //firestore databse initialize
        fStore=FirebaseFirestore.getInstance();

        EditText Title = (EditText) getView().findViewById(R.id.editTextTextPersonName3);
        EditText Description = (EditText) getView().findViewById(R.id.editTextTextMultiLine3);
        Spinner Institutes = (Spinner) getView().findViewById(R.id.institute);
        Log.d(TAG, "newAnnouncement:" + Institutes.getSelectedItem().toString());

        String rTitle=Title.getText().toString().trim();
        String rDescription=Description.getText().toString();
        String rInstitutes=Institutes.getSelectedItem().toString();

        if(TextUtils.isEmpty(rTitle)){
            Title.setError("Title should not be empty");
            return;

        }

        if(TextUtils.isEmpty(rDescription)){
            Title.setError("Title should not be empty");
            return;

        }else{
            userID=fAuth.getCurrentUser().getUid();

            //database document reference
            DocumentReference documentReference= fStore.collection("announcements").document();   //Collection="announce"

            Map<String, Object> announce= new HashMap<>();
            announce.put("title", rTitle);
            announce.put("description", rDescription);
            announce.put("institutes", rInstitutes);
            announce.put("user", userID);

            documentReference.set(announce).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG, "User profile created for "+userID);
                    Title.getText().clear();
                    Description.getText().clear();
                    Toast.makeText(getActivity(), "Announcement Created", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "OnFailure: "+e.toString());
                    Toast.makeText(getActivity(), "Error!.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
/*public class AdminAnnouncement extends Fragment implements View.OnClickListener  {

    Button Add_announcement;
    TextView Add_announcement_text;
    GridView coursesGV;
    ArrayList<Announcement> dataModalArrayList;
    FirebaseFirestore db;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.fragment_admin_announcement, container, false);

        // Inflate the layout for this fragment
        Add_announcement = (Button) myView.findViewById(R.id.add_announcement);


        Add_announcement.setOnClickListener(this);


        coursesGV = myView.findViewById(R.id.admin_announcemets_sec);
        dataModalArrayList = new ArrayList<>();

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();
        // here we are calling a method
        // to load data in our list view.
        loadDatainGridView();
        return myView;
    }

    @Override
    public void onClick(View view) {
        Fragment fragment;



            fragment = new newAnnouncement();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.defaultDashboard, fragment);
            ft.commit();
*/