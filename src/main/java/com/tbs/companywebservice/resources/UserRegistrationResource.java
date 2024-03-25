/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice.resources;

import com.tbs.companywebservice.UserDataSQL;
import com.tbs.companywebservice.models.RegistrationRequest;
import com.tbs.companywebservice.models.User;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author tom
 */
@Path("company")
public class UserRegistrationResource {
//  UserDataSQL userData = new UserDataSQL();    
  
  // Register User: POST /users/register
  // Response:
  // 201 Created if the user is successfully registered.
  // 400 Bad Request if the request body is invalid or missing.
  // 409 Conflict if the username or email already exists.

//  @POST
//  @Path("users/register")
//  @Consumes(MediaType.APPLICATION_JSON)
//  public Response registerUser(RegistrationRequest registrationRequest) {
//    if(registrationRequest == null) return Response.status(HTTPCodes.BAD_REQUEST).build();
//    else {
//      User user = new User(0, registrationRequest.getUserName(), registrationRequest.getEmail(), registrationRequest.getPassWord(), false);
//      int returnCode = userData.createUser(user);
//      return Response.status(returnCode).build();
//    }
//  }

  // Login User: POST /users/login
  // Response:
  // 200 OK with a JWT (JSON Web Token) if login is successful.
  // 400 Bad Request if the request body is invalid or missing.
  // 401 Unauthorized if the credentials are invalid.

//  @POST
//  @Path("users/login")
//  @Consumes(MediaType.APPLICATION_JSON)
//  public Response loginUser(User user) {
//    int returnCode;
//    if(user == null) returnCode = HTTPCodes.BAD_REQUEST;
//    User storedUser = userData.readUser(user.getUserName());
//    if(storedUser.getPassWord() != user.getPassWord() || !storedUser.getActivated()) returnCode = HTTPCodes.UNAUTHORIZED;
//    else returnCode = HTTPCodes.OK;
//    return Response.status(returnCode).build();
//  }

  // Get User Profile GET /users/{userName}
  // Response:
  // 200 OK with the user profile JSON.
  // 400 Bad Request if the request body is invalid or missing.
  // 401 Unauthorized if the JWT is missing or invalid.
  // 404 Not Found if the user is not found.
      
//  @GET
//  @Path("users/{userName}")
//  @Produces(MediaType.APPLICATION_JSON)
//  public Response readUser(@PathParam("userName") String userName) {
//    if(userName == null) return Response.status(HTTPCodes.BAD_REQUEST).build(); 
//    User user = userData.readUser(userName);
//    if(user == null) return Response.status(HTTPCodes.NOT_FOUND).build();
//    return Response.ok(user).build();
//  }

  // Update User Profile: PUT /users/{userName}
  // Response:
  // 200 OK with the updated user profile JSON.
  // 400 Bad Request if the request body is invalid or missing.
  // 401 Unauthorized if the JWT is missing or invalid.
  // 404 Not Found if the user is not found.
  // 409 Conflict if the new username or email already exists.
   
//  @PUT
//  @Path("/users/{userName}")
//  @Consumes(MediaType.APPLICATION_JSON)
//  public Response updateUser(@PathParam("userName") String userName, User user) {
//    userName.updateUser(id, department);
//    return Response.ok("Department updated").build();
//  }
  
  // Delete User Account: DELETE /users/{userId}
  // Response:
  // 204 No Content if the user account is successfully deleted.
  // 401 Unauthorized if the JWT is missing or invalid.
  // 404 Not Found if the user is not found.

  // Confirm Email: GET /users/confirm-email?token={confirmationToken}
  // Response:
  // 200 OK if the email is successfully confirmed.
  // 400 Bad Request if the confirmation token is missing or invalid.

}
