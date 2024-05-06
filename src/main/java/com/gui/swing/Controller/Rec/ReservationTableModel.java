package com.gui.swing.Controller.Rec;

import com.gui.swing.Controller.Admin.AdminDashboard;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.text.SimpleDateFormat;
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
    private List<String> roomList;

    public ReservationTableModel(int floorNumber, List<String> roomList) {
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

        // Sử dụng roomList đã được truyền vào để thêm dữ liệu cho mỗi phòng
        for (String room : roomList) {
            Vector<Object> row = new Vector<>();
            row.add(room); // Tên phòng
            for (String timeSlot : timeSlots) {
                boolean isAvailable = (Math.random() < 0.5); // Lấy ngẫu nhiên true hoặc false
                JButton button = new JButton(isAvailable ? "Available" : "Not Available");
                button.setBackground(isAvailable ? Color.GREEN : Color.RED);
                button.addActionListener((ActionEvent e) -> {
                    if (isAvailable) {
                        BookingRoom bookingRoom = new BookingRoom(room);
                        bookingRoom.setVisible(true);
                    } else {
                        // Hiển thị hộp thoại với hai lựa chọn: Thanh toán và Hủy
                        int response = JOptionPane.showConfirmDialog(null, "This room has been booked, do you want to pay for the room?", "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (response == JOptionPane.YES_OPTION) {
                            PaymentForm paymentForm = new PaymentForm();
                            paymentForm.setVisible(true);
                        } else if (response == JOptionPane.NO_OPTION) {
                           
                        }
                    }
                });
                row.add(button);
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
