package com.gui.swing.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "room_info")
@Data
public class RoomInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomInfoId;

    private String keyRoomInfo;

    private String valueRoomInfo;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private Room room;

    public RoomInfo(String keyRoomInfo, String valueRoomInfo) {
        this.keyRoomInfo = keyRoomInfo;
        this.valueRoomInfo = valueRoomInfo;
    }

    public RoomInfo() {

    }
}
