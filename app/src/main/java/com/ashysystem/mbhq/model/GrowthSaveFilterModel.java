package com.ashysystem.mbhq.model;

public class GrowthSaveFilterModel {
    private String userid = "";
    private Boolean bool_only_pic = false;


    private Boolean bool_I_choose_To_Know = false;// added
    private Boolean bool_show_img = false;


    private Boolean bool_I_Appreciate = false; // added
    private Boolean bool_proud_of = false;
    private Boolean bool_accomplished = false;
    private Boolean bool_observed = false;
    private Boolean bool_learned = false;
    private Boolean bool_showimage = true;
    private Boolean bool_praised = false;
    private Boolean bool_let_go_of = false;
    private Boolean bool_dreamt_of = false;
    private Boolean bool_journal_entry = false;
    private Boolean bool_grateful_for = false; //add by jyotirmoy
    private Boolean bool_working_towards = false; //add by jyotirmoy
    private Boolean bool_feeling_growth = false; //add by jyotirmoy
    private Boolean bool_committing_to = false; //add by jyotirmoy
    private Boolean bool_prompt_of_The_day = false; //add by jyotirmoy
    private Boolean bool_found_gift = false; //add by jyotirmoy
    private Boolean bool_felt_happy_today = false; //add by jyotirmoy
    private Boolean bool_I_acknowledge = false; //add by jyotirmoy
    private Boolean bool_the_story = false;
    private Boolean bool_challenged = false;
    private Boolean bool_celebrating = false;
    private Boolean bool_laughed = false;
    private Boolean bool_Today_I_M_Feeling = false;
    private Integer selected_date_range_filter_value = 0;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Boolean getBool_show_img() {
        return bool_show_img;
    }

    public void setBool_show_img(Boolean bool_show_img) {
        this.bool_show_img = bool_show_img;
    }

    public Boolean getBool_showimage() {
        return bool_showimage;
    }

    public void setBool_showimage(Boolean bool_showimage) {
        this.bool_showimage = bool_showimage;
    }

    public Boolean getBool_working_towards() {
        return bool_working_towards;
    }

    public void setBool_working_towards(Boolean bool_working_towards) {
        this.bool_working_towards = bool_working_towards;
    }

    public Boolean getBool_found_gift() {
        return bool_found_gift;
    }

    public void setBool_found_gift(Boolean bool_found_gift) {
        this.bool_found_gift = bool_found_gift;
    }

    public Boolean getBool_I_acknowledge() {
        return bool_I_acknowledge;
    }

    public void setBool_I_acknowledge(Boolean bool_I_acknowledge) {
        this.bool_I_acknowledge = bool_I_acknowledge;
    }

    public Boolean getBool_felt_happy_today() {
        return bool_felt_happy_today;
    }

    public void setBool_felt_happy_today(Boolean bool_felt_happy_today) {
        this.bool_felt_happy_today = bool_felt_happy_today;
    }

    public Boolean getBool_prompt_of_The_day() {
        return bool_prompt_of_The_day;
    }

    public void setBool_prompt_of_The_day(Boolean bool_prompt_of_The_day) {
        this.bool_prompt_of_The_day = bool_prompt_of_The_day;
    }

    public Boolean getBool_feeling_growth() {
        return bool_feeling_growth;
    }

    public void setBool_feeling_growth(Boolean bool_feeling_growth) {
        this.bool_feeling_growth = bool_feeling_growth;
    }

    public Boolean getBool_committing_to() {
        return bool_committing_to;
    }

    public void setBool_committing_to(Boolean bool_committing_to) {
        this.bool_committing_to = bool_committing_to;
    }

    private String strGrowthSerach = "";

    public Boolean getBool_grateful_for() {
        return bool_grateful_for;
    }

    public void setBool_grateful_for(Boolean bool_grateful_for) {
        this.bool_grateful_for = bool_grateful_for;
    }

    public Boolean getBool_Today_I_M_Feeling() {//
        return bool_Today_I_M_Feeling;
    }

    public void setBool_Today_I_M_Feeling(Boolean bool_Today_I_M_Feeling) {
        this.bool_Today_I_M_Feeling = bool_Today_I_M_Feeling;
    }

    public Boolean getBool_proud_of() {//
        return bool_proud_of;
    }

    public GrowthSaveFilterModel setBool_proud_of(Boolean bool_proud_of) {
        this.bool_proud_of = bool_proud_of;
        return this;
    }

    public Boolean getBool_accomplished() {//
        return bool_accomplished;
    }

    public GrowthSaveFilterModel setBool_accomplished(Boolean bool_accomplished) {
        this.bool_accomplished = bool_accomplished;
        return this;
    }

    public Boolean getBool_observed() {//
        return bool_observed;
    }

    public GrowthSaveFilterModel setBool_observed(Boolean bool_observed) {
        this.bool_observed = bool_observed;
        return this;
    }

    public Boolean getBool_learned() {//
        return bool_learned;
    }

    public GrowthSaveFilterModel setBool_learned(Boolean bool_learned) {
        this.bool_learned = bool_learned;
        return this;
    }

    public Boolean getBool_praised() {//
        return bool_praised;
    }

    public GrowthSaveFilterModel setBool_praised(Boolean bool_praised) {
        this.bool_praised = bool_praised;
        return this;
    }

    public Boolean getBool_let_go_of() {//
        return bool_let_go_of;
    }

    public GrowthSaveFilterModel setBool_let_go_of(Boolean bool_let_go_of) {
        this.bool_let_go_of = bool_let_go_of;
        return this;
    }

    public Boolean getBool_dreamt_of() {//
        return bool_dreamt_of;
    }

    public GrowthSaveFilterModel setBool_dreamt_of(Boolean bool_dreamt_of) {
        this.bool_dreamt_of = bool_dreamt_of;
        return this;
    }

    public Boolean getBool_journal_entry() {//
        return bool_journal_entry;
    }

    public GrowthSaveFilterModel setBool_journal_entry(Boolean bool_journal_entry) {
        this.bool_journal_entry = bool_journal_entry;
        return this;
    }

    public Integer getSelected_date_range_filter_value() {//
        return selected_date_range_filter_value;
    }

    public GrowthSaveFilterModel setSelected_date_range_filter_value(Integer selected_date_range_filter_value) {
        this.selected_date_range_filter_value = selected_date_range_filter_value;
        return this;
    }

    public String getStrGrowthSerach() {//
        return strGrowthSerach;
    }

    public GrowthSaveFilterModel setStrGrowthSerach(String strGrowthSerach) {
        this.strGrowthSerach = strGrowthSerach;
        return this;
    }

    public Boolean getBool_the_story() {//
        return bool_the_story;
    }

    public void setBool_the_story(Boolean bool_the_story) {
        this.bool_the_story = bool_the_story;
    }

    public Boolean getBool_challenged() {//
        return bool_challenged;
    }

    public void setBool_challenged(Boolean bool_challenged) {
        this.bool_challenged = bool_challenged;
    }

    public Boolean getBool_celebrating() {//
        return bool_celebrating;
    }

    public void setBool_celebrating(Boolean bool_celebrating) {
        this.bool_celebrating = bool_celebrating;
    }

    public Boolean getBool_laughed() {//
        return bool_laughed;
    }

    public void setBool_laughed(Boolean bool_laughed) {
        this.bool_laughed = bool_laughed;
    }

    public Boolean getBool_I_Appreciate() {
        return bool_I_Appreciate;
    }

    public void setBool_I_Appreciate(Boolean bool_I_Appreciate) {
        this.bool_I_Appreciate = bool_I_Appreciate;
    }

    public Boolean getBool_I_choose_To_Know() {
        return bool_I_choose_To_Know;
    }

    public void setBool_I_choose_To_Know(Boolean bool_I_choose_To_Know) {
        this.bool_I_choose_To_Know = bool_I_choose_To_Know;
    }

    public Boolean getBool_only_pic() {
        return bool_only_pic;
    }

    public void setBool_only_pic(Boolean bool_only_pic) {
        this.bool_only_pic = bool_only_pic;
    }


    @Override
    public String toString() {
        return "GrowthSaveFilterModel{" +
                "field1='" + selected_date_range_filter_value + '\'' +
                ", field4='" + bool_Today_I_M_Feeling + '\'' +
                ", field5='" + bool_only_pic + '\'' +
                ", field6='" + bool_I_choose_To_Know + '\'' +
                ", field7='" + bool_show_img + '\'' +
                ", field8='" + bool_I_Appreciate + '\'' +
                ", field9='" + bool_proud_of + '\'' +
                ", field10='" + bool_accomplished + '\'' +
                ", field11='" + bool_observed + '\'' +
                ", field12='" + bool_learned + '\'' +
                ", field13='" + bool_showimage + '\'' +
                ", field14='" + bool_praised + '\'' +
                ", field15='" + bool_let_go_of + '\'' +
                ", field16='" + bool_dreamt_of + '\'' +
                ", field17='" + bool_journal_entry + '\'' +
                ", field18='" + bool_grateful_for + '\'' +
                ", field19='" + bool_working_towards + '\'' +
                ", field20='" + bool_feeling_growth + '\'' +
                ", field21='" + bool_committing_to + '\'' +
                ", field22='" + bool_prompt_of_The_day + '\'' +
                ", field23='" + bool_found_gift + '\'' +
                ", field24='" + bool_felt_happy_today + '\'' +
                ", field25='" + bool_I_acknowledge + '\'' +
                ", field26='" + bool_the_story + '\'' +
                ", field27='" + bool_challenged + '\'' +
                ", field28='" + bool_celebrating + '\'' +
                ", field29='" + bool_laughed + '\'' +
                ", field30='" + bool_Today_I_M_Feeling + '\'' +
                '}';
    }
}

