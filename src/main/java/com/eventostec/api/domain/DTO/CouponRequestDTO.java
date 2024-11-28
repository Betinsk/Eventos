package com.eventostec.api.domain.DTO;

public record CouponRequestDTO(String code, Integer discount, Long valid) {
}