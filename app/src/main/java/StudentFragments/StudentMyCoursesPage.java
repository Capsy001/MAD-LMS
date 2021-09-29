package StudentFragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.betterlearn.R;

import models.Content;
import models.Institute;


public class StudentMyCoursesPage extends Fragment {

    public StudentMyCoursesPage() {
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
         View root=inflater.inflate(R.layout.fragment_student_my_courses_page, container, false);

        ProgressDialog dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading . .");
        dialog.show();

        Bundle bundle=getArguments();
        Content content =(Content) bundle.getSerializable("selected_content");



        TextView inst=root.findViewById(R.id.instituteMCP);
        TextView crs=root.findViewById(R.id.coursesMCP);
        TextView title=root.findViewById(R.id.titleMCP);
        TextView desc=root.findViewById(R.id.descriptionMCP);
        TextView link_desc=root.findViewById(R.id.linkdescMCP);
        Button download=root.findViewById(R.id.downloadMCP);
        Button gotolink=root.findViewById(R.id.gotolinkMCP);

        title.setText(content.getTitle());
        inst.setText(content.getInstituteName());
        crs.setText(content.getCourseName());
        desc.setText(content.getDescription());
        link_desc.setText(content.getLinkDescription());

        Toast.makeText(getActivity(), content.getType()+"xxxx", Toast.LENGTH_SHORT).show();


        if(content.getType().equals("text")){
            download.setVisibility(View.GONE);
            gotolink.setVisibility(View.GONE);
        }

        if(content.getType().equals("pdf")){
            gotolink.setVisibility(View.GONE);
        }
        if(content.getType().equals("link")){
            download.setVisibility(View.GONE);

        }

        dialog.dismiss();

        gotolink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    Uri uri = Uri.parse(content.getLinkDescription()); // missing 'http://' will cause crashed
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(getActivity(), "Error with the link!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(content.getDownloadURL()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });






        return root;



    }
}