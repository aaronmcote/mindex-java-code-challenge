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

    // Recursive function to count all employees that report to this emplyee
    // including all subsequent reports.
    // Assumption: reports form a directed acyclic graph (there are not loops)
    private int countReports(Employee employee){
        int count = 0;

        // Check that this emplyee has any direct reports
        if ( employee.getDirectReports() != null){

            // Count each direct report and recursively count all their reports
            for( Employee directReport : employee.getDirectReports() ){
                
                String id = directReport.getEmployeeId();

                // Get the employee information for this direct report
                directReport = employeeRepository.findByEmployeeId(id);

                if (directReport == null) {
                    throw new RuntimeException("Invalid employeeId: " + id);
                }

                // Recursively count sub reports and add them to the count
                // plus one to account for this direct report
                count += countReports(directReport) + 1;
            }
        }

        return count;
    }

    // Takes an employee ID and creates and returns a ReportingStructure
    // object for that employee if they exist
    public ReportingStructure getReport(String id) {
        LOG.debug("Getting reports for employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        // Compute number of reports for this employee
        int numberOfReports = countReports(employee);

        ReportingStructure report = new ReportingStructure(employee, numberOfReports);

        return report;
    }

}
