package com.com.springboot.batch.writeindb.model;

public class EmployeeDTO {
    private String firstName;
    private String lastName;
    private String companyName;
   

    public EmployeeDTO() {
    }

    public EmployeeDTO(String firstName, String lastName, String companyName ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.companyName = companyName;
       
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

   
    @Override
    public String toString() {
        return "EmployeeDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", companyName='" + companyName + '\'' +
                '}';
    }
}
