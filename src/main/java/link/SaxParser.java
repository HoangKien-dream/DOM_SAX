package link;
import link.util.ArticlesHandler;
import link.util.ConnectionHelper;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class SaxParser {
    private static final String XML_FILE_NAME = "https://vnexpress.net/rss/tam-su.rss";
    public static void main(String[] args) {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            ArticlesHandler articlesHandler = new ArticlesHandler();
            saxParser.parse( XML_FILE_NAME, articlesHandler);
            ArrayList<Articles> listArticles =  articlesHandler.getArrayList();
            for (Articles currentItems :
                    listArticles) {
//                register(currentItems);
                System.out.println(currentItems.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
