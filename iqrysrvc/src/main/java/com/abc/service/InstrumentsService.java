package com.abc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abc.entity.Instrument;
import com.abc.repository.InstrumentsRepository;

@Component
public class InstrumentsService {
	
	@Autowired
	InstrumentsRepository instrumentsRepository;
	
	public Instrument getInstrument(Integer id) {
		
		return instrumentsRepository.findOne(id);
	}
}
