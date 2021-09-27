package com.game.model.engine;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TextParserTest {
    HashSet<String> testValues;
    TextParser parser = new TextParser();

    @Before
    public void setup() {
        testValues = new HashSet<>();
        testValues.add("NORTH");
        testValues.add("GO");
        testValues.add("USE");
        testValues.add("STINGING");
        testValues.add("HAIRS");
    }

    @Test
    public void parseCommand_shouldBeEmpty_whenPassingNonKeywords() {
        assertEquals(0, parser.parseInput("hello world").size());
    }

    @Test
    public void parseCommand_shouldContainKeywords_whenPassingKeywords() {
        assertEquals(Arrays.asList("GO","NORTH"), parser.parseInput("go north"));
    }

    @Test
    public void parseCommand_shouldContainKeywords_whenPassingSomeKeywords() {
        assertEquals(Arrays.asList("GO","NORTH"), parser.parseInput("go north please"));
    }

    @Test
    public void parseCommand_shouldHaveThreeWords_whenPassingThreeKeywords() {
        assertEquals(Arrays.asList("USE", "STINGING", "HAIRS"), parser.parseInput("use stinging hairs"));

    }

    @Test
    public void parseCommand_shouldHaveSizeThree_whenPassingThreeKeywords() {
        assertEquals(3, parser.parseInput("use stinging hairs").size());
    }
}