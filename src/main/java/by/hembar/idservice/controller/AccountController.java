package by.hembar.idservice.controller;

import com.solbeg.nuserservice.model.UserResponse;
import com.solbeg.nuserservice.service.AccountService;
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
    public void sendActivationCode(){
        accountService.sendActivationCode();
    }

    @PutMapping("/verify-mail/{code}")
    public void verifyMail(@PathVariable String code){
        accountService.verifyMail(code);
    }
}
