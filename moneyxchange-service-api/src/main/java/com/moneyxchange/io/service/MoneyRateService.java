package com.moneyxchange.io.service;

import java.util.Collection;
import java.util.List;

import com.moneyxchange.io.model.MoneyRate;

public interface MoneyRateService {
	List<MoneyRate> findBySymbols(Collection<String> symbols);

	List<MoneyRate> putMoneyRate();
}
