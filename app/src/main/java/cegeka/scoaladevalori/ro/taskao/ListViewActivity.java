package cegeka.scoaladevalori.ro.taskao;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {
    int k=0;
    private FirebaseAuth firebaseAuth;
    DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;
    EditText titleList,dateList,descList;
    String title, desc, date, id;
    SwipeRefreshLayout swipeRefreshLayout;
    //private FirebaseDatabase firebaseDatabase;
    //private FirebaseUser firebaseUser;
    //String mUserId;
    //static String mActId;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipe_down);

        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseUser = firebaseAuth.getCurrentUser();
        //firebaseDatabase = FirebaseDatabase.getInstance();
        //mUserId = firebaseUser.getUid();

        lv = (ListView) findViewById(R.id.listView);

        //INITIALIZE FIREBASE DB
        db= FirebaseDatabase.getInstance().getReference();
        helper=new FirebaseHelper(db);

        adapter=new CustomAdapter(ListViewActivity.this, helper.getUserActivities());
        lv.setAdapter(adapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInputDialog();
                refresh();
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });
    }

    public class CustomAdapter extends BaseAdapter {
        Context c;
        DatabaseReference db;
        int s;
        ArrayList<UserActivities> userActivities;


        public CustomAdapter(Context c, ArrayList<UserActivities> userActivities) {
            this.c = c;
            this.userActivities = userActivities;
        }
        @Override
        public int getCount() {
            return userActivities.size();
        }

        @Override
        public Object getItem(int pos) {
            return userActivities.get(pos);
        }

        @Override
        public long getItemId(int pos) {
            return pos;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if(convertView==null)
            {
                convertView= LayoutInflater.from(c).inflate(R.layout.single_item,viewGroup,false);
            }

            TextView titleShow= (TextView) convertView.findViewById(R.id.tvTitleShow);
            TextView dateShow= (TextView) convertView.findViewById(R.id.tvDateShow);
            TextView descShow= (TextView) convertView.findViewById(R.id.tvDescriptionShow);

            final UserActivities s= (UserActivities) this.getItem(position);

            titleShow.setText(s.getUserActivityTitile());
            dateShow.setText(s.getUserActivityDate());
            descShow.setText(s.getUserActivityDescription());


            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //OPEN DETAIL
                    openDetailActivity(s.getUserActivityTitile(),s.getUserActivityDate(),s.getUserActivityDescription(),s.getUserActivityId());

                }
            });

            return convertView;
        }

        //OPEN DETAIL ACTIVITY
        private void openDetailActivity(String...details)
        {

            Intent i=new Intent(c,DetailActivity.class);
            i.putExtra("NAME_KEY",details[0]);
            i.putExtra("DATE_KEY",details[1]);
            i.putExtra("DESC_KEY",details[2]);
            i.putExtra("DELETE_KEY",details[3]);


            c.startActivity(i);

        }
    }
    private void displayInputDialog()
    {
        final Dialog d=new Dialog(ListViewActivity.this);
        d.setTitle("Save To Firebase");
        d.setContentView(R.layout.activity_add);

        titleList= (EditText) d.findViewById(R.id.etTitleActivity);
        dateList= (EditText) d.findViewById(R.id.etDueDate);
        descList= (EditText) d.findViewById(R.id.etDescriptionActivity);
        Button saveBtn= (Button) d.findViewById(R.id.btnAddActivityMain);


        //SAVE
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //GET DATA
                title=titleList.getText().toString();
                date=dateList.getText().toString();
                desc=descList.getText().toString();



                //SET DATA
                UserActivities s=new UserActivities();
                s.setUserActivityTitile(title);
                s.setUserActivityDate(date);
                s.setUserActivityDescription(desc);




                //SIMPLE VALIDATION
                if(validate())
                {
                    //THEN SAVE
                    String id_introdus= helper.save(s);

                    if(id_introdus!=null)
                    {
                        id = id_introdus;
                        s.setUserActivityId(id);
                        //IF SAVED CLEAR EDITXT
                        titleList.setText("");
                        dateList.setText("");
                        descList.setText("");

                        k=0;
                        d.dismiss();
                    }
                }

            }
        });
        d.show();

    }

    public void refresh()
    {

        lv.setAdapter( null );
        lv.setAdapter(adapter);
        swipeRefreshLayout.setRefreshing(false);
    }

    public void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(ListViewActivity.this, LoginActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private Boolean validate() {
        Boolean result = false;
        title = titleList.getText().toString();
        desc = descList.getText().toString();
        date = dateList.getText().toString();

        if (title.isEmpty() || desc.isEmpty() || date.isEmpty()) {
            Toast.makeText(ListViewActivity.this, "Please enter all the details!", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }

}
