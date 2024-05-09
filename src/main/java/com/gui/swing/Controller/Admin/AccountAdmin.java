/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.gui.swing.Controller.Admin;

import com.gui.swing.DTO.UserDTO;
import com.gui.swing.Entity.User;
import com.gui.swing.Service.UserService;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Acer
 */
public class AccountAdmin extends javax.swing.JPanel {

    /**
     * Creates new form AccountAdmin
     */
    private ConfigurableApplicationContext context;
    private DefaultTableModel tableModel;

    public AccountAdmin() {

    }

    public AccountAdmin(ConfigurableApplicationContext context) {
        this.context = context;
        initComponents();
        populateTable("", "");

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchText = inputSearch.getText();
                String searchType = String.valueOf(typeSearch.getSelectedItem());
                // Thực hiện tìm kiếm tại đây
                populateTable(searchText, searchType);
                System.out.println("Search Text: " + searchText + ", Search Type: " + searchType);
            }
        });

        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                inputSearch.setText("");
                populateTable("", "");
            }
        });
    }

    private void populateTable(String searchText, String searchType) {
        String[] columnNames = {"ID", "Email", "Active", "Role", "Action"};
        List<UserDTO> accountList = getDataAccount(searchText, searchType);
        Object[][] data = new Object[accountList.size()][UserDTO.class.getDeclaredFields().length];

        for (int i = 0; i < accountList.size(); i++) {
            data[i][0] = accountList.get(i).getId();
            data[i][1] = accountList.get(i).getUserName();
            data[i][2] = accountList.get(i).getIsActive();
            data[i][3] = accountList.get(i).getRole();
        }
        DefaultTableModel model = new DefaultTableModel(
                data,
                columnNames
        );

        // Set the model to the table
        tableContent.setModel(model);

        // Add button renderer and editor
        tableContent.getColumnModel().getColumn(4).setCellRenderer(new ButtonRenderer());
        tableContent.getColumnModel().getColumn(4).setCellEditor(new ButtonEditor(new JCheckBox()));
        tableContent.getColumnModel().getColumn(4).setPreferredWidth(250);
    }

    private List<UserDTO> getDataAccount(String searchText, String searchType) {
        UserService userService = context.getBean(UserService.class);
        if (searchText.isEmpty() && searchType.isEmpty()) {
            return userService.listAllUser();
        }
        return userService.listUserWithFilter(searchText, searchType);
    }

    // Button Renderer
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

    // Button Editor
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
                        String userName = tableContent.getModel().getValueAt(selectedRow, 1).toString();
                        System.out.println(userName);
                        UserService userService = context.getBean(UserService.class);
                        User user = userService.findUserByName(userName);
                        // Tạo đối tượng EditAccount và set dữ liệu
                        MoreInfoAccount moreInfoAccount = new MoreInfoAccount();
                        moreInfoAccount.setFieldData(user.getUserName(), user.getFullName(), user.getPhone(), user.getRole().getRoleName().toUpperCase(), user.getIdentificationCard(), String.valueOf(user.getUserIsActive()).toUpperCase()); // giả sử bạn đã viết phương thức này trong EditAccount
//
//                        // Hiển thị frame
                        moreInfoAccount.setLocationRelativeTo(null); // để vị trí ở giữa màn hình
                        moreInfoAccount.setVisible(true);
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
                        String userName = tableContent.getModel().getValueAt(selectedRow, 1).toString();
                        System.out.println(userName);
                        UserService userService = context.getBean(UserService.class);
                        User user = userService.findUserByName(userName);
                        // Tạo đối tượng EditAccount và set dữ liệu
                        EditAccount editAccountFrame = new EditAccount(context);
                        editAccountFrame.setFieldData(user.getUserName(), user.getFullName(), user.getPhone(), user.getRole().getRoleName().toUpperCase(), user.getIdentificationCard(), String.valueOf(user.getUserIsActive()).toUpperCase()); // giả sử bạn đã viết phương thức này trong EditAccount
//
//                        // Hiển thị frame
                        editAccountFrame.setLocationRelativeTo(null); // để vị trí ở giữa màn hình
                        editAccountFrame.setVisible(true);
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
                        String userName = tableContent.getModel().getValueAt(selectedRow, 1).toString();
                        String status = tableContent.getModel().getValueAt(selectedRow, 2).toString();

                        // Hiển thị hộp thoại xác nhận trung tâm màn hình
                        int confirm = JOptionPane.showConfirmDialog(
                                null, // sử dụng null để hộp thoại xuất hiện ở giữa màn hình
                                "Are you sure to " + (status.equalsIgnoreCase("true") ? "deactive" : "active") + " account " + userName + "?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            // Xóa dòng khỏi tableModel
//                            tableModel.removeRow(selectedRow);
                            // Thêm mã để xóa khỏi database nếu cần
                            UserService userService = context.getBean(UserService.class);
                            Boolean result = userService.updateStatusUser(userName);
                            // Thông báo đã xóa thành công, lại hiển thị ở giữa màn hình
                            if (result) {
                                JOptionPane.showMessageDialog(
                                        null, // hiển thị ở giữa màn hình
                                        (status.equalsIgnoreCase("true") ? "Deactive" : "Active") + " account " + userName + " successfully.",
                                        "Successful",
                                        JOptionPane.INFORMATION_MESSAGE
                                );
                                populateTable("", "");
                            } else {
                                JOptionPane.showMessageDialog(
                                        null, // hiển thị ở giữa màn hình
                                        "Have some error. Please try again!!!",
                                        "Error",
                                        JOptionPane.ERROR_MESSAGE
                                );
                            }
                        }
                    } else {
                        JOptionPane.showMessageDialog(
                                null, // hiển thị ở giữa màn hình
                                "Please select an account to delete.",
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
        inputSearch = new javax.swing.JTextField();
        typeSearch = new javax.swing.JComboBox<>();
        btnSearch = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tableContent = new javax.swing.JTable();
        btnSearch1 = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(1258, 700));

        typeSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Full Name", "Role", "CCCD", "User Name", "Is Active" }));

        btnSearch.setBackground(new java.awt.Color(51, 153, 255));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("SEARCH");

        btnReset.setBackground(new java.awt.Color(255, 102, 102));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("RESET");

        tableContent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "First Name", "Last Name", "Phone", "CCCD", "Email", "Action"
            }
        ));
        tableContent.setRowHeight(35);
        jScrollPane1.setViewportView(tableContent);

        btnSearch1.setBackground(new java.awt.Color(51, 153, 255));
        btnSearch1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSearch1.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch1.setText("ADD");
        btnSearch1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearch1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 559, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(typeSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(btnSearch1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(inputSearch)
                    .addComponent(typeSearch)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(btnSearch1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(58, 58, 58))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearch1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearch1ActionPerformed

        AddNewAccount addAccountFrame = new AddNewAccount(context);
        addAccountFrame.setLocationRelativeTo(null); // Đặt vị trí của frame ở giữa màn hình
        addAccountFrame.addWindowListener(new WindowAdapter() {
            public void windowClosed(WindowEvent e) {
                addAccountFrame.dispose();
                // Thực hiện các hành động sau khi cửa sổ đã đóng
                populateTable("", "");
            }
        });
//        addAccountFrame.addWindowListener(new AccountAdmin(context));
        addAccountFrame.setVisible(true);
    }//GEN-LAST:event_btnSearch1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearch1;
    private javax.swing.JTextField inputSearch;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableContent;
    private javax.swing.JComboBox<String> typeSearch;
    // End of variables declaration//GEN-END:variables
}
