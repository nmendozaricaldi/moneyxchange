package com.moneyxchange.io.service.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.moneyxchange.io.model.MoneyRate;
import com.moneyxchange.io.model.ui.MoneyChangeUI;
import com.moneyxchange.io.repository.MoneyRateRepository;
import com.moneyxchange.io.service.MoneyRateService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional(readOnly = true)
public class MoneyRateServiceImpl implements MoneyRateService {
	private static final Logger LOGGER = LoggerFactory.getLogger(MoneyRateServiceImpl.class);

	@Autowired
	private MoneyRateRepository moneyRateRepository;

	@Autowired
	private SimpMessagingTemplate messagingTemplate;
	
	@Override
	@Cacheable("moneyRates")
	public List<MoneyRate> findBySymbols(Collection<String> symbols) {
		return moneyRateRepository.findBySymbolIn(symbols);
	}

	//send new rates to websocket
	@Scheduled(fixedRate = 300000)
//	@Scheduled(fixedRate = 5000)
	@CachePut("moneyRates")
	@Override
	@Transactional
	public List<MoneyRate> putMoneyRate() {
		LOGGER.debug("Changing rates and sending weksocket");
		List<MoneyRate> rates = (List<MoneyRate>) moneyRateRepository.findAll();
		Random randomGenerator = new Random();
		for (MoneyRate moneyRate : rates) {
			double variance = randomGenerator.nextDouble() * (Math.random() > 0.5 ? 1 : -1);
			moneyRate.setRate(moneyRate.getRate() + variance);
		}
		moneyRateRepository.save(rates);
		MoneyChangeUI changeUI = new MoneyChangeUI();
		changeUI.setRates(rates);
		changeUI.setBase("USD");
		changeUI.setDate(new Date());
		messagingTemplate.convertAndSend("/rates", changeUI);
		return rates;
	}

}
