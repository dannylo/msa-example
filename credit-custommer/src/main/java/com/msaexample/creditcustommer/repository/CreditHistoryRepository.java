package com.msaexample.creditcustommer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.msaexample.creditcustommer.domain.CreditHistory;

@Repository
public interface CreditHistoryRepository extends JpaRepository<CreditHistory, Long> {

}
