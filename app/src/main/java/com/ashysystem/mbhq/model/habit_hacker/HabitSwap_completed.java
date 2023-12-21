package com.ashysystem.mbhq.model.habit_hacker;

import java.util.ArrayList;

public class HabitSwap_completed {

    private ArrayList<HabitSwap> habitSwap_completeds = new ArrayList<>();
    private ArrayList<HabitSwap> habitSwap_hiddens = new ArrayList<>();
    private ArrayList<HabitSwap> habitSwap_inprogresss = new ArrayList<>();

    public ArrayList<HabitSwap> getHabitSwap_completeds() {
        return habitSwap_completeds;
    }

    public void setHabitSwap_completeds(ArrayList<HabitSwap> habitSwap_completeds) {
        this.habitSwap_completeds = habitSwap_completeds;
    }

    public ArrayList<HabitSwap> getHabitSwap_hiddens() {
        return habitSwap_hiddens;
    }

    public void setHabitSwap_hiddens(ArrayList<HabitSwap> habitSwap_hiddens) {
        this.habitSwap_hiddens = habitSwap_hiddens;
    }

    public ArrayList<HabitSwap> getHabitSwap_inprogresss() {
        return habitSwap_inprogresss;
    }

    public void setHabitSwap_inprogresss(ArrayList<HabitSwap> habitSwap_inprogresss) {
        this.habitSwap_inprogresss = habitSwap_inprogresss;
    }

}
