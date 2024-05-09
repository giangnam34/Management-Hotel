package com.gui.swing.Service;

import com.gui.swing.DTO.Response.CheckoutRoomResponse;
import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.Entity.Enum.EnumTypeRent;
import com.gui.swing.Entity.Guest;
import com.gui.swing.Entity.Room;
import com.gui.swing.Entity.RoomGuest;
import com.gui.swing.Repository.RoomGuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingRoomService {

    @Autowired
    private RoomService roomService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private RoomGuestRepository roomGuestRepository;

    public List<RoomGuest> findAll(){
        return roomGuestRepository.findAll();
    }

    // Check in
    public GeneralResponse bookingRoom(String roomName, String typeBooking, String firstName, String lastName, String identification, String phone, String email, LocalDateTime timeBegin, LocalDateTime timeEnd){
        GeneralResponse generalResponse = new GeneralResponse();
        try {
            Room room = roomService.findByRoomName(roomName);
            RoomGuest roomGuest = new RoomGuest();
            roomGuest.setRoom(room);
            roomGuest.setDateBegin(timeBegin);
            roomGuest.setDateEnd(timeEnd);
            roomGuest.setTypeRent(typeBooking.equalsIgnoreCase("Hour") ? EnumTypeRent.Hour : EnumTypeRent.Day);
            Guest guest = new Guest();
            if (guestService.existGuestByIdentification(identification)){
                guest = guestService.findGuestByIdentification(identification);
            } else {
                if (email != null && !email.isEmpty()) guest.setEmail(email);
                if (phone != null && !phone.isEmpty()) guest.setPhone(phone);
                if (firstName != null && !firstName.isEmpty()) guest.setFirstName(firstName);
                if (lastName != null && !lastName.isEmpty()) guest.setLastName(lastName);
                guest.setIdentificationCard(identification);
                if (!guestService.addNewGuest(guest)) {
                    generalResponse.setStatus(-1);
                    generalResponse.setMessage("Thêm khách hàng mới thất bại!");
                }
            }
            roomGuest.setGuest(guest);
            roomGuestRepository.save(roomGuest);
            generalResponse.setStatus(1);
            generalResponse.setMessage("Đặt phòng thành công!");
        } catch (IllegalArgumentException illegalArgumentException){
            generalResponse.setStatus(-1);
            generalResponse.setMessage(illegalArgumentException.getMessage());
            return generalResponse;
        }
        return generalResponse;
    }

    public CheckoutRoomResponse checkOut(String roomName){
        CheckoutRoomResponse checkoutRoomResponse = new CheckoutRoomResponse();
        List<RoomGuest> roomGuestList = roomGuestRepository.findAll();
        RoomGuest findRoomGuest = roomGuestList.stream().filter(roomGuest -> roomGuest.getDateBegin().isBefore(LocalDateTime.now()) && roomGuest.getDateEnd().isAfter(LocalDateTime.now())).toList().get(0);
        findRoomGuest.setDateEnd(LocalDateTime.now());
        roomGuestRepository.save(findRoomGuest);
        checkoutRoomResponse.setTimeCheckOut(findRoomGuest.getDateEnd());
        checkoutRoomResponse.setCustomerIdentification(findRoomGuest.getGuest().getIdentificationCard());
        checkoutRoomResponse.setEmail(findRoomGuest.getGuest().getEmail());
        checkoutRoomResponse.setPhone(findRoomGuest.getGuest().getPhone());
        checkoutRoomResponse.setCustomerFirstName(findRoomGuest.getGuest().getFirstName());
        checkoutRoomResponse.setCustomerLastName(findRoomGuest.getGuest().getLastName());
        checkoutRoomResponse.setTotalPrice(calcPriceRoom(findRoomGuest));
        return checkoutRoomResponse;
    }

    // Check out

    // Calc price room  (loại đặt phòng, giờ)

    private Double calcPriceRoom(RoomGuest roomGuest){
        long time = calcTime(roomGuest.getDateBegin(),roomGuest.getDateEnd());
        if (roomGuest.getTypeRent() == EnumTypeRent.Day){
            time = time % 86400 == 0 ? time/86400 : time/86400 + 1;
            return roomGuest.getRoom().getType().getRoomTypePricePerDay()*time;
        } else {
            time = time % 3600 == 0 ? time/3600 : time/3600 + 1;
            return roomGuest.getRoom().getType().getRoomTypePricePerHour()*time;
        }
    }


    private Long calcTime(LocalDateTime dateBegin, LocalDateTime dateEnd){
        return ChronoUnit.SECONDS.between(dateBegin,dateEnd);
    }
    // Calc hour rent room




}
