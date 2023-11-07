package com.attendance_management_system.service.serviceImpl;

import com.attendance_management_system.model.AttendanceRecord;
import com.attendance_management_system.repository.AttendanceRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceRecordServiceImpl {
    @Autowired
    private AttendanceRecordRepository attendanceRecordRepository;

    public List<AttendanceRecord> getAllAttendanceRecords(){
       return attendanceRecordRepository.findAll();
    }
    public Optional<AttendanceRecord> getById(Long id){
        return attendanceRecordRepository.findById(id);
    }
    public AttendanceRecord createRecord(AttendanceRecord attendanceRecord){
        return attendanceRecordRepository.save(attendanceRecord);
    }
    public AttendanceRecord updateRecord(Long id,AttendanceRecord updateRecord){
        Optional<AttendanceRecord> existingRecord=attendanceRecordRepository.findById(id);
        if (existingRecord.isPresent()) {
            AttendanceRecord attendanceRecord=existingRecord.get();
            attendanceRecord.setCheckInTime(updateRecord.getCheckInTime());
            attendanceRecord.setCheckOutTime(updateRecord.getCheckOutTime());
            attendanceRecord.setDate(updateRecord.getDate());
            attendanceRecord.setStatus(updateRecord.getStatus());
            return attendanceRecordRepository.save(updateRecord);
        }else {
//                throw new AttendanceNotFoundException();
            return null;
        }
    }
    public void deleteRecord(Long id){
        attendanceRecordRepository.deleteById(id);
    }
}
