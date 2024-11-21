package com.eventostec.api.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eventostec.api.domain.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID>{

}
