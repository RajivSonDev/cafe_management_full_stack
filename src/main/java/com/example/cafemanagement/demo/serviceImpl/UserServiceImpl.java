package com.example.cafemanagement.demo.serviceImpl;

import com.example.cafemanagement.demo.POJO.User;
import com.example.cafemanagement.demo.contents.CafeContents;
import com.example.cafemanagement.demo.dao.UserDao;
import com.example.cafemanagement.demo.service.UserService;
import com.example.cafemanagement.demo.utils.CafeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {

        log.info("Inside signup {}",requestMap);

        try{
            if(validateSignUpMap(requestMap)){

                User user=userDao.findByEmailId(requestMap.get("email"));

                if(Objects.isNull(user)){

                    userDao.save(getUserFromMap(requestMap));
                    return CafeUtils.getResponseEntity("Successfully Registered",HttpStatus.OK);
                }
                else{
                    return CafeUtils.getResponseEntity("Email already exists", HttpStatus.BAD_REQUEST);
                }

            }
            else{
                return CafeUtils.getResponseEntity(CafeContents.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeContents.SOMETHING_WENT_WRONG,HttpStatus.BAD_REQUEST);
    }

    private User getUserFromMap(Map<String, String> requestMap) {

        User user=new User();

        user.setName(requestMap.get("name"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setStatus("false");
        user.setRole("user");

        return user;
    }

    private boolean validateSignUpMap(Map<String,String> requestMap){

        if(requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("password") && requestMap.containsKey("email")){
            return true;
        }

        return false;
    }


}
