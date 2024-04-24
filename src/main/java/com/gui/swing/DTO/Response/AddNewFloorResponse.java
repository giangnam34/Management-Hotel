package com.gui.swing.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Data;

public class AddNewFloorResponse extends GeneralResponse {

    public AddNewFloorResponse(int status, String message) {
        super(status, message);
    }
}
