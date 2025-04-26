package edu.ijse.gdse71.serenity.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User implements SuperEntity{

    @Id
    private String id;
    private String username;
    private String password;
    private String role;
}
