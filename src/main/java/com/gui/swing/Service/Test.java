package com.gui.swing.Service;

import com.gui.swing.DTO.Request.AddNewRoomRequest;
import com.gui.swing.DTO.Request.ChangePasswordRequest;
import com.gui.swing.DTO.Request.SendEmailRequest;
import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.DTO.Response.InfoRoomResponse;
import com.gui.swing.DTO.Response.Pair;
import com.gui.swing.Entity.Enum.EnumTypeRoom;
import com.gui.swing.Repository.FloorRepository;
import com.gui.swing.Repository.GuestRepository;
import com.gui.swing.Repository.RoomGuestRepository;
import com.gui.swing.Repository.RoomRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class Test {

    @Autowired
    AuthenticationServiceImpl authenticationService;

    @Autowired
    EmailService emailService;

    @Autowired
    InfoRoomService infoRoomService;

    @Autowired
    private RoomGuestRepository roomGuestRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private GuestRepository guestRepository;

    @Autowired
    private InfoFloorService infoFloorService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private FloorRepository floorRepository;


    public void testLogin(){
        System.out.println(authenticationService.authentication("giangnam.17122002@gmail.com","12345678").getMessage());

    }
    public void testSendEmail() throws MessagingException {
        Map<String,Object> context = new HashMap<>();
        context.put("text",123);
        GeneralResponse generalResponse = emailService.sendMessageUsingThymeleafTemplate(new SendEmailRequest("giangnam.17122002@gmail.com", "Confirmation code", context));
        System.out.println(generalResponse.getMessage());
    }

    public void testForgetPassword() throws MessagingException {
        GeneralResponse generalResponse = authenticationService.forgetPassword("giangnam.17122002@gmail.com");
        System.out.println(authenticationService.confirmOTPCode("giangnam.17122002@gmail.com",123456) ? "Mã xác nhận đúng" : "Mã xác nhận sai");
    }

    public void testChangePassword(){
        GeneralResponse generalResponse = authenticationService.changePassword(new ChangePasswordRequest("huutrong1101@gmail.com","Giangnam1@","Giangnam1@"));
        System.out.println(generalResponse);
    }

    public void testGetInfoRoom(){
        List<InfoRoomResponse> result = infoRoomService.getAllRoomAtFloor("Floor2", true);
        for(InfoRoomResponse room : result){
            System.out.println(room);
        }
    }

    public void testIsRoomRent(){
//        Guest guest = new Guest(2,"Vo Giang Nam", "051202007648");
//        guestRepository.save(guest);
//        RoomGuest roomGuest = new RoomGuest(1, LocalDateTime.now().minusDays(0),LocalDateTime.now().plusDays(7), EnumTypeRent.Day,roomRepository.findById(1).get(),guestRepository.findById(2).get());
//        roomGuestRepository.save(roomGuest);
        System.out.println(infoRoomService.isRoomRent(1, LocalDateTime.now().minusDays(10), LocalDateTime.now().minusDays(9)));
    }

    public void testGetAllFloor(){
        System.out.println(infoFloorService.getAllFloor());
    }

    public void testAddNewFloor(){
        System.out.println(adminService.addNewFloorWithDataOfExistingDataFloor("Floor14", "Floor1"));
    }

    public void testAddNewRoomToFloor(){
        AddNewRoomRequest addNewRoomRequest = new AddNewRoomRequest();
        addNewRoomRequest.setRoomName("Test room");
        addNewRoomRequest.setRoomType(EnumTypeRoom.Vip);
        List<Pair> infoRoom = new ArrayList<>();
        infoRoom.add(new Pair("Diện tích", "40m2"));
        infoRoom.add(new Pair("Số người ở tối đa", "4 người"));
        infoRoom.add(new Pair("Mặt tiền", "3 mặt tiền"));
        addNewRoomRequest.setInfoRoom(infoRoom);
        System.out.println(adminService.addNewRoomToFloor(addNewRoomRequest,"Floor1"));
    }

}
