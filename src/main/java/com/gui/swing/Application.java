/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gui.swing;

import com.gui.swing.Controller.Splash;

import com.gui.swing.Controller.MyFrame;
import com.gui.swing.Service.EmailService;
import com.gui.swing.Service.Test;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.swing.*;

@SpringBootApplication(scanBasePackages = { "com.gui.swing.Config", "com.gui.swing.Entity" ,"com.gui.swing.Repository", "com.gui.swing.Service", "com.gui.swing.Controller"})
public class Application extends javax.swing.JFrame{

    @Autowired
    private Test test;

    @Autowired
    private EmailService emailService;

    public static void main(String[] args) {
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
        SpringApplication.run(Application.class, args);
    }

    private static void displayMainFrame(ConfigurableApplicationContext context) {
        SwingUtilities.invokeLater(() -> {
            Splash splash = new Splash(context);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Bean
    public CommandLineRunner demo() throws MessagingException {
//		test.testLogin();
//		test.testSendEmail();
//		test.testForgetPassword();
//		test.testChangePassword();
//		test.testGetInfoRoom();
//		test.testIsRoomRent();
//        test.testGetAllFloor();
//        test.testAddNewFloor();
//        test.testAddNewRoomToFloor();
        return (args) ->{
            System.out.println(1);
        };
    }

}
