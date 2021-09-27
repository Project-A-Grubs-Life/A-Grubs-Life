package com.game.model.materials;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class HelpTest {
    Map<String, String> helpStuff = new HashMap<>();
    Help map = new Help();

    @Before
    public void initialize() {
        helpStuff.put("GENESIS", "From here, you can <b>attack</b> the local wildlife if you're strong enough, <b>go</b> to another area on the map, <b>eat leaf</b> for strength, and more!");
        helpStuff.put("WOODS", "From here, you can <b>attack</b> the local wildlife if you're strong enough, <b>go</b> to another area on the map, <b>eat leaf</b> for strength, and more!");
        helpStuff.put("HOLE", "Not much to do in here but rest and strategize! You can <b>go</b> back to your birthplace.");
        helpStuff.put("LAKE", "From here, you can <b>attack</b> the local wildlife if you're strong enough, <b>go</b> to another area on the map, <b>eat leaf</b> for strength, and more!");
        helpStuff.put("WEB", "In the spider's web, you can be eaten, so, like <b>attack</b> the spider and maybe consider boot scooting out of here.");
        helpStuff.put("HILL", "You can <b>attack</b> the ants or, if you're strong enough, form an <b>ant alliance</b>. You can <b>go</b> to another location and <b>eat leaf</b>.");
        helpStuff.put("FLOWERS", "There are flowers everywhere, my dude. You can <b>eat</b> alllll day here. But also, you can <b>attack bee</b> or hide.");
        helpStuff.put("BOSS", "This is your BOSS BATTLE, my friend. Hope you've got some acid attacks in you.");
        helpStuff.put("TREE", "When you've beaten the boss and rescued your mate, you can return here to officially win the game!");
        helpStuff.put("ALL", "Actions to the left can apply to [self, bee, ant, spider, bird, flies, rat, squirrel, self, crypsis, stinging hairs, stink, noxious spit, click defense, acid attack].");
        helpStuff.put("DEFENSES", "You can defend yourself with crypsis (level 1), stinging hairs (2), stink attack (3), noxious spit (4), click defense (5), ant alliance (6), and acid attack (7).");
    }

    @Test
    public void getHelp() {
        for (Map.Entry items : helpStuff.entrySet()) {
            assertEquals(items.getValue(), Help.getHelp(items.getKey().toString()));
        }
    }

    @Test
    public void testGetHelp() {
        Caterpillar cat = new Caterpillar(10, 10, 10);
        cat.setCurrentLocation(new Location("HILL", "On the hill", "", "", "", ""));
        assertEquals(helpStuff.get("DEFENSES"), Help.getHelp(cat, "DEFENSES"));
    }
}