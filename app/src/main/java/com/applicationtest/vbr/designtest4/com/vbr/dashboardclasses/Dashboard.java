package com.applicationtest.vbr.designtest4.com.vbr.dashboardclasses;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.applicationtest.vbr.designtest4.R;

import java.util.List;


public class Dashboard extends AppCompatActivity{

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);


       toolbar = (Toolbar) findViewById(R.id.mToolbar_dashboard);
        setSupportActionBar(toolbar);

        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }

    public static class CustomAdapterDash extends RecyclerView.Adapter<CustomAdapterDash.ViewHolder>
    {
        Context context;



        List<DashboardData> my_data;
        public CustomAdapterDash(Context context,List<DashboardData> my_data) {

            this.context = context;
            this.my_data = my_data;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerlist_item,parent,false);

            return new ViewHolder(itemView);

        }
        public static class ViewHolder extends RecyclerView.ViewHolder
        {
            public TextView teamnameDash;

            public  ImageView DashboardArrow;
            public CardView dashboardCardview;



            public ViewHolder(View itemView) {
                super(itemView);
                this.teamnameDash = (TextView)itemView.findViewById(R.id.team_name_dash);

                this.DashboardArrow = (ImageView)itemView.findViewById(R.id.team2_image);
                this.dashboardCardview = (CardView)itemView.findViewById(R.id.dashboard_cardview);



            }


        }



        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {





            holder.teamnameDash.setText("my team 1");





        }



        @Override
        public int getItemCount() {
            return my_data.size();
        }





    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
