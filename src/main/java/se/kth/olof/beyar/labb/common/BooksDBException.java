package se.kth.olof.beyar.labb.common;

import java.sql.SQLException;

public class BooksDBException extends RuntimeException
{
    public BooksDBException(SQLException e) {
        super(e.getMessage());

        if (e.getErrorCode() == 0)
        {
            System.out.println("Error: You entered wrong credentials");
        }
    }
}