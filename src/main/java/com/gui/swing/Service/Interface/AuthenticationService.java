package com.gui.swing.Service.Interface;

import com.gui.swing.DTO.Request.ChangePasswordRequest;
import com.gui.swing.DTO.Response.GeneralResponse;
import jakarta.mail.MessagingException;

public interface AuthenticationService {
    String encodePassword(String rawPassword);

    GeneralResponse authentication(String username, String password);

    GeneralResponse forgetPassword(String email) throws MessagingException;

    Boolean confirmOTPCode(String userName, int otpCode);

    int sendConfirmationCode(String email) throws MessagingException;

    GeneralResponse changePassword(ChangePasswordRequest changePasswordRequest);
}
