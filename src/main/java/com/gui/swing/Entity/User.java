package com.gui.swing.Entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(unique = true)
    private String userName;

    private String userPassword;

    private Boolean userIsActive;

    private String phone;

    private String fullName;

    @Column(unique = true)
    private String identificationCard;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private OTPCode otpCode;

    public User(){
        this.userIsActive = true;
    }

}
