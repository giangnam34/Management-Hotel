//package com.gui.swing.Controller;
//
//import com.formdev.flatlaf.extras.FlatAnimatedLafChange;
//import com.gui.swing.Application;
//import com.gui.swing.Controller.application.form.LoginForm;
//import com.gui.swing.Controller.application.form.MainForm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import javax.swing.*;
//import java.awt.*;
//
//@Controller
//public class ApplicationForm extends javax.swing.JFrame{
//
//    private static ApplicationForm app;
//
//    @Autowired
//    private MainForm mainForm;
//
//    @Autowired
//    private LoginForm loginForm;
//    public ApplicationForm() {
//        initComponents();
//        setSize(new Dimension(1366, 768));
//        setLocationRelativeTo(null);
////        mainForm = new MainForm();
////        loginForm = new LoginForm();
////        setContentPane(loginForm);
//    }
//
//    public static void showForm(Component component) {
//        component.applyComponentOrientation(app.getComponentOrientation());
//        app.mainForm.showForm(component);
//    }
//
//    public static void login() {
//        FlatAnimatedLafChange.showSnapshot();
//        app.setContentPane(app.mainForm);
//        app.mainForm.applyComponentOrientation(app.getComponentOrientation());
//        setSelectedMenu(0, 0);
//        app.mainForm.hideMenu();
//        SwingUtilities.updateComponentTreeUI(app.mainForm);
//        FlatAnimatedLafChange.hideSnapshotWithAnimation();
//    }
//
//    public static void logout() {
//        FlatAnimatedLafChange.showSnapshot();
//        app.setContentPane(app.loginForm);
//        app.loginForm.applyComponentOrientation(app.getComponentOrientation());
//        SwingUtilities.updateComponentTreeUI(app.loginForm);
//        FlatAnimatedLafChange.hideSnapshotWithAnimation();
//    }
//
//    public static void setSelectedMenu(int index, int subIndex) {
//        app.mainForm.setSelectedMenu(index, subIndex);
//    }
//
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
//    private void initComponents() {
//
//        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
//        setUndecorated(true);
//
//        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
//        getContentPane().setLayout(layout);
//        layout.setHorizontalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGap(0, 719, Short.MAX_VALUE)
//        );
//        layout.setVerticalGroup(
//                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//                        .addGap(0, 521, Short.MAX_VALUE)
//        );
//
//        pack();
//    }// </editor-fold>//GEN-END:initComponents
//}
