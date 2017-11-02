package com.Myrestdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.Myrestdemo.dto.RegisterDTO;
import com.Myrestdemo.dto.StatusDTO;
import com.Myrestdemo.service.RegisterService;

@RestController
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @RequestMapping ( path = "/api/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody RegisterDTO registerDTO) {
        try {
            StatusDTO statusDTO = registerService.register(registerDTO);
            return ResponseEntity.ok(statusDTO);
        }
        catch (Exception ex) {
            System.out.println("Exception :" + ex.getMessage());
            ex.printStackTrace();

            StatusDTO statusDTO = new StatusDTO().fail(500, ex.getMessage());
            return new ResponseEntity(statusDTO, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
