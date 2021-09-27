package com.game.model.engine;


import com.game.controller.Game;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Help;
import com.game.model.materials.Location;

import java.util.*;

/**
 * Class to execute on user commands
 */
public class CommandProcessor {
    private Caterpillar caterpillar;
    private HashMap<String, Location> locations;
    private HashMap<String, Enemy> enemies;
    private Enemy enemy;  // shortcut for finding your enemy
    private boolean misfire;
    private Help help = new Help();

    public CommandProcessor(Caterpillar caterpillar, HashMap<String,Location> locations, HashMap<String, Enemy> enemies){
        this.caterpillar = caterpillar;
        this.locations = locations;
        this.enemies = enemies;
    }

    /**
     * Formats input and sends to command processing
     * @param strings
     */
    public void executeCommand(ArrayList<String> strings) {
        if (strings.isEmpty()) {
            processTypo();
        } else if (strings.get(0).equalsIgnoreCase("help")) {
            String focus = "";
            if (strings.size() == 3) {
                focus = strings.get(1).toUpperCase() + " " + strings.get(2).toUpperCase();
            } else if (strings.size() > 1) {
                focus = strings.get(1).toUpperCase();
            }
            processCommand("help", focus);
        } else if (strings.size() == 2 && strings.get(0) != null && strings.get(1) != null) {
            this.enemy = enemies.get(caterpillar.getCurrentLocation().getName().toLowerCase());
            String action = strings.get(0).toUpperCase(Locale.ROOT);
            String focus = strings.get(1).toUpperCase(Locale.ROOT);
            this.misfire = true;
            processCommand(action, focus); // passing in to either the combat system or command menu
        } else if (strings.size() == 3 && strings.get(0) != null && strings.get(1) != null) {
            this.enemy = enemies.get(caterpillar.getCurrentLocation().getName().toLowerCase());
            String action = strings.get(0).toUpperCase(Locale.ROOT);
            String focus = strings.get(1).toUpperCase(Locale.ROOT) + " " + strings.get(2).toUpperCase(Locale.ROOT);
            this.misfire = true;
            processCommand(action, focus);
        } else {
            processTypo();
        }
    }

    /**
     * Sets health and strength to enable god mode on GODMODE command
     * @param focus
     */
    private void processGodMode(String focus) {
        if (focus.equalsIgnoreCase("GODMODE")) {
            caterpillar.setHealth(9999999);
            caterpillar.setStrength(99999999);
            caterpillar.setLevel(7);
            misfire = false;
            caterpillar.setLastAction("The Power of God him/her/itself (god is in an existential crisis) flows through you!");
        }
    }

    /**
     *
     * @param action
     * @param focus
     */
    private void processCommand(String action, String focus){
        if (enemies.containsKey(enemies.get(caterpillar.getCurrentLocation().getName()))
                && enemies.get(caterpillar.getCurrentLocation().getName().toLowerCase()).isInCombat()) {
            runCombatCheck(action, focus);
        } else if ((caterpillar.isWinner() && caterpillar.isInTree()) || caterpillar.getHealth() <= 0) {
            runGameEndMenu(action, focus);
        } else {
            runProcessMenu(action, focus);
            if (misfire) {
                processCantDoThatHere();
            }
        }
    }


    private void runCombatCheck(String action, String focus) {
        if (action.toUpperCase(Locale.ROOT).equalsIgnoreCase("ATTACK")) {
            processAttack(focus);
        }
        if (focus.toUpperCase(Locale.ROOT).equalsIgnoreCase("RUN")) {
            processRun(focus);
        }
    }

    private void processCantDoThatHere() {
        caterpillar.setLastAction("You can't do that here.. We don't have that");
    }

    /**
     *     This method is where to put any new commands.. each of the cases links out to the corresponding logic
     *     method... this is essentially a directory for incoming eligible commands.
     */
    private void runProcessMenu(String action, String focus) {
        switch(action.toUpperCase(Locale.ROOT)) {
            case "GO":
                processNavigation(focus.toLowerCase());
                processGodMode(focus);
                break;
            case "EAT":
                processEating(focus);
                break;
            case "HIDE":
                processHide(focus);
                break;
            case "HELP":
                processHelp(focus);
                break;
            case "ASSIST":
                processAntAssistance(focus);
                break;
            case "LEAVE":
                processLeave(focus);
                break;
            case "ATTACK":
                processAttack(focus);
                break;
            case "RUN":
                processRun(focus);
                break;
            case "EXIT":
                processExit(focus);
                break;
            case "VOLUME":
                processVolume(focus);
                break;
            case "SHIELD":
                processShield(focus);
                break;
            case "USE":
                processUse(focus);
                break;
        }
    }

    private void processHelp(String focus) {
        String advice = "";
        if (focus.equalsIgnoreCase("all")) {
            advice = help.getHelp("ALL");
        } else if (focus.isEmpty()) {
            advice = help.getHelp(caterpillar.getCurrentLocation().getName()) + "<br>Choose a command from the list to the left or type <b>help all</b> to get details on how to use those commands.";
        } else {
            advice = help.getHelp(caterpillar, focus);
        }

        if (caterpillar.getDefenses().containsKey(focus.toUpperCase())) {
            help.getHelp(caterpillar, focus.toUpperCase());
        }

        caterpillar.setLastAction(advice);
    }


    private void runGameEndMenu(String action, String focus) {
        if (!action.equalsIgnoreCase("EXIT")) {
            caterpillar.setLastAction("Pssst. Hey you! Yeah, player. You. Game's over. You can type 'exit game' and play again!");
        } else {
            processExit(focus);
        }
    }

    private void processTypo() {
        caterpillar.setLastAction("I can't process that, try again with a verb/noun combo of relevant game objects.");
    }

    // Caterpillar can use its defense moves in battle depending on the level
    private void processUse(String focus) {

        // attacks change as we level and get more powerful

        misfire = false;
        String battleMsg = "";

        // checks to see if caterpillar attack is usable
        if (!caterpillar.isInCombat()) {
            misfire = false;
            caterpillar.setLastAction("Cannot use " + focus + " right now. You are not in combat.");
        }
        if (caterpillar.isInCombat() && caterpillar.getDefenses().containsKey(focus)) {
            misfire = false;
            battleMsg += caterpillar.getDefenses().get(focus);
            int newHealth = enemy.getHealth() - caterpillar.getStrength() - strengthFactor();
            int factor = 0;

            // If the caterpillar sets up a shield, it will take damage by half and
            // the shield will disappear
            if (caterpillar.getShield()) {
                battleMsg += " You took half damage, but your silk shield went away...";
            }
            enemy.setHealth(newHealth - factor);

            if (enemy.getHealth() <= 0) {
                enemy.setHidden(true);
                enemy.setInCombat(false);
                caterpillar.setInCombat(false);
                caterpillar.setExperience(caterpillar.getExperience() + 10);
//                boolean check = caterpillar.getLevel() == 2;
                if (enemy.getName().equalsIgnoreCase("squirrel")) {
                    caterpillar.setWinner(true);
                }
//                    caterpillar.levelUp();
//                if (check) {
                    if (caterpillar.isWinner()) {
                        battleMsg += "<br>You have defeated the mighty " + enemy.getName() + ".<br>" + "After beating the boss you find your mate! Together you can find the tree and live happily ever after, ending the game.";
                    } else {
                        battleMsg += "<br>You have defeated the mighty " + enemy.getName() + ".";
                    }
//                }
            }
            enemyAttack();
            caterpillar.setLastAction(battleMsg);
            if (caterpillar.getHealth() <= 0) {
                caterpillar.setLastAction("Oh, dear. You have died.");
            }
        } else if (caterpillar.isInCombat() && !caterpillar.getDefenses().containsKey(focus)) {
            misfire = false;
            caterpillar.setLastAction("That move doesn't exist.");
        }


    }

    // Changes the state of the caterpillar to attacking. This will enable the caterpillar to
    // use its defenses
    private void processAttack(String focus) {
        if (focus.equalsIgnoreCase(enemy.getName())) {
            if (!caterpillar.isInCombat()) {
                caterpillar.setInCombat(true);
                misfire = false;
                caterpillar.setLastAction("Prepare for battle!");
            } else {
                misfire = false;
                caterpillar.setLastAction("Already fighting.");
            }
        }


    }

    // Take half damage if shield is up, otherwise, take normal damage
    private void enemyAttack() {
        if (caterpillar.getShield()) {
            caterpillar.setHealth(caterpillar.getHealth() - (enemy.getStrength() / 2));
            caterpillar.setShield(false);
        } else {
            caterpillar.setHealth(caterpillar.getHealth() - enemy.getStrength());
        }
    }

    private int strengthFactor() {
        int strength = 0;
        if (caterpillar.getStrength() > enemy.getStrength()) {
            strength = 5;
        } else if (caterpillar.getStrength() < enemy.getStrength()) {
            strength = -5;
        }
        return strength;
    }

    private void processAntAssistance(String focus) {
        if (focus.equalsIgnoreCase("ANT") && caterpillar.getLevel() >= 2) {
            //DONE : Implement "Ant can be used in combat" logic here.
            caterpillar.setStrength(caterpillar.getStrength() + 60);
            caterpillar.setLastAction("You have received assistance from a friendly ant.");
            misfire = false;
        }
    }

    private void processRun(String focus) {
        if (focus.toUpperCase(Locale.ROOT).equalsIgnoreCase("RUN")) {
            misfire = false;
            if (caterpillar.getStrength() > enemy.getStrength() && enemy.isAggressive() == true) {
                caterpillar.setLastAction("You ran away from the fight despite enemy efforts to subdue you.");
                enemy.setHidden(true);
                enemy.setInCombat(false);
            } else if (caterpillar.getStrength() > enemy.getStrength() && enemy.isAggressive() == false) {
                caterpillar.setLastAction("You ran away from the fight and live another day.");
                enemy.setHidden(true);
                enemy.setInCombat(false);
            } else if (caterpillar.getStrength() < enemy.getStrength() && enemy.isAggressive() == false) {
                caterpillar.setLastAction("You were lucky this time, the enemy gave up its pursuit.");
                enemy.setHidden(true);
                enemy.setInCombat(false);
            } else {
                caterpillar.setLastAction("Oh no, you can't escape, you are forced to fight!");
                enemy.setInCombat(true);
            }

        }
    }
    private void processLeave(String focus) {
    }

    private void processHide(String focus) {

        String randomBird = enemy.displayBirdRandomly();
        if (randomBird.equals("Bird")) {
            if ((enemy.getName().equalsIgnoreCase("Bird")) && (caterpillar.getLevel() == 1) && (focus.equalsIgnoreCase("CATERPILLAR"))) {
                caterpillar.setHealth(caterpillar.getHealth() + 30);
                caterpillar.setLastAction("Great job! You are hidden from the bird.");
                this.misfire = false;
            }
        } else if ((enemy.getName().equalsIgnoreCase("Bird")) && (caterpillar.getLevel() == 1) && (focus.equalsIgnoreCase("CATERPILLAR"))) {
            caterpillar.setLastAction("The closest place to hide is 2 miles away.");
            this.misfire = false;
        }
    }


    private void processEating(String focus) {
        switch (focus.toLowerCase()) {
            case "leaf":
                caterpillar.eat(caterpillar.getCurrentLocation().getLeaf());

                if (!caterpillar.getLastAction().contains("level")) {
                    caterpillar.setLastAction("You eat a leaf!");
                }
                misfire = false;
        }
    }

    private void processNavigation(String focus) {
        switch (focus) {
            case "north":
                if (!caterpillar.getCurrentLocation().getNorth().trim().equalsIgnoreCase("DEAD_END")) {
                    caterpillar.setCurrentLocation(locations.get(caterpillar.getCurrentLocation().getNorth().trim()));
                    caterpillar.setLastAction("You travel north.");
                    misfire = false;
                }


                break;
            case "south":
                if (!caterpillar.getCurrentLocation().getSouth().equalsIgnoreCase("DEAD_END")) {
                    caterpillar.setCurrentLocation(locations.get(caterpillar.getCurrentLocation().getSouth().trim()));
                    caterpillar.setLastAction("You travel south.");
                    misfire = false;
                }
                break;
            case "east":
                if (!caterpillar.getCurrentLocation().getEast().equalsIgnoreCase("DEAD_END")) {
                    caterpillar.setCurrentLocation(locations.get(caterpillar.getCurrentLocation().getEast().trim()));
                    caterpillar.setLastAction("You travel east.");

                    if (caterpillar.isWinner()) {
                        caterpillar.setLastAction("You have made it to safe refuge with your mate! Congratulations, you've won the game.");
                    }

                    misfire = false;
                }
                break;
            case "west":
                if (!caterpillar.getCurrentLocation().getWest().equalsIgnoreCase("DEAD_END")) {
                    caterpillar.setCurrentLocation(locations.get(caterpillar.getCurrentLocation().getWest().trim()));
                    caterpillar.setLastAction("You travel west.");
                    misfire = false;
                }
                break;
        }
    }

    private void processExit(String focus) {
        switch (focus.toLowerCase()) {
            case "game":
                misfire = false;
                System.exit(0);
                break;
        }
    }

    // Shields the user and halves the damage taken after next combat action
    private void processShield(String focus) {
        String player = focus.toLowerCase();
        if (player.equals("self") || player.equals("caterpillar")) {
            misfire = false;
            caterpillar.setShield(true);
            caterpillar.setLastAction("A layer of silk shields your body. The next damage dealt to you will be reduced by half!");
        }
    }

    // Displays integer number volume to user
    private void processVolume(String focus) {
        double volume = Game.currentAudio.changeVolume(focus) * 100;
        misfire = false;
        caterpillar.setLastAction("Volume is at " + (int) (volume + 1));
    }
}
