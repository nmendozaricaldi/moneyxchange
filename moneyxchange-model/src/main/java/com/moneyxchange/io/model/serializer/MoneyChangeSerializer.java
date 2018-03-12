package com.moneyxchange.io.model.serializer;

import java.io.IOException;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.moneyxchange.io.model.MoneyRate;
import com.moneyxchange.io.model.ui.MoneyChangeUI;

public class MoneyChangeSerializer extends JsonSerializer<MoneyChangeUI> {

	public static final SimpleDateFormat dt = new SimpleDateFormat("yyyyy-mm-dd hh:mm:ss");

	// "yyyy-MM-dd"
	@Override
	public void serialize(MoneyChangeUI value, JsonGenerator gen, SerializerProvider seri)
			throws IOException, JsonProcessingException {

		gen.writeStartObject();
		gen.writeStringField("base", value.getBase());
		gen.writeStringField("date", dt.format(value.getDate()));
		gen.writeFieldName("rates");
		gen.writeStartObject();
		for (MoneyRate rate : value.getRates()) {
			gen.writeNumberField(rate.getSymbol(), rate.getRate());
		}
		gen.writeEndObject();
		gen.writeEndObject();

	}

}
