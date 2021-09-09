package database;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import models.Person;

public class DataAccess {

    private DatabaseReference dbreference;

    public DataAccess() {

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        dbreference=db.getReference(Person.class.getSimpleName());
    }

    public Task<Void> add(Person person) throws Exception {

            if(person==null){
                throw new Exception("Null signup information");
            }
           else{

                return dbreference.push().setValue(person);

            }


    }
}
