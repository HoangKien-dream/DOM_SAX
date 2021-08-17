package link;

import link.util.ConnectionHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;


public class DomParser {
    public static void main(String[] args) {
        readXMl();
    }

    public static boolean register(Articles articles) {
        try {
            Connection cnn = ConnectionHelper.getConnection();
            if (cnn == null) {
                System.out.println("Can not connection to database");
                return false;
            }
            StringBuilder sqlBuilder = new StringBuilder();
            sqlBuilder.append("insert into articles");
            sqlBuilder.append(" ");
            sqlBuilder.append("(title, description, pubdate, link, guild, slash)");
            sqlBuilder.append(" ");
            sqlBuilder.append("values");
            sqlBuilder.append(" ");
            sqlBuilder.append("(?, ?, ?, ?, ?, ?)");
            PreparedStatement preparedStatement = cnn.prepareStatement(sqlBuilder.toString());
            preparedStatement.setString(1, articles.getTitle());
            preparedStatement.setString(2, articles.getDescription());
            preparedStatement.setString(3, articles.getPubDate());
            preparedStatement.setString(4, articles.getLink());
            preparedStatement.setString(5, articles.getGuid());
            preparedStatement.setString(6, articles.getSlash());
            preparedStatement.execute();
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
        }
        return false;
    }
    Articles items = new Articles();
    public static ArrayList<Articles> readXMl() {
        ArrayList<Articles> arrayList = new ArrayList<>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse("https://vnexpress.net/rss/tam-su.rss");
            Element element = document.getDocumentElement();
            NodeList nodeList = element.getElementsByTagName("item");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Articles items = new Articles();
                Node itemNode = nodeList.item(i);
//                System.out.println(itemNode.getNodeName());
                NodeList itemChildNode = itemNode.getChildNodes();
                for (int j = 0; j < itemChildNode.getLength(); j++) {
                    Node childNode = itemChildNode.item(j);
//                    System.out.println(childNode.getNodeName() + " - " + childNode.getNodeType());
                    if (childNode.getNodeType() != Node.ELEMENT_NODE) {
                        continue;
                    }
                    switch (childNode.getNodeName()) {
                        case "title":
                            items.setTitle(childNode.getTextContent());
                            break;
                        case "description":
                            items.setDescription(childNode.getTextContent());
                            break;
                        case "pubDate":
                            items.setPubDate(childNode.getTextContent());
                            break;
                        case "link":
                            items.setLink(childNode.getTextContent());
                            break;
                        case "guid":
                            items.setGuid(childNode.getTextContent());
                            break;
                        case "slash:comments":
                            items.setSlash(childNode.getTextContent());
                            break;
                    }
                }
                arrayList.add(items);
            }
            for (int i = 0; i < arrayList.size(); i++) {
                register(arrayList.get(i));
//                System.out.println(arrayList.get(i).getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
