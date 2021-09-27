package com.game.view;

import org.junit.Test;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class ViewWindowTest {

    @Test
    public void readHTML_returnsString_successfully() {
        HashMap<String, String> testMap = new HashMap<>();
        testMap.put("[[move]]", "something that replaces the keyword");
        assertEquals("<html>" +
                "<style>" +
                "    html, body{" +
                "        background-color: #EAEAEA;" +
                "        text-align: center;" +
                "        font-size: 3.5em;" +
                "    }" +
                "</style>" +
                "<body>" +
                "<h1>something that replaces the keyword</h1>" +
                "</body>" +
                "</html>", ViewWindow.readHTML("lastMoveBody.html", testMap));
    }

    @Test
    public void readMap_returnsString_successfully() {
        List<String> testList = new ArrayList<>(Arrays.asList("www.example.com", "[[HILL]]", "[[HILL CHANGED]]"));
        assertEquals("<html><style>    body {        width: 100vw;        height: 400px;        background-color: " +
                "black;        padding-left: 10px;    }    div {        background-color: grey;        text-align: " +
                "left;        color: white;    }    .target{        color: green;    }    .path{        color: " +
                "blue;    }    .magic{        color: black;    }</style><body><a><img width=\"200\" height=\"200\" " +
                "src=\"www.example.com\"></a><div>-------------------------------------------------------------------" +
                "---------------------------------------</div><div class=\"magic\">-----------------------------------" +
                "------------------------------------------------------------------</div><div><b class=\"magic\">------" +
                "----------------</b>[[WOODS]]<b class=\"magic\">------</b>[[HILL CHANGED]]<b class=\"path\">--------" +
                "</b>[[BOSS]]<b class=\"magic\">---------------------------</b></div><div><b class=\"magic\">----------" +
                "------------------</b><b class=\"path\">|</b><b class=\"magic\">-------------------</b><b class=" +
                "\"path\">|</b><b class=\"magic\">------------------</b><b class=\"path\">|</b><b class=\"magic\">------" +
                "-------------------------</b></div><div><b class=\"magic\">--</b>[[HOLE]]<b class=\"path\">------</b>" +
                "[[GENESIS]]<b class=\"path\">--------</b>[[WEB]]<b class=\"path\">----</b>[[FLOWERS]]<b class=\"path\">" +
                "-----</b>[[TREE]]<b class=\"magic\">-----</b></div><div><b class=\"magic\">--------------------------" +
                "--</b><b class=\"path\">|</b><b class=\"magic\">------------------------------------------------------" +
                "-----------------</b></div><div><b class=\"magic\">-----------------------</b>[[LAKE]]<b class=\"magic\">" +
                "------------------------------------------------------------------</b></div><div class=\"magic\">----" +
                "-------------------------------------------------------------------------------------------------</div>" +
                "<div>-----------------------------------------------------------------------------------------------" +
                "-----------</div></body></html>", ViewWindow.readMap("map.html", testList));
    }
}
