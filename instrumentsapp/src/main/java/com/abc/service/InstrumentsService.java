package com.abc.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abc.common.Constants;
import com.abc.entity.Instrument;
import com.abc.repository.InstrumentsRepository;

@Component
public class InstrumentsService {
	
	@Autowired
	InstrumentsRepository instrumentsRepository;
	
	@Autowired
	RulesService rulesService;
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	public Instrument getInstrument(Integer id) {
		
		return instrumentsRepository.findOne(id);
	}
	
	public Instrument saveInstrument(Instrument instrument) {
		
		Instrument saveInstrument = rulesService.applyRules(instrument);
		
		instrumentsRepository.save(saveInstrument);
		
		rabbitTemplate.convertAndSend(Constants.QUEUE_NAME, "Instrument created: "+ saveInstrument.getName());
		
		return saveInstrument;
	}
	
	public Instrument saveInstrument(Integer id, Double newprice) {
		
		Instrument instrument = getInstrument(id);
		
		instrument.setPrice(newprice);
		
		return instrumentsRepository.save(rulesService.applyRules(instrument));
	}
}
