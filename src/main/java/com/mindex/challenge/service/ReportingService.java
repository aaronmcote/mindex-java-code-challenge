package com.mindex.challenge.service;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportingService {

    private static final Logger LOG = LoggerFactory.getLogger(ReportingService.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    // Takes an employee ID and creates and returns a ReportingStructure
    // object for that employee if they exist
    public ReportingStructure getReport(String id) {
        LOG.debug("Getting reports for employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        //TODO compute number of reports for this employee
        int numberOfReports = 0;

        ReportingStructure report = new ReportingStructure(employee, numberOfReports);

        return report;
    }

}
