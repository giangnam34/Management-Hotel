package com.gui.swing.Service;


import com.gui.swing.DTO.Response.InfoRoomResponse;
import com.gui.swing.Entity.Floor;
import com.gui.swing.Entity.Room;
import com.gui.swing.Entity.User;
import com.gui.swing.Repository.FloorRepository;
import com.gui.swing.Repository.RoomGuestRepository;
import com.gui.swing.Repository.RoomRepository;
import com.gui.swing.Service.Mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class InfoRoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private RoomGuestRepository roomGuestRepository;

    public List<InfoRoomResponse> getAllRoomAtFloor(String floorName, Boolean status) // floorName: tên tầng , status: trạng thái của phòng, không/đang hoạt động
    {
        List<InfoRoomResponse> result = new ArrayList<>();
        Floor floor = floorRepository.findByFloorName(floorName);
        for (Room room : floor.getRoomList()){
            if (room.getRoomIsActive().equals(status)) {
                result.add(roomMapper.toRoomResponse(room));
            }
        }
        return result;
    }

    public Boolean isRoomRent(int roomId, LocalDateTime timeBegin, LocalDateTime timeEnd){
        return roomGuestRepository.existsRoomGuestByRoomRoomIdAndDateBeginBeforeAndDateEndAfter(roomId,timeBegin,timeBegin) ||
                roomGuestRepository.existsRoomGuestByRoomRoomIdAndDateBeginBeforeAndDateEndAfter(roomId,timeEnd,timeEnd);
    }

    // Not done
    public List<InfoRoomResponse> searchRoom(){
        return null;
    }

}
