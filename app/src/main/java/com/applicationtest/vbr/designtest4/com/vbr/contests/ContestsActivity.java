package com.applicationtest.vbr.designtest4.com.vbr.contests;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.applicationtest.vbr.designtest4.R;
import com.applicationtest.vbr.designtest4.com.vbr.model.ContestsModel;
import com.applicationtest.vbr.designtest4.com.vbr.teamselection.TeamSelection1;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by C5245675 on 4/8/2017.
 */
public class ContestsActivity extends AppCompatActivity {

    ProgressBar pb;
    Button button;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    List<ContestsModel> contest_data = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contests_layout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.mToolbar);
        TextView mToolbarTitle  = (TextView)toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        loadcontests();

        Intent i = getIntent();

        String match_intent_one = i.getStringExtra("team_intent_one");
        String match_intent_two = i.getStringExtra("team_intent_two");

        String title = match_intent_one  + "  VS  " + match_intent_two;

        mToolbarTitle.setText(title);

        recyclerView = (RecyclerView) findViewById(R.id.contestsrecyclerView);

        //layoutManager = new LinearLayoutManager(this);
        layoutManager = new GridLayoutManager(this,2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CustomAdaptercontests( this,contest_data);
        recyclerView.setAdapter(adapter);


        pb = (ProgressBar) findViewById(R.id.progress_bar);







    }


    void loadcontests()
    {
       AsyncTask<Void,Void,Void> task = new AsyncTask<Void,Void,Void>()
        {
            @Override
            protected void onPreExecute() {
            super.onPreExecute();

        }



            @Override
            protected Void doInBackground(Void... voids) {

                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("http://10.0.2.2/TEST/Latest/loadcontests.php")
                        .build();


                try {


                    Response response = client.newCall(request).execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("contests");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject tempObject = jsonArray.getJSONObject(i);

                        ContestsModel contestdata = new ContestsModel(tempObject.getString("contest_entry")
                                , tempObject.getString("contest_split_between"), tempObject.getString("contest_money_total"));
                        contest_data.add(contestdata);

                    }

                }
                catch (Exception e)

                {
                    e.printStackTrace();
                }

            return null;
        }

            @Override
            protected void onPostExecute(Void avoid) {
            super.onPostExecute(avoid);

        }
        };
        task.execute();



    }





    public static class CustomAdaptercontests extends RecyclerView.Adapter<CustomAdaptercontests.ViewHolder>
    {
        Context context;



        List<ContestsModel> contests_data;
        public CustomAdaptercontests(Context context,List<ContestsModel> contests_data) {

            this.context = context;
            this.contests_data = contests_data;

        }

        @Override
        public CustomAdaptercontests.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contests_cardview,parent,false);

            return new CustomAdaptercontests.ViewHolder(itemView);

        }
        public static class ViewHolder extends RecyclerView.ViewHolder
        {
            public TextView entryFee;
            public TextView totalWinnings;
            public TextView splitBy;
            public Button bt;



            public ViewHolder(View itemView) {
                super(itemView);
                this.entryFee = (TextView)itemView.findViewById(R.id.entry_fee);
                this.totalWinnings = (TextView)itemView.findViewById(R.id.total_winnings);
                this.splitBy = (TextView)itemView.findViewById(R.id.splitBy);
                this.bt = (Button)itemView.findViewById(R.id.button);




              /*  cardView.setOnClickListener(new
                                                    View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View view) {
                                                            Toast.makeText(view.getContext(),"pressed",Toast.LENGTH_LONG).show();
                                                        }
                                                    });*/
            }


        }



        @Override
        public void onBindViewHolder(final CustomAdaptercontests.ViewHolder holder, int position) {



            holder.entryFee.setText( "Entry \u20B9"+" "+ contests_data.get(position).getContest_entry());

            holder.totalWinnings.setText("Winnings \u20B9"+" "+contests_data.get(position).getContest_total_money());
            System.out.println("Winnings \u20B9"+" "+contests_data.get(position).getContest_total_money());
            holder.splitBy.setText("Split By"+" "+contests_data.get(position).getContest_split_between());

           holder.bt.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   Intent i = new Intent(v.getContext(),TeamSelection1.class);
                   v.getContext().startActivity(i);


               }
           });



        }



        @Override
        public int getItemCount() {
            return contests_data.size();
        }





    }

}
