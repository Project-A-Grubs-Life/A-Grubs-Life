package com.game.view;

import com.game.controller.Game;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyListener;

public class ViewWindow {


    private JFrame window;
    private JLabel lastMoveLabel;
    private JLabel caterpillarStatLabel;
    private JLabel enemyStatLabel;
    private JLabel descriptionLabel;
    private JLabel roomLabel;
    private JLabel mapLabel;
    private JTextField inputField;
    private JPanel inputPanel;
    private JPanel statPanel;
    private JPanel descriptionPanel;
    private JPanel locationPanel;
    private KeyListener listener;

    public ViewWindow() {
        setUpWindow();
    }

    private void setUpWindow() {
        this.window = new JFrame("A Grub's Life.");
        this.window.setLayout(new BorderLayout());
        this.window.setPreferredSize(new Dimension(1024, 768));
        this.window.setVisible(true);
        this.window.setResizable(true);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.window.pack();
        setUpInputPanel();
        setUpStatPanel();
        setUpDescriptionPanel();
        setUpLocationPanel();
        this.window.add(inputPanel, BorderLayout.SOUTH);
        this.window.add(statPanel, BorderLayout.EAST);
        this.window.add(descriptionPanel, BorderLayout.CENTER);
        this.window.add(locationPanel, BorderLayout.WEST);

    }

    private void setUpInputPanel() {
        this.inputPanel = new JPanel();
        Color background = new Color(10, 80, 20, 158);
        inputPanel.setLayout(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createLineBorder(new Color(110, 16, 5)));
        inputPanel.setBackground(background);
        inputPanel.setPreferredSize(new Dimension(1000, 200));

        setUpInputField(inputPanel);
        setUpLastMoveLabel();
        inputPanel.add(inputField, BorderLayout.NORTH);
        inputPanel.add(lastMoveLabel, BorderLayout.CENTER);


    }

    private void setUpInputField(JPanel inputPanel) {
        this.inputField = new JTextField(50);
        inputField.setBorder(BorderFactory.createTitledBorder("Enter your command as a [VERB/NOUN]: \n " +
                ""));
        inputField.setBackground(new Color(217, 224, 214));
        inputField.addActionListener(e -> {

            String input = inputField.getText();
            Game.getProcessor().processCommand(input);
            inputField.setText("");
            updateCaterpillarStatus();

        });


    }

    private void setUpLastMoveLabel() {
        this.lastMoveLabel = new JLabel();
        String lastAction = Game.caterpillar.getLastAction();
        System.out.println(lastAction);
        if (lastAction != null) {
            lastMoveLabel.setText("<html> " +
                    "<h1>" + lastAction + "</h1>" +
                    "</html>");

        } else {
            lastMoveLabel.setBorder(BorderFactory.createTitledBorder("Your Last Move"));
            lastMoveLabel.setText("<html><body>" +
                    "                                  " +
                    "<body></html>");

        }

    }

    private void setUpStatPanel() {
        this.statPanel = new JPanel();
        statPanel.setLayout(new BorderLayout());
        statPanel.setPreferredSize(new Dimension(300, 800));
        statPanel.setLayout(new GridLayout(0, 1));
        statPanel.setBackground(new Color(0, 0, 0));



        setCaterpillarStatLabel();
        setEnemyStatLabel();
        statPanel.add(caterpillarStatLabel, BorderLayout.NORTH);
        statPanel.add(enemyStatLabel, BorderLayout.SOUTH);



    }

    private void setCaterpillarStatLabel() {
        this.caterpillarStatLabel = new JLabel();
        caterpillarStatLabel.setText("<html>\n" +
                "<style>\n" +
                "table {\n" +
                "color:green;\n" +
                "font-size:20px;\n" +
                "padding:15px;\n" +
                "}\n" +
                "</style>\n" +
                "<table style=\"width:5%\">\n" +
                "<tr>\n" +
                "<td style=\"text-align: left;\">Strength: </td><td>" + Game.caterpillar.getStrength() +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"text-align: left;\">Health: </td><td>" + Game.caterpillar.getHealth() +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"text-align: left;\">Level: </td><td>" + Game.caterpillar.getLevel() +
                "</td>\n" +
                "</tr>\n" +
                "<tr>\n" +
                "<td style=\"text-align: left;\">Experience: </td><td>" + Game.caterpillar.getExperience() +
                "/" + Game.caterpillar.getMaxExperience() +
                "</td>\n" +
                "</tr>\n" +
                "</table>\n" +
                "\n" +
                "</html>");
        caterpillarStatLabel.setBorder(BorderFactory.createTitledBorder("Caterpillar"));
        TitledBorder tb = new TitledBorder("Caterpillar Stats");
        tb.setTitleColor(Color.GREEN);
        caterpillarStatLabel.setBorder(tb);

    }

    private void setEnemyStatLabel() {
        this.enemyStatLabel = new JLabel();

        if (Game.caterpillar.getCurrentLocation().getEnemy() != null) {
            enemyStatLabel.setText(
                    "<html>\n" +
                            "<style>\n" +
                            "table {\n" +
                            "color:green;\n" +
                            "font-size:20px;\n" +
                            "padding:15px;\n" +
                            "}\n" +
                            "</style>\n" +
                            "<table style=\"width:5%\">\n" +
                            "<tr>\n" +
                            "<td style=\"text-align: left;\">Strength: </td><td>" +
                            Game.caterpillar.getCurrentLocation().getEnemy().getStrength() +
                            "</td>\n" +
                            "</tr>\n" +
                            "<tr>\n" +
                            "<td style=\"text-align: left;\">Health: </td><td>" +
                            Game.caterpillar.getCurrentLocation().getEnemy().getHealth() +
                            "</td>\n" +
                            "</tr>\n" +
                            "</table>\n" +
                            "\n" +
                            "</html>");

        }
        else{
            enemyStatLabel.setText("");
        }

        enemyStatLabel.setBorder(BorderFactory.createTitledBorder(Game.caterpillar.getCurrentLocation().getEnemy().getName()));
        TitledBorder eb = new TitledBorder("NO Enemy");
        eb.setTitle(Game.caterpillar.getCurrentLocation().getEnemy().getName() + " Stats");
        eb.setTitleColor(Color.GREEN);
        enemyStatLabel.setBorder(eb);

    }

    private void setUpDescriptionPanel() {
        this.descriptionPanel = new JPanel();
        this.descriptionLabel = new JLabel();
        descriptionPanel.setPreferredSize(new Dimension(700, 600));
        descriptionPanel.setBackground(new Color(255, 255, 255));
        descriptionPanel.setBorder(BorderFactory.createLineBorder(new Color(110, 16, 5)));

        setDiscriptionLabel();
        descriptionPanel.add(descriptionLabel, BorderLayout.CENTER);


    }

    private void setDiscriptionLabel() {

        String location = Game.caterpillar.getCurrentLocation().getName().toLowerCase();
        String desc = Game.caterpillar.getCurrentLocation().getDescription().toLowerCase();

//        descriptionLabel.setLocation(100,100);
        descriptionLabel.setText("<html> " +
                "<style>" +
                "p {padding-bottom: 280px }" +
                "</style>" +
                "<a href=\"https://en.wikipedia.org/wiki/Caterpillar\">Caterpillar Wiki</a>" +
                "<h1> " + location + "</h1> <br>" +
                "<p> " + desc + "</p><br><br><br><br>" +
                "  </html>\n");

    }


    private void setUpLocationPanel() {
        this.locationPanel = new JPanel();
        locationPanel.setLayout(new BorderLayout());
        locationPanel.setPreferredSize(new Dimension(300, 800));
        locationPanel.setLayout(new GridLayout(0, 1));
        locationPanel.setBackground(new Color(0, 0, 0));
        setMapLabel();
        setRoomLabel();
        locationPanel.add(mapLabel, BorderLayout.NORTH);
        locationPanel.add(roomLabel, BorderLayout.SOUTH);

    }

    private void setMapLabel() {
        this.mapLabel = new JLabel();
        TitledBorder map = new TitledBorder("Map");
        map.setTitleColor(Color.GREEN);
        mapLabel.setBorder(map);

    }

    private void setRoomLabel() {
        this.roomLabel = new JLabel();
        TitledBorder room = new TitledBorder("Room");
        room.setTitleColor(Color.GREEN);
        roomLabel.setBorder(room);


    }

    public void updateCaterpillarStatus() {
        setUpLastMoveLabel();
        setCaterpillarStatLabel();
        setEnemyStatLabel();
        setDiscriptionLabel();
        setRoomLabel();
        setMapLabel();
        this.window.repaint();
    }


}
