package com.game.model.materials;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Creates a class tracking available defenses as defined in defenses.txt.
 * Defense level defined in Caterpillar class
 */
public class Defenses {
    private final int maxLevel = 7;
    private List<Details> defenses;


    /**
     * Inner class holding defense details
     */
    private class Details {
        String name;
        String description;

        Details(String name, String description) {
            this.name = name;
            this.description = description;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public String toString() {
            return "name=" + name + "; description=" + description;
        }

    }


    public Defenses() {
        defenses = initialize();
    }

    public String getName(int level) {
        if (level < 1 || level > maxLevel) {
            return "";
        }
        return defenses.get(level - 1).getName();
    }

    public String getDescription(int level) {
        if (level < 1 || level > maxLevel) {
            return "";
        }
        return defenses.get(level - 1).getDescription();
    }

    private List<Details> initialize() {
        List<Details> defenseData = new ArrayList<>();
        String[] info;
        try {
            InputStream inputStream = getClass().getResourceAsStream("defenses.txt");
            InputStreamReader myReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(myReader);
            String line = null;
            while((line = br.readLine() )!= null){
                info = line.split(":");
                Details data = new Details(info[0].trim(), info[2].trim());
                defenseData.add(Integer.parseInt(info[1].trim()) - 1, data);
            }
            br.close();
            myReader.close();
            inputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return defenseData;
    }

    public Map<String, String> getDefenses(int level) {
        if (level < 1) {
            return null;
        }

        Map<String, String> retVal = new HashMap<>();
        for (int i = 0; i < level; i++) {
            if (i >= defenses.size()) {
                break;
            }
            retVal.put(defenses.get(i).getName(), defenses.get(i).getDescription());
        }
        return retVal;
    }

    @Override
    public String toString() {
        String retVal = "";
        for (int i = 0; i < defenses.size(); i++) {
            retVal += "Defenses {" + "name=" + getName(i) + "description=" + getDescription(i) + "}";
        }
        return retVal;
    }
}