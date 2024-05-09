package com.gui.swing.DTO.Response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CheckoutRoomResponse {

    private String customerIdentification;

    private String customerFirstName;

    private String customerLastName;

    private String phone;

    private String email;

    private Double totalPrice;

    private LocalDateTime timeCheckOut;
}
