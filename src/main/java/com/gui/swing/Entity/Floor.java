package com.gui.swing.Entity;


import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Floor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int floorId;

    @Column(unique = true)
    private String floorName;

    private Boolean isActive;

    @OneToMany(mappedBy = "floor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Room> roomList;

    public Floor(String floorName) {
        this.floorName = floorName;
        this.roomList = new ArrayList<>();
        this.isActive = true;
    }

    public Floor() {

    }

    public void addNewRoom(Room room) {
        this.roomList.add(room);
        room.setFloor(this);
    }

    public void removeRoom(Room room) {
        room.setFloor(null);
        this.roomList.remove(room);
    }

}
