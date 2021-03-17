package ro.ubb.catalog.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ro.ubb.catalog.web.dto.UserCredentialsDto;

import java.util.stream.Collectors;

@RestController
public class UserController {

    public static final Logger log = LoggerFactory.getLogger(UserController.class);


    @RequestMapping(value = "/user",method = RequestMethod.GET)
    UserCredentialsDto getTypeOfUser(){
        log.trace("getTypeOfUser - method entered");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        log.trace("getTypeOfUser - method exit={}",userDetails);
        return new UserCredentialsDto(userDetails.getAuthorities().stream().map(elem->elem.toString()).collect(Collectors.toList()));
    }


}
