package com.gui.swing.Repository;

import com.gui.swing.Entity.Enum.EnumTypeRoom;
import com.gui.swing.Entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TypeRepository extends JpaRepository<Type, Integer> {

    public Type findByRoomTypeValue(EnumTypeRoom typeRoom);

    public Boolean existsByRoomTypeValue(EnumTypeRoom typeRoom);
}