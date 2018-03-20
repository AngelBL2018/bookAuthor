package bookauthor.bookauthor.rest;

import bookauthor.bookauthor.dto.AuthenticationRequest;
import bookauthor.bookauthor.dto.AuthenticationResponse;
import bookauthor.bookauthor.dto.UserDto;
import bookauthor.bookauthor.jwt.JwtTokenUtil;
import bookauthor.bookauthor.model.User;
import bookauthor.bookauthor.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

@PostMapping("/auth")
public ResponseEntity auth(@RequestBody AuthenticationRequest authentiactionRequest){
    User user= userRepository.findUsersByEmail(authentiactionRequest.getEmail());
    if (user != null &&   passwordEncoder.matches(authentiactionRequest.getPassword(),user.getPassword())){
            String token = jwtTokenUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(AuthenticationResponse.builder()
            .token(token)
            .userDto(UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .userType(user.getUserType())
            .build()).build());

    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("unauthorized");

}

@PostMapping
    public ResponseEntity addUser(@RequestBody User user){
    if (userRepository.findUsersByEmail(user.getEmail()) == null){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("created");
    }
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exist with email");
}



}
