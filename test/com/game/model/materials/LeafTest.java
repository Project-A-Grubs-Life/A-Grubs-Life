package com.game.model.materials;

import org.junit.Test;

import static org.junit.Assert.*;

public class LeafTest {

    @Test
    public void testGetXpReturnsNonNegativeValue() {
        Leaf myLeaf;
        for (int i = 0 ; i < 100; i++) {
            myLeaf = new Leaf();
            assertTrue(myLeaf.getXp() >= 0);
        }
    }
}