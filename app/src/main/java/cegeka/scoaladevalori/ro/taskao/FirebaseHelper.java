package cegeka.scoaladevalori.ro.taskao;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



public class FirebaseHelper {

    DatabaseReference db ;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    String mUserId;


    Boolean saved=null;
    static ArrayList <UserActivities>  userActivities=new ArrayList<>();


    public FirebaseHelper(DatabaseReference db) {
        this.db = db;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mUserId = firebaseUser.getUid();

    }

    //WRITE IF NOT NULL
    public Boolean save(UserActivities activity)
    {
        if(activity==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child(mUserId).child("tasks").push().setValue(activity);
                saved=true;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }
    //IMPLEMENT FETCH DATA AND FILL ARRAYLIST

    private void fetchData(DataSnapshot dataSnapshot)
    {
        userActivities.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            UserActivities activity=ds.getValue(UserActivities.class);
            userActivities.add(activity);

        }
    }

    public ArrayList<UserActivities> getUserActivities() {
         DatabaseReference dbRef = db.child(mUserId).child("tasks");
         dbRef.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 UserActivities x = dataSnapshot.getValue(UserActivities.class);
                 userActivities.add(x);
             }

             @Override
             public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

             }

             @Override
             public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

             }

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {

             }
         });
         return userActivities;
    }

    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<UserActivities> retrieve() {
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //fetchData(dataSnapshot);


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                //fetchData(dataSnapshot);


            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return userActivities;
    }
}