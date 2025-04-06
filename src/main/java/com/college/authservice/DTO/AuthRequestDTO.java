package com.college.authservice.DTO;

import com.college.authservice.enums.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
	@NotBlank
    private String username;
    @NotBlank
    private String password;

    @NotBlank
    private String rollno;

    @NotNull
    private Role role;
}
