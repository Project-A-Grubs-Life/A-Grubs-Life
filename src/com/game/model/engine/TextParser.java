package com.game.model.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

public class TextParser {
    private HashSet<String> verbs;
    private HashSet<String> nouns;

    public TextParser() {
         super();
         populateVerbs();
         populateNouns();

    }

    private void populateNouns() {
        this.nouns = new HashSet();
        nouns.add("NORTH");
        nouns.add("LEAF");
        nouns.add("SOUTH");
        nouns.add("EAST");
        nouns.add("WEST");
        nouns.add("ANT");
        nouns.add("BEE");
        nouns.add("FLIES");
        nouns.add("SQUIRREL");
        nouns.add("COMBAT");
        nouns.add("GAME");
        nouns.add("SPIDER");
        nouns.add("BIRD");
        nouns.add("RAT");
        nouns.add("CATERPILLAR");
        nouns.add("UP");
        nouns.add("DOWN");
        nouns.add("SELF");
        nouns.add("GODMODE");
        nouns.add("ALL");
        nouns.add("DEFENSES");
        nouns.add("STINGING");
    }

    private void populateVerbs() {
        this.verbs = new HashSet();
        verbs.add("GO");
        verbs.add("EAT");
        verbs.add("TAME");
        verbs.add("HIDE");
        verbs.add("ATTACK");
        verbs.add("HELP");
        verbs.add("LOOK");
        verbs.add("START");
        verbs.add("RUN");
        verbs.add("GODMODE");
        verbs.add("EXIT");
        verbs.add("SHIELD");
        verbs.add("USE");
        verbs.add("CRYPSIS");
        verbs.add("STINGING HAIRS");
        verbs.add("HAIRS");
        verbs.add("STINK");
        verbs.add("NOXIOUS");
        verbs.add("SPIT");
        verbs.add("CLICK");
        verbs.add("DEFENSE");
        verbs.add("ALLIANCE");
        verbs.add("ACID");
        verbs.add("VOLUME");
        verbs.add("DEFENSES");
    }


    //If we don't get a viable verb and noun then we will pass null.
    public ArrayList<String> parseInput(String unParsedCommand) {
        String[] result;
        if(unParsedCommand != null) {
            result = unParsedCommand.toUpperCase(Locale.ROOT).split(" ");
            ArrayList<String> list = new ArrayList<>();

            for (String str : result) {
                if (verbs.contains(str.toUpperCase(Locale.ROOT)) || nouns.contains(str.toUpperCase(Locale.ROOT))) {
                    list.add(str);
                }
            }
            return list;
        }
        return null;
    }
}
