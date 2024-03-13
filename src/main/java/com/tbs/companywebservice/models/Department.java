package com.tbs.companywebservice.models;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author tom
 */
public class Department {
  int id;
  String name;
  String location;
  
  public Department() {} // Required default constructor for JAXB
  
  public Department(int id, String name, String location) {
    this.id = id;
    this.name = name;
    this.location = location;
  }
  
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }
  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
  public String getLocation() { return location; }
  public void setLocation(String location) { this.location = location; }
  public String toString() { return "Department ID:" + id + " Name:" + name + " Location:" + location; }
}
