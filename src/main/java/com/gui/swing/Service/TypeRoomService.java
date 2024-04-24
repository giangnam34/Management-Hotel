package com.gui.swing.Service;

import com.gui.swing.Entity.Enum.EnumTypeRoom;
import com.gui.swing.Entity.Type;
import com.gui.swing.Repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TypeRoomService {

    @Autowired
    private TypeRepository typeRepository;

    public Type findTypeByName(EnumTypeRoom typeRoom){
        if (typeRepository.existsByRoomTypeValue(typeRoom))
            return typeRepository.findByRoomTypeValue(typeRoom);
        throw new IllegalArgumentException("Loại phòng không hợp lệ!");
    }

    // public Boolean addNewRoomType

    // public Boolean updateValueRoomType

    // public Boolean updatePriceRoomType
}
