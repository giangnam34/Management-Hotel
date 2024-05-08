/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.gui.swing.Controller.Admin;

import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.Entity.Floor;
import com.gui.swing.Entity.Room;
import com.gui.swing.Entity.RoomInfo;
import com.gui.swing.Entity.Type;
import com.gui.swing.Entity.User;
import com.gui.swing.Service.FloorService;
import com.gui.swing.Service.RoomService;
import com.gui.swing.Service.TypeRoomService;
import com.gui.swing.Service.UserService;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Acer
 */
public class RoomAdmin extends javax.swing.JPanel {

    private ConfigurableApplicationContext context;
    private DefaultTableModel tableModel;

    /**
     * Creates new form RoomAdmin
     */
    public RoomAdmin() {
    }

    public RoomAdmin(ConfigurableApplicationContext context) {
        this.context = context;
        initComponents();
        fillData();
        populateTable("", "", "", "");

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputSearch.getText();
                String selectedTypeRoom = (String) filterTypeRoom.getSelectedItem();
                String selectedStatus = (String) filterStatus.getSelectedItem();
                String selectedFloor = (String) filterFloor.getSelectedItem();

                System.out.println(selectedStatus);

                // Thực hiện tìm kiếm tại đây
                populateTable(inputText, selectedTypeRoom, selectedStatus, selectedFloor);
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterFloor.setSelectedItem("--- Choose ---");
                filterTypeRoom.setSelectedItem("--- Choose ---");
                filterStatus.setSelectedItem("--- Choose ---");

                // Đặt lại trường nhập liệu tìm kiếm
                inputSearch.setText("");

                // Tải lại dữ liệu và hiển thị trên bảng (ví dụ mặc định là tài liệu trang đầu tiên)
                populateTable("", "--- Choose ---", "--- Choose ---", "Floor1");
            }
        });

    }

    private void fillFloor() {
        FloorService floorService = context.getBean(FloorService.class);
        List<Floor> floors = floorService.getAllFloor();

        filterFloor.removeAllItems();
        filterFloor.addItem("--- Choose ---");

        for (Floor floor : floors) {
            filterFloor.addItem(floor.getFloorName());
        }

        filterFloor.setSelectedItem("Floor1");
    }

    private void fillTypeRoom() {
        TypeRoomService typeRoomService = context.getBean(TypeRoomService.class);
        List<Type> listType = typeRoomService.getAllType();

        filterTypeRoom.removeAllItems();
        filterTypeRoom.addItem("--- Choose ---");

        for (Type type : listType) {
            filterTypeRoom.addItem(type.getRoomTypeValue().toString());
        }
    }

    private void fillData() {
        fillFloor();
        fillTypeRoom();
    }

    private void populateTable(String inputText, String selectedTypeRoom, String selectedStatus, String selectedFloor) {
        // Xác định tên các cột theo ý muốn của bạn
        String[] columnNames = {"ID", "Room Name", "Active", "Floor", "Type", "Action"};

        // Tạo model với tên cột đã định nghĩa
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Fetch rooms
        List<Room> rooms = getRooms(inputText, selectedTypeRoom, selectedStatus, selectedFloor);

        // Populate the table model with room data
        for (Room room : rooms) {
            // Create a row for each room
            Object[] row = new Object[]{
                room.getRoomId(),
                room.getRoomName(),
                room.getRoomIsActive() ? "Yes" : "No",
                room.getFloor().getFloorName(),
                room.getType().getRoomTypeValue(),};
            model.addRow(row);
        }

        // Set model to the table
        tableContent.setModel(model);

        // Set button renderer and editor for the Action column
        tableContent.getColumnModel().getColumn(5).setCellRenderer(new ButtonRenderer());
        tableContent.getColumnModel().getColumn(5).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Define the preferred width for the Action column if necessary
        tableContent.getColumnModel().getColumn(5).setPreferredWidth(250);
    }

    private List<Room> getRooms(String inputText, String selectedTypeRoom, String selectedStatus, String selectedFloor) {
        RoomService roomService = context.getBean(RoomService.class);
        if (inputText.isEmpty() && selectedFloor.isEmpty()) {
            return roomService.findByFloor("Floor1");
        }
        return roomService.getRoomsByFilters(inputText, selectedTypeRoom, selectedStatus, selectedFloor);
    }

    public class ButtonRenderer extends JPanel implements TableCellRenderer {

        protected JButton moreInfoButton;

        protected JButton editButton;

        protected JButton deleteButton;

        public ButtonRenderer() {
            moreInfoButton = new JButton("See more");
            editButton = new JButton("Edit");
            deleteButton = new JButton("Change status");
            setOpaque(true);

            // Thiết lập layout cho panel
            this.setLayout(new FlowLayout(FlowLayout.CENTER));
            this.add(moreInfoButton);
            this.add(editButton);
            this.add(deleteButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    public class ButtonEditor extends DefaultCellEditor {

        protected JPanel panel;
        protected JButton moreInfoButton;

        protected JButton editButton;

        protected JButton deleteButton;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            moreInfoButton = new JButton("See more");
            editButton = new JButton("Edit");
            deleteButton = new JButton("Change status");

            panel.add(moreInfoButton);
            panel.add(editButton);
            panel.add(deleteButton);

            // Bắt sự kiện cho nút Edit
            moreInfoButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Lấy dữ liệu từ bảng
                    int selectedRow = tableContent.getSelectedRow();
                    System.out.println(selectedRow);

                    if (selectedRow >= 0) {
                        int roomId = Integer.parseInt(tableContent.getModel().getValueAt(selectedRow, 0).toString());
                        System.out.println(roomId);
                        RoomService roomService = context.getBean(RoomService.class);
                        Room room = roomService.findByRoomId(roomId);

                        if (room != null) {
                            MoreInfoRoom moreInfoRoom = new MoreInfoRoom();
                            moreInfoRoom.setRoomInfo(room);
                            moreInfoRoom.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Room information is not available for Room ID: " + roomId,
                                    "Information Not Found",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Please select an account to edit.",
                                "No Selection",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
            });

            editButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Lấy dữ liệu từ bảng
                    int selectedRow = tableContent.getSelectedRow();
                    System.out.println(selectedRow);
                    if (selectedRow >= 0) {
                        int roomId = Integer.parseInt(tableContent.getModel().getValueAt(selectedRow, 0).toString());
                        RoomService roomService = context.getBean(RoomService.class);
                        Room room = roomService.findByRoomId(roomId);
                        // Tạo đối tượng EditAccount và set dữ liệu
                        EditRoomInfo editRoomInfo = new EditRoomInfo(context, room);
                        editRoomInfo.addWindowListener(new WindowAdapter() {
                            public void windowClosed(WindowEvent e) {
                                editRoomInfo.dispose();
                                // Thực hiện các hành động sau khi cửa sổ đã đóng
                                btnReset.doClick();
                            }
                        });
//                        editRoomInfo.setFieldData(room);
                        editRoomInfo.setVisible(true);
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Please select an account to edit.",
                                "No Selection",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
            });

            // Bắt sự kiện cho nút Delete
            deleteButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    int selectedRow = tableContent.getSelectedRow();
                    if (selectedRow >= 0) {
                        int roomId = Integer.parseInt(tableContent.getModel().getValueAt(selectedRow, 0).toString());
                        String roomName = tableContent.getModel().getValueAt(selectedRow, 1).toString();
                        String status = tableContent.getModel().getValueAt(selectedRow, 2).toString();

                        int confirm = JOptionPane.showConfirmDialog(
                                null,
                                "Are you sure to " + (status.equalsIgnoreCase("true") ? "deactivate" : "activate")
                                + " account " + roomName + "?",
                                "Confirm",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (confirm == JOptionPane.YES_OPTION) {

                            boolean newStatus = status.equalsIgnoreCase("Yes") ? false : true;
                            RoomService roomService = context.getBean(RoomService.class);
                            GeneralResponse response = roomService.changeStatusRoom(roomId, newStatus);

                            if (response.getStatus() == 1) { // Giả sử trường `status` là int, và giá trị 1 biểu diễn thành công
                                JOptionPane.showMessageDialog(
                                        null,
                                        (newStatus ? "Activated" : "Deactivated") + " account " + roomName + " successfully.",
                                        "Successful",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                                populateTable("", "", "", "");
                            } else {
                                JOptionPane.showMessageDialog(
                                        null,
                                        response.getMessage(), // Sử dụng thông điệp từ GeneralResponse
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                null,
                                "Please select an account to change status.",
                                "No Selection",
                                JOptionPane.WARNING_MESSAGE
                        );
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return ""; // Không trả về giá trị cụ thể nào
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        inputSearch = new javax.swing.JTextField();
        btnSearch1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableContent = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        filterFloor = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        filterTypeRoom = new javax.swing.JComboBox<>();
        jPanel11 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        filterStatus = new javax.swing.JComboBox<>();

        btnReset.setBackground(new java.awt.Color(255, 102, 102));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("RESET");

        btnSearch.setBackground(new java.awt.Color(51, 153, 255));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("SEARCH");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(inputSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                            .addComponent(inputSearch))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        btnSearch1.setBackground(new java.awt.Color(51, 153, 255));
        btnSearch1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSearch1.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch1.setText("ADD");
        btnSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch1ActionPerformed(evt);
            }
        });

        tableContent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Room Name", "IsActive", "Floor", "Type", "Action"
            }
        ));
        tableContent.setRowHeight(35);
        jScrollPane1.setViewportView(tableContent);

        jPanel7.setPreferredSize(new java.awt.Dimension(250, 85));

        jLabel3.setText("Floor");

        filterFloor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Choose ---", "1 Large", "1 Small", "2 Large", "2 Small", "1 Large , 1 Small" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterFloor, 0, 198, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterFloor, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );

        jPanel9.setPreferredSize(new java.awt.Dimension(250, 85));

        jLabel5.setText("Type of Room");

        filterTypeRoom.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Choose --- ", " " }));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterTypeRoom, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel5)
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterTypeRoom, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );

        jPanel11.setPreferredSize(new java.awt.Dimension(250, 85));

        jLabel7.setText("Status");

        filterStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Choose ---", "Yes", "No" }));

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterStatus, 0, 198, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel7)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterStatus, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 184, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(167, 167, 167)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(62, 62, 62))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(10, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(btnSearch1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(15, 15, 15))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed


    }//GEN-LAST:event_btnSearch1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JComboBox<String> filterBed;
    private javax.swing.JComboBox<String> filterFloor;
    private javax.swing.JComboBox<String> filterStatus;
    private javax.swing.JComboBox<String> filterType;
    private javax.swing.JComboBox<String> filterTypeRoom;
    private javax.swing.JTextField inputSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableContent;
    // End of variables declaration//GEN-END:variables
}
