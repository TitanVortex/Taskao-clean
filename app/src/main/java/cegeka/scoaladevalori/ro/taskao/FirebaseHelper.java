package cegeka.scoaladevalori.ro.taskao;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Toast;

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
    String mUserId, mActivityId;
    ListViewActivity.CustomAdapter adapter;


    String saved=null;
    static ArrayList <UserActivities>  userActivities=new ArrayList<>();


    public FirebaseHelper(DatabaseReference db) {
        this.db = db;

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        mUserId = firebaseUser.getUid();

    }

    //WRITE IF NOT NULL
    public String save(UserActivities activity)
    {
        if(activity==null)
        {
            saved=null;
        }else
        {
            mActivityId=db.child(mUserId).child("tasks").push().getKey();
            activity.userActivityId = mActivityId;
            db.child(mUserId).child("tasks").child(mActivityId).setValue(activity);
            try
            {

                saved=mActivityId;

            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=null;
            }
        }

        return saved;
    }

    public void remove(String id_tasks) {
        db.child(mUserId).child("tasks").child(id_tasks).removeValue();
    }

    public String getmActivityId(String mActivityId) {
        return this.mActivityId;
    }

    public ArrayList<UserActivities> getUserActivities() {
         DatabaseReference dbRef = db.child(mUserId).child("tasks");
         dbRef.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 UserActivities x = dataSnapshot.getValue(UserActivities.class);
                 userActivities.add(x);
                 //adapter.notifyDataSetChanged();
             }

             @Override
             public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

             }

             @Override
             public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                 //adapter.notifyDataSetChanged();
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


}