package com.gui.swing.Repository;

import com.gui.swing.Entity.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Integer> {

    public Boolean existsGuestByIdentificationCard(String identificationCard);

    public Guest findGuestByIdentificationCard(String identificationCard);
}