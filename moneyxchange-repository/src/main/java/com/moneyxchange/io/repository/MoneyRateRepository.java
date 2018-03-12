package com.moneyxchange.io.repository;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.moneyxchange.io.model.MoneyRate;

public interface MoneyRateRepository extends CrudRepository<MoneyRate, String> {

	List<MoneyRate> findBySymbolIn(Collection<String> symbols);
}
