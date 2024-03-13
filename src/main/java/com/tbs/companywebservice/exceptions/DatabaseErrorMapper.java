/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice.exceptions;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

/**
 *
 * @author tom
 */
@Provider
public class DatabaseErrorMapper implements ExceptionMapper<DatabaseErrorException> {
  @Override
  public Response toResponse(DatabaseErrorException exception) {
    return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(exception.getMessage()).build();
  }
}
