package dev.mateusz.barber.demo.dao;

import java.util.List;

import dev.mateusz.barber.demo.entity.User;

public interface UserDao {
	
	User findByUserPhoneNumber(int thePhoneNumber, int theIdUser);

    User findByUserName(String theUserName, int theIdUser);
    
    User findByUserEmail(String theEmail, int theIdUser);
    
    User findByUserPhoneNumber(int thePhoneNumber);
	
    User findByUserName(String theUserName);
    
    User findByUserEmail(String theEmail);
    
    void saveUser(User user);

    List<User> getUsers();
    
    void deleteUser(int theId);
    
    List<User> searchUsers(String theSearchName);

	User getUserById(int theId);
    
}