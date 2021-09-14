/*
    This is the game class. It instantiates all of the games materials, and updates them. The game will use the model package and view package to pass data from the user to the system and vice versa.
 */
package com.game.controller;

import com.game.model.engine.LogicEngine;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Location;
import com.game.view.ViewWindow;

import java.io.*;
import java.util.HashMap;
import java.util.Locale;

public class Game {
    private HashMap<String, Location> locations;
    private HashMap<String, Enemy> enemies;
    private Caterpillar caterpillar;
    private LogicEngine processor;
    private ViewWindow viewWindow;

    public Game() {

    }

    /**
     *   Called by the client to start a new game.
     */
    public void start(){
        setUpComponents();
        run();
    }

    /**
     * Instantiates the necessary fields of a Game object.
     */
    private void setUpComponents(){
        this.enemies = populateEnemies();
        this.locations = populateLocations();
        this.caterpillar = new Caterpillar(100,0,0);
        this.processor = new LogicEngine(caterpillar,locations, enemies);
        this.caterpillar.setCurrentLocation(locations.get("GENESIS"));
        this.viewWindow = new ViewWindow(caterpillar, processor);
    }

    /**
     *  Controls the game loop.
     *  View updates with user input.
     *
     */
    private void run(){
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int counter = 0;
                viewWindow.welcomeMessage();
                playAudio("src/resources/music/forest.wav");
                viewWindow.updateCaterpillarStatus();
                caterpillar.healthRegenerator(counter++);
            }
        });
    }

    /**
     * Generates location objects from text file data
     * @return hashmap of location objects (String, Location)
     */
    private HashMap<String,Location> populateLocations(){
        HashMap<String,Location> locations = new HashMap<>();
        String[] locationFields;
        try{
            InputStream inputStream = getClass().getResourceAsStream("locations.txt");
            InputStreamReader myReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(myReader);
            String line = null;
            while((line = br.readLine() )!= null){
                locationFields = line.split(":");

                Location loc = new Location(locationFields[0].trim(),locationFields[1].trim(), locationFields[2].trim(), locationFields[3].trim(), locationFields[4].trim(),locationFields[5].trim() );
                loc.setEnemy(enemies.get(locationFields[0].trim().toLowerCase(Locale.ROOT)));
                locations.put(locationFields[0].trim(), loc);
            }
            System.out.println(locations.toString());
            br.close();
            myReader.close();
            inputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return locations;
    }

    /**
     * Populates Enemy objects from an external text file.
     * @return Hashmap of Enemy objects (String, Enemy)
     */
    private HashMap<String,Enemy> populateEnemies() {
        HashMap<String, Enemy> enemies = new HashMap<>();
        String[] enemyFields;
        try {
            InputStream inputStream = getClass().getResourceAsStream("enemies.txt");
            InputStreamReader myReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(myReader);
            String line = null;
            while((line = br.readLine() )!= null){
                enemyFields = line.split(",");

                Enemy enemy = new Enemy(enemyFields[0].trim(),Integer.parseInt(enemyFields[ 1].trim()), Integer.parseInt(enemyFields[ 2].trim()), Integer.parseInt(enemyFields[ 3].trim()), Boolean.parseBoolean(enemyFields[4].trim()), Boolean.parseBoolean(enemyFields[5].trim()), enemyFields[6].trim(), Boolean.parseBoolean(enemyFields[7].trim()));
                enemies.put(enemyFields[6].trim(), enemy);

            }
            System.out.println(enemies.toString());
            br.close();
            myReader.close();
            inputStream.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return enemies;
    }

    /**
     * Get a hashmap of enemies in the game
     * @return Hashmap of Enemy objects (String, Enemy)
     */
    public HashMap<String, Enemy> getEnemies() {
        return enemies;
    }

    public static void playAudio(String musicFilePath){
        Thread musicThread = new Thread(new Audio(musicFilePath), "backgroundMusicThread");
        musicThread.run();
    }

}