package com.company.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import com.company.formbean.AppUserForm;
import com.company.model.AppUser;
import com.company.model.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
 
@Repository
public class AppUserDAO {
 
   
    @Autowired
    private PasswordEncoder passwordEncoder;
 
    private static final Map<Long, AppUser> USERS_MAP = new HashMap<>();
 
    static {
        initDATA();
    }
 
    private static void initDATA() {
        String encrytedPassword = "";
 
        AppUser nik = new AppUser(1L, "nik", "Nik", "Nikos", 
                true, Gender.MALE, "nik@gmail.com", encrytedPassword, "Russia");
 
        AppUser koly= new AppUser(2L, "koly", "Koly", "Kolyde", 
                true, Gender.MALE, "koly@gmail.com", encrytedPassword, "US");
 
        USERS_MAP.put(nik.getUserId(), nik);
        USERS_MAP.put(koly.getUserId(), koly);
    }
 
    public Long getMaxUserId() {
        long max = 0;
        for (Long id : USERS_MAP.keySet()) {
            if (id > max) {
                max = id;
            }
        }
        return max;
    }
 

 
    public AppUser findAppUserByUserName(String userName) {
        Collection<AppUser> appUsers = USERS_MAP.values();
        for (AppUser u : appUsers) {
            if (u.getUserName().equals(userName)) {
                return u;
            }
        }
        return null;
    }
 
    public AppUser findAppUserByEmail(String email) {
        Collection<AppUser> appUsers = USERS_MAP.values();
        for (AppUser u : appUsers) {
            if (u.getEmail().equals(email)) {
                return u;
            }
        }
        return null;
    }
 
    public List<AppUser> getAppUsers() {
        List<AppUser> list = new ArrayList<>();
 
        list.addAll(USERS_MAP.values());
        return list;
    }
 
    public AppUser createAppUser(AppUserForm form) {
        Long userId = this.getMaxUserId() + 1;
        String encrytedPassword = this.passwordEncoder.encode(form.getPassword());
 
        AppUser user = new AppUser(userId, form.getUserName(), 
                form.getFirstName(), form.getLastName(), false, 
                form.getGender(), form.getEmail(), form.getCountryCode(), 
                encrytedPassword);
 
        USERS_MAP.put(userId, user);
        return user;
    }
 
}
