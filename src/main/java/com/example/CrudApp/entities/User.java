package com.example.CrudApp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    private String userId;
    //@NotNull
    private String userName;
    //@Column(unique = true)
   // @jakarta.validation.constraints.Email
    private String Email;
   // @Max(value = 12)
   // @Min(value = 3)
    private String Password;
    //@Min(2)
    //@Max(10)
    private String gender;
    //@NotBlank
    //@Column(length =100)
    private String about;

}
