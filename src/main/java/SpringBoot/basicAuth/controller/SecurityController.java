package SpringBoot.basicAuth.controller;

import SpringBoot.basicAuth.entity.UserInfo;
import SpringBoot.basicAuth.model.AuthRequest;
import SpringBoot.basicAuth.service.JwtService;
import SpringBoot.basicAuth.service.UserInfoDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class SecurityController {

    @Autowired
    private UserInfoDetailService userInfoDetailService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/helloMsg")
    public String helloMsg(){
        return "Hello Security";
    }

    @GetMapping("/confidentialMsg")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String confidentialMsg(){
        return "ConfidentialMsg";
    }

    @GetMapping("/notMuchConfidential")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String notMuchConfidential(){
        return "notMuchConfidential";
    }

    @PostMapping("/newUser")
    public String addNewUser(@RequestBody UserInfo userInfo){
        return userInfoDetailService.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest){
       Authentication auth= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));
        if(auth.isAuthenticated()){
            String token = jwtService.generateToken(authRequest.getUserName());
            return ResponseEntity.ok(token);
        }else{
            throw new UsernameNotFoundException("Invalid Username and Password");
        }
    }

}
