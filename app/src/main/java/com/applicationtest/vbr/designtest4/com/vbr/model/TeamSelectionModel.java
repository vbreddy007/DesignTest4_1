package com.applicationtest.vbr.designtest4.com.vbr.model;

/**
 * Created by C5245675 on 4/29/2017.
 */

public class TeamSelectionModel {

    String playerID;
    String playerName;
    String PlayerTeam;

    public TeamSelectionModel() {
    }

    public TeamSelectionModel( String playerName) {

        this.playerName = playerName;

    }



    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getPlayerTeam() {
        return PlayerTeam;
    }

    public void setPlayerTeam(String playerTeam) {
        PlayerTeam = playerTeam;
    }


}
