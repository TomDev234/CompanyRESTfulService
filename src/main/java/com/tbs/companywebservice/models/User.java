/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice.models;

/**
 *
 * @author tom
 */
public class User {
  int id;
  String userName;
  String email;
  String passWord;
  Boolean activated;
  public User(int id, String userName, String email, String passWord, Boolean activated) {
    this.id = id;
    this.userName = userName;
    this.email = email;
    this.passWord = passWord;
    this.activated = activated;
  }
  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPassWord() { return passWord; }
  public void setPassWord(String passWord) { this.passWord = passWord; }
  public Boolean getActivated() { return activated; }
  public void setActivated(Boolean activated) { this.activated = activated; }
}
