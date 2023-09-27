package com.example.backendsmartcities.constroller;

import com.example.backendsmartcities.dto.UserDto;
import com.example.backendsmartcities.exception.UserNotFoundException;
import com.example.backendsmartcities.model.MessageResponse;
import com.example.backendsmartcities.model.PasswordRequest;
import com.example.backendsmartcities.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Author: Badreddine TIRGANI
 */
@CrossOrigin(value = "*", exposedHeaders = "")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final ModelMapper modelMapper;
    private final UserService userService;

    private final PasswordEncoder encoder;

    public UserController(ModelMapper modelMapper, UserService userService,PasswordEncoder encoder) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.encoder = encoder;
    }

    @Operation(summary = "This is to add  the users in the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = " user details saved in database", content = {@Content(mediaType = "application/json")}), @ApiResponse(responseCode = "404", description = " Page Not Found", content = @Content)})
    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) throws Exception {
        userDto.setPassword(encoder.encode(userDto.getPassword()));

        // convert DTO to entity
        UserDto userRequest = modelMapper.map(userDto, UserDto.class);

        UserDto user = userService.save(userRequest);

        // convert entity to DTO
        UserDto postResponse = modelMapper.map(user, UserDto.class);

        return new ResponseEntity<>(postResponse, HttpStatus.CREATED);
    }
    @Operation(summary = "This is to update the User in the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "User details updated in database", content = {@Content(mediaType = "application/json")}), @ApiResponse(responseCode = "404", description = "Page Not Found", content = @Content)})
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> update(@PathVariable long id, @RequestBody UserDto userDto) throws Exception {
        userDto.setId(id);
        userDto.setPassword(encoder.encode(userDto.getPassword()));
        //

        //
        try {
            UserDto t = userService.update(id,userDto);
            return new ResponseEntity<>(t, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "This is the list of all users that we have in the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "This is the list of all users that we have in the database", content = {@Content(mediaType = "application/json")}), @ApiResponse(responseCode = "404", description = " Page Not Found", content = @Content)})
    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> getAllUsers() {
        return userService.getAll();
    }

    @Operation(summary = "This is to get the details of particular User in the database")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = " Score details fetched from database", content = {@Content(mediaType = "application/json")}), @ApiResponse(responseCode = "404", description = " Page Not Found", content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getById(@PathVariable(name = "id") Long id) throws Exception {
        UserDto userDto = userService.findById(id).orElseThrow(() -> new Exception("Score not found with id: " + id));

        return ResponseEntity.ok().body(userDto);
    }
    @Operation(summary = "Get the details of users with a specific role from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details fetched successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "No users found with the specified role", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    //get CurrentUser
    @GetMapping("/getCurentUser/{userName}")
    public ResponseEntity<UserDto> getUserByUserName(@PathVariable(name = "userName") String userName) throws Exception {
        UserDto userDto = userService.findByUserName(userName);

        return ResponseEntity.ok().body(userDto);
    }
    @Operation(summary = "Get the details of users with a specific role from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User details fetched successfully", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "No users found with the specified role", content = @Content),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content)
    })
    @GetMapping("/roles/{role}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<UserDto>> getByRole(@PathVariable(name = "role") String role) {
        try {
            List<UserDto> userDtoList = userService.findAllByRole(role);
            if (!userDtoList.isEmpty()) {
                return ResponseEntity.ok().body(userDtoList);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    @PutMapping("/{userId}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable Long userId,
                                            @RequestBody PasswordRequest passwordRequest) {
        try {
            userService.changePassword(userId, passwordRequest.getOldPassword(), passwordRequest.getNewPassword());
            return ResponseEntity.ok(new MessageResponse("Password updated successfully!"));
        } catch (UserNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(new Exception("User not found"));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new Exception("Invalid password"));
        }
    }
    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.softDelete(id);
        return ResponseEntity.ok().build();
    }

}
