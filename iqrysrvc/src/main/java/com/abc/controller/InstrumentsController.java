package com.abc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abc.entity.Instrument;
import com.abc.service.InstrumentsService;

@RestController
public class InstrumentsController {
	
	@Autowired
	InstrumentsService instrumentsService;
	
	@RequestMapping(method=RequestMethod.GET, value="/instrument/{id}")
	public Instrument getInstrument(@PathVariable Integer id) {
		
		Instrument instrument = instrumentsService.getInstrument(id);
		
		return instrument;
	}
}
