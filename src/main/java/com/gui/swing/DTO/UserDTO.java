package com.gui.swing.DTO;

import lombok.Data;

@Data
public class UserDTO {

    private int id;

    private String userName;

    private Boolean isActive;

    private String role;

    private String action;

    private String fullName;

    private String identificationCard;


    public UserDTO(int userId, String userName, Boolean userIsActive, String roleName, String fullName, String identificationCard) {
        this.id = userId;
        this.userName = userName;
        this.isActive = userIsActive;
        this.role = roleName;
        this.fullName = fullName;
        this.identificationCard = identificationCard;
    }
}
