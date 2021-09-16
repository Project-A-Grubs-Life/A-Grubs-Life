package com.game.view;

import com.game.model.engine.LogicEngine;
import com.game.model.materials.Caterpillar;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;


public class ViewWindow {

    private Caterpillar caterpillar;

    private JFrame window;
    private JPanel statPanel;
    private JPanel descriptionPanel;
    private JPanel inputPanel;
    private JPanel instructions;
    private JLabel caterpillarStatLabel;
    private JLabel enemyStatLabel;
    private JLabel instDesc;
    private JTextField inputField;
    private JEditorPane descriptionArea;
    private String input;
    private LogicEngine processor;
    private JLabel labelVerbs;
    private JLabel labelNouns;
    private PanelListener listener;
    private TitledBorder tb;
    private TitledBorder eb;
    private JPanel mapPanel;
    private JEditorPane mapArea;
    private JEditorPane lastMove;
    private JPanel soundImage;

    public ViewWindow(Caterpillar caterpillar, LogicEngine processor) {
        this.caterpillar = caterpillar;
        this.processor = processor;

        setUpComponents();
        updateMapPanel();
        updateDescriptionPanel();
        updateCaterpillarStatus();
    }

    public void welcomeMessage() {
        this.instructions = new JPanel();
        this.instDesc = new JLabel();
        this.soundImage = new JPanel();
        instDesc.setText(readHTML("instructions.html", null));
        instructions.add(instDesc);
        String startGameAudio = "src/resources/images/audio.jpg";
        BufferedImage myPicture = getAudioFile(startGameAudio);
        Image imageIcon = new ImageIcon(myPicture).getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        JLabel picLabel = new JLabel(new ImageIcon(imageIcon));
        soundImage.add(picLabel);
        instructions.add(soundImage);
    }

    public String getInput() {
        return this.input;
    }

    /*
     * static method to retrieve audio files
     */
    //METHOD IS PUBLIC ONLY FOR TESTING WILL CHANGE TO PRIVATE BEFORE RELEASE AND DELETE THIS COMMENT
    public static BufferedImage getAudioFile(String audioPath){
        try {
            return ImageIO.read(new File(audioPath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateCaterpillarStatus() {
        updateLastMove();
        updateStatPanel();
        updateMapPanel();
        this.window.repaint();
    }

    private void updateDescriptionPanel() {
        //Step 1: Create a structure will will pass to method
        HashMap<String, String> data = new HashMap<>();
        data.put("[[location]]", caterpillar.getCurrentLocation().getName().toLowerCase());
        data.put("[[desc]]", caterpillar.getCurrentLocation().getDescription().toLowerCase());

        //Step 2: Set the desc label that calls our helper method
        descriptionArea.setText(readHTML("description.html", data));
    }

    //==================SETUP METHODS============================
    private void setUpComponents() {
        welcomeMessage();
        setUpInputPanel();
        setUpStatPanel();
        setUpDescriptionPanel();
        setUpMapPanel();
        setUpWindow();
    }

    private void setUpMapPanel() {
        this.mapPanel = new JPanel();
        mapPanel.setLayout(new BorderLayout());
        this.mapArea = new JEditorPane();
        mapArea.setContentType("text/html");
        mapArea.setEditable(false);
        mapPanel.setPreferredSize(new Dimension(200, 200));
        mapPanel.setBackground(new Color(0, 0, 0));
        mapPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255)));
        mapPanel.add(mapArea);
    }

    private void setUpWindow() {
        this.window = new JFrame("A Grub's Life.");
        this.window.setLayout(new BorderLayout());
        this.window.add(statPanel, BorderLayout.EAST);
        this.window.add(descriptionPanel, BorderLayout.NORTH);
        this.window.add(mapPanel, BorderLayout.CENTER);
        this.window.add(inputPanel, BorderLayout.SOUTH);
        this.window.add(instructions, BorderLayout.WEST);
        this.window.setPreferredSize(new Dimension(850, 650));
        this.window.setVisible(true);
        this.window.setResizable(true);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                if (JOptionPane.showConfirmDialog(window,
                        "Are you sure you do not want to continue playing?", "Stop Game?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
                    System.exit(0);
                }
            }
        });
        this.window.pack();
    }

    private void setUpDescriptionPanel() {
        this.descriptionPanel = new JPanel();
        descriptionPanel.setLayout(new BorderLayout());
        listener = new PanelListener();
        this.descriptionArea = new JEditorPane();
        descriptionArea.setContentType("text/html");
        descriptionArea.addMouseListener(listener);
        descriptionArea.setEditable(false);
        descriptionPanel.setPreferredSize(new Dimension(700,150));
        descriptionPanel.setBackground(new Color(255, 255, 255));
        descriptionPanel.setBorder(BorderFactory.createLineBorder(new Color(110, 16, 5)));
        descriptionPanel.add(descriptionArea);
    }

    private void setUpStatPanel() {
        this.statPanel = new JPanel();
        this.caterpillarStatLabel = new JLabel();
        this.enemyStatLabel = new JLabel();
        statPanel.setLayout(new BorderLayout());
        statPanel.setPreferredSize(new Dimension(250, 600));
        statPanel.setLayout(new GridLayout(0, 1));
        setCaterpillarStats();
        setEnemyStats();
        statPanel.add(caterpillarStatLabel, BorderLayout.NORTH);
        statPanel.add(enemyStatLabel, BorderLayout.CENTER);
        statPanel.setBackground(new Color(0, 0, 0));
        this.tb = new TitledBorder("Caterpillar Stats");
        this.eb = new TitledBorder(caterpillar.getCurrentLocation().getEnemy().getName() + " Stats");
        tb.setTitleColor(Color.GREEN);
        eb.setTitleColor(Color.GREEN);
        caterpillarStatLabel.setBorder(tb);
        enemyStatLabel.setBorder(eb);
    }

    private void setCaterpillarStats() {
        caterpillarStatLabel.setText("");
        caterpillarStatLabel.setBorder(BorderFactory.createTitledBorder("Caterpillar"));
    }

    private void setEnemyStats() {
        enemyStatLabel.setText("");
        enemyStatLabel.setBorder(BorderFactory.createTitledBorder(caterpillar.getCurrentLocation().getEnemy().getName()));
    }

    private void updateStatPanel() {
        //Step 1: Create a structure will will pass to method
        HashMap<String, String> myStats = new HashMap<>();
        myStats.put("[[strength]]",  String.valueOf(caterpillar.getStrength()));
        myStats.put("[[health]]",  String.valueOf(caterpillar.getHealth()));
        myStats.put("[[level]]",  String.valueOf(caterpillar.getLevel()));
        myStats.put("[[exp]]",  String.valueOf(caterpillar.getExperience()/caterpillar.getMaxExperience()));

        //Step 2: Set the stat label that calls our helper method
        caterpillarStatLabel.setText(readHTML("statPanel.html", myStats));

        //Step 3: Check if there's an enemy at the location
        if (caterpillar.getCurrentLocation().getEnemy() != null) {
            //Step 3a: Create a structure will will pass to method
            HashMap<String, String> enemyStats = new HashMap<>();
            enemyStats.put("[[strength]]",  String.valueOf(caterpillar.getCurrentLocation().getEnemy().getStrength()));
            enemyStats.put("[[health]]",  String.valueOf(caterpillar.getCurrentLocation().getEnemy().getHealth()));

            //Step 3b: Set the desc label that calls our helper method
            enemyStatLabel.setText(readHTML("statPanelEnemy.html", enemyStats));
            eb.setTitle(caterpillar.getCurrentLocation().getEnemy().getName() + " Stats");
        }
    }

    private void updateMapPanel() {
        HashMap<String, String> myLoc = new HashMap<>();
        myLoc.put("[[" + caterpillar.getCurrentLocation().getName() + "]]",  "<b class=\"target\">[[" + caterpillar.getCurrentLocation().getName() + "]]</b>");
        mapArea.setText(readMap("map.html", myLoc));
    }

    private void setUpInputPanel() {
        this.inputPanel = new JPanel();
        Color background = new Color(10, 80, 20, 158);
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createLineBorder(new Color(110, 16, 5)));
        inputPanel.setBackground(background);
        inputPanel.setPreferredSize(new Dimension(1000, 200));
        setUpLabelNouns();
        setUpLabelVerbs();
        setUpInputField();
        setUpLastMove();
        lastMove.setContentType("text/html");
        inputPanel.add(inputField, BorderLayout.NORTH);
        inputPanel.add(labelVerbs, BorderLayout.WEST);
        inputPanel.add(labelNouns, BorderLayout.EAST);
        inputPanel.add(lastMove, BorderLayout.CENTER);
    }

    private void setUpLabelNouns() {
        this.labelNouns = new JLabel();
        labelNouns.setText(readHTML("direction.html", null));
        labelNouns.setBorder(BorderFactory.createTitledBorder("Directions"));
    }

    private void setUpLabelVerbs() {
        this.labelVerbs = new JLabel();
        labelVerbs.setText(readHTML("actions.html", null));
        labelVerbs.setBorder(BorderFactory.createTitledBorder("Actions"));
    }

    private void setUpInputField() {
        this.inputField = new JTextField(50);
        inputField.setBorder(BorderFactory.createTitledBorder("Enter your command as a [VERB/NOUN]: \n " +
                ""));
        inputField.setBackground(new Color(217, 224, 214));
        inputField.addActionListener(e -> {
            this.input = inputField.getText();
            processor.processCommand(getInput());
            inputField.setText("");
            updateDescriptionPanel();
            updateMapPanel();
            updateLastMove();
            updateCaterpillarStatus();
        });
    }

    private void setUpLastMove() {
        this.lastMove = new JEditorPane();
        lastMove.setContentType("text/html");
        lastMove.setEditable(false);
        lastMove.setBorder(BorderFactory.createTitledBorder("Your Last Move"));
        lastMove.setText(readHTML("lastMoveTitle.html", null));
    }

    private void updateLastMove() {
        //In here we should add a getLastAction table element, this will let the user know the last thing they sucessfuly did... this variable should be updated in every command process function
        //Step 1: Create a structure will will pass to method
        HashMap<String, String> move = new HashMap<>();
        move.put("[[move]]",  caterpillar.getLastAction());
        //Step 2: Set the last move body label that calls our helper method
        lastMove.setText(readHTML("lastMoveBody.html", move));
    }

    private String readHTML(String path, HashMap<String, String> data) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            //Step 1: Get the data from file
            InputStream inputStream = getClass().getResourceAsStream(path);
            InputStreamReader myReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(myReader);
            String line = null;

            //Step 2: Read line by line if any value exists lets append it
            while ((line = br.readLine()) != null) {
                //Step 2a: to check if I need to replace with a value -- would be faster to just check if it even has the symbol to replace
                //It would be faster this way -- less iterators because of unknown number of values that would need to be replaced
                if (line.contains("[[")) {
                    //Step 2b: loop over the hashmap
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        if (line.contains(entry.getKey())) {
                            //Need to the get the position of a string values and replace to the entry value
                            contentBuilder.append(line.replace(entry.getKey(), entry.getValue()));
                        }
                    }
                }else{
                    contentBuilder.append(line);
                }
            }
            //Step 3: Make sure to close the connections if done!
            br.close();
            myReader.close();
            inputStream.close();
        } catch (IOException e) {
            System.out.println("Error reading the HTML file: " + e);
        }
        return contentBuilder.toString();
    }

    private String readMap(String path, HashMap<String, String> data) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            //Step 1: Get the data from file
            InputStream inputStream = getClass().getResourceAsStream(path);
            InputStreamReader myReader = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(myReader);
            String line = null;

            //Step 2: Read line by line if any value exists lets append it
            while ((line = br.readLine()) != null) {
                //Step 2a: to check if I need to replace with a value -- would be faster to just check if it even has the symbol to replace
                //It would be faster this way -- less iterators because of unknown number of values that would need to be replaced
                if (line.contains("[[")) {
                    //Step 2b: loop over the hashmap
                    for (Map.Entry<String, String> entry : data.entrySet()) {
                        if (line.contains(entry.getKey())) {
                            //Need to the get the position of a string values and replace to the entry value
                            contentBuilder.append(line.replace(entry.getKey(), entry.getValue()));
                        }else{
                            contentBuilder.append(line);
                        }
                    }
                }else{
                    contentBuilder.append(line);
                }
            }
            //Step 3: Make sure to close the connections if done!
            br.close();
            myReader.close();
            inputStream.close();
        } catch (IOException e) {
            System.out.println("Error reading the HTML file: " + e);
        }
        return contentBuilder.toString();
    }

    private class PanelListener implements MouseListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            try {
                Desktop.getDesktop().browse(new URI("https://en.wikipedia.org/wiki/Caterpillar"));

            } catch (IOException | URISyntaxException e1) {
                e1.printStackTrace();
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}

