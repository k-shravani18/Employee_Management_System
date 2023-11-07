package com.attendance_management_system.controller;

import com.attendance_management_system.model.AttendanceRecord;
import com.attendance_management_system.service.AttendanceRecordService;
import com.attendance_management_system.service.serviceImpl.AttendanceRecordServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AttendanceRecordController {

    @Autowired
    private AttendanceRecordServiceImpl attendanceRecordService;

    @GetMapping("/getAll")
    public ResponseEntity<List<AttendanceRecord>> getAllRecords() {
        List<AttendanceRecord> records = attendanceRecordService.getAllAttendanceRecords();
        return ResponseEntity.ok(records);
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<AttendanceRecord> getById(@PathVariable Long id) {
        Optional<AttendanceRecord> record = attendanceRecordService.getById(id);
        return record.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/createRecord")
    public ResponseEntity<AttendanceRecord> createRecord(@RequestBody AttendanceRecord attendanceRecord) {
        AttendanceRecord createdRecord = attendanceRecordService.createRecord(attendanceRecord);
        return ResponseEntity.ok(createdRecord);
    }

    @PutMapping("/updateRecord/{id}")
    public ResponseEntity<AttendanceRecord> updateRecord(@PathVariable Long id, @RequestBody AttendanceRecord updateRecord) {
        AttendanceRecord updatedRecord = attendanceRecordService.updateRecord(id, updateRecord);
        return ResponseEntity.ok(updatedRecord);
    }

    @DeleteMapping("/deleteRecord/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        attendanceRecordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
