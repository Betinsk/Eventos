package com.eventostec.api.resources;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eventostec.api.domain.Event;
import com.eventostec.api.domain.DTO.EventRequestDTO;
import com.eventostec.api.domain.DTO.EventResponseDTO;
import com.eventostec.api.domain.event.EventDetailsDTO;
import com.eventostec.api.services.EventService;

@RestController
@RequestMapping("/api/event")
public class EventController {
	
	@Autowired
	private EventService eventService;
	
	@PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<Event> create(@RequestParam("title") String title,
                                        @RequestParam(value = "description", required = false) String description,
                                        @RequestParam("date") Long date,
                                        @RequestParam("city") String city,
                                        @RequestParam("state") String state,
                                        @RequestParam("remote") Boolean remote,
                                        @RequestParam("eventUrl") String eventUrl,
                                        @RequestParam(value = "image", required = false) MultipartFile image) {
        EventRequestDTO eventRequestDTO = new EventRequestDTO(title, description, date, city, state, remote, eventUrl, image);
        Event newEvent = this.eventService.createEvent(eventRequestDTO);
        return ResponseEntity.ok(newEvent);
    }
	
	@GetMapping
		public ResponseEntity<List<EventResponseDTO>> getEvents(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size){
			List<EventResponseDTO> allEvents = this.eventService.getUpComingEvents(page, size);
					return ResponseEntity.ok(allEvents);
		}
	 
	
	 @GetMapping("/filter")
	    public ResponseEntity<List<EventResponseDTO>> getFilteredEvents(@RequestParam(defaultValue = "0") int page,
	                                                                    @RequestParam(defaultValue = "10") int size,
	                                                                    @RequestParam String city,
	                                                                    @RequestParam String uf,
	                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
	                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
	        List<EventResponseDTO> events = eventService.getFilteredEvents(page, size, city, uf, startDate, endDate);
	        return ResponseEntity.ok(events);
	    }
	 
	 

	    @GetMapping("/{eventId}")
	    public ResponseEntity<EventDetailsDTO> getEventDetails(@PathVariable UUID eventId) {
	        EventDetailsDTO eventDetails = eventService.getEventDetails(eventId);
	        return ResponseEntity.ok(eventDetails);
	    }
	 
	

	

}
