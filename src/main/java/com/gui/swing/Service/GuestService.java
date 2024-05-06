package com.gui.swing.Service;


import com.gui.swing.Entity.Guest;
import com.gui.swing.Repository.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;


    public List<Guest> listAllGuest(){
        return guestRepository.findAll();
    }

    public List<Guest> listGuestWithFilter(String searchText, String searchType){
        List<Guest> result = listAllGuest();
        if (searchType.equalsIgnoreCase("first Name")){
            result = result.stream().filter(guest -> guest.getFirstName().toUpperCase().contains(searchText.toUpperCase())).toList();
        } if (searchType.equalsIgnoreCase("last Name")){
            result = result.stream().filter(guest -> guest.getLastName().toUpperCase().contains(searchText.toUpperCase())).toList();
        }if (searchType.equalsIgnoreCase("phone")){
            result = result.stream().filter(guest -> guest.getPhone().toUpperCase().contains(searchText.toUpperCase())).toList();
        } if (searchType.equalsIgnoreCase("email")){
            result = result.stream().filter(guest -> guest.getEmail().toUpperCase().contains(searchText.toUpperCase())).toList();
        } if (searchType.equalsIgnoreCase("cccd")){
            result = result.stream().filter(guest -> guest.getIdentificationCard().toUpperCase().contains(searchText.toUpperCase())).toList();
        }
        return result;
    }
}
