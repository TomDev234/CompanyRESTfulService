/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice.exceptions;

/**
 *
 * @author tom
 */
public class DatabaseErrorException extends Exception {
  public DatabaseErrorException() {
    super();
  }
  
  public DatabaseErrorException(String message) {
    super(message);
  }
}
