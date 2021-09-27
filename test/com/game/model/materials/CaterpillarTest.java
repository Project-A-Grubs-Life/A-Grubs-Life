package com.game.model.materials;

import com.game.model.materials.Caterpillar;
import com.game.model.materials.Location;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CaterpillarTest {
    Caterpillar cat = new Caterpillar(0,0,0);

    @Test
    public void getHealthReturnsCorrectHealthLevel() {
        assertEquals(0, cat.getHealth());
        cat.setHealth(10);
        assertEquals(10, cat.getHealth());
        cat.setHealth(-10);
        assertEquals(-10, cat.getHealth());
        cat.setHealth(0);
        assertEquals(0, cat.getHealth());
    }

    @Test
    public void getExperienceReturnsCorrectXPLevel() {
        assertEquals(0, cat.getExperience());
        cat.setExperience(10);
        assertEquals(10, cat.getExperience());
        cat.setExperience(-10);
        assertEquals(-10, cat.getExperience());
        cat.setExperience(0);
        assertEquals(0, cat.getExperience());
    }

    @Test
    public void getStrengthReturnsCorrectStrengthLevel() {
        assertEquals(0, cat.getStrength());
        cat.setStrength(10);
        assertEquals(10, cat.getStrength());
        cat.setStrength(-10);
        assertEquals(-10, cat.getStrength());
        cat.setStrength(0);
        assertEquals(0, cat.getStrength());
    }

    @Test
    public void getCurrentLocationReturnsNullAfterInstantiation() {
        assertNull(cat.getCurrentLocation());
    }

    @Test
    public void getCurrentLocationReturnsCorrectLocation() {
        cat.setCurrentLocation(new Location("genesis", "", "", "", "", ""));
        assertEquals("genesis", cat.getCurrentLocation().getName());
    }


}