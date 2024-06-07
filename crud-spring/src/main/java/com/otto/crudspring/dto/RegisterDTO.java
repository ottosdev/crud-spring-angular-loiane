package com.otto.crudspring.dto;

import com.otto.crudspring.model.UserRole;

public record RegisterDTO(String username, String password, UserRole role) {
}
