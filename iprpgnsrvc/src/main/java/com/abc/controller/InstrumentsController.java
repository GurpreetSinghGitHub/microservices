package com.abc.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abc.entity.Instrument;

@RestController
public class InstrumentsController {
	
	@RequestMapping(method=RequestMethod.POST, value="/instrument")
	public Instrument newInstrument(@RequestBody Instrument inputInstrument) {
		
		System.out.println("Propagting Instrument:"+inputInstrument.getName());
		
		return inputInstrument;
	}
}
