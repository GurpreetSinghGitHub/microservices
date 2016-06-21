package com.abc.service.rules;

import org.springframework.stereotype.Component;

import com.abc.common.Constants;
import com.abc.entity.Instrument;

@Component
public class PriceFactorRule {
	
	private Instrument instrument;
	
	public PriceFactorRule() {		
	}
	
	public PriceFactorRule(Instrument instrument) {
		
		this.instrument = instrument;
	}
	
	public Instrument apply() {
		
		if(null!=instrument && null!=instrument.getType()) {
			
			if(instrument.getType().equals(Constants.INSTRUMENT_TYPE_EQUITY)) {
				
				instrument.setPrice(instrument.getPrice()*1.01);				
			}
			else {
				
				instrument.setPrice(instrument.getPrice()*1.02);
			}
		}
		
		return instrument;
	}
}
