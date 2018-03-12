package com.moneyxchange.io.controller;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moneyxchange.io.model.MoneyRate;
import com.moneyxchange.io.model.ui.MoneyChangeUI;
import com.moneyxchange.io.service.MoneyRateService;

@Controller
public class MoneyChangeController {

	@Autowired
	private MoneyRateService moneyRateService;

	public MoneyChangeController() {
		super();
	}

	// API - read
	@PreAuthorize("#oauth2.hasScope('read')")
	@RequestMapping(method = RequestMethod.GET, value = "/latest")
	@ResponseBody
	public MoneyChangeUI findById(
			@RequestParam(value = "symbols", required = false, defaultValue = "EU") Collection<String> symbols,
			@RequestParam(value = "BASE", required = false, defaultValue = "USD") String base) {
		MoneyChangeUI changeUI = new MoneyChangeUI();
		List<MoneyRate> moneyRates = moneyRateService.findBySymbols(symbols);
		changeUI.setRates(moneyRates);
		changeUI.setBase(base);
		changeUI.setDate(new Date());
		return changeUI;
	}

}
