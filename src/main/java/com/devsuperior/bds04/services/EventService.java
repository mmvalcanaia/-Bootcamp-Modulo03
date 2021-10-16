package com.devsuperior.bds04.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.bds04.dto.EventDTO;
import com.devsuperior.bds04.entities.Event;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.repositories.EventRepository;

@Service
public class EventService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private EventRepository repository;

	@Transactional
	public Page<EventDTO> findAllPaged(Pageable pageable){
		Page<Event> events = repository.findAll(pageable);
		return events.map(event -> new EventDTO(event));
	}
	
	@Transactional
	public EventDTO insert(EventDTO dto) {
		Event entity = new Event();
		copyToEntity(entity, dto);
		entity = repository.save(entity);
		return new EventDTO(entity);
	}

	private void copyToEntity(Event entity, EventDTO dto) {
		entity.setName(dto.getName());
		entity.setDate(dto.getDate());
		entity.setUrl(dto.getUrl());
		entity.setCity(cityRepository.getOne(dto.getCityId()));
	}
}
