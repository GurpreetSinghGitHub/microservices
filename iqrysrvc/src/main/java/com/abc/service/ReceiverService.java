package com.abc.service;

import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abc.entity.Instrument;
import com.abc.repository.InstrumentsRepository;

@Component
public class ReceiverService {
	
	@Autowired
	InstrumentsRepository instrumentsRepository;

	public void receiveMessage(String message) {
		
		System.out.println("Received <" + message + ">");
		
		try {
		
			StringTokenizer tokenizer = new StringTokenizer(message, ":");
			
			Instrument instrument = new Instrument();
			
			instrument.setId(Integer.parseInt(tokenizer.nextToken()));
			instrument.setName(tokenizer.nextToken());
			instrument.setType(tokenizer.nextToken());
			instrument.setPrice(Double.parseDouble(tokenizer.nextToken()));
					
			instrumentsRepository.save(instrument);
		}
		catch (Exception ex) {
			
			System.out.println("Error creating instrument in IQryService Applicaiton..."+ex.getMessage());
			ex.printStackTrace();
		}
	}
}
