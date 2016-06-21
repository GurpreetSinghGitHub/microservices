package com.abc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.abc.entity.Instrument;
import com.abc.entity.PriceFactor;
import com.abc.service.InstrumentsService;

@RestController
@RefreshScope
public class InstrumentsController {
	
	@Autowired
	InstrumentsService instrumentsService;
	
	@Value("${price.factor.equity}") String priceFactorEquity;
	@Value("${price.factor.other}") String priceFactorOther;
	
	@RequestMapping(method=RequestMethod.POST, value="/instrument")
	public Instrument newInstrument(@RequestBody Instrument inputInstrument) {
		
		PriceFactor priceFactor = new PriceFactor();
		if(null!=priceFactorEquity)
			priceFactor.setEquity(Double.parseDouble(priceFactorEquity));
		else
			priceFactor.setEquity(1.0);
		if(null!=priceFactorOther)
			priceFactor.setOther(Double.parseDouble(priceFactorOther));
		else
			priceFactor.setOther(1.0);
		
		Instrument instrument = instrumentsService.saveInstrument(inputInstrument, priceFactor);
		
		return instrument;
	}

	@RequestMapping(method=RequestMethod.PUT, value="/instrument/{id}/price/{newprice}")
	public Instrument updateInstrument(@PathVariable Integer id, Double newprice) {
		
		Instrument instrument = instrumentsService.saveInstrument(id, newprice);
		
		return instrument;
	}
}
