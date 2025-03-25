package by.hembar.idservice.controller;

import com.solbeg.nuserservice.model.UserArchiveResponse;
import com.solbeg.nuserservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('activate:users')")
    public Page<UserArchiveResponse> getAllUsers(Pageable pageable, @RequestParam(required = false) Boolean isActive){
        return adminService.getAllUsers(isActive, pageable);
    }

    @PreAuthorize("hasAuthority('activate:users')")
    @PutMapping("/activate/{id}")
    public void activateUser(@PathVariable Long id){
        adminService.activateUserById(id);
    }
}
