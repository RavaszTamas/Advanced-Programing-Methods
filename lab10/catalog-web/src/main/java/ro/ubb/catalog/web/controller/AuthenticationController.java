//package ro.ubb.catalog.web.controller;
//
//import lombok.AllArgsConstructor;
//import lombok.NonNull;
//import lombok.experimental.FieldDefaults;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import ro.ubb.catalog.core.service.UserService;
//
//import java.util.UUID;
//
//import static lombok.AccessLevel.PACKAGE;
//import static lombok.AccessLevel.PRIVATE;
//
//@RestController
//@RequestMapping("/public/users")
//@FieldDefaults(level = PRIVATE, makeFinal = true)
//@AllArgsConstructor(access = PACKAGE)
//final class PublicUsersController {
//    @NonNull
//    UserService authentication;
//
//    @PostMapping("/register")
//    String register(
//            @RequestParam("username") final String username,
//            @RequestParam("password") final String password) {
//        users
//                .save(
//                        User
//                                .builder()
//                                .id(username)
//                                .username(username)
//                                .password(password)
//                                .build()
//                );
//
//        return login(username, password);
//    }
//
//    @PostMapping("/login")
//    String login(
//            @RequestParam("username") final String username,
//            @RequestParam("password") final String password) {
//        return authentication
//                .login(username, password)
//                .orElseThrow(() -> new RuntimeException("invalid login and/or password"));
//    }
//}
//
