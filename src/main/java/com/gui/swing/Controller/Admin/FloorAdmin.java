/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.gui.swing.Controller.Admin;

import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.Entity.Floor;
import com.gui.swing.Entity.Room;
import com.gui.swing.Service.FloorService;
import com.gui.swing.Service.RoomService;
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
public class FloorAdmin extends javax.swing.JPanel {

    private ConfigurableApplicationContext context;

    /**
     * Creates new form IncomeAdmin
     */
    public FloorAdmin() {
    }

    public FloorAdmin(ConfigurableApplicationContext context) {
        this.context = context;
        initComponents();
        populateTable("", "");

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = inputSearch.getText();

                String selectedStatus = (String) filterStatus.getSelectedItem();

                System.out.println(selectedStatus);

                populateTable(inputText, selectedStatus);
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterStatus.setSelectedItem("--- Choose ---");

                // Đặt lại trường nhập liệu tìm kiếm
                inputSearch.setText("");

                // Tải lại dữ liệu và hiển thị trên bảng (ví dụ mặc định là tài liệu trang đầu tiên)
                populateTable("", "--- Choose ---");
            }
        });
    }

    private void populateTable(String inputText, String selectedStatus) {
        // Xác định tên các cột theo ý muốn của bạn
        String[] columnNames = {"ID", "Floor Name", "Active", "Action"};

        // Tạo model với tên cột đã định nghĩa
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        // Fetch rooms
        List<Floor> floors = getFloors(inputText, selectedStatus);

        System.out.println("Floor size: " + floors.size());

        // Populate the table model with room data
        for (Floor floor : floors) {
            // Create a row for each room
            Object[] row = new Object[]{
                floor.getFloorId(),
                floor.getFloorName(),
                (floor.getIsActive() != null && floor.getIsActive()) ? "Yes" : "No",};
            model.addRow(row);
        }

        tableContent.setModel(model);
        tableContent.getColumnModel().getColumn(3).setCellRenderer(new ButtonRenderer());
        tableContent.getColumnModel().getColumn(3).setCellEditor(new ButtonEditor(new JCheckBox()));

        // Set button renderer and editor for the Action column
    }

    private List<Floor> getFloors(String inputText, String selectedStatus) {
        FloorService floorService = context.getBean(FloorService.class);
        if (inputText.isEmpty() && selectedStatus.isEmpty()) {
            return floorService.getAllFloor();
        }
        return floorService.getFloorsByFilters(inputText, selectedStatus);
    }

    public class ButtonRenderer extends JPanel implements TableCellRenderer {

        private JButton moreInfoButton;
        private JButton editButton;
        private JButton changeStatusButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.CENTER));
            moreInfoButton = new JButton("See more");
            editButton = new JButton("Edit");
            changeStatusButton = new JButton("Change status");

            add(moreInfoButton);
            add(editButton);
            add(changeStatusButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    public class ButtonEditor extends DefaultCellEditor {

        private JPanel panel;
        private JButton moreInfoButton;
        private JButton editButton;
        private JButton changeStatusButton;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            moreInfoButton = new JButton("See more");
            editButton = new JButton("Edit");
            changeStatusButton = new JButton("Change status");

            panel.add(moreInfoButton);
            panel.add(editButton);
            panel.add(changeStatusButton);

            moreInfoButton.addActionListener(e -> {
                int selectedRow = tableContent.getSelectedRow();
                if (selectedRow >= 0) {
                    int roomId = Integer.parseInt(tableContent.getModel().getValueAt(selectedRow, 0).toString());
                    String floorName = tableContent.getModel().getValueAt(selectedRow, 1).toString();
                    System.out.println(roomId);
                    FloorService floorService = context.getBean(FloorService.class);
                    Floor floor = floorService.findFloorByName(floorName);

                    if (floor != null) {
                        MoreInfoFloor moreInfoFloor = new MoreInfoFloor();
                        moreInfoFloor.setFloorInfo(floor);
                        moreInfoFloor.setVisible(true);
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
            });

            editButton.addActionListener(e -> {
                int selectedRow = tableContent.getSelectedRow();
                if (selectedRow >= 0) {
                    String floorName = tableContent.getModel().getValueAt(selectedRow, 1).toString();
                    FloorService floorService = context.getBean(FloorService.class);
                    Floor floor = floorService.findFloorByName(floorName);

                    EditFloorInfo editFloorInfo = new EditFloorInfo(context, floor);
                    editFloorInfo.addWindowListener(new WindowAdapter() {
                        public void windowClosed(WindowEvent e) {
                            editFloorInfo.dispose();
                            // Thực hiện các hành động sau khi cửa sổ đã đóng
                            btnReset.doClick();
                        }
                    });
//                        editRoomInfo.setFieldData(room);
                    editFloorInfo.setVisible(true);
                }
            });

            changeStatusButton.addActionListener(e -> {
                int selectedRow = tableContent.getSelectedRow();
                if (selectedRow >= 0) {
                    String floorName = tableContent.getModel().getValueAt(selectedRow, 1).toString();
                    String status = tableContent.getModel().getValueAt(selectedRow, 2).toString();

                    int confirm = JOptionPane.showConfirmDialog(
                            null,
                            "Are you sure to " + (status.equalsIgnoreCase("true") ? "deactivate" : "activate")
                            + " account " + floorName + "?",
                            "Confirm",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE
                    );

                    if (confirm == JOptionPane.YES_OPTION) {

                        boolean newStatus = status.equalsIgnoreCase("Yes") ? false : true;
                        FloorService floorService = context.getBean(FloorService.class);
                        GeneralResponse response = floorService.changeStatusFloor(floorName, newStatus);

                        if (response.getStatus() == 1) { // Giả sử trường `status` là int, và giá trị 1 biểu diễn thành công
                            JOptionPane.showMessageDialog(
                                    null,
                                    (newStatus ? "Activated" : "Deactivated") + " account " + floorName + " successfully.",
                                    "Successful",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                            populateTable("", "");
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
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value,
                boolean isSelected, int row, int column) {
            return panel;
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

        jPanel2 = new javax.swing.JPanel();
        btnReset = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        inputSearch = new javax.swing.JTextField();
        filterStatus = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableContent = new javax.swing.JTable();
        btnCreate = new javax.swing.JButton();

        btnReset.setBackground(new java.awt.Color(255, 102, 102));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("RESET");

        btnSearch.setBackground(new java.awt.Color(51, 153, 255));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("SEARCH");

        filterStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Choose ---", "Yes", "No" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 626, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(filterStatus, 0, 183, Short.MAX_VALUE)
                .addGap(18, 18, 18)
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
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                            .addComponent(inputSearch)
                            .addComponent(filterStatus))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

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

        btnCreate.setBackground(new java.awt.Color(51, 153, 255));
        btnCreate.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnCreate.setForeground(new java.awt.Color(255, 255, 255));
        btnCreate.setText("ADD");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(62, 62, 62))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 466, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(42, 42, 42))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        AddNewFloor addFloor = new AddNewFloor(context);
        addFloor.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                addFloor.dispose();

                btnReset.doClick();
            }
        });
        addFloor.setVisible(true);
    }//GEN-LAST:event_btnCreateActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> filterStatus;
    private javax.swing.JTextField inputSearch;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableContent;
    // End of variables declaration//GEN-END:variables
}
