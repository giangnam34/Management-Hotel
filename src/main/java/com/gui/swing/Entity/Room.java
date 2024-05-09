package com.gui.swing.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;

    @Column(unique = true)
    private String roomName;

    private Boolean roomIsActive;

    @ManyToOne
    @JoinColumn(name = "floor_id", nullable = false)
    private Floor floor;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private Type type;

    @OneToMany(mappedBy = "room", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<RoomInfo> roomInfoList;

    public Room(){
        this.roomIsActive = true;
        this.roomInfoList = new ArrayList<>();
    }
    
    public Room(String roomName){
       this.roomIsActive = true;
        this.roomInfoList = new ArrayList<>();
        this.roomName = roomName;
    }
    
    public Room(String roomName, Boolean roomIsActive) {
        this.roomName = roomName;
        this.roomIsActive = roomIsActive;
    }

    public void addNewRoomInfo(RoomInfo roomInfo) {
        this.roomInfoList.add(roomInfo);
        roomInfo.setRoom(this);
    }

    public void removeRoomInfo(RoomInfo roomInfo) {
        this.roomInfoList.remove(roomInfo);

    }

}
