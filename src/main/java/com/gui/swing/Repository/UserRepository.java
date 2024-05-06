package com.gui.swing.Repository;

import com.gui.swing.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUserName(String userName);

    public Boolean existsUserByUserName(String userName);

    public Boolean existsUserByIdentificationCard(String identificationCard);
}