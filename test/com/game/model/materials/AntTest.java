package com.game.model.materials;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AntTest {

    @Test
    public void getNameReturnsRandomAnts() {
        Ant ant1 = new Ant();
        List<String> ants = new ArrayList<>(Arrays.asList("ant A","ant B", "ant C", "ant D"));
        assertTrue(ants.contains(ant1.getName()));
    }

    @Test
    public void getNameDoesNotMatchUnnamedAnts() {
        Ant ant1 = new Ant();
        List<String> ants = new ArrayList<>((Arrays.asList("antA", "ant1", "Anta", "Ant A", "AntA", "Ant a", "ANT A", "antB", "ant2", "Antb", "Ant B", "AntB", "Ant b", "ANT B", "antC", "ant3", "Antc", "Ant C", "AntC", "Ant c", "ANT C")));
        assertFalse(ants.contains(ant1.getName()));
    }


}