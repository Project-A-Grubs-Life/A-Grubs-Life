package com.game.controller;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

public class Audio implements Runnable {

    /**
     * Enum class for the different room containing location and audio file path
     */
    private enum AudioPaths {

        GENESIS("GENESIS", "/resources/music/forest.wav"),
        START("START", "/resources/music/forest.wav"),
        WOODS("WOODS", "/resources/music/woods_owl.wav"),
        HOLE("HOLE", "/resources/music/hole.wav"),
        LAKE("LAKE", "/resources/music/lake.wav"),
        HILL("HILL", "/resources/music/anthill.wav"),
        FLOWERS("FLOWERS", "/resources/music/flowers.wav"),
        BOSS("BOSS", "/resources/music/boss.wav"),
        TREE("TREE", "/resources/music/tree.wav"),
        WEB("WEB", "/resources/music/spider.wav");

        private String location;
        private String path;    //file sound file path for location

        AudioPaths(String location, String path) {
            this.location = location;
            this.path = path;
        }

        //Get the location
        private String getLocation() {
            return location;
        }

        //Get the audio path for the location
        private String getPath() {
            return path;
        }
    }

    private String musicFilePath; //music file path from resource folder
    private static Clip clip;     //Clip is the player that plays the audio
    private static boolean isPlaying;
    private double volume = 0.99;

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public double getVolume() {
        return volume;
    }


    public Audio(String musicFilePath) {
        this.musicFilePath = musicFilePath;
    }

    @Override
    public void run() {
        playBackgroundMusic(musicFilePath);
    }

    public void stop() {
        clip.stop();
        isPlaying = false;
    }

    public static boolean isIsPlaying() {
        return isPlaying;
    }

    public static void setIsPlaying(boolean isPlaying) {
        Audio.isPlaying = isPlaying;
    }

    public static float getMaxVolume(){
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return volumeControl.getMaximum();
    }

    public static float getMinVolume(){
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return volumeControl.getMinimum();
    }

    public static void setClipVolume(Float volume){
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        volumeControl.setValue(volume);
    }

    public static float getCurrentClipVolume(){
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        return volumeControl.getValue();
    }

    /**
     * This method will be used to play a sound or music
     * /**
     * Changes volume of background sound, the static clip object in class Audio
     *
     * @param direction UP or DOWN
     *                  /**
     *                  Changes volume of background sound, the static clip object in class Audio
     * @param direction UP or DOWN
     *
     */
    public double changeVolume(String direction) {
        try {
            double delta = direction.equalsIgnoreCase("UP") ? 0.1 : -0.1;
            double newVol = getVolume() + delta;
            float max = getMaxVolume();
            float min = getMinVolume();
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float newVal = (float) ((min + newVol * (max - min)));
            if (newVal > max) {
                  newVal = max;
                  newVol -= delta;
            }
            volumeControl.setValue(newVal);
            setVolume(newVol);
            return newVol;
        }catch(IllegalArgumentException e) {
            e.getMessage();
        }
        return 0.09;
    }

    /*
     *This method will be used to play a sound or music
     */
    public void playBackgroundMusic(String musicFilePath) {

        //Used to match musicFilePath to the correct enum and once found return the path of that enum
        String musicPath = Arrays.stream(
                AudioPaths.values())
                .filter(audioPaths ->
                        audioPaths.getLocation().equalsIgnoreCase(musicFilePath))
                .findAny().orElse(null).getPath();

        try {
            //Get Audio file
//            File file = new File(musicPath);
            InputStream file = getClass().getResourceAsStream(musicPath);
            //Get Clip that will be use to open and play the sound/music
            clip = AudioSystem.getClip();

            //Get the file as an AudioInputStream
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(file));

            //Get the current format of the AudioInputStream
            AudioFormat baseFormat = inputStream.getFormat();

            //Create a new AudioFormat to convert the to from the old format
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
                    baseFormat.getChannels() * 2, baseFormat.getSampleRate(), false);

            //Get a new AudioInputStream from the old format to the new format
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(decodedFormat, inputStream);

            //Open and start playing the audio stream
            clip.open(audioInputStream);
            setIsPlaying(true);
            setVolume(getCurrentClipVolume());

            //Clip will loop the audio until Clip is stop/closed
            clip.loop(Clip.LOOP_CONTINUOUSLY);

            //Close streams
            inputStream.close();
            audioInputStream.close();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}

