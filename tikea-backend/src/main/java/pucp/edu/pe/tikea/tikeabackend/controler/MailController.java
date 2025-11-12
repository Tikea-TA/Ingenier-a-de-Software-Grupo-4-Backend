package pucp.edu.pe.tikea.tikeabackend.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.web.bind.annotation.*;
import pucp.edu.pe.tikea.tikeabackend.services.MailService;

@RestController
@RequestMapping("/api/mail")
@RequiredArgsConstructor
public class MailController {
     private final MailService mailService;

    @PostMapping("/text")
    public ResponseEntity<Void> sendText(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body) {

        mailService.sendText(to, subject, body);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/html")
    public ResponseEntity<Void> sendHtml(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String html) {

        mailService.sendHtml(to, subject, html);
        return ResponseEntity.accepted().build();
    }
}
