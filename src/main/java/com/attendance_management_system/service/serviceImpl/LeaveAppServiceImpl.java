package com.attendance_management_system.service.serviceImpl;

import com.attendance_management_system.model.AttendanceRecord;
import com.attendance_management_system.model.LeaveApp;
import com.attendance_management_system.repository.LeaveAppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeaveAppServiceImpl {
    @Autowired
    private LeaveAppRepository leaveAppRepository;

    public List<LeaveApp> getAllLeaveApp(){
        return leaveAppRepository.findAll();
    }
    public Optional<LeaveApp> getById(Long id){
        return leaveAppRepository.findById(id);
    }
    public LeaveApp createLeaveApp(LeaveApp leaveApp){
        return leaveAppRepository.save(leaveApp);
    }
    public LeaveApp updateLeaveApp(Long id, LeaveApp updateLeaveApp){
        Optional<LeaveApp> existingLeaveApp=leaveAppRepository.findById(id);
        if (existingLeaveApp.isPresent()) {
            LeaveApp leaveApp=existingLeaveApp.get();
            leaveApp.setStartDate(updateLeaveApp.getStartDate());
            leaveApp.setEndDate(updateLeaveApp.getEndDate());
            leaveApp.setReason(updateLeaveApp.getReason());
            leaveApp.setStatus(updateLeaveApp.getStatus());
            return leaveAppRepository.save(updateLeaveApp);
        }else {
//                throw new AttendanceNotFoundException();
            return null;
        }
    }
    public void deleteLeaveApp(Long id){
        leaveAppRepository.deleteById(id);
    }
}
