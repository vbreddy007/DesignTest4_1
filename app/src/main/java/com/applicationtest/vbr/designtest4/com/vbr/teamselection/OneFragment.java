package com.applicationtest.vbr.designtest4.com.vbr.teamselection;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import com.applicationtest.vbr.designtest4.PlayersModel;
import com.applicationtest.vbr.designtest4.R;
import com.bumptech.glide.Glide;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by C5245675 on 4/15/2017.
 */
public class OneFragment extends Fragment  {

    RecyclerView recyclerView;
    List<PlayersModel> data_list = new ArrayList<>();
    CustomAdapter adapter;
    public OneFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("on on start method");
        load_players();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.coordinator_layout, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerView);
        setupRecyclerView(recyclerView);
        return rootView;
    }

    void load_players()
    {

        AsyncTask<Void,Void,Void> task = new AsyncTask<Void,Void,Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("httP://10.0.2.2/TEST/Latest/matchpPlayers.php")
                        .build();


                try
                {
                    Response response = client.newCall(request).execute();

                    System.out.println("response is "+response);

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    System.out.println("this is JSON OBJECT" + jsonObject);

                    JSONArray jsonArray = jsonObject.getJSONArray("players");

                    for(int i=0 ; i<jsonArray.length();i++)
                    {

                        JSONObject tempObject = jsonArray.getJSONObject(i);

                       // PlayersModel playerdata = new PlayersModel(tempObject.getString("player_name")
                              //  ,tempObject.getString("player_points"),tempObject.getString("player_team"),tempObject.getString("player_team_icon"),tempObject.getString("player_cat"));
                      //  data_list.add(playerdata);

                    }


                }
                catch (IOException IOe)
                {
                    IOe.printStackTrace();
                }
                catch (JSONException jse)
                {
                    jse.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };
        task.execute();


    }

    public void setupRecyclerView(RecyclerView recyclerView){

        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new CustomAdapter(getActivity(),data_list);
        recyclerView.setAdapter(adapter);


    }



    public static class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder>
    {
        int selected_position=-1;
        Context context;
       TeamSelection teamSelection;
        OneFragment fragment;

        List<PlayersModel> playerData;
        public CustomAdapter(Context context,List<PlayersModel> playerData) {

            this.context = context;
            this.playerData = playerData;
            teamSelection= (TeamSelection)context;


        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_select_cardview,parent,false);

            return new ViewHolder(itemView,teamSelection);




        }
        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.playerName.setText(playerData.get(position).getPlayer_name());
            holder.playerTeam.setText(playerData.get(position).getPlayer_team());
            holder.playerPoints.setText(playerData.get(position).getPlayer_points());
            Glide.with(context).load(playerData.get(position).getPlayer_team_icon()).into(holder.playerTeamIcon);

            if(!teamSelection.is_in_action_mode) {
                holder.checkBox.setVisibility(View.GONE);
            }
            else
            {
                holder.checkBox.setVisibility(View.GONE);
                holder.checkBox.setEnabled(false);
            }




        }

        public  static class ViewHolder extends RecyclerView.ViewHolder
        {

            public TextView playerName;
            public TextView playerTeam;
            public TextView playerPoints;
            public ImageView playerTeamIcon;
            public CheckBox checkBox;
            TeamSelection teamSelection;
            CardView cardView;

            public ViewHolder(View itemView,TeamSelection teamSelection) {
                super(itemView);
                this.playerName = (TextView)itemView.findViewById(R.id.player_name);
                this.playerPoints = (TextView)itemView.findViewById(R.id.player_points);
                this.playerTeam = (TextView)itemView.findViewById(R.id.player_team);
                this.playerTeamIcon =(ImageView)itemView.findViewById(R.id.player_team_icon);
                this.checkBox = (CheckBox)itemView.findViewById(R.id.check_box);


                this.teamSelection = teamSelection;
                cardView = (CardView)itemView.findViewById(R.id.player_select_cardview);







            }


        }


        @Override
        public int getItemCount() {
            return playerData.size();
        }
    }


}
