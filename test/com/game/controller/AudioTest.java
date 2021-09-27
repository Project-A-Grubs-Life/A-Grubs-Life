package com.game.controller;

import com.game.model.engine.TextParser;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class AudioTest {

    Map<String,String> testValues;

    @Before
    public void setup() {
        testValues = new HashMap<>();
        testValues.put("GENESIS", "/resources/music/forest.wav");
        testValues.put("START", "/resources/music/forest.wav");
        testValues.put("WOODS", "/resources/music/woods_owl.wav");
        testValues.put("HOLE", "/resources/music/hole.wav");
        testValues.put("LAKE", "/resources/music/lake.wav");
        testValues.put("HILL", "/resources/music/anthill.wav");
        testValues.put("FLOWERS", "/resources/music/flowers.wav");
        testValues.put("BOSS", "/resources/music/boss.wav");
        testValues.put("TREE", "/resources/music/tree.wav");
        testValues.put("WEB", "/resources/music/spider.wav");
    }

    @Test
    public void testAudioResourcesStreamsAreNotNullForEachAudioFile(){
        testValues.forEach((name, path) -> assertNotNull(getClass().getResourceAsStream(path)));
    }

}
