package com.eventostec.api.resources;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.eventostec.api.domain.Event;
import com.eventostec.api.domain.DTO.EventRequestDTO;
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
	
	   public EventDetailsDTO getEventDetails(UUID eventId) {
	        Event event = repository.findById(eventId)
	                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

	        Optional<Address> address = addressService.findByEventId(eventId);

	        List<Coupon> coupons = couponService.consultCoupons(eventId, new Date());

	        List<EventDetailsDTO.CouponDTO> couponDTOs = coupons.stream()
	                .map(coupon -> new EventDetailsDTO.CouponDTO(
	                        coupon.getCode(),
	                        coupon.getDiscount(),
	                        coupon.getValid()))
	                .collect(Collectors.toList());

	        return new EventDetailsDTO(
	                event.getId(),
	                event.getTitle(),
	                event.getDescription(),
	                event.getDate(),
	                address.isPresent() ? address.get().getCity() : "",
	                address.isPresent() ? address.get().getUf() : "",
	                event.getImgUrl(),
	                event.getEventUrl(),
	                couponDTOs);
	    }
	

}
