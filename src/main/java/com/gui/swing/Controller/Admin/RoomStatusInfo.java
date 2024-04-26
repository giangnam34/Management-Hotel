/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gui.swing.Controller.Admin;

/**
 *
 * @author Acer
 */
public class RoomStatusInfo {
    private String statusName;
    private int value;

    public RoomStatusInfo(String statusName, int value) {
        this.statusName = statusName;
        this.value = value;
    }

    // Getter và Setter
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
