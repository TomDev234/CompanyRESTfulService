/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice.models;

/**
 *
 * @author tom
 */
public class LoginRequest {
  String userName;
  String email;
  public LoginRequest(String userName, String email) {
    this.userName = userName;
    this.email = email;
  }
  public String getUserName() { return userName; }
  public void setUserName(String userName) { this.userName = userName; }
  public String getEmail() { return email; }
  public void setEmail(String email) { this.email = email; }
}
