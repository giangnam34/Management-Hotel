package com.gui.swing.Service;

import com.gui.swing.DTO.Request.AddNewRoomRequest;
import com.gui.swing.DTO.Response.AddNewFloorResponse;
import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.Entity.Floor;
import com.gui.swing.Entity.Room;
import com.gui.swing.Repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private FloorService floorService;

    @Autowired
    private RoomService roomService;
    
     @Autowired
    private BookingRoomService bookingRoomService;
    

    public AddNewFloorResponse addNewFloor(String floorName){
        return floorService.addNewFloor(floorName);
    }

    public AddNewFloorResponse addNewFloorWithDataOfExistingDataFloor(String floorName, String existingFloorName){
        return floorService.addNewFloorWithDataOfExistingDataFloor(floorName,existingFloorName);
    }

    public GeneralResponse addNewRoomToFloor(AddNewRoomRequest addNewRoomRequest, String floorName){
        return floorService.addNewRoomToFloor(addNewRoomRequest,floorName);
    }

    public Long getTotalOccupancy(){
        return roomService.countNumberAllRoom();
    }

    public Long getRealOccupancy(){
        return roomService.countNumberAllRoomIsActive();
    }

    public Long getOccupied(){
        return getTotalOccupancy() - getRealOccupancy();
    }
    
    public double getRevenue(){
        return bookingRoomService.getTotalRevenue();
    }
    
    public int getBookedRoom(){
        return bookingRoomService.getBookedRoomCount();
    }
    
    
}
