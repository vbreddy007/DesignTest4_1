package com.applicationtest.vbr.designtest4.com.vbr.contests;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applicationtest.vbr.designtest4.MyData;
import com.applicationtest.vbr.designtest4.R;

import com.bumptech.glide.Glide;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.List;


public class CONTESTS extends Fragment {

    RecyclerView recyclerView;
    List<MyData> data_list = new ArrayList<>();
    CustomAdapter adapter;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        View rootView = inflater.inflate(R.layout.contests_layout, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.contestsrecyclerView);
        setupRecyclerView(recyclerView);



        return rootView;
    }

    private void setupRecyclerView(RecyclerView recyclerView){

        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(),2));
        adapter = new CONTESTS.CustomAdapter(getActivity(),data_list);
        recyclerView.setAdapter(adapter);


    }

    public static class CustomAdapter extends RecyclerView.Adapter<CONTESTS.CustomAdapter.ViewHolder>
    {
        Context context;
        List<MyData> my_data;

        public CustomAdapter(Context context,List<MyData> my_data) {

            this.context = context;
            this.my_data = my_data;

        }

        @Override
        public CONTESTS.CustomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.contests_card1,parent,false);

            return new ViewHolder(itemView);

        }
        public static class ViewHolder extends RecyclerView.ViewHolder
        {


            public CardView cardView;

            public TextView percentage;
            public DonutProgress donutProgress;


            public DonutProgress getDonutProgress() {
                return donutProgress;
            }

            public void setDonutProgress(DonutProgress donutProgress) {
                this.donutProgress = donutProgress;
            }

            public ViewHolder(View itemView) {
                super(itemView);



                    // this.donutProgress = (DonutProgress)itemView.findViewById(R.id.prg_bar);
                setDonutProgress((DonutProgress)itemView.findViewById(R.id.prg_bar));





            }



        }



        @Override
        public void onBindViewHolder(final CONTESTS.CustomAdapter.ViewHolder holder, int position) {



           // holder.teamTwo.setText(my_data.get(position).getTeam_two());

           // holder.percentage.setText("90");

            new PrgressBarTask().execute();
            holder.donutProgress.setProgress(4);


           /* holder.cardView.setOnClickListener(new View.OnClickListener()
                                               {

                                                   @Override
                                                   public void onClick(View view) {

                                                       Intent intent = new Intent(view.getContext(),ContestsActivity1.class);
                                                      // intent.putExtra("team_intent_one",holder.teamOne.getText());
                                                       //intent.putExtra("team_intent_two",holder.teamTwo.getText());

                                                       view.getContext().startActivity(intent);

                                                       // Toast.makeText(view.getContext(),"pressed"+holder.teamOne.getText(),Toast.LENGTH_LONG).show();
                                                   }
                                               }

            );*/


        }



        @Override
        public int getItemCount() {


            //return my_data.size();
            return 5;
        }


        class PrgressBarTask extends AsyncTask<Void,Integer,Integer>
        {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }

            @Override
            protected void onProgressUpdate(Integer... values) {

                donutProgress.setProgress(values[0]);



            }

            @Override
            protected Integer doInBackground(Void... params) {

                for (int i=0;i<=100;i++)
                {
                    publishProgress(i);

                    try
                    {
                        Thread.sleep(200);
                    }
                    catch (InterruptedException ioe)
                    {
                        ioe.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);

            }
        }


    }

}
