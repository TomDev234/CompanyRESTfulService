/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice.exceptions;

/**
 *
 * @author tom
 */
public class EntryAlreadyExistsException extends Exception {
  public EntryAlreadyExistsException() {
    super();
  }
  
  public EntryAlreadyExistsException(String message) {
    super(message);
  }
}
