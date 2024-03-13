/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice;

import com.tbs.companywebservice.models.Department;
import com.tbs.companywebservice.models.Employee;
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
public class CompanyDataSQL {
  private static CompanyDataSQL instance;
  final static String DATABASE = "jdbc:mysql://localhost:3306";
  final static String URI = "jdbc:mysql://localhost:3306/Company?useTimeZone=true&serverTimezone=UTC&autoReconnect=true&useSSL=false";
  final static String LOGIN = "root";
  final static String PASSWORD = "root";

  public synchronized static CompanyDataSQL getInstance() {
    if (instance == null) {
      instance = new CompanyDataSQL();
    }
    return instance;
  }
  
  public static void createDatabase() {
    System.out.println("Create Database");
    try(Connection connection = DriverManager.getConnection(DATABASE, LOGIN, PASSWORD);
      Statement statement = connection.createStatement()) {
      String sqlCommand = "CREATE DATABASE IF NOT EXISTS Company";
      statement.executeUpdate(sqlCommand);
      System.out.println("Company Database created");
    } 
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
  
  public static void createTables() {
    System.out.println("Create Tables");
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      Statement statement = connection.createStatement();
      String sqlCommand = "CREATE TABLE IF NOT EXISTS Departments (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), location VARCHAR(100))";
      statement.execute(sqlCommand);
      sqlCommand = "CREATE TABLE IF NOT EXISTS Employees (id INT PRIMARY KEY AUTO_INCREMENT, "
      + "name VARCHAR(45), extension INT, email VARCHAR(45), startDate CHAR(10), departmentId INT, FOREIGN KEY (departmentId) REFERENCES Departments(id))";
      statement.execute(sqlCommand);
      System.out.println("Tables created");
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
  
  // RESTful API /departments
  
  public boolean createDepartment(Department department) {
    boolean result = false;
    if (selectColumnFromTable("name", "Departments", department.getName()) != 0) {
      System.out.println("Department exists");
    }
    else {
      try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
        String sqlCommand = "INSERT IGNORE INTO Departments (name, location) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sqlCommand);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        int rowsInserted = statement.executeUpdate();
        if(rowsInserted > 0) {
          System.out.println("Department inserted " + rowsInserted);
          result = true;
        }
      }
      catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
    return result;
  }
  
  public List<Department> readAllDepartments() {
    List<Department> departments = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT * FROM Departments";
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sqlCommand);
      while (resultSet.next()) {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String location = resultSet.getString("location");
        Department department = new Department(id, name, location);
        departments.add(department);
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return departments;
  }
  
  public void bulkUpdateDepartments(List<Department> departments) {
    try (Connection connection = DriverManager.getConnection(URI, "root", "root")) {
      for(Department department : departments) {      
        String sqlCommand = "UPDATE Departments SET name=?, location=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sqlCommand);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setInt(3, department.getId());
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) System.out.println("Departments updated " + rowsUpdated);
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
  
  public void deleteAllDepartments() { 
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "DELETE FROM Departments";
      Statement statement = connection.createStatement();
      statement.execute(sqlCommand);
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }

  // RESTful API /departments/ID
  
  public Department readDepartment(int id) {
    Department department = null;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT * FROM Departments WHERE id = ?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        String name = resultSet.getString("name");
        String location = resultSet.getString("location");
        department = new Department(id, name, location);
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return department;
  }
  
  public void updateDepartment(int id, Department department) {
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "UPDATE Departments SET name=?, location=? WHERE id=?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setString(1, department.getName());
      statement.setString(2, department.getLocation());
      statement.setInt(3, id);
      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated > 0) System.out.println("Departments updated " + rowsUpdated);
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
  
  public void deleteDepartment(int id) {
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "DELETE FROM Departments WHERE id=?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setInt(1, id);
      int rowsDeleted = statement.executeUpdate();
      if(rowsDeleted > 0) System.out.println("Departments deleted " + rowsDeleted);
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
  
  // RESTful API /employees
          
  public boolean createEmployee(Employee employee) {
    boolean result = false;
    if (selectColumnFromTable("name", "Employees", employee.getName()) != 0) {
      System.out.println("Employee exists");
    }
    else {
      try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
        String sqlCommand = "INSERT IGNORE INTO Employees (name, extension, email, startDate, departmentId) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sqlCommand);
        statement.setString(1, employee.getName());
        statement.setInt(2, employee.getExtension());
        statement.setString(3, employee.getEmail());
        statement.setString(4, employee.getStartDate());
        statement.setInt(5, employee.getDepartmentId());
        int rowsInserted = statement.executeUpdate();
        if(rowsInserted > 0) {
          System.out.println("Employee inserted " + rowsInserted);
          result = true;
        }
      } 
      catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
    return result;
  }
  
  public List<Employee> readAllEmployees() {
    List<Employee> employees = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT * FROM Employees";
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sqlCommand);
      int count = 0;
      while (resultSet.next()) {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        int extension = resultSet.getInt("extension");
        String email = resultSet.getString("email");
        String startDate = resultSet.getString("startDate");
        int departmentId = resultSet.getInt("departmentId");
        Employee employee = new Employee(id, name, extension, email, startDate, departmentId);
        employees.add(employee);
        count++;
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return employees;
  }
  
  public void bulkUpdateEmployees(List<Employee> employees) {
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      for(Employee employee : employees) {
        String sqlCommand = "UPDATE Employees SET name=?, extension=?, email=?, startDate=?, departmentId=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sqlCommand);        
        statement.setString(1, employee.getName());
        statement.setInt(2, employee.getExtension());
        statement.setString(3, employee.getEmail());
        statement.setString(4, employee.getStartDate());
        statement.setInt(5, employee.getDepartmentId());
        statement.setInt(6, employee.getId());
        int rowsUpdated = statement.executeUpdate();
        if (rowsUpdated > 0) System.out.println("Employees updated " + rowsUpdated);
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
     
  public void deleteAllEmployees() {
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "DELETE FROM Employees";
      Statement statement = connection.createStatement();
      statement.execute(sqlCommand);
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
  
  // RESTful API /employees/ID
  
  public Employee readEmployee(int id) {
    Employee employee = null;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT * FROM Employees WHERE id = ?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        String name = resultSet.getString("name");
        int extension = resultSet.getInt("extension");
        String email = resultSet.getString("email");
        String startDate = resultSet.getString("startDate");
        int departmentId = resultSet.getInt("departmentId");
        employee = new Employee(id, name, extension, email, startDate, departmentId);
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }    
    return employee;
  }
  
  public void updateEmployee(int id, Employee employee) { 
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "UPDATE Employees SET name=?, extension=?, email=?, startDate=?, departmentId=? WHERE id=?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setString(1, employee.getName());
      statement.setInt(2, employee.getExtension());
      statement.setString(3, employee.getEmail());
      statement.setString(4, employee.getStartDate());
      statement.setInt(5, employee.getDepartmentId());
      int rowsUpdated = statement.executeUpdate();
      if (rowsUpdated > 0) System.out.println("Employee updated " + rowsUpdated);
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
  
  public void deleteEmployee(int id) {
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "DELETE FROM Employees WHERE id=?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setInt(1, id);
      int rowsDeleted = statement.executeUpdate();
      if(rowsDeleted > 0) System.out.println("Employees deleted " + rowsDeleted);
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
  
  // RESTful API /departments/ID/employees
  
  public List<Employee> readEmployeesFromDepartment(int departmentId) {
    List<Employee> employees = new ArrayList<>();
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT * FROM `Employees` WHERE departmentId = ?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setInt(1, departmentId);
      ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        Integer id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        Integer extension = resultSet.getInt("extension");
        String email = resultSet.getString("email");
        String startDate = resultSet.getString("startDate");
        Employee employee = new Employee(id, name, extension, email, startDate, departmentId);
        employees.add(employee);
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }    
    return employees;
  }
  
  public void deleteAllEmployeesFromDepartment(int departmentId) {
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "DELETE FROM Employees WHERE departmentId = ?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setInt(1, departmentId);
      int rowsDeleted = statement.executeUpdate();
      if(rowsDeleted > 0) System.out.println("Employees deleted " + rowsDeleted);
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
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
    CompanyDataSQL companyData = new CompanyDataSQL();
    List<Department> departments = companyData.readAllDepartments();
    System.out.println("Departments:" + departments);
  }
}
