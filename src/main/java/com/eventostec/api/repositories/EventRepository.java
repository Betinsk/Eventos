package com.eventostec.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventostec.api.domain.Event;

public interface EventRepository extends JpaRepository<Event, UUID>{

}
