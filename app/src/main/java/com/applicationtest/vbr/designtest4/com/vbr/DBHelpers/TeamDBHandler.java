package com.applicationtest.vbr.designtest4.com.vbr.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.applicationtest.vbr.designtest4.PlayersModel;
import com.applicationtest.vbr.designtest4.com.vbr.model.TeamSelectionModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by C5245675 on 4/29/2017.
 */

public class TeamDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "teamsManager";
    private static final String TABLE_TEAMS = "teams";
    private static final String TABLE_TEAMS_TEST = "teamsTEST";

    private static final String PLAYER_ID = "id";
    private static final String PLAYER_NAME = "name";
    private static final String PLAYER_TEAM = "playerTeam";
    TeamSelectionModel teamSelectionModel = new TeamSelectionModel();

    public TeamDBHandler(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION  );
    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TEAMS_TABLE = "CREATE TABLE " + TABLE_TEAMS + "("
                + PLAYER_ID  + " TEXT PRIMARY KEY," + PLAYER_NAME + " TEXT,"
                +  PLAYER_TEAM + " TEXT" + ")";
        //db.execSQL(CREATE_TEAMS_TABLE);

        String CREATE_TEST_TEAMS_TABLE = "CREATE TABLE " +TABLE_TEAMS_TEST+"(" + PLAYER_NAME + " TEXT " + ")";

        System.out.println(CREATE_TEST_TEAMS_TABLE);
        db.execSQL(CREATE_TEST_TEAMS_TABLE);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TEAMS_TEST);
        onCreate(db);

    }


    public void saveTeam(TeamSelectionModel teamSelectionModel)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        //values.put("KEY_PLAYER_ID",teamSelectionModel.getPlayerID());
        values.put("PLAYER_NAME",teamSelectionModel.getPlayerName());
       // values.put("KEY_PLAYER_TEAM",teamSelectionModel.getPlayerTeam());
        db.insert(TABLE_TEAMS_TEST,null,values);
        db.close();


    }

    public List<TeamSelectionModel> getAllTeams()
    {

        List<TeamSelectionModel> teamsList = new ArrayList<TeamSelectionModel>();
        String selectQuery = "SELECT * FROM " + TABLE_TEAMS_TEST;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery,null);


        if(cursor.moveToFirst())
        {
            do {
                teamSelectionModel.setPlayerID(cursor.getString(0));
                teamSelectionModel.setPlayerName(cursor.getString(1));
                teamSelectionModel.setPlayerTeam(cursor.getString(3));

                teamsList.add(teamSelectionModel);

                }
                while (cursor.moveToNext());



        }

        return teamsList;



    }



}
