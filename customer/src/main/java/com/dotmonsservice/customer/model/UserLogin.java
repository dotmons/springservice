package com.dotmonsservice.customer.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="user_details_login")
public class UserLogin {
    @Id
    @SequenceGenerator(name="sequence_user", sequenceName = "sequence_user")
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "sequence_user")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;
    private String userrole;

}
