package com.gui.swing.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewUserRequest {

    private String userName;

    private String password;

    private String phone;

    private String fullName;

    private String identificationCard;

}
