package com.moneyxchange.io.model.ui;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.moneyxchange.io.model.MoneyRate;
import com.moneyxchange.io.model.serializer.MoneyChangeSerializer;

@JsonSerialize(using = MoneyChangeSerializer.class)
public class MoneyChangeUI {
	private String base;
	private Date date;
	private List<MoneyRate> rates;

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public List<MoneyRate> getRates() {
		return rates;
	}

	public void setRates(List<MoneyRate> rates) {
		this.rates = rates;
	}

}
