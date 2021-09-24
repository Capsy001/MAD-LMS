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
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this.getActivity(), R.array.institutes, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
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

            documentReference.set(announce).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.d(TAG, "User profile created for "+userID);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Log.d(TAG, "OnFailure: "+e.toString());
                }
            });
        }
    }

}