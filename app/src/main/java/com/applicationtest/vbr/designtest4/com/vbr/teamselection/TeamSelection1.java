package com.applicationtest.vbr.designtest4.com.vbr.teamselection;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.applicationtest.vbr.designtest4.PlayersModel;
import com.applicationtest.vbr.designtest4.R;
import com.applicationtest.vbr.designtest4.com.vbr.DBHelpers.TeamDBHandler;
import com.applicationtest.vbr.designtest4.com.vbr.model.TeamSelectionModel;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;


public class TeamSelection1 extends AppCompatActivity implements View.OnLongClickListener {

    boolean is_in_action_mode = false;

    List<PlayersModel> data_list = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    Toolbar toolbar;
    ArrayList<PlayersModel> selection_list = new ArrayList<>();
    ArrayList<String> selection_BAT = new ArrayList<>();
    ArrayList<String> selection_BOW = new ArrayList<>();
    ArrayList<String> selection_WK  = new ArrayList<>();
    ArrayList<String> selected_team = new ArrayList<>();
     int count_selection = 0;
    int bat_count_selection = 0;
    int bow_count_selection = 0;
    int wk_count_selection = 0;
    TextView fabText;
    Button saveTeam;
    String playerName;
    String teamStringforJSON;
    String teamnameG;
    FirebaseUser firebaseUser;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.team_selection1);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        load_players();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        layoutManager = new GridLayoutManager(this,2);

       // layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new CustomAdapter1(this, data_list);
        recyclerView.setAdapter(adapter);
        fabText = (TextView) findViewById(R.id.fab2Text);
        toolbar = (Toolbar)findViewById(R.id.teamSelectionToolbar);
        saveTeam=(Button) findViewById(R.id.button_save);




        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    void load_players() {

        AsyncTask<Void, Void, Void> task = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url("httP://10.0.2.2/TEST/Latest/matchpPlayers.php")
                        .build();


                try {
                    Response response = client.newCall(request).execute();

                    System.out.println("response is " + response);

                    JSONObject jsonObject = new JSONObject(response.body().string());

                    System.out.println("this is JSON OBJECT" + jsonObject);

                    JSONArray jsonArray = jsonObject.getJSONArray("players");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject tempObject = jsonArray.getJSONObject(i);

                        PlayersModel playerdata = new PlayersModel(tempObject.getString("player_id"),tempObject.getString("player_name")
                                , tempObject.getString("player_points"), tempObject.getString("player_team"),
                                tempObject.getString("player_team_icon"),tempObject.getString("player_cat"));
                        data_list.add(playerdata);

                    }


                } catch (IOException IOe) {
                    IOe.printStackTrace();
                } catch (JSONException jse) {
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


    void senTeamMethod() {

        AsyncTask<Void,Void,Void> sendteamTask = new AsyncTask<Void,Void,Void>() {

            @Override
            protected Void doInBackground(Void... urls) {

                MediaType mt = MediaType.parse("application/json; charset=utf-8");
                JSONObject jsonObject = new JSONObject();
                String json;

                try {

                    System.out.println("background task of send team is executing");

                   // JSONObject ob = jsonObject.accumulate("team", teamStringforJSON.toString());

                   JSONObject ob = new JSONObject();
                    ob.put("team",teamStringforJSON.toString());
                    ob.put("teamname",teamnameG);

                    System.out.println("JSONOBJECT accumuluted " + ob.toString());



                    okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();
                 //   RequestBody body = RequestBody.create(mt, ob.toString());

                   // RequestBody body = new MultipartBuilder()

                           // .addFormDataPart("team",ob.toString()).build();



                    // in okHttp 3.x
                    FormBody.Builder formBuilder = new FormBody.Builder()
                            .add("team",teamStringforJSON.toString());



                   formBuilder.add("teamname",teamnameG);

                    if(firebaseUser!=null)
                    {

                        String uName= firebaseUser.getDisplayName();
                        String uEmail = firebaseUser.getEmail();

                        if(uEmail != null)
                        {
                            formBuilder.add("useremail",uEmail);
                        }
                    }



                    okhttp3.RequestBody  body = formBuilder.build();

                   okhttp3.Request request = new okhttp3.Request.Builder()
                            .url("http://10.0.2.2/TEST/Latest/readteam.php")
                            .post(body)
                            .build();

                    System.out.println("This is post body "+body.toString());

                    okhttp3.Response response = client.newCall(request).execute();
                    String responseS = response.body().string();

                    Log.i("@", "" + responseS);


                } catch (Exception e) {
                    e.printStackTrace();


                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        };
        sendteamTask.execute();



    }





    @Override
    public boolean onLongClick(View v) {

        is_in_action_mode = true;
        adapter.notifyDataSetChanged();


        return true;
    }

    public void prepareSelection(View view, int position,String Dum,String player_Name, String playerteam)

    {
        System.out.println("inside prepared selection "+player_Name +" , "+playerteam);
       // System.out.println("data list position " + data_list.get(position).toString());
       // System.out.println("the DUM value" + Dum);
        if (((CheckBox) view).isChecked())

        {
            //System.out.println("inside check condtion " );
            if(Dum.equalsIgnoreCase("BAT"))
            {
                //System.out.println("inside first if" );
                selection_BAT.add("BAT");
               // System.out.println("inside if and after adding" );
                bat_count_selection = bat_count_selection +1;

            }
            else if(Dum.equalsIgnoreCase("BOW"))
            {
                selection_BOW.add("BOW");
                bow_count_selection = bow_count_selection +1;
            }
            else if(Dum.equalsIgnoreCase("WK"))
            {
                selection_WK.add("WK");
                wk_count_selection = wk_count_selection +1;

            }

            selection_list.add(data_list.get(position));
            selected_team.add(player_Name);
            System.out.println("this is final selected team" +selected_team.toString() );

            System.out.println("this is slelection list" + selection_list);
            count_selection = count_selection + 1;

            System.out.println("count selection " + count_selection);
            System.out.println("selction CAt "+selection_BAT.isEmpty() +" and sizq" +selection_BAT.size() +" "+selection_BOW.isEmpty() + " "+selection_WK.isEmpty());
            updateTeam(selected_team);
            updateCounter(count_selection,bat_count_selection,bow_count_selection,wk_count_selection);
        }

        else {
            selection_list.remove(data_list.get(position));

            if(selection_BAT.size()>0)
            selection_BAT.remove(selection_BAT.size()-1);
            if(selection_BOW.size()>0)
            selection_BOW.remove(selection_BOW.size()-1);
            if(selection_WK.size()>0)
            selection_WK.remove(selection_WK.size()-1);

            if(selected_team.size()>0)
                selected_team.remove(selected_team.size()-1);

            System.out.println("this is final selected team" +selected_team.toString() );

            bat_count_selection = bat_count_selection -1;
            bow_count_selection = bow_count_selection -1;
            wk_count_selection = wk_count_selection -1;
            count_selection = count_selection - 1;
            updateTeam(selected_team);
            updateCounter(count_selection,bat_count_selection,bow_count_selection,wk_count_selection);
        }


    }
    public void updateTeam(ArrayList<String> MselectedTeam)
    {

        System.out.println("inside update Team"+MselectedTeam.toString());
       // teamStringforJSON = MselectedTeam.toString();

        //converting Arraylist to string

        teamStringforJSON =android.text.TextUtils.join(",", MselectedTeam);




    }

    public void teamnameDialog()
    {

        LayoutInflater li = LayoutInflater.from(this);
        final View promptview = li.inflate(R.layout.teamname_alertdialogview,null);

        AlertDialog alertDialog = new AlertDialog.Builder(TeamSelection1.this).create();
       alertDialog.setView(promptview);

        alertDialog.setButton(Dialog.BUTTON_POSITIVE,"SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                EditText _temaname = (EditText)promptview.findViewById(R.id.team_name_dialog);

                teamnameG= _temaname.getText().toString();

                senTeamMethod();


            }
        });

alertDialog.show();



    }

    public void updateCounter(int count_selection,int bat_count_selection,int bow_count_selection,int wk_count_selection)


    {
        System.out.println("inside update counter");
        fabText.setText(count_selection+"/11");
        if(bat_count_selection>1)
        {
            Dialog dialog = new Dialog(this, R.style.CustomAlertDialog);

// Setting the title and layout for the dialog
            dialog.setTitle("you have selected more than 2 batsman");

            // Toast.makeText(this,"count is "+count_selection,Toast.LENGTH_SHORT).show();
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();

        }

        if (count_selection == 0)

        {
            System.out.println("called if count selection is 0 ");
            Toast.makeText(this, "count is " + count_selection, Toast.LENGTH_LONG).show();

        } else if (count_selection > 3 )
        {

            Dialog dialog = new Dialog(this, R.style.CustomAlertDialog);

// Setting the title and layout for the dialog
            dialog.setTitle("Error Please select only less than 11 players");

            // Toast.makeText(this,"count is "+count_selection,Toast.LENGTH_SHORT).show();
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.show();


        }
        else if(count_selection ==1)
        {

            saveTeam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


teamnameDialog();






                }
            });


        }

    }

        class CustomAdapter1 extends RecyclerView.Adapter<CustomAdapter1.ViewHolder> {
            int selected_position = -1;
            Context context;
            TeamSelection1 teamSelection1;


            List<PlayersModel> playerData;
            private String playercatDum;
            private String playerIDG;
            private String player_name;
            private String player_team;


            public CustomAdapter1(Context context, List<PlayersModel> playerData) {

                this.context = context;
                this.playerData = playerData;
                teamSelection1 = (TeamSelection1) context;



            }

            @Override
            public CustomAdapter1.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.player_select_cardview, parent, false);

                return new CustomAdapter1.ViewHolder(itemView, teamSelection1);


            }

            @Override
            public void onBindViewHolder(CustomAdapter1.ViewHolder holder, final int position) {

                holder.playerName.setText(playerData.get(position).getPlayer_name());
                holder.playerTeam.setText(playerData.get(position).getPlayer_team());
                holder.playerPoints.setText(playerData.get(position).getPlayer_points());
                Glide.with(context).load(playerData.get(position).getPlayer_team_icon()).into(holder.playerTeamIcon);
                holder.playeCat.setText(playerData.get(position).getPlayer_cat());
                holder.player_Id.setText(playerData.get(position).getPlayer_id());



               // holder.playeCat.setVisibility(View.INVISIBLE);

               /* holder.cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        playercatDum = playerData.get(position).getPlayer_cat();
                        System.out.println("caled when clicked on card");
                        //Toast.makeText(v.getContext(),playercatDum,Toast.LENGTH_LONG).show();

                    }
                });*/
                holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        playercatDum = playerData.get(position).getPlayer_cat();
                        player_name = playerData.get(position).getPlayer_name();
                        playerIDG = playerData.get(position).getPlayer_id();
                        player_team = playerData.get(position).getPlayer_team();

                        System.out.println("called when the player categery is called" + player_name);

                    }
                });


                if (!teamSelection1.is_in_action_mode) {
                    holder.checkBox.setVisibility(View.GONE);

                } else {
                    holder.checkBox.setVisibility(View.VISIBLE);
                    holder.checkBox.setChecked(false);

                }


            }


            class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

                public TextView playerName;
                public TextView playerTeam;
                public TextView playerPoints;
                public ImageView playerTeamIcon;
                public CheckBox checkBox;
                TeamSelection1 teamSelection1;
                public TextView playeCat;
                public TextView player_Id;
                CardView cardView;

                public ViewHolder(View itemView, TeamSelection1 teamSelection1) {
                    super(itemView);
                    this.playerName = (TextView) itemView.findViewById(R.id.player_name);
                    this.playerPoints = (TextView) itemView.findViewById(R.id.player_points);
                    this.playerTeam = (TextView) itemView.findViewById(R.id.player_team);
                    this.playerTeamIcon = (ImageView) itemView.findViewById(R.id.player_team_icon);
                    this.checkBox = (CheckBox) itemView.findViewById(R.id.check_box);
                    this.playeCat = (TextView) itemView.findViewById(R.id.playercate);
                    this.player_Id = (TextView) itemView.findViewById(R.id.playerid);





                    this.teamSelection1 = teamSelection1;
                    cardView = (CardView) itemView.findViewById(R.id.player_select_cardview);

                    cardView.setOnLongClickListener(teamSelection1);

                    checkBox.setOnClickListener(this);


                }


                @Override
                public void onClick(View v) {
                    teamSelection1.prepareSelection(v, getAdapterPosition(),playercatDum,player_name,player_team);

                    System.out.println("value of player DUM in onClick method" + playercatDum);
                }
            }


            @Override
            public int getItemCount() {
                return playerData.size();
            }
        }

}
