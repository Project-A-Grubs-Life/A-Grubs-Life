package com.game.model.materials;

import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

public class DefensesTest {
    Map<String, String> testValues;
    Defenses defenses = new Defenses();

    @Before
    public void setup() {
        testValues = new HashMap<>();
        testValues.put("CRYPSIS", "Your coloring offers you natural camouflage from predators.");
        testValues.put("STINGING HAIRS", "You have grown tiny hairs with irritating poison to injure and discourage predators.");
        testValues.put("STINK ATTACK", "You can now eject a violently bad smell that scares away predators.");
        testValues.put("NOXIOUS SPIT","You have evolved the ability to eject noxious poison from your face. The loud clicking noise it makes is a further deterrent.");
        testValues.put("CLICK DEFENSE", "Your organs can make clicking sounds that scare off predators and block bat sonar.");
        testValues.put("ANT ALLIANCE", "You secrete a sweet liquid that ants find irresistible. They will fight to the death to protect you and your sweet, sweet nectar.");
        testValues.put("ACID ATTACK", "Spit acid on any target, you totally metal butterfly!");
    }

    @Test
    public void getNameIsCorrectForValidLevel() {
        assertEquals("CRYPSIS", defenses.getName(1));
        assertEquals("ACID ATTACK", defenses.getName(7));
    }

    @Test
    public void getNameIsEmptyStringForInvalidLevel() {
        assertEquals("", defenses.getName(0));
        assertEquals("", defenses.getName(-1));
        assertEquals("", defenses.getName(8));
        assertEquals("", defenses.getName(100));
    }

    @Test
    public void getDescriptionIsCorrectForValidLevel() {
        assertEquals("You can now eject a violently bad smell that scares away predators.", defenses.getDescription(3));
        assertEquals("Spit acid on any target, you totally metal butterfly!", defenses.getDescription(7));
    }

    @Test
    public void getDescriptionIsEmptyForInvalidLevel() {
        assertEquals("", defenses.getDescription(0));
        assertEquals("", defenses.getDescription(-1));
        assertEquals("", defenses.getDescription(8));
    }

    @Test
    public void getDefensesIsValidForLevel1() {
        Map<String, String> defenseMap = defenses.getDefenses(1);
        assertEquals(1, defenseMap.size());
        assertTrue(defenseMap.containsKey("CRYPSIS"));
        assertEquals("Your coloring offers you natural camouflage from predators.", defenseMap.get("CRYPSIS"));
        assertFalse(defenseMap.containsKey("STINGING HAIRS"));
    }

    @Test
    public void getDefensesIsValidForMultipleLevels() {
        Map<String, String> defenseMap = defenses.getDefenses(3);
        assertEquals(3, defenseMap.size());
        assertTrue(defenseMap.containsKey("CRYPSIS"));
        assertTrue(defenseMap.containsKey("STINGING HAIRS"));
        assertTrue(defenseMap.containsKey("STINK ATTACK"));
        assertFalse(defenseMap.containsKey("ANT ALLIANCE"));
    }

    @Test
    public void getDefensesReturnsNullForInvalidLevel() {
        assertNull(defenses.getDefenses(0));
        assertNull(defenses.getDefenses(-1));
    }

    @Test
    public void getDefensesReturnsCappedMapForInvalidLevelsOverMax() {
        assertEquals(7, defenses.getDefenses(8).size());
        assertEquals(7, defenses.getDefenses(100).size());
    }
}