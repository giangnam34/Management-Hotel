package com.gui.swing.Repository;

import com.gui.swing.Entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {

    public List<Room> findAllByFloorFloorName(String floorName);

    public Boolean existsRoomByRoomId(int roomId);

    public Room findByRoomId(int roomId);

    public Boolean existsRoomByRoomName(String roomName);

    public Room findByRoomName(String roomName);

    public Boolean existsRoomByRoomNameAndFloor_FloorName(String roomName, String floorName);

    public Room findByRoomNameAndFloor_FloorName(String roomName, String floorName);

    public Long countRoomByRoomIsActive(Boolean isActive);


}