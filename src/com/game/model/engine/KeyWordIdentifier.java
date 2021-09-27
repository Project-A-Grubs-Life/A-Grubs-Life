package com.game.model.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;

//grab appropriate command (verb, noun)
public class KeyWordIdentifier {

    HashSet<String> keywords;

    public KeyWordIdentifier() {
        keywords = new HashSet<>();
        keywords.add("GO");
        keywords.add("NORTH");
        keywords.add("LEAF");
        keywords.add("EAT");
        keywords.add("SOUTH");
        keywords.add("EAST");
        keywords.add("WEST");
        keywords.add("ANT");
        keywords.add("TAME");
        keywords.add("HELP");
        keywords.add("COMBAT");
        keywords.add("START");
        keywords.add("GAME");
        keywords.add("SPIDER");
        keywords.add("BIRD");
        keywords.add("FLIES");
        keywords.add("RAT");
        keywords.add("SQUIRREL");
        keywords.add("BEE");
        keywords.add("ATTACK");
        keywords.add("GODMODE");
        keywords.add("CATERPILLAR");
        keywords.add("HIDE");
        keywords.add("EXIT");
        keywords.add("SHIELD");
        keywords.add("SELF");
        keywords.add("USE");
        keywords.add("CRYPSIS");
        keywords.add("STINGING");
        keywords.add("HAIRS");
        keywords.add("STINK");
        keywords.add("NOXIOUS");
        keywords.add("SPIT");
        keywords.add("CLICK");
        keywords.add("DEFENSE");
        keywords.add("ALLIANCE");
        keywords.add("ACID");
        keywords.add("VOLUME");
        keywords.add("UP");
        keywords.add("DOWN");
        keywords.add("ALL");
        keywords.add("DEFENSES");
    }


    public ArrayList<String> identifyKeywords(ArrayList<String> parsedInput) {
        ArrayList<String> result = new ArrayList();
        parsedInput.size();
        if (parsedInput != null) {
            for (int i = 0; i < parsedInput.size(); i++) {
                if (!keywords.contains(parsedInput.get(i))) {
                    return result;
                } else {
                    result.add(parsedInput.get(i).toUpperCase(Locale.ROOT));
                }
            }
        }

        return result;
    }
}
