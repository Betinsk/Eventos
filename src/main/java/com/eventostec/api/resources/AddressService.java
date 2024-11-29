package com.eventostec.api.resources;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eventostec.api.domain.Address;
import com.eventostec.api.domain.Event;
import com.eventostec.api.domain.DTO.EventRequestDTO;
import com.eventostec.api.repositories.AddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AddressService {
	
	@Autowired
    private  AddressRepository addressRepository;

    public void createAddress(EventRequestDTO data, Event event) {
        Address address = new Address();
        address.setCity(data.city());
        address.setUf(data.state());
        address.setEvent(event);
        addressRepository.save(address);
    }

    public Optional<Address> findByEventId(UUID eventId) {
        return addressRepository.findByEventId(eventId);
    }
}