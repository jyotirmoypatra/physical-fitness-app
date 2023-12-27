package com.ashysystem.mbhq.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by android-arindam on 5/12/17.
 */

public class Title extends ExpandableGroup<SubTitle> {
    public Title(String s, List subTitles) {
        super(s,subTitles);
    }
}
