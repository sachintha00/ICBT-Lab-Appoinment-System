package com.icbt.abc.repositories;

import com.icbt.abc.entities.LabAppointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepositories extends JpaRepository<LabAppointment, Integer> {
}
