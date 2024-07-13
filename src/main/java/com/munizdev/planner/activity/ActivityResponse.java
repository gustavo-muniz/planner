package com.munizdev.planner.activity;

import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.UUID;

public record ActivityResponse(UUID id, String title, LocalDateTime occursAt) {
}
