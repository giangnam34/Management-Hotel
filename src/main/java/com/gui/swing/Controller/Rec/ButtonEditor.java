/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gui.swing.Controller.Rec;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;

/**
 *
 * @author Acer
 */
public class ButtonEditor extends DefaultCellEditor {

    protected JButton button;
    private String label;
    private boolean isPushed;
    private final JFrame parentFrame; // Biến để lưu JFrame chính hoặc container cha

    public ButtonEditor(JCheckBox checkBox, JFrame parentFrame) {
        super(checkBox);
        this.parentFrame = parentFrame;
        button = new JButton();
        button.setOpaque(true);
        button.addActionListener((ActionEvent e) -> {
            // Xử lý hành động khi nút được nhấn, ví dụ:
            System.out.println("Check");
            
            // Thêm mã xử lý khác tại đây nếu cần thiết
            // Dừng việc chỉnh sửa ô
            fireEditingStopped();
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        if (value instanceof JButton jButton) {
            button = jButton;
            label = button.getText(); // Thiết lập label cho giá trị trả về khi chỉnh sửa
            isPushed = true;
            button.addActionListener((ActionEvent e) -> {
                // Hành động khi nút được nhấn
                fireEditingStopped();
            });
        }
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        if (isPushed) {
            // Đặt hành động cho button ở đây nếu cần thiết
        }
        isPushed = false;
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        isPushed = false;
        return super.stopCellEditing();
    }
}
