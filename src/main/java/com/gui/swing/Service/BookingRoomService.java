package com.gui.swing.Service;

import com.gui.swing.DTO.Response.CheckoutRoomResponse;
import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.Entity.Enum.EnumTypeRent;
import com.gui.swing.Entity.Guest;
import com.gui.swing.Entity.Room;
import com.gui.swing.Entity.RoomGuest;
import com.gui.swing.Repository.RoomGuestRepository;
import java.text.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookingRoomService {

    @Autowired
    private RoomService roomService;

    @Autowired
    private GuestService guestService;

    @Autowired
    private RoomGuestRepository roomGuestRepository;

    public List<RoomGuest> findAll() {
        return roomGuestRepository.findAll();
    }

    public List<RoomGuest> getBookingRoomByFilters(String inputText, String selectedType, String dateCheckIn, String dateCheckOut) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        List<RoomGuest> bookingRoomList = roomGuestRepository.findAll();

        LocalDate checkInDate = null;
        LocalDate checkOutDate = null;
        String inputTextLower = inputText.toLowerCase();

        try {
            if (!dateCheckIn.isEmpty()) {
                Date parsedDateCheckIn = dateFormat.parse(dateCheckIn);
                checkInDate = parsedDateCheckIn.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
            if (!dateCheckOut.isEmpty()) {
                Date parsedDateCheckOut = dateFormat.parse(dateCheckOut);
                checkOutDate = parsedDateCheckOut.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final LocalDate finalCheckInDate = checkInDate;
        final LocalDate finalCheckOutDate = checkOutDate;

        if (!inputText.isEmpty()) {
            switch (selectedType) {
                case "ID":
                    bookingRoomList = bookingRoomList.stream()
                            .filter(roomGuest -> roomGuest.getGuest().getIdentificationCard().toLowerCase().contains(inputTextLower))
                            .collect(Collectors.toList());
                    break;
                case "First Name":
                    bookingRoomList = bookingRoomList.stream()
                            .filter(roomGuest -> roomGuest.getGuest().getFirstName().toLowerCase().contains(inputTextLower))
                            .collect(Collectors.toList());
                    break;
                case "Last Name":
                    bookingRoomList = bookingRoomList.stream()
                            .filter(roomGuest -> roomGuest.getGuest().getLastName().toLowerCase().contains(inputTextLower))
                            .collect(Collectors.toList());
                    break;
                case "Room":
                    bookingRoomList = bookingRoomList.stream()
                            .filter(roomGuest -> roomGuest.getRoom().getRoomName().toLowerCase().contains(inputTextLower))
                            .collect(Collectors.toList());
                    break;
                default:
                    // Do nothing or handle default case
                    break;
            }
        }

        if (finalCheckInDate != null || finalCheckOutDate != null) {
            bookingRoomList = bookingRoomList.stream()
                    .filter(roomGuest -> {
                        LocalDate roomCheckInDate = roomGuest.getDateBegin().toLocalDate();
                        LocalDate roomCheckOutDate = roomGuest.getDateEnd().toLocalDate();

                        // Filter RoomGuests within the selected date range
                        if (finalCheckInDate != null && finalCheckOutDate != null) {
                            return roomCheckInDate.isEqual(finalCheckInDate) && roomCheckOutDate.isEqual(finalCheckOutDate);
                        } else if (finalCheckInDate != null) {
                            // Filter by check-in date
                            return roomCheckInDate.isEqual(finalCheckInDate);
                        } else { // finalCheckOutDate != null
                            // Filter by check-out date
                            return roomCheckOutDate.isEqual(finalCheckOutDate);
                        }
                    })
                    .collect(Collectors.toList());
        }

        return bookingRoomList;
    }

    // Check in
    public GeneralResponse bookingRoom(String roomName, String typeBooking, String firstName, String lastName, String identification, String phone, String email, LocalDateTime timeBegin, LocalDateTime timeEnd) {
        GeneralResponse generalResponse = new GeneralResponse();
        try {
            Room room = roomService.findByRoomName(roomName);
            RoomGuest roomGuest = new RoomGuest();
            roomGuest.setRoom(room);
            roomGuest.setDateBegin(timeBegin);
            roomGuest.setDateEnd(timeEnd);
            roomGuest.setTypeRent(typeBooking.equalsIgnoreCase("Hour") ? EnumTypeRent.Hour : EnumTypeRent.Day);
            Guest guest = new Guest();
            if (guestService.existGuestByIdentification(identification)) {
                guest = guestService.findGuestByIdentification(identification);
            } else {
                if (email != null && !email.isEmpty()) {
                    guest.setEmail(email);
                }
                if (phone != null && !phone.isEmpty()) {
                    guest.setPhone(phone);
                }
                if (firstName != null && !firstName.isEmpty()) {
                    guest.setFirstName(firstName);
                }
                if (lastName != null && !lastName.isEmpty()) {
                    guest.setLastName(lastName);
                }
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
        } catch (IllegalArgumentException illegalArgumentException) {
            generalResponse.setStatus(-1);
            generalResponse.setMessage(illegalArgumentException.getMessage());
            return generalResponse;
        }
        return generalResponse;
    }

    public CheckoutRoomResponse checkOut(String roomName) {
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
    private Double calcPriceRoom(RoomGuest roomGuest) {
        long time = calcTime(roomGuest.getDateBegin(), roomGuest.getDateEnd());
        if (roomGuest.getTypeRent() == EnumTypeRent.Day) {
            time = time % 86400 == 0 ? time / 86400 : time / 86400 + 1;
            return roomGuest.getRoom().getType().getRoomTypePricePerDay() * time;
        } else {
            time = time % 3600 == 0 ? time / 3600 : time / 3600 + 1;
            return roomGuest.getRoom().getType().getRoomTypePricePerHour() * time;
        }
    }

    private Long calcTime(LocalDateTime dateBegin, LocalDateTime dateEnd) {
        return ChronoUnit.SECONDS.between(dateBegin, dateEnd);
    }

    public double getTotalRevenue() {
        LocalDateTime today = LocalDateTime.now();
        List<RoomGuest> roomGuestList = roomGuestRepository.findAll();
        double totalRevenue = 0.0;

        for (RoomGuest roomGuest : roomGuestList) {
            LocalDateTime checkInDate = roomGuest.getDateBegin();
            LocalDateTime checkOutDate = roomGuest.getDateEnd();

            if (checkInDate.isBefore(today) && checkOutDate.isAfter(today)) {
                totalRevenue += calcPriceRoom(roomGuest);
            }
        }

        return totalRevenue;
    }

    // Đếm số lượng phòng đã được đặt
    public int getBookedRoomCount() {
        LocalDateTime today = LocalDateTime.now();
        List<RoomGuest> roomGuestList = roomGuestRepository.findAll();
        Set<String> bookedRoomNames = new HashSet<>();

        for (RoomGuest roomGuest : roomGuestList) {
            LocalDateTime checkInDate = roomGuest.getDateBegin();
            LocalDateTime checkOutDate = roomGuest.getDateEnd();

            if ((checkInDate.isBefore(today) && checkOutDate.isAfter(today))) {
                bookedRoomNames.add(roomGuest.getRoom().getRoomName());
            }
        }

        return bookedRoomNames.size();
    }
    // Calc hour rent room

}
