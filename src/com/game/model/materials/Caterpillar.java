package com.game.model.materials;

import java.util.HashMap;
import java.util.Map;

import com.game.controller.Game;

public class Caterpillar {
    public boolean winner;
    public boolean inTree = false;
    private int health;
    private int experience;
    private int strength ;
    private boolean shield = false;

    public boolean isInTree() {
        return inTree;
    }

    public void setInTree(boolean inTree) {
        this.inTree = inTree;
    }

    public boolean isInCombat() {
        return inCombat;
    }

    public void setInCombat(boolean inCombat) {
        this.inCombat = inCombat;
    }

    private boolean inCombat = false;
    private int level = 1;
    private long lastStartTime = System.currentTimeMillis();
    private final int maxLevel = 7;
    private final int maxExperience = 12;

    private Location currentLocation;
    private boolean hidden;
    private String lastAction;

    private final Defenses defenses;

    public  Caterpillar(int health, int experience, int strength){
        this.health = health;
        this.experience = experience;
        this.strength = strength;
        this.hidden = false;
        this.lastAction = "";
        this.winner = false;
        defenses = new Defenses();
    }

    public void setCurrentLocation(Location location){ //we should move this to the bottom
        System.out.println("Moving from " + currentLocation + " to " + location.getName());
        Game.playAudio(location.getName());
        this.currentLocation = location;
        if (location.getName().equalsIgnoreCase("tree")) {
            setInTree(true);
        } else {
            setInTree(false);
        }
    }

    public Location getCurrentLocation(){
        return this.currentLocation;
    }

    public void eat(Leaf leaf){
        setHealth(getHealth() + 10);
        if ((getExperience() + leaf.getXp()) >= maxExperience && level < maxLevel) {
            setExperience((getExperience() + leaf.getXp()) % maxExperience); //level up and transfers remaining to experience
            levelUp(); //increases level / ends the stage once appropriate level
            }
        else {
            if (level < maxLevel){
                setExperience(getExperience() + leaf.getXp() ); // no levelup by experience up
            }

        }
    }

    public void levelUp() {
        String action = "";
        setStrength(strength + 50);
        setLevel(level + 1);
        Map<String, String> abilities = getDefenses();

        if (getLevel() == maxLevel) {
            action = "You have reached level " + maxLevel + "! You are now a butterfly.";
        }

        action += "You now have these attack abilities!";
        for (Map.Entry<String, String> entry : abilities.entrySet()) {
            String name = entry.getKey();
            String description = entry.getValue();
            action += "<br><span style='font-style: bold;'>" + name + "</span><br>" + description;
        }

        setLastAction(action);
    }

    public void healthRegenerator(){
        int timeDiff = (int)(System.currentTimeMillis() - lastStartTime) / 1000;
        setHealth(getHealth() + timeDiff);
    }

    public int getHealth() {
        return health;
    }

    public boolean isWinner() {
        return winner;
    }

    public void setWinner(boolean winner) {
        this.winner = winner;
    }

    public void setHealth(int health) {
        this.health = health;
        this.lastStartTime = System.currentTimeMillis();
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Map<String, String> getDefenses() {
        return defenses.getDefenses(getLevel());
    }

    public Map<String, String> getAllDefenses() {
        return defenses.getDefenses(maxLevel);
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setLastAction(String str){
        this.lastAction = str;
    }
    public String getLastAction(){
        return this.lastAction;
    }

    public boolean isHidden() {
        return hidden;
    }

    public boolean setShield(boolean bool) {
        return this.shield = bool;
    }

    public boolean getShield() {
        return this.shield;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }
    public int getMaxExperience() {
        return maxExperience;
    }
}
