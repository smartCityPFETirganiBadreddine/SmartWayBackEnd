package com.example.backendsmartcities.constroller;

import com.example.backendsmartcities.dto.LoginDto;
import com.example.backendsmartcities.dto.RoleDto;
import com.example.backendsmartcities.dto.TokenResponseDTO;
import com.example.backendsmartcities.dto.UserDto;
import com.example.backendsmartcities.entity.PasswordResetToken;
import com.example.backendsmartcities.entity.Role;
import com.example.backendsmartcities.entity.User;
import com.example.backendsmartcities.enums.ERole;
import com.example.backendsmartcities.mail.ResetPasswordRequest;
import com.example.backendsmartcities.repository.PasswordResetTokenRepository;
import com.example.backendsmartcities.repository.RoleRepository;
import com.example.backendsmartcities.repository.UserRepository;
import com.example.backendsmartcities.response.ResponseMessage;
import com.example.backendsmartcities.security.jwt.JwtUtils;
import com.example.backendsmartcities.service.EmailService;
import com.example.backendsmartcities.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * Author: Badreddine TIRGANI
 */
@CrossOrigin(value = "*", exposedHeaders = "")
@RestController
@RequestMapping("/api/auth")

public class AuthController {


    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private EmailService emailService;

    @Autowired
    private JavaMailSender javaMailSender;

    private final ModelMapper modelMapper;
    private final AuthenticationManager authenticationManager;


    private final UserRepository userRepository;


    private final UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;



    private final  RoleRepository roleRepository;


    private final PasswordEncoder encoder;



    private final JwtUtils jwtUtils;

    public AuthController(ModelMapper modelMapper,AuthenticationManager authenticationManager, UserRepository userRepository, UserService userService, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userService = userService;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.modelMapper =modelMapper;
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDTO> authenticateUser(@Valid @RequestBody LoginDto loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            String jwtRefresh = jwtUtils.generateRefreshJwtToken(authentication);
            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(jwt, jwtRefresh);
            return ResponseEntity.ok(tokenResponseDTO);
        } catch (AuthenticationException e) {
            String errorMessage = "Invalid username or password";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponseDTO(errorMessage));
        } catch (Exception e) {
            String errorMessage = "Unexpected error occurred";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TokenResponseDTO(errorMessage));
        }
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserDto signUpRequest) {
        if (userRepository.existsByUserName(signUpRequest.getUserName())) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Error: Email is already in use!"));
        }
        User user = modelMapper.map(signUpRequest, User.class);
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        // Create new user's account
        //User user = new User(signUpRequest.getUserName(), signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

        List<RoleDto> strRoles = signUpRequest.getRoles();
        List<Role> roles = new ArrayList<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if ("ROLE_USER".equals(role.getName().name())) {
                    Role modRole = roleRepository.findByName(ERole.ROLE_USER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(modRole);
                } else if ("ROLE_ADMIN".equals(role.getName().name())) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_ADMIN).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                else if ("ROLE_OKSA_OR".equals(role.getName().name())) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_OR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                else if ("ROLE_OKSA_CHEF_DIRECTION".equals(role.getName().name())) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_CHEF_DIRECTION).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                else if ("ROLE_OKSA_CHEF_DEP".equals(role.getName().name())) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_CHEF_DEP).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                else if ("ROLE_OKSA_OR_INT".equals(role.getName().name())) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_OR_INT).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                else if ("ROLE_OKSA_OPERATOR".equals(role.getName().name())) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_OPERATOR).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                else if ("ROLE_OKSA_CHEF".equals(role.getName().name())) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_CHEF).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                else if ("ROLE_OKSA_CHEF_EQP".equals(role.getName().name())) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_CHEF_EQP).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                else if ("ROLE_OKSA_EQP_MEMBER".equals(role.getName().name())) {
                    Role userRole = roleRepository.findByName(ERole.ROLE_OKSA_EQP_MEMBER).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
                else{
                    Role userRole = roleRepository.findByName(ERole.ROLE_CLIENT).orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    roles.add(userRole);
                }
            });
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new ResponseMessage("User registered successfully!"));
    }

    @PostMapping("/sign-out")
    public ResponseEntity<?> logoutUser() {
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(new ResponseMessage("You've been signed out!"));
    }


    @PostMapping(path = "/refreshToken")
    public ResponseEntity<TokenResponseDTO> refreshTokens() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String jwt = jwtUtils.generateJwtToken(authentication);
            String jwtRefresh = jwtUtils.generateRefreshJwtToken(authentication);

            TokenResponseDTO tokenResponseDTO = new TokenResponseDTO(jwt, jwtRefresh);
            return ResponseEntity.ok(tokenResponseDTO);
        } catch (ExpiredJwtException e) {
            String errorMessage = "Refresh token has expired";
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new TokenResponseDTO(errorMessage));
        } catch (Exception e) {
            String errorMessage = "Refresh token is not valid";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new TokenResponseDTO(errorMessage));
        }
    }




   @GetMapping("/forgot-password")
   public ResponseEntity<?> forgotPassword(@RequestParam String email) throws MessagingException {

// Find the user by email
       User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

// Generate a random password reset token
       String token = UUID.randomUUID().toString();

// Save the token in the database
       PasswordResetToken passwordResetToken = new PasswordResetToken(token, user);
       passwordResetTokenRepository.save(passwordResetToken);

// Create a button link for the password reset
       //http://localhost:3000/resetPassword
       //String resetUrl = "http://154.144.243.81:89/resetPassword?token=" + token;
       String resetUrl = "http://localhost:8080/api/auth/reset-password?token=" + token;
       String buttonText = "Reset Password";
       String buttonLink = "<a href=" + resetUrl + " target='_blank' style='background-color: #008CBA; color: white; padding: 12px 20px; text-align: center; text-decoration: none; display: inline-block; border-radius: 4px;'>"+buttonText+"</a>";

// Create the email message
       String subject = "Reset Your Password";
       String message = "Bonjour,<br/>" +
               "<br/>" +
               "Vous avez demandé une réinitialisation de votre mot de passe pour votre compte SmartCity. Pour procéder à la réinitialisation, veuillez cliquer sur le bouton ci-dessous :<br/><br/>" + buttonLink+"<br/><br/>Si vous ne pouvez pas accéder au bouton, veuillez copier et coller l'URL suivante dans votre navigateur :<br/><a href ='"+resetUrl+"'>"+resetUrl+"</a><br/><br/>Veuillez noter que ce lien de réinitialisation de mot de passe ne sera valide que pendant une période limitée.<br/>" +
               "<br/>" +
               "Cordialement,<br/>" +
               "<br/>" +
               "L'équipe SmartCity";

       MimeMessage mimeMessage = javaMailSender.createMimeMessage();
       MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
       messageHelper.setFrom("smartCity@gmail.com");
       messageHelper.setTo(user.getEmail());
       messageHelper.setSubject(subject);
       messageHelper.setText(message, true);


// Send the email message
       javaMailSender.send(mimeMessage);

       return ResponseEntity.ok(new ResponseMessage("Password reset request sent"));
   }
    @PostMapping("/reset-password")
    public ResponseEntity<ResponseMessage> resetPassword(@RequestParam String token, @RequestBody @Valid ResetPasswordRequest resetPasswordRequest) throws MessagingException {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid password reset token"));

        if (passwordResetToken.isExpired()) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Password reset token has expired"));
        }

        User user = passwordResetToken.getUser();

        // Update the user's password
        String encodedPassword = passwordEncoder.encode(resetPasswordRequest.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);

        // Delete the password reset token
        passwordResetTokenRepository.delete(passwordResetToken);

        // Send an email to the user notifying them of the password change
        String subject = "Your password has been changed";
        String message = "Your password has been changed successfully.";

        emailService.sendEmail(user.getEmail(), subject, message);

        ResponseMessage responseMessage = new ResponseMessage("Password reset successfully");
        return ResponseEntity.ok(responseMessage);
    }
}