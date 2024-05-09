package com.gui.swing.Service;

import com.gui.swing.DTO.Request.AddNewRoomRequest;
import com.gui.swing.DTO.Response.AddNewFloorResponse;
import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.DTO.Response.Pair;
import com.gui.swing.Entity.Floor;
import com.gui.swing.Entity.Room;
import com.gui.swing.Entity.RoomInfo;
import com.gui.swing.Repository.FloorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

@Service
public class FloorService {

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private TypeRoomService typeRoomService;

    @Autowired
    private RoomService roomService;

    public List<Floor> getAllFloor() {
        return floorRepository.findAll();
    }

    public Floor findFloorByName(String floorName) {
        if (floorRepository.existsFloorByFloorName(floorName)) {
            return floorRepository.findByFloorName(floorName);
        }
        throw new IllegalArgumentException("Không tìm thấy tên tầng");
    }

    public void saveFloor(Floor floor) {
        try {
            floorRepository.save(floor);
        } catch (DataAccessException dataAccessException) {
            System.out.println(dataAccessException.getMessage());
        }
    }

    public List<Floor> getFloorsByFilters(String searchText, String status) {
        List<Floor> floorList = floorRepository.findAll();

        if (status != null && !status.isEmpty() && !"--- Choose ---".equals(status)) {
            boolean isActive = "Yes".equalsIgnoreCase(status);
            floorList = floorList.stream()
                    .filter(floor -> floor.getIsActive() == isActive)
                    .collect(Collectors.toList());
        }

        if (!StringUtils.isEmpty(searchText)) {
            String searchTextUpper = searchText.toUpperCase();
            floorList = floorList.stream()
                    .filter(floor -> floor.getFloorName().toUpperCase().contains(searchTextUpper))
                    .collect(Collectors.toList());
        }

        return floorList;
    }

    public AddNewFloorResponse addNewFloor(String floorName) {
        try {
            if (floorRepository.existsFloorByFloorName(floorName)) {
                return new AddNewFloorResponse(0, "Tên tầng đã tồn tại!");
            }
            Floor floor = new Floor(floorName);
            floorRepository.save(floor);
        } catch (DataAccessException dataAccessException) {
            return new AddNewFloorResponse(0, "Thêm tầng mới thất bại!");
        }
        return new AddNewFloorResponse(1, "Thêm tầng mới thành công!");
    }

    public AddNewFloorResponse addNewFloorWithDataOfExistingDataFloor(String floorName, String existingFloorName) {
        try {
            Floor floor = new Floor(floorName);
            Floor existingFloor = findFloorByName(existingFloorName);
            for (Room room : existingFloor.getRoomList()) {
                Room newRoom = new Room(room.getRoomName());
                newRoom.setFloor(floor);
                newRoom.setRoomName(room.getRoomName());
                newRoom.setType(room.getType());
                newRoom.setRoomIsActive(room.getRoomIsActive());
                newRoom.setRoomInfoList(room.getRoomInfoList());
                floor.addNewRoom(newRoom);
            }
            floorRepository.save(floor);
        } catch (DataAccessException dataAccessException) {
            System.out.println(dataAccessException.getMessage());
            return new AddNewFloorResponse(0, "Thêm tầng mới thất bại!");
        } catch (IllegalArgumentException illegalArgumentException) {
            return new AddNewFloorResponse(0, illegalArgumentException.getMessage());
        }
        return new AddNewFloorResponse(1, "Thêm tầng mới thành công!");
    }

    public GeneralResponse addNewRoomToFloor(AddNewRoomRequest addNewRoomRequest, String floorName) {
        try {
            Floor existingFloor = findFloorByName(floorName);
            Room room = new Room(addNewRoomRequest.getRoomName());
            room.setFloor(existingFloor);
            room.setType(typeRoomService.findTypeByName(addNewRoomRequest.getRoomType()));
            List<RoomInfo> roomInfoList = room.getRoomInfoList();
            for (Pair pair : addNewRoomRequest.getInfoRoom()) {
                RoomInfo roomInfo = new RoomInfo(pair.getKey(), pair.getValue());
                roomInfo.setRoom(room);
                roomInfoList.add(roomInfo);
            }
            roomService.saveRoom(room);
        } catch (IllegalArgumentException illegalArgumentException) {
            return new AddNewFloorResponse(0, illegalArgumentException.getMessage());
        } catch (DataAccessException dataAccessException) {
            return new AddNewFloorResponse(0, "Có lỗi xảy ra. Vui lòng thử lại!");
        }
        return new GeneralResponse(1, "Thêm phòng mới vào tầng thành công!");
    }

    public GeneralResponse changeStatusFloor(String floorName, Boolean status) {
        try {
            Floor floor = findFloorByName(floorName);
            floor.setIsActive(status);
            floorRepository.save(floor);
        } catch (IllegalArgumentException illegalArgumentException) {
            return new GeneralResponse(0, illegalArgumentException.getMessage());
        }
        return new GeneralResponse(1, "Đổi trạng thái hoạt động của tầng thành công!");
    }

    public GeneralResponse changeRoomToAnotherFloor(int roomId, String floorName) {
        try {
            Room room = roomService.findByRoomId(roomId);
            if (room.getFloor().getFloorName().equals(floorName)) {
                return new GeneralResponse(0, "Phòng " + room.getRoomName() + " đã ở tầng " + floorName + " từ trước");
            }
            Floor floor = findFloorByName(floorName);
            room.setFloor(floor);
            roomService.saveRoom(room);
        } catch (IllegalArgumentException illegalArgumentException) {
            return new GeneralResponse(0, illegalArgumentException.getMessage());
        } catch (DataAccessException dataAccessException) {
            return new GeneralResponse(0, "Có lỗi xảy ra. Vui lòng thử lại!");
        }
        return new GeneralResponse(1, "Chuyển phòng sang tầng mới thành công!");
    }

    // Management Dashboard
}
