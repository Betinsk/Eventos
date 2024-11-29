package com.eventostec.api.resources;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eventostec.api.domain.Coupon;
import com.eventostec.api.domain.DTO.CouponRequestDTO;
import com.eventostec.api.services.CouponService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/coupon")
@RequiredArgsConstructor
public class CouponController {

	@Autowired
    private  CouponService couponService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Coupon> addCouponsToEvent(@PathVariable UUID eventId, @RequestBody CouponRequestDTO data) {
        Coupon coupons = couponService.addCouponToEvent(eventId, data);
        return ResponseEntity.ok(coupons);
    }
}