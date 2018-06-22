package cegeka.scoaladevalori.ro.taskao;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

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