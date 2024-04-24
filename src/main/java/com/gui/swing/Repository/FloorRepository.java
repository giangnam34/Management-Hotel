package com.gui.swing.Repository;

import com.gui.swing.Entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FloorRepository extends JpaRepository<Floor, Integer> {

    Floor findByFloorName(String floorName);

    boolean existsFloorByFloorName(String floorName);


}