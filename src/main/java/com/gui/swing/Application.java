/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gui.swing;

import com.gui.swing.Controller.Splash;

/**
 *
 * @author Acer
 */
public class Application {

    public static void main(String args[]) {
        /* Thiết lập Look and Feel */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Khởi động màn hình Splash.
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Splash().setVisible(true);
                // Cửa sổ chính của Application sẽ được hiển thị từ bên trong Splash khi progress đạt 100%
            }
        });
    }
}
