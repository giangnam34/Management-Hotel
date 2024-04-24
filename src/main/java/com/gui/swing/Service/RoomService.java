package com.gui.swing.Service;


import com.gui.swing.Entity.Room;
import com.gui.swing.Entity.RoomInfo;
import com.gui.swing.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    public Room findByRoomId(int roomId){
        if (roomRepository.existsRoomByRoomId(roomId)){
            return roomRepository.findByRoomId(roomId);
        }
        throw new IllegalArgumentException("Không tìm thấy phòng");
    }

    public Room findByRoomNameAndFloor(String roomName, String floorName){
        if (roomRepository.existsRoomByRoomNameAndFloor_FloorName(roomName,floorName)){
            return roomRepository.findByRoomNameAndFloor_FloorName(roomName,floorName);
        }
        throw new IllegalArgumentException("Không tìm thấy phòng");
    }

    public void saveRoom(Room room){
        roomRepository.save(room);
    }

     public Boolean addNewInfoRoom(int roomId, RoomInfo roomInfo){
        try{
            Room room = findByRoomId(roomId);
            room.addNewRoomInfo(roomInfo);
            roomRepository.save(room);
        } catch(DataAccessException | IllegalArgumentException dataAccessException){
            return false;
        }
        return true;
     }

    public Boolean removeRoomInfo(int roomId, RoomInfo roomInfo){
        try{
            Room room = findByRoomId(roomId);
            room.removeRoomInfo(roomInfo);
            roomRepository.save(room);
        } catch(DataAccessException | IllegalArgumentException dataAccessException){
            return false;
        }
        return true;
    }

    // public Boolean changeStatusRoom

    // public Boolean updateTypeOfRoom

}
