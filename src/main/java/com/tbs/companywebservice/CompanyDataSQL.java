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
  
  public int createDepartment(Department department) {
    int rowsInserted = -1;
    if (selectColumnFromTable("name", "Departments", department.getName()) == 0) {
      try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
        String sqlCommand = "INSERT IGNORE INTO Departments (name, location) VALUES (?, ?)";
        PreparedStatement statement = connection.prepareStatement(sqlCommand);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        rowsInserted = statement.executeUpdate();
      }
      catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
    return rowsInserted;
  }
  
  public List<Department> readAllDepartments() {
    List<Department> departments = null;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT * FROM Departments";
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sqlCommand);
      departments = new ArrayList<Department>();
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
  
  public int bulkUpdateDepartments(List<Department> departments) {
    int rowsUpdated = -1;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      rowsUpdated = 0;
      for(Department department : departments) {      
        String sqlCommand = "UPDATE Departments SET name=?, location=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sqlCommand);
        statement.setString(1, department.getName());
        statement.setString(2, department.getLocation());
        statement.setInt(3, department.getId());
        rowsUpdated += statement.executeUpdate();
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return rowsUpdated;
  }
  
  public Boolean deleteAllDepartments() {
    Boolean result = false;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {   
      String sqlCommand = "DELETE FROM Employees";
      Statement statement = connection.createStatement();
      statement.execute(sqlCommand);      
      sqlCommand = "DELETE FROM Departments";
      statement = connection.createStatement();
      statement.execute(sqlCommand);
      result = true;
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return result;
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
  
  public int updateDepartment(int id, Department department) {
    int rowsUpdated = -1;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "UPDATE Departments SET name=?, location=? WHERE id=?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setString(1, department.getName());
      statement.setString(2, department.getLocation());
      statement.setInt(3, id);
      rowsUpdated = statement.executeUpdate();
      if (rowsUpdated > 0) System.out.println("Departments updated " + rowsUpdated);
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return rowsUpdated;
  }
  
  public int deleteDepartment(int id) {
    int rowsDeleted = -1;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "DELETE FROM Departments WHERE id=?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setInt(1, id);
      rowsDeleted = statement.executeUpdate();
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return rowsDeleted;
  }
  
  // RESTful API /employees
          
  public int createEmployee(Employee employee) {
    int rowsInserted = -1;
    if ((employeeExists(employee) > 0) ||
        (departmentExists(employee.getDepartmentId()) < 1)) {
      rowsInserted = 0;
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
        rowsInserted = statement.executeUpdate();
      }
      catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
    return rowsInserted;
  }
  
  public List<Employee> readAllEmployees() {
    List<Employee> employees = null;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT * FROM Employees";
      Statement statement = connection.createStatement();
      ResultSet resultSet = statement.executeQuery(sqlCommand);
      int count = 0;
      employees = new ArrayList<>();
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
  
  public int bulkUpdateEmployees(List<Employee> employees) {
    int rowsUpdated = -1;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      rowsUpdated = 0;
      for(Employee employee : employees) {
        String sqlCommand = "UPDATE Employees SET name=?, extension=?, email=?, startDate=?, departmentId=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sqlCommand);        
        statement.setString(1, employee.getName());
        statement.setInt(2, employee.getExtension());
        statement.setString(3, employee.getEmail());
        statement.setString(4, employee.getStartDate());
        statement.setInt(5, employee.getDepartmentId());
        statement.setInt(6, employee.getId());
        rowsUpdated += statement.executeUpdate();
      }
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    System.out.println("bulkUpdateEmployees " + rowsUpdated);
    return rowsUpdated;
  }
     
  public Boolean deleteAllEmployees() {
    Boolean result = false;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "DELETE FROM Employees";
      Statement statement = connection.createStatement();
      statement.execute(sqlCommand);
      result = true;
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return result;
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
     
  public int updateEmployee(int id, Employee employee) {
    int rowsUpdated = -1;
    if (id != employee.getId()) {
      rowsUpdated = -2;
    }
    else {
      try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
        rowsUpdated = 0;
        String sqlCommand = "UPDATE Employees SET name=?, extension=?, email=?, startDate=?, departmentId=? WHERE id=?";
        PreparedStatement statement = connection.prepareStatement(sqlCommand);
        statement.setString(1, employee.getName());
        statement.setInt(2, employee.getExtension());
        statement.setString(3, employee.getEmail());
        statement.setString(4, employee.getStartDate());
        statement.setInt(5, employee.getDepartmentId());
        statement.setInt(6, id);
        rowsUpdated = statement.executeUpdate();
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
    return rowsUpdated;
  }

  public int deleteEmployee(int id) {
    int rowsDeleted = -1;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "DELETE FROM Employees WHERE id=?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setInt(1, id);
      rowsDeleted = statement.executeUpdate();
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return rowsDeleted;
  }
  
  // RESTful API /departments/ID/employees
  
  public List<Employee> readEmployeesFromDepartment(int departmentId) {
    List<Employee> employees = null;
    if (departmentExists(departmentId) > 0) {
      try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
        String sqlCommand = "SELECT * FROM `Employees` WHERE departmentId = ?";
        PreparedStatement statement = connection.prepareStatement(sqlCommand);
        statement.setInt(1, departmentId);
        ResultSet resultSet = statement.executeQuery();
        employees = new ArrayList<>();
        while (resultSet.next()) {
          Integer id = resultSet.getInt("id");
          String name = resultSet.getString("name");
          Integer extension = resultSet.getInt("extension");
          String email = resultSet.getString("email");
          String startDate = resultSet.getString("startDate");
          Employee employee = new Employee(id, name, extension, email, startDate, departmentId);
          employees.add(employee);
        }
      } catch (SQLException e) {
        System.err.println(e.getMessage());
      }
    }
    return employees;
  }
  
  public int deleteAllEmployeesFromDepartment(int departmentId) {
    int rowsDeleted = -1;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "DELETE FROM Employees WHERE departmentId = ?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setInt(1, departmentId);
      rowsDeleted = statement.executeUpdate();
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return rowsDeleted;
  }
  
  private static int selectColumnFromTable(String column, String table, String value) {
    int count = -1;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT ? FROM ? WHERE ? = ?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setString(1, column);
      statement.setString(2, table);
      statement.setString(3, column);
      statement.setString(4, value);
      ResultSet resultSet = statement.executeQuery();
      count = 0;
      while (resultSet.next()) count++;
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }    
    return count;
  }
  
  private static int departmentExists(int id) {
    int count = -1;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT name FROM Departments WHERE id = ?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setInt(1, id);
      ResultSet resultSet = statement.executeQuery();
      count = 0;
      while (resultSet.next()) count++;
    }
    catch (SQLException e) {
      System.err.println(e.getMessage());
    }
    return count;
  }
  
  private static int employeeExists(Employee employee) {
    int count = -1;
    try (Connection connection = DriverManager.getConnection(URI, LOGIN, PASSWORD)) {
      String sqlCommand = "SELECT name, email FROM Employees WHERE name = ? OR email = ?";
      PreparedStatement statement = connection.prepareStatement(sqlCommand);
      statement.setString(1, employee.getName());
      statement.setString(2, employee.getEmail());
      ResultSet resultSet = statement.executeQuery();
      count = 0;
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
    System.out.println(departments);
    List<Employee> employees = companyData.readAllEmployees();
    System.out.println(employees);
  }
}
