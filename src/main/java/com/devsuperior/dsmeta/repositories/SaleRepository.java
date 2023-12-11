package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(
            "SELECT new com.devsuperior.dsmeta.dto.SaleReportDTO(s.id, s.date, s.amount, s.seller.name) " +
            "FROM Sale s " +
            "WHERE s.date BETWEEN :minDate AND :maxDate " +
            "AND UPPER(s.seller.name) LIKE UPPER(CONCAT('%',:name,'%'))")
    Page<SaleReportDTO> searchSaleReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);


    @Query(
            "SELECT new com.devsuperior.dsmeta.dto.SaleSummaryDTO(sm.seller.name, SUM(sm.amount)) " +
            "FROM Sale sm " +
            "WHERE sm.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY sm.seller.name")
    List<SaleSummaryDTO> summary(LocalDate minDate, LocalDate maxDate);
}
