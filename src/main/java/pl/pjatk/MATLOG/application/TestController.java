package pl.pjatk.MATLOG.Application;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/hello")
    public ResponseEntity<String> getHelloMessage() {
        return ResponseEntity.ok("Hello");
    }

    @PostMapping("/block")
    public ResponseEntity<String> getHello2Message(Authentication auth) {
        return ResponseEntity.ok("Hello2 "  + auth.getName());
    }
}
