package com.gui.swing.DTO;

import lombok.Data;

@Data
public class UserDTO {

    private int id;

    private String userName;

    private Boolean isActive;

    private String role;

    private String action;

    public UserDTO(int userId, String userName, Boolean userIsActive, String roleName) {
        this.id = userId;
        this.userName = userName;
        this.isActive = userIsActive;
        this.role = roleName;
    }
}
