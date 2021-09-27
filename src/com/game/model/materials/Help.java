package com.game.model.materials;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class Help {
    private static Map<String, String> helpDirectory = new HashMap<>();

    public Help() {
        //read in help file and populate the McMap.
        try {
            InputStream inputStream = getClass().getResourceAsStream("help.txt");
            InputStreamReader myReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(myReader);
            String line = null;
            while((line = br.readLine()) != null) {
                String[] info = line.split(":");
                helpDirectory.put(info[0].trim(), info[1].trim());
            }
            br.close();
            myReader.close();
            inputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static String getHelp(String location) {
        if (location.equalsIgnoreCase("ALL")) {
            return helpDirectory.get("ALL");
        } else if (helpDirectory.containsKey(location)) {
            return helpDirectory.get(location);
        }
        return "Sorry, I seem to be lost. Where are we?";
    }

    public static String getHelp(Caterpillar caterpillar, String defense) {
        if (caterpillar.getAllDefenses().containsKey(defense.toUpperCase())) {
            return "With " + defense + " you acquire this ability: " + caterpillar.getAllDefenses().get(defense);
        } else if (defense.equalsIgnoreCase("defenses")) {
            return helpDirectory.get("DEFENSES");
        }
        return "I'm not sure what that is: " + defense;
    }
}