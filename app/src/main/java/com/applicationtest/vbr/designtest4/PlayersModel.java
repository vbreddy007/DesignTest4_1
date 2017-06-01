package com.applicationtest.vbr.designtest4;

/**
 * Created by C5245675 on 4/16/2017.
 */
public class PlayersModel {

String player_name;
    String player_team;
    String player_points;
    String player_team_icon;
    String player_cat;
    String player_id;


    public PlayersModel() {
    }



    public PlayersModel(String player_id,String player_name, String player_points, String player_team, String player_team_icon, String player_cat) {
        this.player_name = player_name;
        this.player_points = player_points;
        this.player_team = player_team;
        this.player_team_icon = player_team_icon;
        this.player_cat = player_cat;
    }
    public String getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(String player_id) {
        this.player_id = player_id;
    }

    public String getPlayer_cat() {
        return player_cat;
    }

    public void setPlayer_cat(String player_cat) {
        this.player_cat = player_cat;
    }

    public String getPlayer_team_icon() {
        return player_team_icon;
    }

    public void setPlayer_team_icon(String player_team_icon) {
        this.player_team_icon = player_team_icon;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }

    public String getPlayer_points() {
        return player_points;
    }

    public void setPlayer_points(String player_points) {
        this.player_points = player_points;
    }

    public String getPlayer_team() {
        return player_team;
    }

    public void setPlayer_team(String player_team) {
        this.player_team = player_team;
    }
}
