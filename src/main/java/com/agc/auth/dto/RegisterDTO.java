package com.agc.auth.dto;

import com.agc.auth.model.UserRole;

public record RegisterDTO(String email, String password, UserRole role) {
}
