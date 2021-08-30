package com.game.model.engine;

import com.game.model.engine.CommandProcessor;
import com.game.model.engine.KeyWordIdentifier;
import com.game.model.engine.Prompter;
import com.game.model.engine.TextParser;
import com.game.model.materials.Caterpillar;
import com.game.model.materials.Enemy;
import com.game.model.materials.Location;
import com.game.view.ViewWindow;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class LogicEngine {
    private CommandProcessor commandProcessor;
    private KeyWordIdentifier keyWordIdentifier;
    private Prompter prompter;
    private TextParser textParser;

    public LogicEngine(Caterpillar caterpillar, HashMap<String, Location> locations, HashMap<String, Enemy> enemies){
        setUpEngineComponents(caterpillar,locations, enemies);
    }

    private void setUpEngineComponents(Caterpillar caterpillar, HashMap<String, Location> locations, HashMap<String,Enemy> enemies){
        this.prompter = new Prompter();
        this.textParser = new TextParser();
        this.keyWordIdentifier = new KeyWordIdentifier();
        this.commandProcessor = new CommandProcessor(caterpillar, locations, enemies);
    }
    public void processCommand(String userInput){//
        ArrayList parsedInput = textParser.parseInput(userInput);
        ArrayList command = keyWordIdentifier.identifyKewWords(parsedInput);
        commandProcessor.executeCommand(command);
    }



    public CommandProcessor getCommandProcessor() {
        return commandProcessor;
    }

    public void setCommandProcessor(CommandProcessor commandProcessor) {
        this.commandProcessor = commandProcessor;
    }

    public KeyWordIdentifier getKeyWordIdentifier() {
        return keyWordIdentifier;
    }

    public void setKeyWordIdentifier(KeyWordIdentifier keyWordIdentifier) {
        this.keyWordIdentifier = keyWordIdentifier;
    }

    public Prompter getPrompter() {
        return prompter;
    }

    public void setPrompter(Prompter prompter) {
        this.prompter = prompter;
    }

    public TextParser getTextParser() {
        return textParser;
    }

    public void setTextParser(TextParser textParser) {
        this.textParser = textParser;
    }
}
