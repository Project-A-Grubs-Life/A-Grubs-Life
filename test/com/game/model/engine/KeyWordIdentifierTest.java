package com.game.model.engine;

import org.junit.Before;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.*;

public class KeyWordIdentifierTest {

    HashSet<String> keywords;
    KeyWordIdentifier identifier = new KeyWordIdentifier();

    @Before
    public void setup() {
        keywords = new HashSet<>();
        keywords.add("EAT");
        keywords.add("LEAF");
    }

    @Test
    public void parseCommand_shouldBeSizeTwo_whenPassingTwoKeywords() {
        ArrayList<String> arr = new ArrayList<>(Arrays.asList("EAT", "LEAF")) ;
        assertEquals(2, identifier.identifyKeywords(arr).size());
    }

    @Test
    public void parseCommand_shouldContainKeywords_whenPassingValidKeywords() {
        ArrayList<String> arr = new ArrayList<>(Arrays.asList("EAT", "LEAF")) ;
        assertEquals(Arrays.asList("EAT", "LEAF"), identifier.identifyKeywords(arr));
    }


}