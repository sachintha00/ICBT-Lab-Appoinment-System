package com.icbt.abc.repositories;


import com.icbt.abc.entities.Reports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportRepository extends JpaRepository<Reports, Integer> {
}
