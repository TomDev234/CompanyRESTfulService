/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice.models;

/**
 *
 * @author tom
 */
public class RegistrationRequest {
  String userName;
  String email;
  String passWord;
  public RegistrationRequest(String userName, String email, String passWord) {
    this.userName = userName;
    this.email = email;
    this.passWord = passWord;
  }
  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
  public String getPassWord() { return passWord; }
  public void setPassWord(String passWord) { this.passWord = passWord; }
}
