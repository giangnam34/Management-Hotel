package com.gui.swing.Service;

import com.gui.swing.DTO.Response.AddNewFloorResponse;
import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.DTO.Response.Pair;
import com.gui.swing.DTO.UserDTO;
import com.gui.swing.Entity.Enum.EnumTypeRoom;
import com.gui.swing.Entity.Floor;
import com.gui.swing.Entity.Room;
import com.gui.swing.Entity.RoomInfo;
import com.gui.swing.Repository.FloorRepository;
import com.gui.swing.Repository.RoomInfoRepository;
import com.gui.swing.Repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.util.StringUtils;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private FloorRepository floorRepository;

    @Autowired
    private TypeRoomService typeRoomService;

    @Autowired
    private RoomInfoRepository roomInfoRepository;

    public Long countNumberAllRoom() {
        return roomRepository.count();
    }

    public Long countNumberAllRoomIsActive() {
        return roomRepository.countRoomByRoomIsActive(true);
    }

    public Room findByRoomId(int roomId) {
        if (roomRepository.existsRoomByRoomId(roomId)) {
            return roomRepository.findByRoomId(roomId);
        }
        throw new IllegalArgumentException("Không tìm thấy phòng");
    }

    public Room findByRoomName(String roomName) {
        if (roomRepository.existsRoomByRoomName(roomName)) {
            return roomRepository.findByRoomName(roomName);
        }
        throw new IllegalArgumentException("Không tìm thấy phòng");
    }

    public List<Room> findByFloor(String floorName) {
        return roomRepository.findAllByFloorFloorName(floorName);
    }

    public List<Room> getRoomsByFilters(String searchText, String typeRoom, String status, String floor) {
        List<Room> roomList = "--- Choose ---".equals(floor) ? roomRepository.findAllByFloorFloorName("Floor1") : roomRepository.findAllByFloorFloorName(floor);

        // Lọc theo loại phòng, bỏ qua nếu giá trị là "--- Choose ---"
        if (!StringUtils.isEmpty(typeRoom) && !"--- Choose ---".equals(typeRoom)) {
            roomList = roomList.stream()
                    .filter(room -> typeRoom.equalsIgnoreCase(room.getType().getRoomTypeValue().toString()))
                    .collect(Collectors.toList());
        }

        if (status != null && !status.isEmpty() && !"--- Choose ---".equals(status)) {
            boolean isActive = "Yes".equalsIgnoreCase(status);
            roomList = roomList.stream()
                    .filter(room -> room.getRoomIsActive() == isActive)
                    .collect(Collectors.toList());
        }

        if (!StringUtils.isEmpty(searchText)) {
            String searchTextUpper = searchText.toUpperCase();
            roomList = roomList.stream()
                    .filter(room -> room.getRoomName().toUpperCase().contains(searchTextUpper))
                    .collect(Collectors.toList());
        }

        return roomList;
    }
    
    public List<Room> getRoomsInDashboard(String floorName, String typeOfRoom, String view, String bed, String bathTub, String searchText) {
        List<Room> roomList = "--- Choose ---".equals(floorName) ? roomRepository.findAllByFloorFloorName("Floor1") : roomRepository.findAllByFloorFloorName(floorName);

        // Lọc theo loại phòng, bỏ qua nếu giá trị là "--- Choose ---"
        if (!StringUtils.isEmpty(typeOfRoom) && !"--- Choose ---".equals(typeOfRoom)) {
            roomList = roomList.stream()
                    .filter(room -> typeOfRoom.equalsIgnoreCase(room.getType().getRoomTypeValue().toString()))
                    .collect(Collectors.toList());
        }
        
        if (view != null && !view.isEmpty()) {
            if (view.equalsIgnoreCase("YES")) {
                roomList = roomList.stream().filter(room -> !room.getRoomInfoList().stream().filter(roomInfo -> roomInfo.getKeyRoomInfo().equalsIgnoreCase("Cửa sổ")).toList().isEmpty()).toList();
            } else {
                roomList = roomList.stream().filter(room -> room.getRoomInfoList().stream().filter(roomInfo -> roomInfo.getKeyRoomInfo().equalsIgnoreCase("Cửa sổ")).toList().isEmpty()).toList();
            }
        }
        if (bed != null && !bed.isEmpty()) {
            roomList = roomList.stream().filter(room -> !room.getRoomInfoList().stream().filter(roomInfo -> roomInfo.getKeyRoomInfo().equalsIgnoreCase("Giường") && roomInfo.getValueRoomInfo().equalsIgnoreCase(bed)).toList().isEmpty()).toList();
        }
        
        if (bathTub != null && !bathTub.isEmpty()) {
            if (bathTub.equalsIgnoreCase("YES")) {
                roomList = roomList.stream().filter(room -> !room.getRoomInfoList().stream().filter(roomInfo -> roomInfo.getKeyRoomInfo().equalsIgnoreCase("Bồn tắm")).toList().isEmpty()).toList();
            } else {
                roomList = roomList.stream().filter(room -> room.getRoomInfoList().stream().filter(roomInfo -> roomInfo.getKeyRoomInfo().equalsIgnoreCase("Bồn tắm")).toList().isEmpty()).toList();
            }
        }

        if (!StringUtils.isEmpty(searchText)) {
            String searchTextUpper = searchText.toUpperCase();
            roomList = roomList.stream()
                    .filter(room -> room.getRoomName().toUpperCase().contains(searchTextUpper))
                    .collect(Collectors.toList());
        }

        return roomList;
    }


    public Room findByRoomNameAndFloor(String roomName, String floorName) {
        if (roomRepository.existsRoomByRoomNameAndFloor_FloorName(roomName, floorName)) {
            return roomRepository.findByRoomNameAndFloor_FloorName(roomName, floorName);
        }
        throw new IllegalArgumentException("Không tìm thấy phòng");
    }

    public void saveRoom(Room room) {
        try {
            roomRepository.save(room);
        } catch (DataAccessException dataAccessException) {
            System.out.println(dataAccessException.getMessage());
        }
    }

    // add new room
    public GeneralResponse addNewRoom(String roomName, EnumTypeRoom typeRoom, List<Pair> infoList, String floorName) {
        try {
            if (roomRepository.existsRoomByRoomNameAndFloor_FloorName(roomName, floorName)) {
                return new GeneralResponse(0, "Phòng đã tồn tại!");
            }
            Room room = new Room(roomName);
            room.setType(typeRoomService.findTypeByName(typeRoom));
            for (Pair info : infoList) {
                room.addNewRoomInfo(new RoomInfo(info.getKey(), info.getValue()));
            }
            Floor floor = floorRepository.findByFloorName(floorName);
            room.setFloor(floor);
            roomRepository.save(room);
        } catch (IllegalArgumentException illegalArgumentException) {
            return new GeneralResponse(0, illegalArgumentException.getMessage());
        } catch (DataAccessException dataAccessException) {
            return new GeneralResponse(0, "Thêm phòng mới thất bại!");
        }
        return new GeneralResponse(1, "Thêm phòng mới thành công!");
    }

    public GeneralResponse addNewInfoRoom(int roomId, RoomInfo roomInfo) {
        try {
            Room room = findByRoomId(roomId);
            room.addNewRoomInfo(roomInfo);
            roomRepository.save(room);
        } catch (DataAccessException | IllegalArgumentException exception) {
            return new GeneralResponse(0, exception.getMessage());
        }
        return new GeneralResponse(1, "Thêm thông tin vào phòng thành công!");
    }

    public GeneralResponse removeRoomInfo(int roomId, RoomInfo roomInfo) {
        try {
            Room room = findByRoomId(roomId);
            for (RoomInfo roomInfo1 : room.getRoomInfoList()) {
                if (roomInfo1.getRoomInfoId() == roomInfo.getRoomInfoId()) {
                    room.removeRoomInfo(roomInfo);
                    roomInfo1.setRoom(null);
                    roomInfoRepository.save(roomInfo1);
                    break;
                }
            }
            roomRepository.save(room);
        } catch (DataAccessException | IllegalArgumentException exception) {
            return new GeneralResponse(0, exception.getMessage());
        }
        return new GeneralResponse(1, "Xóa thông tin thành công!");
    }

    public GeneralResponse changeStatusRoom(int roomId, Boolean roomStatus) {
        try {
            Room room = findByRoomId(roomId);
            room.setRoomIsActive(roomStatus);
            roomRepository.save(room);
        } catch (IllegalArgumentException | DataAccessException exception) {
            return new GeneralResponse(0, exception.getMessage());
        }
        return new GeneralResponse(1, "Thay đổi trạng thái hoạt động của phòng thành công!");
    }

    public GeneralResponse updateTypeOfRoom(int roomId, EnumTypeRoom typeRoom) {
        try {
            Room room = findByRoomId(roomId);
            room.setType(typeRoomService.findTypeByName(typeRoom));
            roomRepository.save(room);
        } catch (IllegalArgumentException | DataAccessException exception) {
            return new GeneralResponse(0, exception.getMessage());
        }
        return new GeneralResponse(1, "Thay đổi loại phòng thành công!");
    }

    public List<Room> searchRoom(String floorName, String typeOfRoom, String view, String bed, String bathTub, String searchText) {
       

        List<Room> roomList = roomRepository.findAllByFloorFloorName(floorName);

        if (typeOfRoom != null && !typeOfRoom.isEmpty()) {
            roomList = roomList.stream().filter(room -> room.getType().getRoomTypeValue().toString().equalsIgnoreCase(typeOfRoom)).toList();
        }
        if (view != null && !view.isEmpty()) {
            if (view.equalsIgnoreCase("YES")) {
                roomList = roomList.stream().filter(room -> !room.getRoomInfoList().stream().filter(roomInfo -> roomInfo.getKeyRoomInfo().equalsIgnoreCase("Cửa sổ")).toList().isEmpty()).toList();
            } else {
                roomList = roomList.stream().filter(room -> room.getRoomInfoList().stream().filter(roomInfo -> roomInfo.getKeyRoomInfo().equalsIgnoreCase("Cửa sổ")).toList().isEmpty()).toList();
            }
        }
        if (bed != null && !bed.isEmpty()) {
            roomList = roomList.stream().filter(room -> !room.getRoomInfoList().stream().filter(roomInfo -> roomInfo.getKeyRoomInfo().equalsIgnoreCase("Giường") && roomInfo.getValueRoomInfo().equalsIgnoreCase(bed)).toList().isEmpty()).toList();
        }
        if (bathTub != null && !bathTub.isEmpty()) {
            if (bathTub.equalsIgnoreCase("YES")) {
                roomList = roomList.stream().filter(room -> !room.getRoomInfoList().stream().filter(roomInfo -> roomInfo.getKeyRoomInfo().equalsIgnoreCase("Bồn tắm")).toList().isEmpty()).toList();
            } else {
                roomList = roomList.stream().filter(room -> room.getRoomInfoList().stream().filter(roomInfo -> roomInfo.getKeyRoomInfo().equalsIgnoreCase("Bồn tắm")).toList().isEmpty()).toList();
            }
        }
        if (searchText != null && !searchText.isEmpty()) {
            String formatText = searchText.trim();
            roomList = roomList.stream().filter(room -> !room.getRoomInfoList().stream().filter(roomInfo -> roomInfo.getKeyRoomInfo().toUpperCase().contains(formatText.toUpperCase()) || roomInfo.getValueRoomInfo().toUpperCase().contains(formatText.toUpperCase()) || roomInfo.getKeyRoomInfo().concat(" " + roomInfo.getValueRoomInfo()).toUpperCase().contains(formatText.toUpperCase())).toList().isEmpty()).toList();
        }
        return roomList;
    }

}
