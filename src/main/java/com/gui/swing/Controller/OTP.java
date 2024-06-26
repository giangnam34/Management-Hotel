/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.gui.swing.Controller;

import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.Service.Interface.AuthenticationService;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import javax.swing.JTextField;
import org.springframework.context.ConfigurableApplicationContext;

/**
 *
 * @author Acer
 */
public class OTP extends javax.swing.JFrame {

    private ConfigurableApplicationContext context;

    private javax.swing.Timer waitTimer;

    private String email;

    /**
     * Creates new form OTP
     */
    public OTP() {
        initComponents();

        setLocationRelativeTo(null);

        // Thêm KeyListener cho các ô input
        txtNum1.addKeyListener(new OTP.DigitKeyListener());
        txtNum2.addKeyListener(new OTP.DigitKeyListener());
        txtNum3.addKeyListener(new OTP.DigitKeyListener());
        txtNum4.addKeyListener(new OTP.DigitKeyListener());

        txtNum1.setHorizontalAlignment(JTextField.CENTER);
        txtNum2.setHorizontalAlignment(JTextField.CENTER);
        txtNum3.setHorizontalAlignment(JTextField.CENTER);
        txtNum4.setHorizontalAlignment(JTextField.CENTER);
    }

    public OTP(String email) {
        this.email = email;
        initComponents();

        setLocationRelativeTo(null);

        // Thêm KeyListener cho các ô input
        txtNum1.addKeyListener(new OTP.DigitKeyListener());
        txtNum2.addKeyListener(new OTP.DigitKeyListener());
        txtNum3.addKeyListener(new OTP.DigitKeyListener());
        txtNum4.addKeyListener(new OTP.DigitKeyListener());

        txtNum1.setHorizontalAlignment(JTextField.CENTER);
        txtNum2.setHorizontalAlignment(JTextField.CENTER);
        txtNum3.setHorizontalAlignment(JTextField.CENTER);
        txtNum4.setHorizontalAlignment(JTextField.CENTER);
    }

    public OTP(ConfigurableApplicationContext context, String email) {
        this.context = context;       
        this.email = email;

        initComponents();

        setLocationRelativeTo(null);

        // Thêm KeyListener cho các ô input
        txtNum1.addKeyListener(new OTP.DigitKeyListener());
        txtNum2.addKeyListener(new OTP.DigitKeyListener());
        txtNum3.addKeyListener(new OTP.DigitKeyListener());
        txtNum4.addKeyListener(new OTP.DigitKeyListener());

        txtNum1.setHorizontalAlignment(JTextField.CENTER);
        txtNum2.setHorizontalAlignment(JTextField.CENTER);
        txtNum3.setHorizontalAlignment(JTextField.CENTER);
        txtNum4.setHorizontalAlignment(JTextField.CENTER);
    }

    private boolean isAllFieldsFilled() {
        return !txtNum1.getText().trim().isEmpty()
                && !txtNum2.getText().trim().isEmpty()
                && !txtNum3.getText().trim().isEmpty()
                && !txtNum4.getText().trim().isEmpty();
    }

    private void changeButtonStateDuringSending() {
        btnSend.setText("SENDING...");
        btnSend.setEnabled(false); // Vô hiệu hóa nút khi người dùng nhấn

        waitTimer = new javax.swing.Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OTP.this.setVisible(false); // Ẩn form OTP
                // Khởi tạo và hiển thị form ResetPassword
                new ResetPassword(context,email).setVisible(true);
                OTP.this.dispose(); // Đóng form OTP
            }
        });
        waitTimer.setRepeats(false);
        waitTimer.start();
    }

    private void centerTextInTextPane(JTextPane textPane) {
        StyledDocument doc = textPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
    }

    private void moveCursorToNextTextPane(JTextPane currentTextPane, JTextPane nextTextPane) {
        if (currentTextPane.getText().length() >= 1) {
            nextTextPane.requestFocus();
        }
    }

    private class DigitKeyListener implements KeyListener {

        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            // Chỉ cho phép nhập kí tự số và chỉ một kí tự
            if (!(Character.isDigit(c) || c == KeyEvent.VK_BACK_SPACE || c == KeyEvent.VK_DELETE) || ((javax.swing.text.JTextComponent) e.getComponent()).getText().length() >= 1) {
                e.consume();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }

        @Override
        public void keyPressed(KeyEvent e) {
            JTextField currentTextField = (JTextField) e.getComponent();
            if (e.getKeyCode() == KeyEvent.VK_TAB) {
                if (currentTextField == txtNum1) {
                    txtNum2.requestFocus();
                } else if (currentTextField == txtNum2) {
                    txtNum3.requestFocus();
                } else if (currentTextField == txtNum3) {
                    txtNum4.requestFocus();
                }
                e.consume();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")

    // Phương thức này gọi service để kiểm tra OTP và mở form đặt lại mật khẩu nếu OTP chính xác
    private void verifyOTPAndResetPassword() {
        // Lấy mã OTP từ các text fields
        int enteredOTP = Integer.parseInt(txtNum1.getText() + txtNum2.getText() + txtNum3.getText() + txtNum4.getText());

        // Gọi service để kiểm tra mã OTP
        AuthenticationService authService = context.getBean(AuthenticationService.class);

        // Giả sử UserName được lưu trước đó sau khi người dùng nhập email của họ
        boolean isCorrectOTP = authService.confirmOTPCode(this.email, enteredOTP);

        if (isCorrectOTP) {
            JOptionPane.showMessageDialog(this, "OTP verification successful.", "Success", JOptionPane.INFORMATION_MESSAGE);
            // OTP đúng. Ẩn form OTP hiện tại và mở form ResetPassword
            // Sau khi hiển thị thông báo, chờ vài giây rồi mở form ResetPassword
            new javax.swing.Timer(2000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            new ResetPassword().setVisible(true);
                            OTP.this.dispose(); // Đóng form OTP
                        }
                    });
                }
            }) {
                {
                    setRepeats(false);
                    start();
                }
            };
        } else {
            // OTP sai. Thông báo cho người dùng
            JOptionPane.showMessageDialog(this, "Invalid OTP entered. Please try again.", "OTP Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        txtNum1 = new javax.swing.JTextField();
        txtNum2 = new javax.swing.JTextField();
        txtNum3 = new javax.swing.JTextField();
        txtNum4 = new javax.swing.JTextField();
        btnSend = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setText("OTP CONFIRM");

        txtNum1.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N

        txtNum2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N

        txtNum3.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N

        txtNum4.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(txtNum1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNum2, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNum3, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txtNum4, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(txtNum1, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
            .addComponent(txtNum3)
            .addComponent(txtNum2)
            .addComponent(txtNum4)
        );

        btnSend.setBackground(new java.awt.Color(51, 204, 0));
        btnSend.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSend.setForeground(new java.awt.Color(255, 255, 255));
        btnSend.setText("SEND");
        btnSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSendActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(134, 134, 134)
                        .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(107, 107, 107)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSend, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSendActionPerformed
        if (isAllFieldsFilled()) {
            changeButtonStateDuringSending();
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.", "Error", JOptionPane.ERROR_MESSAGE);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnSendActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OTP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OTP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OTP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OTP.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OTP().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSend;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtNum1;
    private javax.swing.JTextField txtNum2;
    private javax.swing.JTextField txtNum3;
    private javax.swing.JTextField txtNum4;
    // End of variables declaration//GEN-END:variables
}
