package com.mindex.challenge.service;

import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Compensation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class CompensationService {
    
    private static final Logger LOG = LoggerFactory.getLogger(CompensationService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private CompensationRepository compensationRepository;

    public Compensation create(Compensation comp) {
        LOG.debug("Creating compensation [{}]", comp);

        // Find the associated employee from the repository
        String employeeId = comp.getEmployee().getEmployeeId();
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        // Check this employee exists
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }

        // Store this employee into the compensation data
        comp.setEmployee(employee);

        // Store compensation data in repository
        compensationRepository.insert(comp);

        return comp;
    }

    public Compensation read(String id) {
        LOG.debug("Reading compensation for employee with id [{}]", id);

        // Find the employee using their ID
        Employee employee = employeeRepository.findByEmployeeId(id);

        // Check this employee exists
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        // Find the compensation associated with that employee
        Compensation comp = compensationRepository.findByEmployee(employee);

        // Check this compensation exsists
        if (comp == null) {
            throw new RuntimeException("Compensation does not exist for employee: " + id);
        }

        return comp;
    }
    
}
