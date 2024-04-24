package com.gui.swing.DTO.Request;

import com.gui.swing.DTO.Response.Pair;
import com.gui.swing.Entity.Enum.EnumTypeRoom;
import lombok.Data;

import java.util.List;


@Data
public class AddNewRoomRequest {

    private String roomName;

    private EnumTypeRoom roomType;

    private List<Pair> infoRoom;
}
