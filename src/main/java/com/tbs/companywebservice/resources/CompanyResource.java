/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice.resources;

import com.tbs.companywebservice.CompanyDataSQL;
import com.tbs.companywebservice.ErrorCodes;
import com.tbs.companywebservice.models.Department;
import com.tbs.companywebservice.models.Employee;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.GET;
import java.util.List;

/**
 *
 * @author tom
 */
@Path("company")
public class CompanyResource {
  private CompanyDataSQL companyData = new CompanyDataSQL();
  
  // create a department
  
  @POST
  @Path("departments")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createDepartment(Department department) {
    int returnCode = companyData.createDepartment(department);
    if(returnCode == -1) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else if(returnCode == 0) return Response.status(ErrorCodes.CONFLICT).build();
    else return Response.status(ErrorCodes.CREATED).build();
  }
  
  // read all departments
  
  @GET
  @Path("departments")
  @Produces(MediaType.APPLICATION_JSON)
  public Response readDepartments() {
    List<Department> departments = companyData.readAllDepartments();
    if(departments == null) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else return Response.ok(departments).build();
  }
  
  // bulk update departments
  
  @PUT
  @Path("departments")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateDepartments(List<Department> departments) {
    int rowsUpdated = companyData.bulkUpdateDepartments(departments);
    if(rowsUpdated == -1) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else if(rowsUpdated < departments.size()) return Response.status(ErrorCodes.NOT_FOUND).build();
    else return Response.ok("Departments updated:" + rowsUpdated).build();
  }
  
  // delete all departments
  
  @DELETE
  @Path("departments")
  public Response deleteDepartments() {
    System.out.println("deleteDepartments");
    Boolean returnCode = companyData.deleteAllDepartments();
    if(!returnCode) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else return Response.status(ErrorCodes.NO_CONTENT).build();
  }
  
  // method not allowed 405
  
  @POST
  @Path("departments/{id}")
  public Response createDepartment(@PathParam("id") int id) {
    return Response.status(ErrorCodes.METHOD_NOT_ALLOWED).build();
  }
  
  // return department with ID
  
  @GET
  @Path("departments/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response readDepartment(@PathParam("id") int id) {
    Department department = companyData.readDepartment(id);
    if(department == null) return Response.status(ErrorCodes.NOT_FOUND).build();
    else return Response.ok(department).build();
  }
 
  // update department with ID
  
  @PUT
  @Path("departments/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateDepartment(@PathParam("id") int id, Department department) {
    int rowsUpdated = companyData.updateDepartment(id, department);
    if(rowsUpdated == -1) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else if (rowsUpdated == 0) return Response.status(ErrorCodes.NOT_FOUND).build();
    return Response.ok("Department updated").build();
  }
  
  // ID delete department with ID

  @DELETE
  @Path("departments/{id}")
  public Response deleteDepartment(@PathParam("id") int id) {
    int returnCode = -1;
    returnCode = companyData.deleteAllEmployeesFromDepartment(id);
    if(returnCode != -1) returnCode = companyData.deleteDepartment(id);
    if(returnCode == -1) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else if(returnCode == 0) return Response.status(ErrorCodes.NOT_FOUND).build();
    else return Response.status(ErrorCodes.NO_CONTENT).build();
  }
  
  // create an employee
  
  @POST
  @Path("employees")
  @Consumes(MediaType.APPLICATION_JSON)
   public Response createEmployee(Employee employee) {
    int returnCode = companyData.createEmployee(employee);
    if(returnCode == -1) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else if(returnCode == 0) return Response.status(ErrorCodes.CONFLICT).build();
    else return Response.status(ErrorCodes.OK).build();
  }

  // return all employees
  
  @GET
  @Path("employees")
  @Produces(MediaType.APPLICATION_JSON)
  public Response readAllEmployees() {
    List<Employee> employees = companyData.readAllEmployees();
    if(employees == null) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else return Response.ok(employees).build();
  }
  
  // bulk update employees
  
  @PUT
  @Path("employees")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateEmployees(List<Employee> employees) {
    int rowsUpdated = companyData.bulkUpdateEmployees(employees);
    if(rowsUpdated == -1) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else if(rowsUpdated < employees.size()) return Response.status(ErrorCodes.NOT_FOUND).build();
    return Response.ok("Employees updated " + rowsUpdated).build();
  }
  
  // delete all employees

  @DELETE
  @Path("employees")
  public Response deleteAllEmployees() {
    Boolean success = companyData.deleteAllEmployees();
    if(!success) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else return Response.status(ErrorCodes.NO_CONTENT,"Employees deleted").build();
  }
  
  // method not allowed 405

  @POST
  @Path("employees/{id}")
  public Response createEmployee(@PathParam("id") int id) {
    return Response.status(ErrorCodes.METHOD_NOT_ALLOWED).build();
  }

  // return employee with ID
  
  @GET
  @Path("employees/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response readEmployee(@PathParam("id") int id) {
    Employee employee = companyData.readEmployee(id);
    if(employee == null) return Response.status(ErrorCodes.NOT_FOUND).build();
    return Response.ok(employee).build();
  }
  
  // update employee with ID
  
  @PUT
  @Path("employees/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateEmployee(@PathParam("id") int id, Employee employee) {
    int returnCode = companyData.updateEmployee(id, employee);
    if(returnCode == -2) return Response.status(ErrorCodes.BAD_REQUEST).build();
    else if(returnCode == -1) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else if(returnCode == 0) return Response.status(ErrorCodes.NOT_FOUND).build();
    else return Response.ok("Employee updated").build();
  }

  // delete employee with ID

  @DELETE
  @Path("employees/{id}")
  public Response deleteEmployee(@PathParam("id") int id) {
    int rowsDeleted = companyData.deleteEmployee(id);
    if(rowsDeleted == -1) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR).build();
    else return Response.status(ErrorCodes.NO_CONTENT).build();
  }
    
  // method not allowed 405
  
  @POST
  @Path("departments/{id}/employees")
  public Response createEmployeeFromDepartment(@PathParam("id") int id, Employee employee) {
    return Response.status(ErrorCodes.METHOD_NOT_ALLOWED).build();
  }
  
  // return employees from department with ID
  
  @GET
  @Path("departments/{id}/employees")
  @Produces(MediaType.APPLICATION_JSON)
  public Response readEmployeesFromDepartment(@PathParam("id") int id) {
    List<Employee> employees = companyData.readEmployeesFromDepartment(id);
    if(employees == null) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR,"Database Error or Department does not exist.").build();
    else return Response.ok(employees).build();
  }
  
  // update employees from department with ID
  
  @PUT
  @Path("departments/{id}/employees")
  public Response updateEmployeesFromDepartment() {
    return Response.status(ErrorCodes.METHOD_NOT_ALLOWED).build();
  }
  
  // delete employees from department with ID

  @DELETE
  @Path("departments/{id}/employees")
  public Response deleteEmployeesFromDepartment(@PathParam("id") int id) {
    int rowsDeleted = companyData.deleteAllEmployeesFromDepartment(id);
    if(rowsDeleted == -1) return Response.status(ErrorCodes.INTERNAL_SERVER_ERROR,"Database Error").build();
    return Response.status(ErrorCodes.NO_CONTENT,"Employees deleted " + rowsDeleted).build();
  }
}
