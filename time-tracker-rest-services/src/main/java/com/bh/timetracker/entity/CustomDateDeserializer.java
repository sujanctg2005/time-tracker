package com.bh.timetracker.entity;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Calendar;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

	@Override
	public Date deserialize(JsonParser jsonparser, DeserializationContext context)
			throws IOException, JsonProcessingException {
		String dateAsString = jsonparser.getText();
		try {
			Date date = CustomDataSerializer.FORMATTER.parse(dateAsString);
			
			 /*Calendar calendar = Calendar.getInstance(
			 CustomCalendarSerializer.LOCAL_TIME_ZONE,
			  CustomCalendarSerializer.LOCALE_HUNGARIAN ); calendar.setTime(date);*/
			 
			return date;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

}