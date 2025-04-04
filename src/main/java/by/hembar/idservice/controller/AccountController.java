package by.hembar.idservice.controller;

import by.hembar.idservice.model.DefaultResponse;
import by.hembar.idservice.model.UserResponse;
import by.hembar.idservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    public UserResponse getCurrentProfileInfo() {
        return accountService.getCurrentProfileInfo();
    }

    @GetMapping("/verify-mail")
    public DefaultResponse sendActivationCode(){
        return accountService.sendActivationCode();
    }

    @PutMapping("/verify-mail/{code}")
    public DefaultResponse verifyMail(@PathVariable String code){
        return accountService.verifyMail(code);
    }
}
