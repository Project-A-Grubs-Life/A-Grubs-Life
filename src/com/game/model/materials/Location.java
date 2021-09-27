/**
 *  This class is a representation of a location in the game.
 **/
package com.game.model.materials;


public class Location {
    private String name;
    private String description;
    private String north;
    private String south;
    private String east;
    private String west;
    private Leaf leaf;
    private Enemy enemy;


    public Location(String name, String description, String north, String south, String east, String west) {
        this.name = name;
        this.description = description;
        this.north = north;
        this.south = south;
        this.east = east;
        this.west = west;
        setLeaf();
    }

    private void setLeaf() {
        this.leaf = new Leaf();
    }

    public Leaf getLeaf(){
        return this.leaf;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getNorth() {
        return north;
    }

    public String getSouth() {
        return south;
    }

    public String getEast() {
        return east;
    }

    public String getWest() {
        return west;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public void setEnemy(Enemy enemy){this.enemy = enemy;}
}
