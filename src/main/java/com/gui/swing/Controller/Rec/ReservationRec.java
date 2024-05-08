/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.gui.swing.Controller.Rec;

import com.gui.swing.Entity.Floor;
import com.gui.swing.Entity.Room;
import com.gui.swing.Service.FloorService;
import com.gui.swing.Service.RoomService;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.util.Date;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 *
 * @author Acer
 */
public class ReservationRec extends javax.swing.JPanel {

    private ConfigurableApplicationContext context;
    private Map<String, List<Room>> floorData;


    /**
     * Creates new form Reservation
     */
    public ReservationRec(){

    }
    public ReservationRec(ConfigurableApplicationContext context) {
        this.context = context;
        initComponents();
        initializeFloorData();
        initTabs();

        filterDate.setDate(new Date());

        filterDate.setMinSelectableDate(new Date());

        filterDate.getDateEditor().addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent e) {
                System.out.println("Have change");
                if ("date".equals(e.getPropertyName())) {
                    if (((Date) e.getNewValue()).before(new Date())) {
                        filterDate.setDate((Date) e.getOldValue());
                    }
                }
            }
        });  
    }

    private List<Floor> getAllFloor() {
        FloorService floorService = context.getBean(FloorService.class);
        return floorService.getAllFloor();
    }

    private void initTabs() {
        jTabbedPane1.removeAll();
        List<Floor> floorList = getAllFloor();
        for (int i = 0; i < floorList.size(); i++) {
            JPanel panel = new JPanel(new BorderLayout());
            JTable table = new JTable();
            // Không cần gọi initTableForFloor ở đây nữa
            JScrollPane jScrollPane = new JScrollPane(table);
            panel.add(jScrollPane, BorderLayout.CENTER);
            jTabbedPane1.addTab(floorList.get(i).getFloorName(), panel); // Thêm tab cho tầng
        }

        // Đăng ký ChangeListener
        jTabbedPane1.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                System.out.println("Have change");
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(ReservationRec.this);

                if (parentFrame != null) {
                    // Lấy tham chiếu đến JTable đang hiển thị trên tab hiện tại.
                    int index = jTabbedPane1.getSelectedIndex();
                    JPanel selectedPanel = (JPanel) jTabbedPane1.getComponentAt(index);
                    JScrollPane scrollPane = (JScrollPane) selectedPanel.getComponent(0);
                    JTable table = (JTable) scrollPane.getViewport().getView();

                    // Áp dụng model và editor mới để đảm bảo rằng JFrame sẵn sàng.
                    initTableForFloor(table, index + 1); // +1 vì index bắt đầu từ 0
                    table.setDefaultEditor(JButton.class, new ButtonEditor(new JCheckBox(), parentFrame));
                    table.setDefaultRenderer(JButton.class, new ButtonRenderer());
                }
            }
        });

        // Ngay sau khi các tab được thêm vào, cập nhật dữ liệu cho tab đầu tiên (nếu có)
        if (!floorList.isEmpty()) {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(ReservationRec.this);
            jTabbedPane1.setSelectedIndex(0);
            JPanel selectedPanel = (JPanel) jTabbedPane1.getSelectedComponent();
            JScrollPane scrollPane = (JScrollPane) selectedPanel.getComponent(0);
            JTable table = (JTable) scrollPane.getViewport().getView();

            initTableForFloor(table, 1); // +1 vì index bắt đầu từ 0
            table.setDefaultEditor(JButton.class, new ButtonEditor(new JCheckBox(), parentFrame));
            table.setDefaultRenderer(JButton.class, new ButtonRenderer());
        }
    }

    private void initializeFloorData() {
        FloorService floorService = context.getBean(FloorService.class);
        List<Floor> floorList = floorService.getAllFloor();
        floorData = new HashMap<>();
        for (Floor floor: floorList){
            floorData.put(floor.getFloorName(), floor.getRoomList().stream().filter(Room::getRoomIsActive).toList());
        }
    }

    private void initTableForFloor(JTable table, int floorNumber) {
        List<Room> roomList = floorData.get("Floor" + floorNumber);
        if (roomList == null) {
            roomList = new ArrayList<>(); // Tránh NullPointerException nếu không có dữ liệu
        }
        ReservationTableModel tableModel = new ReservationTableModel(LocalDateTime.now(),context, floorNumber, roomList);
        table.setModel(tableModel);
        table.setRowHeight(35);
        table.setDefaultRenderer(JButton.class, new ButtonRenderer());
    }

    private void initTableForFloor(LocalDateTime localDateTime, JTable table, int floorNumber) {
        List<Room> roomList = floorData.get("Floor" + floorNumber);
        if (roomList == null) {
            roomList = new ArrayList<>(); // Tránh NullPointerException nếu không có dữ liệu
        }
        ReservationTableModel tableModel = new ReservationTableModel(localDateTime, context, floorNumber, roomList);
        table.setModel(tableModel);
        table.setRowHeight(35);
        table.setDefaultRenderer(JButton.class, new ButtonRenderer());
    }

    private void updateTableData() {
        initComponents();
        initializeFloorData();
        // Code để refresh JTable, ví dụ:
        //((AbstractTableModel) jTable1.getModel()).fireTableDataChanged();
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
        btnSearch = new javax.swing.JButton();
        btnReload = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        inputSearch = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        filterBed = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        filterDate = new com.toedter.calendar.JDateChooser();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        filterType = new javax.swing.JComboBox<>();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        filterBathtub = new javax.swing.JComboBox<>();
        jPanel10 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        filterView = new javax.swing.JComboBox<>();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();

        setPreferredSize(new java.awt.Dimension(1298, 620));

        btnSearch.setBackground(new java.awt.Color(51, 153, 255));
        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnSearch.setForeground(new java.awt.Color(255, 255, 255));
        btnSearch.setText("SEARCH");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnReload.setText("RELOAD");
        btnReload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReloadActionPerformed(evt);
            }
        });

        btnReset.setBackground(new java.awt.Color(255, 102, 102));
        btnReset.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnReset.setForeground(new java.awt.Color(255, 255, 255));
        btnReset.setText("RESET");
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(inputSearch)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnReload, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(inputSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel4.setPreferredSize(new java.awt.Dimension(250, 85));

        jLabel1.setText("Bed");

        filterBed.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Choose ---", "1 Large", "1 Small", "2 Large", "2 Small", "1 Large , 1 Small" }));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterBed, 0, 198, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterBed, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );

        jPanel6.setPreferredSize(new java.awt.Dimension(250, 85));

        jLabel4.setText("Date");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(filterDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(79, 79, 79)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterDate, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel5.setPreferredSize(new java.awt.Dimension(250, 85));

        jLabel2.setText("Type of Room");

        filterType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Choose --- ", "SuperLuxury", "Luxury", "Vip", "Good", "Normal" }));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(66, 66, 66)
                .addComponent(jLabel2)
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterType, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );

        jPanel9.setPreferredSize(new java.awt.Dimension(250, 85));

        jLabel3.setText("Bathtub");

        filterBathtub.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Choose ---", "Yes", "No" }));

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterBathtub, 0, 198, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterBathtub, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );

        jPanel10.setPreferredSize(new java.awt.Dimension(250, 85));

        jLabel5.setText("View");

        filterView.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "--- Choose ---", "Yes", "No" }));

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(filterView, 0, 198, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(filterView, javax.swing.GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Room Type", "05/04/2024", "06/04/2024", "07/04/2024", "08/04/2024", "09/04/2024", "10/04/2024"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1153, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 73, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("tab1", jPanel2);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab2", jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("tab3", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(71, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 446, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String searchText = inputSearch.getText();

        // Lấy giá trị từ combo box
        String selectedBed = (String) filterBed.getSelectedItem();
        String selectedType = (String) filterType.getSelectedItem();
        String selectedBathtub = (String) filterBathtub.getSelectedItem();
        String selectedView = (String) filterView.getSelectedItem();

        // Lấy giá trị ngày từ date chooser. Bạn cần định dạng ngày lại theo cách bạn muốn hiển thị
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        String selectedDate = filterDate.getDate() != null ? dateFormat.format(filterDate.getDate()) : "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

        LocalDate localDate = LocalDate.parse(selectedDate,formatter);
        LocalDateTime dateTime = localDate.atStartOfDay();
        System.out.println(dateTime);
        // Xử lý hoặc hiển thị các giá trị này theo yêu cầu của bạn
        System.out.println("Search Text: " + searchText);
        System.out.println("Selected Bed: " + selectedBed);
        System.out.println("Selected Type: " + selectedType);
        System.out.println("Selected Bathtub: " + selectedBathtub);
        System.out.println("Selected View: " + selectedView);
        System.out.println("Selected Date: " + selectedDate);
        RoomService roomService = context.getBean(RoomService.class);
        selectedBed = getValueSearchText(selectedBed);
        selectedType = getValueSearchText(selectedType);
        selectedBathtub = getValueSearchText(selectedBathtub);
        selectedView = getValueSearchText(selectedView);
        searchText = getValueSearchText(searchText);
        int index = jTabbedPane1.getSelectedIndex();

        List<Room> resultSearchRoom = roomService.searchRoom("Floor" + (index+1),selectedType,selectedView,selectedBed, selectedBathtub, searchText);
        floorData.put("Floor" + (index+1) , resultSearchRoom);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(ReservationRec.this);
        JPanel selectedPanel = (JPanel) jTabbedPane1.getComponentAt(index);
        JScrollPane scrollPane = (JScrollPane) selectedPanel.getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();

        // Áp dụng model và editor mới để đảm bảo rằng JFrame sẵn sàng.
        initTableForFloor(dateTime, table, index + 1); // +1 vì index bắt đầu từ 0
        table.setDefaultEditor(JButton.class, new ButtonEditor(new JCheckBox(), parentFrame));
        table.setDefaultRenderer(JButton.class, new ButtonRenderer());
    }//GEN-LAST:event_btnSearchActionPerformed

    private String getValueSearchText(String search){
        if (search != null){
            search = search.trim();
            if (search.equalsIgnoreCase("--- Choose ---"))
                return "";
            else
                return search;
        } else {
            return "";
        }
    }

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        inputSearch.setText("");

        // Reset combo boxes về giá trị mặc định hoặc giá trị đầu tiên
        filterBed.setSelectedIndex(0);
        filterType.setSelectedIndex(0);
        filterBathtub.setSelectedIndex(0);
        filterView.setSelectedIndex(0);

        // Reset JDateChooser về null hoặc đến một ngày cụ thể nếu cần
        filterDate.setDate(new Date());

        updateTableData();
    }//GEN-LAST:event_btnResetActionPerformed

    private void btnReloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReloadActionPerformed
//        btnReset.doClick();
        int index = jTabbedPane1.getSelectedIndex();
        RoomService roomService = context.getBean(RoomService.class);
        List<Room> resultSearchRoom = roomService.searchRoom("Floor" + (index+1),"","","","","");
        floorData.put("Floor" + (index+1) , resultSearchRoom);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(ReservationRec.this);
        JPanel selectedPanel = (JPanel) jTabbedPane1.getComponentAt(index);
        JScrollPane scrollPane = (JScrollPane) selectedPanel.getComponent(0);
        JTable table = (JTable) scrollPane.getViewport().getView();

        // Áp dụng model và editor mới để đảm bảo rằng JFrame sẵn sàng.
        initTableForFloor(table, index + 1); // +1 vì index bắt đầu từ 0
        table.setDefaultEditor(JButton.class, new ButtonEditor(new JCheckBox(), parentFrame));
        table.setDefaultRenderer(JButton.class, new ButtonRenderer());
    }//GEN-LAST:event_btnReloadActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnReload;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> filterBathtub;
    private javax.swing.JComboBox<String> filterBed;
    private com.toedter.calendar.JDateChooser filterDate;
    private javax.swing.JComboBox<String> filterType;
    private javax.swing.JComboBox<String> filterView;
    private javax.swing.JTextField inputSearch;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
