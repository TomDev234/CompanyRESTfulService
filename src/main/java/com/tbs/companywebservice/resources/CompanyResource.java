/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice.resources;

import com.tbs.companywebservice.models.Employee;
import com.tbs.companywebservice.models.Department;
import com.tbs.companywebservice.CompanyDataSQL;
import com.tbs.companywebservice.HTTPCodes;
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
  private static int departmentCounter = 1;
  private static int employeeCounter = 1;
  
  // create a department
  
  @POST
  @Path("departments")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response createDepartment(Department department) {
    department.setId(departmentCounter++);
    if(!companyData.createDepartment(department)) return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    else return Response.status(HTTPCodes.CREATED).build();
  }
  
  // read all departments
  
  @GET
  @Path("departments")
  @Produces(MediaType.APPLICATION_JSON)
  public Response readDepartments() {
    List<Department> departments = companyData.readAllDepartments();
    return Response.ok(departments).build();
  }
  
  // bulk update departments
  
  @PUT
  @Path("departments")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateDepartments(List<Department> departments) {
    companyData.bulkUpdateDepartments(departments);
    return Response.ok("Departments updated").build();
  }
  
  // delete all departments
  
  @DELETE
  @Path("departments")
  public Response deleteDepartments() {
    companyData.deleteAllDepartments();
    return Response.ok().build();
  }
  
  // method not allowed 405
  
  @POST
  @Path("departments/{id}")
  public Response createDepartment(@PathParam("id") int id) {
    return Response.status(HTTPCodes.METHOD_NOT_ALLOWED).build();
  }
  
  // return department with ID
  
  @GET
  @Path("departments/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response readDepartment(@PathParam("id") int id) {
    Department department = companyData.readDepartment(id);
    return Response.ok(department).build();
  }
 
  // update department with ID
  
  @PUT
  @Path("departments/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateDepartment(@PathParam("id") int id, Department department) {
    companyData.updateDepartment(id, department);
    return Response.ok().build();
  }
  
  // ID delete department with ID

  @DELETE
  @Path("department/{id}")
  public Response deleteDepartment(@PathParam("id") int id) {
    companyData.deleteDepartment(id);
    return Response.ok().build();
  }
  
  // create an employee
  
  @POST
  @Path("employees")
  @Consumes(MediaType.APPLICATION_JSON)
   public Response createEmployee(Employee employee) {
    employee.setId(employeeCounter++);
    companyData.createEmployee(employee);
    return Response.status(Response.Status.CREATED).build();
  }

  // return all employees
  
  @GET
  @Path("employees")
  @Produces(MediaType.APPLICATION_JSON)
  public Response readAllEmployees() {
    List<Employee> employees = companyData.readAllEmployees();
    return Response.ok(employees).build();
  }
  
  // bulk update employees
  
  @PUT
  @Path("employees")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateEmployees(@PathParam("id") int id, List<Employee> employees) {
    companyData.bulkUpdateEmployees(employees);
    return Response.ok().build();
  }
  
  // delete all employees

  @DELETE
  @Path("employees")
  public Response deleteAllEmployees() {
    companyData.deleteAllEmployees();
    return Response.ok().build();
  }
  
  // method not allowed 405

  @POST
  @Path("employees/{id}")
  public Response createEmployeeWithId(Employee employee, @PathParam("id") int id) {
    return Response.status(HTTPCodes.METHOD_NOT_ALLOWED).build();
  }

  // return employee with ID
  
  @GET
  @Path("employees/{id}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response readEmployee(@PathParam("id") int id) {
    Employee employee = companyData.readEmployee(id);
    return Response.ok(employee).build();
  }
  
  // update employee with ID
  
  @PUT
  @Path("employees/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  public Response updateEmployee(@PathParam("id") int id, Employee employee) {
    companyData.updateEmployee(id, employee);
    return Response.ok().build();
  }

  // delete employee with ID

  @DELETE
  @Path("employees/{id}")
  public Response deleteEmployee(@PathParam("id") int id) {
    companyData.deleteEmployee(id);
    return Response.ok().build();
  }
    
  // method not allowed 405
  
  @POST
  @Path("departments/{id}/employees")
  public Response createEmployeeFromDepartment(Employee employee) {
    return Response.status(HTTPCodes.METHOD_NOT_ALLOWED).build();
  }
  
  // return employees from department with ID
  
  @GET
  @Path("departments/{id}/employees")
  @Produces(MediaType.APPLICATION_JSON)
  public Response readEmployeesFromDepartment(@PathParam("id") int id) {
    return Response.ok(companyData.readEmployeesFromDepartment(id)).build();
  }
  
  // update employees from department with ID
  
  @PUT
  @Path("departments/{id}/employees")
  public Response updateEmployeesFromDepartment() {
    return Response.status(HTTPCodes.METHOD_NOT_ALLOWED).build();
  }
  
  // delete employees from department with ID

  @DELETE
  @Path("departments/{id}/employees")
  public Response deleteEmployeesFromDepartment(@PathParam("id") int id) {
    companyData.deleteAllEmployeesFromDepartment(id);
    return Response.ok().build();
  }
}
