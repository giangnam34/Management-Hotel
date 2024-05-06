package com.gui.swing.Repository;

import com.gui.swing.Entity.OTPCode;
import com.gui.swing.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OTPCodeRepository extends JpaRepository<OTPCode, Long> {

    public OTPCode findByUserUserId(int userId);
}