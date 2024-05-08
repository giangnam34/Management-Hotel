package com.gui.swing.Controller.Rec;

import com.gui.swing.Controller.Admin.AdminDashboard;
import com.gui.swing.Entity.Room;
import com.gui.swing.Service.InfoRoomService;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Vector;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ReservationTableModel extends AbstractTableModel {

    private final Vector<String> columnNames;
    private final Vector<Vector<Object>> data;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private int floorNumber;
    private List<Room> roomList;

    private ConfigurableApplicationContext context;

    private LocalDateTime localDateTime;

    public ReservationTableModel(LocalDateTime localDateTime,ConfigurableApplicationContext context, int floorNumber, List<Room> roomList) {
        this.localDateTime = localDateTime;
        this.context = context;
        this.floorNumber = floorNumber;
        this.roomList = roomList;
        columnNames = new Vector<>();
        data = new Vector<>();

        // Định nghĩa các khung giờ
        String[] timeSlots = new String[]{
            "0h-3h", "3h-6h", "6h-9h", "9h-12h", "12h-15h",
            "15h-18h", "18h-21h", "21h-24h"
        };

        // Thêm tên phòng làm cột đầu tiên
        columnNames.add("Room / Time Slots");

        // Thêm các khung giờ vào tiêu đề cột 
        for (String slot : timeSlots) {
            columnNames.add(slot);
        }

        InfoRoomService infoRoomService = context.getBean(InfoRoomService.class);
        // Sử dụng roomList đã được truyền vào để thêm dữ liệu cho mỗi phòng
        for (Room room : roomList) {
            LocalDateTime timeBegin = localDateTime.toLocalDate().atStartOfDay();
            LocalDateTime timeEnd = timeBegin.plusHours(3);
            Vector<Object> row = new Vector<>();
            row.add(room.getRoomName()); // Tên phòng
            for (String timeSlot : timeSlots) {
                boolean isAvailable;
                if (timeBegin.isBefore(LocalDateTime.now())){
                    if (timeEnd.isAfter(LocalDateTime.now())) {
                        isAvailable = !infoRoomService.isRoomRent(room.getRoomId(), LocalDateTime.now(), timeEnd);
                    } else {
                        isAvailable = false;
                    }
                } else {
                    isAvailable = !infoRoomService.isRoomRent(room.getRoomId(), timeBegin, timeEnd);
                }
                JButton button = new JButton(isAvailable ? "Available" : "Not Available");
                button.setBackground(isAvailable ? Color.GREEN : Color.RED);
                LocalDateTime finalTimeBegin = timeBegin;
                LocalDateTime finalTimeEnd = timeEnd;
                button.addActionListener((ActionEvent e) -> {
                    if (isAvailable) {
                        if (localDateTime.isAfter(LocalDateTime.now().toLocalDate().atStartOfDay()) &&
                            localDateTime.isBefore(LocalDateTime.now().toLocalDate().atStartOfDay().plusHours(24))) {
                            BookingRoom bookingRoom = new BookingRoom(context, room.getRoomName());
                            bookingRoom.setVisible(true);
                        } else {
                            System.out.println("Another booking room");
                        }
                    } else {
                        // Hiển thị hộp thoại với hai lựa chọn: Thanh toán và Hủy
                        if (infoRoomService.isRoomRent(room.getRoomId(), finalTimeBegin, finalTimeEnd)) {
                            int response = JOptionPane.showConfirmDialog(null, "This room has been booked, do you want to pay for the room?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                            if (response == JOptionPane.YES_OPTION) {
                                PaymentForm paymentForm = new PaymentForm();
                                paymentForm.setVisible(true);
                            } else if (response == JOptionPane.NO_OPTION) {

                            }
                        }
                    }
                });
                row.add(button);
                timeBegin = timeBegin.plusHours(3);
                timeEnd = timeEnd.plusHours(3);
            }
            data.add(row);
        }
    }

    @Override
    public int getRowCount() {
        return data.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.size();
    }

    @Override
    public String getColumnName(int column) {
        return columnNames.get(column);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data.get(rowIndex).get(columnIndex);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // Chắc chắn rằng chỉ các cột chứa JButton mới trả về JButton.class
        return (columnIndex == 0) ? String.class : JButton.class;
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex != 0; // Chỉ cho phép chỉnh sửa các cột nút
    }

    // Sử dụng các lớp ButtonRenderer và ButtonEditor làm cách triển khai TableCellRenderer và TableCellEditor
}
