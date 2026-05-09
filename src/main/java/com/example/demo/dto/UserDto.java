package com.example.demo.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String studentId;
    @NonNull
    private String firstName;
    private String lastName;
    private String email;
}
