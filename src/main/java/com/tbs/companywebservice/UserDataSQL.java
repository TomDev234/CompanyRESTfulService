/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice;

import com.tbs.companywebservice.models.User;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tom
 */
public class UserDataSQL {
  final static String URI = "jdbc:mysql://localhost:3306/users?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
  final static String LOGIN = "root";
  final static String PASSWORD = "root";

  // create user
  
  public int createUser(User user) {
    int returnCode = ErrorCodes.BAD_REQUEST;
    if (selectColumnFromTable("userName", "users", user.getUserName()) != 0) {
      System.out.println("User exists");
      returnCode = ErrorCodes.CONFLICT;
    }
    else {
      try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
        String sqlCommand = "INSERT IGNORE INTO users (username, email, password) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sqlCommand);
        statement.setString(1, user.getUserName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassWord());
        int rowsInserted = statement.executeUpdate();
        if(rowsInserted > 0) {
          System.out.println("User inserted " + rowsInserted);
          returnCode = ErrorCodes.CREATED;
        }
      }
      catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
    return returnCode;
  }
  
  public User readUser(String userName) {
    User user = null;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT * FROM users WHERE username = ?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setString(1, userName);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String email = resultSet.getString("email");
        String passWord = resultSet.getString("password");
        Boolean activated = resultSet.getBoolean("activated");
        user = new User(id, userName, email, passWord, activated);
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return user;
  }
  
  public List<User> readAllUsers() {
    List<User> users = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT * FROM users";
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sqlCommand);
      while (resultSet.next()) {
        int id = resultSet.getInt("id");
        String userName = resultSet.getString("username");
        String email = resultSet.getString("email");
        String passWord = resultSet.getString("password");
        Boolean activated = resultSet.getBoolean("activated");
        User user = new User(id, userName, email, passWord, activated);
        users.add(user);
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return users;
  }
  
  public int updateUser(User user) {
    int returnCode = ErrorCodes.UNAUTHORIZED;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "UPDATE users SET email=?, password=?, activated=? WHERE userName=?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setString(1, user.getEmail());
      statement.setString(2, user.getPassWord());
      statement.setString(3, user.getUserName());
      statement.setBoolean(4, user.getActivated());
      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated == 0) {
        returnCode = ErrorCodes.NOT_FOUND;
      }
      else { 
        System.out.println("Users updated " + rowsUpdated);
        returnCode = ErrorCodes.OK;
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return returnCode;
  }
  
  public int deleteUser(User user) {
    int returnCode = ErrorCodes.UNAUTHORIZED;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "DELETE FROM users WHERE userName=?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setString(1, user.getUserName());
      int rowsDeleted = statement.executeUpdate();
      if(rowsDeleted == 0)
        returnCode = ErrorCodes.NOT_FOUND;
      else {
        System.out.println("Users deleted " + rowsDeleted);
        returnCode = ErrorCodes.NO_CONTENT;
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return returnCode;
  }
   
  private static int selectColumnFromTable(String column, String table, String value) {
    int count = 0;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT ? FROM ? WHERE ? = ?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setString(1, column);
      statement.setString(2, table);
      statement.setString(3, column);
      statement.setString(4, value);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) count++;
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return count;
  }
  
  public static void main(String[] args) {
    UserDataSQL userData = new UserDataSQL();
    List<User> users = userData.readAllUsers();
    System.out.println("Users:" + users);
  }
}
