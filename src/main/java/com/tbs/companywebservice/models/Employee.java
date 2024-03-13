package com.tbs.companywebservice.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tom
 */
public class Employee {
  int id;
  String name;
  int extension;
  String email;
  String startDate;
  int departmentId;
  
  public Employee() {} // Required default constructor for JAXB
  
  public Employee(int id, String name, int extension, String email, String startDate, int departmentId) {
    this.id = id;
    this.name = name;
    this.extension = extension;
    this.email = email;
    this.startDate = startDate;
    this.departmentId = departmentId;
  }
  
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public int getExtension() { return extension; }
  public void setExtension(int extension) { this.extension = extension; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getStartDate() { return startDate; }
  public void setStartDate(String startDate) { this.startDate = startDate; }
  public int getDepartmentId() { return departmentId; }
  public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }
  public String toString() { return "Employee ID:" + id + " Name:" + name + " Extension:" + extension + " Email:" + email + " Start Date:" + startDate + " DepartmentId:" + departmentId; }
}
