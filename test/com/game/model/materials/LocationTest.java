package com.game.model.materials;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {

    Location testLoc, blankLoc;

    @Before
    public void initialize() {
        testLoc = new Location("name", "description", "north", "south", "east", "west");
        blankLoc = new Location("", "", "", "", "", "");
    }
    @Test
    public void getLeaf() {
        assertEquals(Leaf.class, testLoc.getLeaf().getClass());
        assertEquals(Leaf.class, blankLoc.getLeaf().getClass());
    }

    @Test
    public void getName() {
        assertEquals("name", testLoc.getName());
        assertEquals("", blankLoc.getName());
    }

    @Test
    public void getDescription() {
        assertEquals("description", testLoc.getDescription());
        assertEquals("", blankLoc.getDescription());
    }

    @Test
    public void getNorth() {
        assertEquals("north", testLoc.getNorth());
        assertEquals("", blankLoc.getNorth());
    }

    @Test
    public void getSouth() {
        assertEquals("south", testLoc.getSouth());
        assertEquals("", blankLoc.getSouth());
    }

    @Test
    public void getEast() {
        assertEquals("east", testLoc.getEast());
        assertEquals("", blankLoc.getEast());
    }

    @Test
    public void getWest() {
        assertEquals("west", testLoc.getWest());
        assertEquals("", blankLoc.getWest());
    }

    @Test
    public void getEnemyIsNullBeforeInitialized() {
        assertNull(testLoc.getEnemy());
        assertNull(blankLoc.getEnemy());
    }

    @Test
    public void setEnemyOnValidLocation() {
        Enemy enemy = new Enemy("enemy1", 0, 0, 0, false, false, "windmill", false);
        testLoc.setEnemy(enemy);
        assertSame(enemy, testLoc.getEnemy());
    }

    @Test
    public void setEnemyOnBlankLocation() {
        Enemy enemy = new Enemy("enemy1", 0, 0, 0, false, false, "windmill", false);
        blankLoc.setEnemy(enemy);
        assertSame(enemy, blankLoc.getEnemy());
    }
}