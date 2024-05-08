package com.gui.swing.Repository;

import com.gui.swing.Entity.RoomInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomInfoRepository extends JpaRepository<RoomInfo, Integer> {
}