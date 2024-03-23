package com.icbt.abc.services;

import com.icbt.abc.entities.LabAppointment;
import com.icbt.abc.entities.Reports;
import com.icbt.abc.repositories.AppointmentRepositories;
import com.icbt.abc.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabReportService {
    @Autowired
    private AppointmentRepositories appointmentRepositories;
    @Autowired
    private ReportRepository reportRepository;

    public Optional<LabAppointment> getDetailsFromUserId(Integer appointmentId){
        Optional<LabAppointment> labAppointment = appointmentRepositories.findById(appointmentId);
        return labAppointment;
    }

    public void storeReport(String appointmentId, String patientId, String patientDescription){
        Reports reports = new Reports();

        reports.setUserId(Integer.parseInt(patientId));
        reports.setAppointmentId(Integer.parseInt(appointmentId));
        reports.setReportPath("D:\\Projects\\ICBT\\ICBT-Lab-Appoinment-System\\src\\main\\resources\\reports\\"+patientId);
        reports.setDescription(patientDescription);

        reportRepository.save(reports);
    }
}
