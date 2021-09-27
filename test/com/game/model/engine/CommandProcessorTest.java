package com.game.model.engine;

import com.game.controller.Audio;
import com.game.controller.Game;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Defenses;
import com.game.model.materials.Enemy;
import com.game.model.materials.Location;
import org.junit.Before;
import org.junit.Test;

import javax.sound.sampled.Clip;
import java.net.URISyntaxException;
import java.util.*;

import static com.game.controller.Game.playAudio;
import static org.junit.Assert.*;

public class CommandProcessorTest {
    private Caterpillar caterpillar;
    HashMap<String, Location> locations;
    HashMap<String, Enemy> enemies;
    private CommandProcessor processor;
    private boolean misfire;
    Defenses defenses = new Defenses();
    private Enemy enemy;

    @Before
    public void setup() {
        caterpillar = new Caterpillar(1,0,0);
        locations = new HashMap<>();
        enemies = new HashMap<>();
        Location loc = new Location("GENESIS", "Welcome to the world, tiny caterpillar! You have just emerged from an egg on a leaf. There are yummy leaves on the ground around you, and nearby, you can see a bird's nest. You are very hungry, so be sure to eat some leaves. You'll want plenty of strength when birds, squirrels, and other predators are nearby. Be sure to befriend the ants when you can -- otherwise, you'd better run!","WOODS","LAKE","WEB","HOLE");
        locations.put("GENESIS", loc);
        Location loc2 = new Location("WOODS","You've crawled into the dark woods, and it's hard to see. Baby birds chirp nearby, and the ominous sound of flapping wings tells you a bird is flying overhead. Watch out!","DEAD_END","GENESIS","DEAD_END","DEAD_END");
        locations.put("WOODS", loc2);
        Enemy e = new Enemy("Bird", 10000, 200, 1, true, false, "genesis", false);
        enemies.put("Bird", e);
        processor = new CommandProcessor(caterpillar, locations, enemies);
        Game game = new Game();
        Audio audio = new Audio("GENESIS");
        caterpillar.setCurrentLocation(locations.get("GENESIS"));
    }

    @Test
    public void testProcessEatLeaf() {
        ArrayList<String> command = new ArrayList<>(Arrays.asList("eat", "leaf"));
        processor.executeCommand(command);
        assertTrue(caterpillar.getHealth() > 10);
    }

    @Test
    public void testExecuteCommand() {
        ArrayList<String> command = new ArrayList<>(Arrays.asList("GO", "GODMODE"));
        processor.executeCommand(command);

        assertEquals(9999999,caterpillar.getHealth());
        assertEquals(99999999, caterpillar.getStrength());
        assertEquals(7, caterpillar.getLevel());
    }

    @Test
    public void testGoNorth() {
        ArrayList<String> command = new ArrayList<>(Arrays.asList("go", "north"));
        processor.executeCommand(command);

        assertEquals("You travel north.", caterpillar.getLastAction());
        assertEquals("WOODS", caterpillar.getCurrentLocation().getName());
    }

    @Test
    public void testGoToInvalidLocation() {
        ArrayList<String> command = new ArrayList<>(Arrays.asList("go", "north"));
        processor.executeCommand(command);
        processor.executeCommand(command);

        assertEquals("You can't do that here.. We don't have that", caterpillar.getLastAction());
        assertEquals("WOODS", caterpillar.getCurrentLocation().getName());
    }

    @Test
    public void testUseShield() {
        ArrayList<String> command = new ArrayList<>(Arrays.asList("shield", "self"));
        processor.executeCommand(command);

        assertEquals("A layer of silk shields your body. The next damage dealt to you will be reduced by half!", caterpillar.getLastAction());
        assertTrue(caterpillar.getShield());
    }

    @Test
    public void testHelpAnt() {
        ArrayList<String> command = new ArrayList<>(Arrays.asList("go", "godmode"));
        processor.executeCommand(command);

        ArrayList<String> command2 = new ArrayList<>(Arrays.asList("assist", "ant"));
        processor.executeCommand(command2);

        assertEquals("You have received assistance from a friendly ant.", caterpillar.getLastAction());
        assertTrue(caterpillar.getStrength() >= 60);
    }

    @Test
    public void testHelpAntInvalidLevel() {
        ArrayList<String> command = new ArrayList<>(Arrays.asList("assist", "ant"));
        processor.executeCommand(command);

        assertEquals("You can't do that here.. We don't have that", caterpillar.getLastAction());
    }


//    @Test
//    public void testShieldAfterBattle() {
//        ArrayList<String> command = new ArrayList<>(Arrays.asList("shield", "self"));
//        processor.executeCommand(command);
//        assertEquals("A layer of silk shields your body. The next damage dealt to you will be reduced by half!", caterpillar.getLastAction());
//        ArrayList<String> command2 = new ArrayList<>(Arrays.asList("attack", "bird"));
//        processor.executeCommand(command2);
//        ArrayList<String> command3 = new ArrayList<>(Arrays.asList("use", "crypsis"));
//        processor.executeCommand(command3);
//
//        assertFalse(caterpillar.getShield());
//    }




//    @Test
//    public void testHideCommand() {
//        Enemy enemy = enemies.get("bird");
//        ArrayList<String> command = new ArrayList<>(Arrays.asList("hide", "caterpillar"));
//        processor.executeCommand(command);
//        assertEquals("Great job! You are hidden from the bird", caterpillar.getLastAction());
//    }



//    @Test
//    public
}