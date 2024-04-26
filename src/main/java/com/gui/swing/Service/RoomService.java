package com.gui.swing.Service;


import com.gui.swing.DTO.Response.AddNewFloorResponse;
import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.DTO.Response.Pair;
import com.gui.swing.Entity.Enum.EnumTypeRoom;
import com.gui.swing.Entity.Floor;
import com.gui.swing.Entity.Room;
import com.gui.swing.Entity.RoomInfo;
import com.gui.swing.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TypeRoomService typeRoomService;

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

    // add new room

    public GeneralResponse addNewRoom(String roomName, EnumTypeRoom typeRoom, List<Pair> infoList, String floorName){
        try {
            if (roomRepository.existsRoomByRoomNameAndFloor_FloorName(roomName,floorName))
                return new GeneralResponse(0, "Phòng đã tồn tại!");
            Room room = new Room(roomName);
            room.setType(typeRoomService.findTypeByName(typeRoom));
            for(Pair info : infoList){
                room.addNewRoomInfo(new RoomInfo(info.getKey(),info.getValue()));
            }
            roomRepository.save(room);
        } catch(IllegalArgumentException illegalArgumentException){
            return new GeneralResponse(0, illegalArgumentException.getMessage());
        } catch(DataAccessException dataAccessException) {
            return new GeneralResponse(0, "Thêm phòng mới thất bại!");
        }
        return new GeneralResponse(1, "Thêm phòng mới thành công!");
    }

     public GeneralResponse addNewInfoRoom(int roomId, RoomInfo roomInfo){
        try{
            Room room = findByRoomId(roomId);
            room.addNewRoomInfo(roomInfo);
            roomRepository.save(room);
        } catch(DataAccessException | IllegalArgumentException exception){
            return new GeneralResponse(0, exception.getMessage());
        }
        return new GeneralResponse(1, "Thêm thông tin vào phòng thành công!");
     }

    public GeneralResponse removeRoomInfo(int roomId, RoomInfo roomInfo){
        try{
            Room room = findByRoomId(roomId);
            room.removeRoomInfo(roomInfo);
            roomRepository.save(room);
        } catch(DataAccessException | IllegalArgumentException exception){
            return new GeneralResponse(0, exception.getMessage());
        }
        return new GeneralResponse(1, "Xóa thông tin thành công!");
    }

     public GeneralResponse changeStatusRoom(int roomId, Boolean roomStatus){
        try{
            Room room = findByRoomId(roomId);
            room.setRoomIsActive(roomStatus);
            roomRepository.save(room);
        } catch (IllegalArgumentException | DataAccessException exception){
            return new GeneralResponse(0, exception.getMessage());
        }
        return new GeneralResponse(1, "Thay đổi trạng thái hoạt động của phòng thành công!");
     }

     public GeneralResponse updateTypeOfRoom(int roomId, EnumTypeRoom typeRoom){
         try{
             Room room = findByRoomId(roomId);
             room.setType(typeRoomService.findTypeByName(typeRoom));
             roomRepository.save(room);
         } catch (IllegalArgumentException | DataAccessException exception){
             return new GeneralResponse(0, exception.getMessage());
         }
         return new GeneralResponse(1, "Thay đổi loại phòng thành công!");
     }

}
