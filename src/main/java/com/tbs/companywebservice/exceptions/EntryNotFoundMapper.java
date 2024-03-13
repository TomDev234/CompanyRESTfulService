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
public class EntryNotFoundMapper implements ExceptionMapper<EntryNotFoundException> {
  @Override
  public Response toResponse(EntryNotFoundException exception) {
    return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessage()).build();
  }
}
