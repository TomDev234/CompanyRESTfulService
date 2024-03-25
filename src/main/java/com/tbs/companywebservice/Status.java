/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice;

/**
 *
 * @author tom
 */
public class Status {
  public String message;
  public int returnCode;
  
  public Status() {
    this.message = "";
    this.returnCode = -1;
  }
  
  public Status(String message, int returnCode) {
    this.message = message;
    this.returnCode = returnCode;
  }
}
