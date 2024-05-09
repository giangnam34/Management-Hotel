package com.gui.swing.Service;

import com.gui.swing.DTO.Response.AddNewFloorResponse;
import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.Entity.Enum.EnumTypeRoom;
import com.gui.swing.Entity.Floor;
import com.gui.swing.Entity.Type;
import com.gui.swing.Repository.TypeRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TypeRoomService {

    @Autowired
    private TypeRepository typeRepository;

    public Type findTypeByName(EnumTypeRoom typeRoom) {
        if (typeRepository.existsByRoomTypeValue(typeRoom)) {
            return typeRepository.findByRoomTypeValue(typeRoom);
        }
        throw new IllegalArgumentException("Loại phòng không hợp lệ!");
    }

    public List<Type> getAllType() {
        return typeRepository.findAll();

    }

//    public GeneralResponse updatePriceRoomType(EnumTypeRoom typeRoom, Double price) {
//        try {
//            Type type = findTypeByName(typeRoom);
//            type.setRoomTypePrice(price);
//            typeRepository.save(type);
//        } catch (IllegalArgumentException | DataAccessException exception) {
//            return new GeneralResponse(0, exception.getMessage());
//        }
//        return new GeneralResponse(1, "Cập nhật giá thành công!");
//    }
}
