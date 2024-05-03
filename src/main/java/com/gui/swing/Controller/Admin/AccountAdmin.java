/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.gui.swing.Controller.Admin;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Acer
 */
public class AccountAdmin extends javax.swing.JPanel {

    /**
     * Creates new form AccountAdmin
     */
    private DefaultTableModel tableModel;

    public AccountAdmin() {
        initComponents();
        customizeTable();
    }

    private void customizeTable() {
        // Set up Table Model
        tableModel = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Cho phép cột "Action" có thể edit để chứa các button
                return column == 6;
            }
        };

        // Add columns to your table model
        tableModel.addColumn("ID");
        tableModel.addColumn("First Name");
        tableModel.addColumn("Last Name");
        tableModel.addColumn("Phone");
        tableModel.addColumn("CCCD");
        tableModel.addColumn("Email");
        tableModel.addColumn("Action");

        // Set the model to the table
        tableContent.setModel(tableModel);

        // Append a row
        tableModel.addRow(new Object[]{"1", "John", "Doe", "0123456789", "111222333", "johndoe@example.com", "Edit/Delete"});
        tableModel.addRow(new Object[]{"2", "John 1", "Doe 1", "0123456789", "111222333", "johndoe1@example.com", "Edit/Delete"});
        tableModel.addRow(new Object[]{"3", "John 2", "Doe 2", "0123456789", "111222333", "johndoe2@example.com", "Edit/Delete"});

        // Add button renderer and editor
        tableContent.getColumnModel().getColumn(6).setCellRenderer(new ButtonRenderer());
        tableContent.getColumnModel().getColumn(6).setCellEditor(new ButtonEditor(new JCheckBox()));
    }

    // Button Renderer
    public class ButtonRenderer extends JPanel implements TableCellRenderer {

        protected JButton editButton;
        protected JButton deleteButton;

        public ButtonRenderer() {
            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");
            setOpaque(true);

            // Thiết lập layout cho panel
            this.setLayout(new FlowLayout(FlowLayout.CENTER));
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
        protected JButton editButton;
        protected JButton deleteButton;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            editButton = new JButton("Edit");
            deleteButton = new JButton("Delete");

            panel.add(editButton);
            panel.add(deleteButton);

            // Bắt sự kiện cho nút Edit
            editButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    // Lấy dữ liệu từ bảng
                    int selectedRow = tableContent.getSelectedRow();
                    if (selectedRow >= 0) {
                        String id = tableModel.getValueAt(selectedRow, 0).toString();
                        String firstName = tableModel.getValueAt(selectedRow, 1).toString();
                        String lastName = tableModel.getValueAt(selectedRow, 2).toString();
                        String phone = tableModel.getValueAt(selectedRow, 3).toString();
                        String cccd = tableModel.getValueAt(selectedRow, 4).toString();
                        String email = tableModel.getValueAt(selectedRow, 5).toString();

                        // Tạo đối tượng EditAccount và set dữ liệu
                        EditAccount editAccountFrame = new EditAccount();
                        editAccountFrame.setFieldData(id, firstName, lastName, phone, cccd, email); // giả sử bạn đã viết phương thức này trong EditAccount

                        // Hiển thị frame
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
                        String firstName = tableContent.getModel().getValueAt(selectedRow, 1).toString();
                        String lastName = tableContent.getModel().getValueAt(selectedRow, 2).toString();

                        // Hiển thị hộp thoại xác nhận trung tâm màn hình
                        int confirm = JOptionPane.showConfirmDialog(
                                null, // sử dụng null để hộp thoại xuất hiện ở giữa màn hình
                                "Are you sure to delete account " + firstName + " " + lastName + "?",
                                "Confirm Delete",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.QUESTION_MESSAGE
                        );

                        if (confirm == JOptionPane.YES_OPTION) {
                            // Xóa dòng khỏi tableModel
                            tableModel.removeRow(selectedRow);
                            // Thêm mã để xóa khỏi database nếu cần

                            // Thông báo đã xóa thành công, lại hiển thị ở giữa màn hình
                            JOptionPane.showMessageDialog(
                                    null, // hiển thị ở giữa màn hình
                                    "Account " + firstName + " " + lastName + " deleted successfully.",
                                    "Successful",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
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

        typeSearch.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "First Name", "Last Name", "Phone", "CCCD", "Email" }));

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
        AddNewAcount addAccountFrame = new AddNewAcount();
        addAccountFrame.setLocationRelativeTo(null); // Đặt vị trí của frame ở giữa màn hình
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
