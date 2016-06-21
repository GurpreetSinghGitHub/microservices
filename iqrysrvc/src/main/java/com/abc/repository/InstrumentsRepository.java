package com.abc.repository;

import org.springframework.data.repository.CrudRepository;

import com.abc.entity.Instrument;

public interface InstrumentsRepository extends CrudRepository<Instrument, Integer> {

}
