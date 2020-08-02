package controllers;

import java.io.InputStream;
import java.sql.*;
import java.util.Date;
import java.util.Properties;

public class DatabaseController {
    private Connection connection;
    private Statement statement;
    private static final String INSERT = "insert into `scores` (`name`,`points`,`good`)values(?,?,?)";

    public DatabaseController() throws Exception {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Properties env = new Properties();
        InputStream resourceStream = loader.getResourceAsStream("application.properties");
        env.load(resourceStream);
        Class c= Class.forName(env.getProperty("driverClassName"));
        DriverManager.registerDriver((Driver) c.newInstance());
        connection = DriverManager.getConnection(env.getProperty("url"), env.getProperty("username"), env.getProperty("password"));
        if(!connection.isClosed()){
            initDatabase();
        }

    }

    private void initDatabase() throws SQLException {
        statement =  connection.createStatement();
        statement.execute("create table `scores` (`id` int auto_increment primary key , `name` varchar(255), `points` int, `good` int(1))");
        System.out.println("Init completed");
    }

    public void put(String person, Integer score, boolean good) throws Exception{
        PreparedStatement preparedStatement =  connection.prepareStatement(INSERT);
        preparedStatement.setString(1,person);
        preparedStatement.setInt(2,score);
        preparedStatement.setBoolean(3,good);
        preparedStatement.execute();
    }

    public void execute(String line) throws Exception{
        try {
            statement.execute(line);

            ResultSet resultSet = statement.getResultSet();
            ResultSetMetaData meta = resultSet.getMetaData();
            int columns = meta.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                System.out.print(String.format("%-30s", meta.getColumnName(i)));
            }
            System.out.println();
            while (resultSet.next()) {
                for (int i = 1; i <= columns; i++) {
                    System.out.print(String.format("%-30s", resultSet.getString(i)));
                }
                System.out.println();

            }
        }catch (SQLException e){
            System.err.println(e.getMessage());
        }
    }

    public void executeOverview(ScoreController scoreController) throws Exception{
        String query1= "select name, count(good) from scores where good=1 group by name order by count(good) desc";
        String query2= "select name, count(good) from scores where good=0 group by name order by count(good) desc";
        ResultSet res1, res2;
        statement.execute(query1);
        Statement stat2 = connection.createStatement();
        stat2.execute(query2);
        res1 = statement.getResultSet();
        res2 = stat2.getResultSet();
        System.out.println(String.format("%-10s", "Position")+String.format("%-30s", "WinnerName")+String.format("%-30s", "Amount of rights")+String.format("%-20s", "Longest streak")+String.format("%-30s", "LoserName")+String.format("%-30s", "Amount of wrongs"));
        int counter =1;
        while (res1.next()){
            res2.next();
            System.out.print(String.format("%-10s", counter));
            for (int i = 1; i <= 2; i++) {
                System.out.print(String.format("%-30s", getValue(res1,i)));
                if(i==2){
                    System.out.print(String.format("%-20s", scoreController.getStreak(getValue(res1,1))));
                }
            }
            for (int i = 1; i <= 2; i++) {
                System.out.print(String.format("%-30s", getValue(res2,i)));
            }
            System.out.println();
            counter++;
        }
    }

    private String getValue(ResultSet resultSet, int id){
        try{
            return resultSet.getString(id);
        }catch (SQLException e){
            return "";
        }
    }
}
