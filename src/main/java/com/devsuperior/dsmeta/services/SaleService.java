package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SaleReportDTO;
import com.devsuperior.dsmeta.dto.SaleSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleReportDTO> searchReports(String minDate, String maxDate, String name, Pageable pageable){
		if(maxDate.isBlank()){
			maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).toString();
		}

		if(minDate.isBlank()){
			minDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1L).toString();
		}

		return repository.searchSaleReport(LocalDate.parse(minDate), LocalDate.parse(maxDate), name, pageable);
	}

	public List<SaleSummaryDTO> searchSummaries(String minDate, String maxDate) {
		if(maxDate.isBlank()){
			maxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).toString();
		}

		if(minDate.isBlank()){
			minDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault()).minusYears(1L).toString();
		}

		return repository.summary(LocalDate.parse(minDate), LocalDate.parse(maxDate));
	}
}
