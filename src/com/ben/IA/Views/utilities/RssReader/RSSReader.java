package com.ben.IA.Views.utilities.RssReader;

import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class RSSReader {

    private static RSSReader instance = null;

    private RSSReader() {
    }

    public static RSSReader getInstance() {
        if (instance == null)
            instance = new RSSReader();
        return instance;
    }

    public String getNews(String URL) {
        StringBuilder sb = new StringBuilder();

        try {

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            URL u = new URL(URL); // your feed url

            Document doc = builder.parse(u.openStream());

            NodeList nodes = doc.getElementsByTagName("item");

            for (int i = 0; i < nodes.getLength(); i++) {

                Element element = (Element) nodes.item(i);
                sb.append("<div style=\"background-color:aqua \">"+"<h1>" + getElementValue(element, "title")+"</h1></br>"+"</div>");
                sb.append("<h3>Publish Date: " + getElementValue(element, "pubDate") + "</h2></br>");
                sb.append("<h3>" + getElementValue(element, "description") + "</h2></br>");
                sb.append("<a href="+getElementValue(element, "link")+">Read more...</a></br>");
            }
            return sb.toString();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return sb.toString();
    }

    private String getCharacterDataFromElement(Element e) {
        try {
            Node child = e.getFirstChild();
            if (child instanceof CharacterData) {
                CharacterData cd = (CharacterData) child;
                return cd.getData();
            }
        } catch (Exception ex) {
        }
        return "";
    } 

    protected float getFloat(String value) {
        if (value != null && !value.equals(""))
            return Float.parseFloat(value);
        return 0;
    }

    protected String getElementValue(Element parent, String label) {
        return getCharacterDataFromElement((Element) parent.getElementsByTagName(label).item(0));
    }
}

