package com.gui.swing.Service;

import com.github.weisj.jsvg.nodes.Use;
import com.gui.swing.DTO.Request.AddNewUserRequest;
import com.gui.swing.DTO.Response.GeneralResponse;
import com.gui.swing.DTO.UserDTO;
import com.gui.swing.Entity.Floor;
import com.gui.swing.Entity.User;
import com.gui.swing.Repository.UserRepository;
import com.gui.swing.Service.Interface.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RoleService roleService;

    public User findUserByName(String userName){
        if (userRepository.existsUserByUserName(userName)){
            return userRepository.findByUserName(userName);
        }
        throw new IllegalArgumentException("Không tìm thấy tên tầng");
    }

    public List<UserDTO> listAllUser(){
        List<UserDTO> result = new ArrayList<>();
        List<User> userList = userRepository.findAll();
        for (User user: userRepository.findAll().stream().filter(user -> !user.getRole().getRoleName().equalsIgnoreCase("Admin")).toList()) {
            UserDTO userDTO = new UserDTO(user.getUserId(),user.getUserName(),user.getUserIsActive(),user.getRole().getRoleName());
            result.add(userDTO);
        }
        return result;
    }

    public List<UserDTO> listUserWithFilter(String searchText, String searchType) {
        List<UserDTO> result = listAllUser();
        if (searchType.equalsIgnoreCase("User Name")){
            result = result.stream().filter(user -> user.getUserName().toUpperCase().contains(searchText.toUpperCase())).toList();
        } if (searchType.equalsIgnoreCase("Is Active")){
            result = result.stream().filter(guest -> guest.getIsActive().toString().toUpperCase().contains(searchText.toUpperCase())).toList();
        }if (searchType.equalsIgnoreCase("Role")){
            result = result.stream().filter(guest -> guest.getRole().toUpperCase().contains(searchText.toUpperCase())).toList();
        }
        return result;
    }

    // Add new user

    public GeneralResponse addNewUser(AddNewUserRequest addNewUserRequest){
        GeneralResponse generalResponse = new GeneralResponse();
        if (userRepository.existsUserByUserName(addNewUserRequest.getUserName()) || userRepository.existsUserByIdentificationCard(addNewUserRequest.getIdentificationCard())){
            generalResponse.setStatus(0);
            generalResponse.setMessage("User đã tồn tại!");
            return generalResponse;
        }
        User user = new User();
        user.setUserName(addNewUserRequest.getUserName());
        user.setUserPassword(authenticationService.encodePassword(addNewUserRequest.getPassword()));
        user.setRole(roleService.getRole("User"));
        user.setPhone(addNewUserRequest.getPhone());
        user.setFullName(addNewUserRequest.getFullName());
        user.setIdentificationCard(addNewUserRequest.getIdentificationCard());
        try {
            userRepository.save(user);
        } catch(DataAccessException dataAccessException){
            generalResponse.setStatus(0);
            generalResponse.setMessage("Dữ liệu không hợp lệ");
            return generalResponse;
        }
        generalResponse.setStatus(1);
        generalResponse.setMessage("Tạo user mới thành công!");
        return generalResponse;
    }

    // Update user

    public GeneralResponse updateUser(String userName, String fullName, String phone){
        GeneralResponse generalResponse = new GeneralResponse();
        try {
            User user = findUserByName(userName);
            user.setFullName(fullName);
            user.setPhone(phone);
            userRepository.save(user);
            generalResponse.setStatus(1);
            generalResponse.setMessage("Cập nhật thông tin thành công!");
            return generalResponse;
        } catch (IllegalArgumentException | DataAccessException exception){
            generalResponse.setStatus(-1);
            generalResponse.setMessage(exception.getMessage());
            return generalResponse;
        }
    }

    public Boolean updateStatusUser(String userName){
        try{
            User user = findUserByName(userName);
            user.setUserIsActive(!user.getUserIsActive());
            userRepository.save(user);
            return true;
        } catch(DataAccessException dataAccessException){
            return false;
        }
    }

    // Delete user

}
