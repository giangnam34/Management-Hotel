use `managementhotel`;

/* Table Role */
INSERT IGNORE INTO role (role_id, role_name, role_is_active) VAlUE (1, 'admin', true);
INSERT IGNORE INTO role (role_id, role_name, role_is_active) VAlUE (2, 'user', true);
INSERT IGNORE INTO role (role_id, role_name, role_is_active) VAlUE (3, 'guest', true);
/* End */

/* Table User */
INSERT IGNORE INTO user (user_id, user_name, user_password, user_is_active, role_id, full_name, identification_card, phone) value (1, 'giangnam.17122002@gmail.com', '$2a$10$GjVNiOcmtvW1ZEqU8uha1uG4QnpjaHvGkgZBfcJVUTRR50hZpbkk2', true, 1, 'Vo Giang Nam', '051202007648', '0968322444');
INSERT IGNORE INTO user (user_id, user_name, user_password, user_is_active, role_id, full_name, identification_card, phone) value (2, 'huutrong1101@gmail.com', '$2a$10$GjVNiOcmtvW1ZEqU8uha1uG4QnpjaHvGkgZBfcJVUTRR50hZpbkk2', true, 2, 'Nguyen Huu Trong', '051202007649', '0968322555');
INSERT IGNORE INTO user (user_id, user_name, user_password, user_is_active, role_id, full_name, identification_card, phone) value (3, 'giangnam.1712@gmail.com', '$2a$10$GjVNiOcmtvW1ZEqU8uha1uG4QnpjaHvGkgZBfcJVUTRR50hZpbkk2', true, 2, 'Vo Giang Nam 2', '051202007650', '0968322666');

/* End */

/* Table Type (type of room) */
INSERT IGNORE INTO type (room_type_id, room_type_price_per_day, room_type_price_per_hour, room_type_value) value (1, 5000000, 500000, 'SuperLuxury');
INSERT IGNORE INTO type (room_type_id, room_type_price_per_day, room_type_price_per_hour, room_type_value) value (2, 4000000, 400000, 'Luxury');
INSERT IGNORE INTO type (room_type_id, room_type_price_per_day, room_type_price_per_hour, room_type_value) value (3, 3000000, 300000, 'Vip');
INSERT IGNORE INTO type (room_type_id, room_type_price_per_day, room_type_price_per_hour, room_type_value) value (4, 2000000, 200000, 'Good');
INSERT IGNORE INTO type (room_type_id, room_type_price_per_day, room_type_price_per_hour, room_type_value) value (5, 1000000, 100000, 'Normal');
/* End */

/* Table Floor */
INSERT IGNORE INTO floor (floor_id, floor_name) value (1, 'Floor1');
INSERT IGNORE INTO floor (floor_id, floor_name) value (2, 'Floor2');
INSERT IGNORE INTO floor (floor_id, floor_name) value (3, 'Floor3');
INSERT IGNORE INTO floor (floor_id, floor_name) value (4, 'Floor4');
INSERT IGNORE INTO floor (floor_id, floor_name) value (5, 'Floor5');
INSERT IGNORE INTO floor (floor_id, floor_name) value (6, 'Floor6');
INSERT IGNORE INTO floor (floor_id, floor_name) value (7, 'Floor7');
INSERT IGNORE INTO floor (floor_id, floor_name) value (8, 'Floor8');
INSERT IGNORE INTO floor (floor_id, floor_name) value (9, 'Floor9');
INSERT IGNORE INTO floor (floor_id, floor_name) value (10, 'Floor10');
/* End */

/* Table Room */
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (1, 1, true, 1, 'Room1');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (2, 2, true, 2, 'Room2');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (3, 3, true, 3, 'Room3');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (4, 4, true, 4, 'Room4');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (5, 5, true, 5, 'Room5');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (6, 6, true, 6, 'Room6');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (7, 7, true, 7, 'Room7');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (8, 8, true, 8, 'Room8');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (9, 9, true, 9, 'Room9');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (1, 10, true, 10, 'Room10');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (1, 11, true, 1, 'Room11');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (2, 12, true, 2, 'Room12');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (3, 13, true, 3, 'Room13');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (4, 14, true, 4, 'Room14');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (5, 15, true, 5, 'Room15');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (6, 16, true, 6, 'Room16');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (7, 17, true, 7, 'Room17');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (8, 18, true, 8, 'Room18');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (9, 19, true, 9, 'Room19');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (10, 20, true, 10, 'Room20');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (1, 21, true, 1, 'Room21');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (2, 22, true, 2, 'Room22');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (3, 23, true, 3, 'Room23');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (4, 24, true, 4, 'Room24');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (5, 25, true, 5, 'Room25');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (6, 26, true, 6, 'Room26');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (7, 27, true, 7, 'Room27');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (8, 28, true, 8, 'Room28');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (9, 29, true, 9, 'Room29');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (10, 30, true, 10, 'Room30');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (1, 31, true, 1, 'Room31');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (2, 32, true, 2, 'Room32');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (3, 33, true, 3, 'Room33');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (4, 34, true, 4, 'Room34');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (5, 35, true, 5, 'Room35');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (6, 36, true, 6, 'Room36');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (7, 37, true, 7, 'Room37');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (8, 38, true, 8, 'Room38');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (9, 39, true, 9, 'Room39');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (10, 40, true, 10, 'Room40');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (1, 41, true, 1, 'Room41');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (2, 42, true, 2, 'Room42');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (3, 43, true, 3, 'Room43');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (4, 44, true, 4, 'Room44');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (5, 45, true, 5, 'Room45');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (6, 46, true, 6, 'Room46');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (7, 47, true, 7, 'Room47');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (8, 48, true, 8, 'Room48');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (9, 49, true, 9, 'Room49');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (10, 50, true, 10, 'Room50');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (1, 51, true, 1, 'Room51');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (2, 52, true, 2, 'Room52');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (3, 53, true, 3, 'Room53');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (4, 54, true, 4, 'Room54');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (5, 55, true, 5, 'Room55');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (6, 56, true, 6, 'Room56');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (7, 57, true, 7, 'Room57');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (8, 58, true, 8, 'Room58');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (9, 59, true, 9, 'Room59');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (10, 60, true, 10, 'Room60');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (1, 61, true, 1, 'Room61');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (2, 62, true, 2, 'Room62');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (3, 63, true, 3, 'Room63');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (4, 64, true, 4, 'Room64');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (5, 65, true, 5, 'Room65');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (6, 66, true, 6, 'Room66');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (7, 67, true, 7, 'Room67');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (8, 68, true, 8, 'Room68');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (9, 69, true, 9, 'Room69');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (10, 70, true, 10, 'Room70');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (1, 71, true, 1, 'Room71');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (2, 72, true, 2, 'Room72');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (3, 73, true, 3, 'Room73');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (4, 74, true, 4, 'Room74');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (5, 75, true, 5, 'Room75');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (6, 76, true, 6, 'Room76');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (7, 77, true, 7, 'Room77');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (8, 78, true, 8, 'Room78');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (9, 79, true, 9, 'Room79');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (10, 80, true, 10, 'Room80');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (1, 81, true, 1, 'Room81');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (2, 82, true, 2, 'Room82');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (3, 83, true, 3, 'Room83');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (4, 84, true, 4, 'Room84');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (5, 85, true, 5, 'Room85');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (6, 86, true, 6, 'Room86');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (7, 87, true, 7, 'Room87');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (8, 88, true, 8, 'Room88');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (9, 89, true, 9, 'Room89');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (10, 90, true, 10, 'Room90');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (1, 91, true, 1, 'Room91');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (2, 92, true, 2, 'Room92');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (3, 93, true, 3, 'Room93');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (4, 94, true, 4, 'Room94');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (5, 95, true, 5, 'Room95');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (6, 96, true, 6, 'Room96');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (7, 97, true, 7, 'Room97');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (8, 98, true, 8, 'Room98');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (9, 99, true, 9, 'Room99');
INSERT IGNORE INTO room (floor_id, room_id, room_is_active, type_id, room_name) VALUE (10, 100, true, 10, 'Room100');
/* End */


/* Table room_info */
INSERT IGNORE INTO room_info (room_id, room_info_id, key_room_info, value_room_info) value (1, 1, 'Diện tích', '100 m2');
INSERT IGNORE INTO room_info (room_id, room_info_id, key_room_info, value_room_info) value (1, 2, 'Giường', '2 giường lớn');
INSERT IGNORE INTO room_info (room_id, room_info_id, key_room_info, value_room_info) value (1, 3, 'Cửa sổ', 'cửa sổ hướng trung tâm Sài Gòn');
INSERT IGNORE INTO room_info (room_id, room_info_id, key_room_info, value_room_info) value (1, 4, 'Điều hòa', '2 điều hòa');
INSERT IGNORE INTO room_info (room_id, room_info_id, key_room_info, value_room_info) value (1, 5, 'Bồn tắm', 'Có bồn tắm');
INSERT IGNORE INTO room_info (room_id, room_info_id, key_room_info, value_room_info) value (1, 6, 'Thức ăn', 'Có mì tôm, snack,...');
INSERT IGNORE INTO room_info (room_id, room_info_id, key_room_info, value_room_info) value (1, 7, 'Đồ uống', 'Free nước lọc, uống 2 chai tặng 1 chai tính tiền 3');
INSERT IGNORE INTO room_info (room_id, room_info_id, key_room_info, value_room_info) value (1, 8, 'Phục vụ', 'Nhân viên phục vụ 24/24h');
INSERT IGNORE INTO room_info (room_id, room_info_id, key_room_info, value_room_info) value (1, 9, 'Giường', '2 giường lớn');
INSERT IGNORE INTO room_info (room_id, room_info_id, key_room_info, value_room_info) value (1, 10, 'Giường', '2 giường lớn');

/* End */

/* Table guest */
INSERT IGNORE INTO guest (id, email, first_name, identification_card, last_name, phone) value (1, 'giangnam.17122002@gmail.com', 'Vo Giang', '123456789' , 'Nam', '0968322444');
INSERT IGNORE INTO guest (id, email, first_name, identification_card, last_name, phone) value (2, 'giangnam.17122002@gmail.com', 'Nguyen Huu', '12345678' , 'Trong', '0968322555');

/* End */

/* Table room_guest */
INSERT IGNORE INTO room_guest (room_guest_id, date_begin, date_end, type_rent, guest_id, room_id) value (1, '2024-04-07 14:21:28.213373', '2024-04-14 14:21:28.213373','Day', 1,1);
INSERT IGNORE INTO room_guest (room_guest_id, date_begin, date_end, type_rent, guest_id, room_id) value (2, '2024-04-01 14:21:28.213373', '2024-04-7 14:20:28.213373','Day', 2,1);

/* End */

