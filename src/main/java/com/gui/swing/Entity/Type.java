package com.gui.swing.Entity;

import com.gui.swing.Entity.Enum.EnumTypeRoom;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Entity
@Data
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomTypeId;

    @Enumerated(EnumType.STRING)
    private EnumTypeRoom roomTypeValue;

    @Min(0)
    private Double roomTypePricePerHour;

    @Min(0)
    private Double roomTypePricePerDay;

    @OneToMany(mappedBy = "type")
    private List<Room> roomList;


}
