package cegeka.scoaladevalori.ro.taskao;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity {


    TextView titleShow,dateShow, descShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        titleShow = (TextView) findViewById(R.id.etTitleDetail);
        dateShow= (TextView) findViewById(R.id.etDateDetail);
        descShow = (TextView) findViewById(R.id.etDescriptionDetail);

        //GET INTENT
        Intent i=this.getIntent();


        //RECEIVE DATA
        String name=i.getExtras().getString("NAME_KEY");
        String date=i.getExtras().getString("DATE_KEY");
        String desc=i.getExtras().getString("DESC_KEY");


        //BIND DATA
        titleShow.setText(name);
        dateShow.setText(date);
        descShow.setText(desc);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }



}
