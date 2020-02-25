package com.employee.api.error;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class FaultCustomSerializer extends JsonSerializer<Fault> {

	@Override
	public void serialize(Fault fault, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException, JsonProcessingException {
		jsonGenerator.writeStartObject();
		
		jsonGenerator.writeFieldName("code");
		jsonGenerator.writeNumber(fault.getError().getCode());

		jsonGenerator.writeFieldName("internalMessage");
		jsonGenerator.writeString(fault.getError().getInternalMessage());

		jsonGenerator.writeFieldName("userMessage");
		jsonGenerator.writeString(fault.getError().getLocalizationKey());

		jsonGenerator.writeFieldName("responseCode");
		jsonGenerator.writeNumber(fault.getError().getResponseCode());

		jsonGenerator.writeEndObject();
		
	}
}