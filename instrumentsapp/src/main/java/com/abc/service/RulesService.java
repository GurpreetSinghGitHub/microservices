package com.abc.service;

import org.springframework.stereotype.Component;

import com.abc.entity.Instrument;
import com.abc.service.rules.PriceFactorRule;

@Component
public class RulesService {
	
	public Instrument applyRules(Instrument input) {
		
		Instrument instrument = new PriceFactorRule(input).apply();	
		
		return instrument;
	}
}
