package com.example.demo.repositories;

import com.example.demo.models.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
//    @Override
    List<LeaveRequest> findLeaveRequestByRequestStatus(String requestStatus);
    List<LeaveRequest> findLeaveRequestByEmployee_EmployeeId(long id);
}
