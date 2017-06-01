package com.applicationtest.vbr.designtest4.com.vbr.model;

/**
 * Created by C5245675 on 4/22/2017.
 */

public class ContestsModel {


    String contest_total_money;
    String contest_split_between;
    String contest_entry;

    public ContestsModel(String contest_entry, String contest_split_between, String contest_total_money) {
        this.contest_entry = contest_entry;
        this.contest_split_between = contest_split_between;
        this.contest_total_money = contest_total_money;
    }

    public String getContest_entry() {
        return contest_entry;
    }

    public void setContest_entry(String contest_entry) {
        this.contest_entry = contest_entry;
    }

    public String getContest_split_between() {
        return contest_split_between;
    }

    public void setContest_split_between(String contest_split_between) {
        this.contest_split_between = contest_split_between;
    }

    public String getContest_total_money() {
        return contest_total_money;
    }

    public void setContest_total_money(String contest_total_money) {
        this.contest_total_money = contest_total_money;
    }
}
