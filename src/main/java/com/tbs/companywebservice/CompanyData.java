/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tbs.companywebservice;

import com.tbs.companywebservice.models.Department;
import com.tbs.companywebservice.models.Employee;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tom
 */
public class CompanyData {
  private static CompanyData instance;
  static List<Department> departments = new ArrayList<>();
  static List<Employee> employees = new ArrayList<>();
  
  public synchronized static CompanyData getInstance() {
    if (instance == null) {
      instance = new CompanyData();
      departments.add(new Department(0,"Marketing","Elmo Street 21"));
      departments.add(new Department(1,"Sales","Evanston Heights"));
      departments.add(new Department(2,"Processing","New York"));
      employees.add(new Employee(0,"Eabha Biddel",430,"eabha.biddel@company.com","01.01.1979",0));
      employees.add(new Employee(1,"Kishant Calloway",431,"kishant.calloway@company.com","02.01.1979",1));
      employees.add(new Employee(2,"Ava Cofel",432,"ava.cofel@company.com","03.01.1979",2));
    }
    return instance;
  }

  // RESTful API /departments

  public boolean createDepartment(Department department) { return departments.add(department); }
  public List<Department> readAllDepartments() { return departments; }
  public void bulkUpdateDepartments(List<Department> newDepartments) { departments.addAll(newDepartments); }
  public void deleteAllDepartments() { departments.clear(); }
  public Department readDepartment(int id) { return departments.get(id); }
  public void updateDepartment(int id, Department department) { departments.set(id, department); }
  public void deleteDepartment(int id) { departments.remove(id); }

  // RESTful API /employees

  public boolean createEmployee(Employee employee) { return employees.add(employee); }
  public List<Employee> readAllEmployees() { return employees; }
  public void bulkUpdateEmployees(List<Employee> newEmployees) { employees.addAll(newEmployees); }
  public void deleteAllEmployees() { employees.clear(); }
    
  // RESTful API /employees/ID

  public Employee readEmployee(int id) { return employees.get(id); }
  public void updateEmployee(int id, Employee employee) { employees.set(id, employee); }
  public void deleteEmployee(int id) { employees.remove(id); }
    
  // RESTful API /departments/ID/employees

  public List<Employee> readEmployeesFromDepartment(int id) {
    List<Employee> employeeList = new ArrayList<>();
    for(Employee employee : employees)
      if(employee.getDepartmentId() == id)
        employeeList.add(employee);
    return employeeList;
  }
  
  public void deleteEmployeesFromDepartment(int departmentId) {
    List<Employee> employeesToRemove = new ArrayList<>();
    for(Employee employee : employees) {
      if(employee.getDepartmentId() == departmentId) employeesToRemove.add(employee);
    }
    employees.removeAll(employeesToRemove);
  }
}
