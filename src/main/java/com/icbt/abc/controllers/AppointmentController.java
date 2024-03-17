package com.icbt.abc.controllers;

import com.icbt.abc.dtos.AppointmentRequestResponse;
import com.icbt.abc.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/appointment")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/schedule")
    public ResponseEntity<AppointmentRequestResponse> scheduleAppointment(@RequestBody AppointmentRequestResponse scheduleRequest){
        return ResponseEntity.ok(appointmentService.scheduleAppointment(scheduleRequest));
    }
}
