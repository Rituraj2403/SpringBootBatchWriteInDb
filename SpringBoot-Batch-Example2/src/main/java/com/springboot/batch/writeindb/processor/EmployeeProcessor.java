package com.springboot.batch.writeindb.processor;

import org.springframework.batch.item.ItemProcessor;

import com.com.springboot.batch.writeindb.model.Employee;
import com.com.springboot.batch.writeindb.model.EmployeeDTO;

public class EmployeeProcessor implements ItemProcessor<Employee, EmployeeDTO> {

    @Override
    public EmployeeDTO process(final Employee employee) throws Exception {
        System.out.println("Transforming Employee(s) to EmployeeDTO(s)..");
        final EmployeeDTO empployeeDto = new EmployeeDTO(employee.getFirstName(), employee.getLastName(),
                employee.getCompanyName());

        return empployeeDto;
    }

}
