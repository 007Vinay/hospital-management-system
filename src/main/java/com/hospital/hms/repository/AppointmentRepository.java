package com.hospital.hms.repository;

import com.hospital.hms.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByStatus(String status);
    List<Appointment> findByDoctorId(Long doctorId);
    List<Appointment> findByPatientId(Long patientId);
    List<Appointment> findByPatientUserUsername(String username);

    //Custom JPQL query to fetch appointments within the specified date range
    @Query("""
            SELECT a 
            FROM Appointment a
            WHERE a.appointmentDate
            BETWEEN :startDate AND :endDate
            """)
    List<Appointment> findAppointmentsBetweenDates(
            @Param("startDate")
            LocalDateTime startDate,

            @Param("endDate")
            LocalDateTime endDate);

    //Search Appointments with Optional Filters
    @Query("""
            SELECT a
            FROM Appointment a
            WHERE (:status IS NULL
            OR a.status = :status)
            
            AND (:doctorId IS NULL
            OR a.doctor.id = :doctorId)
            
            AND (:patientId IS NULL
            OR a.patient.id = :patientId)
            """)
    Page<Appointment> searchAppointments(
            @Param("status")
            String status,

            @Param("doctorId")
            Long doctorId,

            @Param("patientId")
            Long patientId,

            Pageable pageable);
}
