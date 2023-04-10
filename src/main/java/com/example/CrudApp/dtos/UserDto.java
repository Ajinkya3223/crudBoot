package com.example.CrudApp.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {
    private String userId;
   // @Size(min = 3,max = 15 , message = "Invalid name")
    private String userName;
    //@jakarta.validation.constraints.Email(message = "Invalid email")

    private String Email;
   // @NotBlank(message = "password is required")
    private String Password;
   // @Size(min =2,max=9)
    private String gender;
   // @NotBlank(message = "about is required")
    private String about;




}
