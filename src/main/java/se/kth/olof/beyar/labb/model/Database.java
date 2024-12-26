package se.kth.olof.beyar.labb.model;

import se.kth.olof.beyar.labb.common.BooksDBException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Represents a database connection with methods to connect and disconnect from the database.
 */
public class Database
{
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;

    /**
     * Constructs a Database object with the specified schema.
     * The credentials, host and port is provided by the environment variable, if not given, default will be used
     * @param schema the name of the database schema
     */
    public Database(String schema)
    {
        String host = System.getenv("host").isEmpty() ? "localhost" : System.getenv("host");
        String port = System.getenv("port").isEmpty() ? "3306" : System.getenv("port");
        this.url = "jdbc:mysql://" + host + ":" + port + "/" + schema + "?UseClientEnc=UTF8";
        this.username = System.getenv("username");
        this.password = System.getenv("password");
    }

    /**
     * Connects to the database using the specified URL, username, and password.
     * @return a Connection object representing the database connection
     * @throws SQLException if a database access error occurs
     * @throws ClassNotFoundException if the MySQL JDBC Driver class is not found */
    public Connection connect() throws SQLException, ClassNotFoundException
    {
        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("DB user " + username + " connected to: " + url);
            return connection;
        }
        catch (SQLException e)
        {
            throw new BooksDBException(e);
        }
        catch (ClassNotFoundException e)
        {
            throw new ClassNotFoundException();
        }
    }

    /**
     * Disconnects from the database, closing the connection.
     * @throws SQLException if a database access error occurs
     */
    public void disconnect() throws SQLException
    {
        try
        {
            if (connection != null)
            {
                connection.close();
                System.out.println("Connection closed on " + username);
            }
        }
        catch (SQLException e)
        {
            throw new BooksDBException(e);
        }
    }
}
