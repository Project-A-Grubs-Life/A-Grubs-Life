package com.game.model.materials;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class EnemyTest {

    Enemy enemy;

    @Before
    public void initialize() {
        enemy = new Enemy("Enemy1", 10, 10, 2, false, false, "web", false);
    }

    @Test
    public void testSetAndGetHealthReturnsCorrectHealthLevel() {
        enemy.setHealth(0);
        assertEquals(0, enemy.getHealth());
        enemy.setHealth(1000);
        assertEquals(1000, enemy.getHealth());
        enemy.setHealth(-1);
        assertEquals(-1, enemy.getHealth());
    }

    @Test
    public void testGetName() {
        assertEquals("Enemy1", enemy.getName());
        enemy = new Enemy("     ", 10, 10, 2, false, false, "web", false);
        assertEquals("     ", enemy.getName());
        enemy = new Enemy(null, 10, 10, 2, false, false, "web", false);
        assertEquals(null, enemy.getName());
    }

    @Test
    public void testGetHealth() {
        assertEquals(10, enemy.getHealth());
    }

    @Test
    public void testGetRequiredLevelToFightReturnsAnyFightLevel() {
        assertEquals(2, enemy.getRequiredLevelToFight());
        enemy = new Enemy("Enemy1", 10, 10, 2000, false, false, "web", false);
        assertEquals(2000, enemy.getRequiredLevelToFight());
        enemy = new Enemy("Enemy1", 10, 10, -2000, false, false, "web", false);
        assertEquals(-2000, enemy.getRequiredLevelToFight());
        enemy = new Enemy("Enemy1", 10, 10, 0, false, false, "web", false);
        assertEquals(0, enemy.getRequiredLevelToFight());
    }

    @Test
    public void testIsAggressive() {
        assertFalse(enemy.isAggressive());
        enemy = new Enemy("Enemy1", 10, 10, 2, true, false, "web", false);
        assertTrue(enemy.isAggressive());
    }

    @Test
    public void testGetStrength() {
        assertEquals(10, enemy.getStrength());
        enemy = new Enemy("Enemy1", 10, 0, 2, false, false, "web", false);
        assertEquals(0, enemy.getStrength());
        enemy = new Enemy("Enemy1", 10, -10, 2, false, false, "web", false);
        assertEquals(-10, enemy.getStrength());
    }

    @Test
    public void testIsTamable() {
        assertFalse(enemy.isTamable());
        enemy = new Enemy("Enemy1", 10, 10, 2, false, true, "web", false);
        assertTrue(enemy.isTamable());
    }

    @Test
    public void testGetLocation() {
        assertEquals("web", enemy.getLocation());
        assertFalse(enemy.isAggressive());
        enemy = new Enemy("Enemy1", 10, 10, 2, false, false, "crazytown", false);
        assertEquals("crazytown", enemy.getLocation());
        enemy = new Enemy("Enemy1", 10, 10, 2, true, false, null, false);
        assertNull(enemy.getLocation());
    }

    @Test
    public void testIsHidden() {
        assertFalse(enemy.isHidden());
        enemy = new Enemy("Enemy1", 10, 10, 2, false, false, "web", true);
        assertTrue(enemy.isHidden());
    }

    @Test
    public void testSetHidden() {
        enemy.setHidden(true);
        assertTrue(enemy.isHidden());
        enemy.setHidden(false);
        assertFalse(enemy.isHidden());
    }

    @Test
    public void testIsInCombat() {
        assertFalse(enemy.isInCombat());
    }

    @Test
    public void testSetInCombat() {
        enemy.setInCombat(true);
        assertTrue(enemy.isInCombat());
        enemy.setInCombat(false);
        assertFalse(enemy.isInCombat());
        enemy.setInCombat(false);
        assertFalse(enemy.isInCombat());
        enemy.setInCombat(true);
        enemy.setInCombat(true);
        assertTrue(enemy.isInCombat());
    }

    @Test
    public void testGetBird() {
        assertEquals("Bird", enemy.getBird());
    }

    @Test
    public void testGetEmptyString() {
        assertEquals("", enemy.getEmptyString());
    }

    @Test
    public void testDisplayBirdRandomlyOnlyReturnsBirdsAndEmptyStrings() {
        assertNotEquals("bird", enemy.displayBirdRandomly());
        List<String> birds = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            birds.add(enemy.displayBirdRandomly());
        }
        assertEquals(100, Collections.frequency(birds, "Bird") + Collections.frequency(birds, ""));
    }
}