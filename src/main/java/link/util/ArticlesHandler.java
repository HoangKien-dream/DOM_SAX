package link.util;

import link.Articles;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

public class ArticlesHandler extends DefaultHandler {
    private ArrayList<Articles> arrayList = new ArrayList<>();
    private Articles currentItems;
    private boolean isTittle;
    private boolean isDescription;
    private boolean isPubDate;
    private boolean isLink;
    private boolean isGuild;
    private boolean isSlash;

    public ArrayList<Articles> getArrayList() {
        return arrayList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        switch (qName) {
            case "item":
                currentItems = new Articles();
                break;
            case "title":
                isTittle = true;
                break;
            case "description":
                isDescription = true;
                break;
            case "pubdate":
                isPubDate = true;
                break;
            case "link":
                isLink = true;
                break;
            case "guild":
                isGuild = false;
                break;
            case "slash:comments":
                isSlash = false;
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        switch (qName) {
            case "item":
                arrayList.add(currentItems);
                break;
            case "title":
                isTittle = false;
                break;
            case "description":
                isDescription = false;
                break;
            case "pubdate":
                isPubDate = false;
                break;
            case "link":
                isLink = false;
                break;
            case "guild":
                isGuild = false;
                break;
            case "slash:comments":
                isSlash = false;
                break;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String value = new String(ch, start, length);
        if (currentItems != null) {
            if (isTittle) {
                currentItems.setTitle(value);
            } else if (isDescription) {
                currentItems.setDescription(value);
            } else if (isPubDate) {
                currentItems.setPubDate(value);
            } else if (isLink) {
                currentItems.setLink(value);
            }else if (isGuild){
                currentItems.setGuid(value);
            }else if (isSlash){
                currentItems.setSlash(value);
            }
        }
    }
}
