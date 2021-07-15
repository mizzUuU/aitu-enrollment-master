package kz.edy.astanait.product.controller.secretary;

import kz.edy.astanait.product.dto.requestDto.secretary.MarkExamsDtoRequest;
import kz.edy.astanait.product.dto.requestDto.secretary.MessageToUserDtoRequest;
import kz.edy.astanait.product.exception.ExceptionHandling;
import kz.edy.astanait.product.service.secretary.ConfirmBlocksService;
import kz.edy.astanait.product.service.secretary.MarkExamsService;
import kz.edy.astanait.product.service.secretary.UserFilterService;
import kz.edy.astanait.product.service.security.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.HashMap;
@RestController
@RequestMapping(path = "/api/v1/secretary")
public class SecretaryController extends ExceptionHandling {

    private final UserFilterService userFilterService;
    private final EmailService emailService;
    private final ConfirmBlocksService confirmBlocksService;
    private final MarkExamsService markExamsService;

    @Autowired
    public SecretaryController(UserFilterService userFilterService, EmailService emailService, ConfirmBlocksService confirmBlocksService, MarkExamsService markExamsService) {
        this.userFilterService = userFilterService;
        this.emailService = emailService;
        this.confirmBlocksService = confirmBlocksService;
        this.markExamsService = markExamsService;
    }

    @GetMapping("/filter")
    public ResponseEntity<HashMap<String, Object>> userFilter(@RequestParam(value = "type", required = false, defaultValue = "any") String type, @RequestParam(value = "keyword", required = false) String keyword, @RequestParam(value = "EntFrom", required = false) Integer EntFrom,
                                                              @RequestParam(value = "EntTo", required = false) Integer EntTo, @RequestParam(value = "AETEnglishFrom", required = false) Integer AETEnglishFrom,
                                                              @RequestParam(value = "AETEnglishTo", required = false) Integer AETEnglishTo, @RequestParam(value = "fullness", required = false, defaultValue = "false") boolean fullness,
                                                              @RequestParam(value = "englishCertification", required = false, defaultValue = "false") boolean englishCertification,
                                                              @RequestParam(value = "page", required = false, defaultValue = "0") Integer page) {
        HashMap<String, Object> map;
        switch (type) {
            case "iin":
                map = userFilterService.selectFilteredIIN(keyword, EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification, page);
                break;
            case "locality":
                map = userFilterService.selectFilteredLocality(keyword, EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification, page);
                break;
            case "FIO":
                map = userFilterService.selectFilteredFIO(keyword, EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification, page);
                break;
            default:
                map = userFilterService.selectAll(EntFrom, EntTo, AETEnglishFrom, AETEnglishTo, fullness, englishCertification, page);
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @PostMapping("/send-message-to-user")
    public ResponseEntity<HttpStatus> sendMessageToUser(@RequestBody MessageToUserDtoRequest messageToUserDtoRequest) throws MessagingException {
        emailService.sendEmailMessageToUser(messageToUserDtoRequest.getEmail(), messageToUserDtoRequest.getMessage());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/confirm-blocks")
    public ResponseEntity<HttpStatus> confirmBlocks(@RequestParam("user_id") Long id) {
        confirmBlocksService.confirmBlocks(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/mark-exams")
    public ResponseEntity<HttpStatus> markExams(@RequestBody MarkExamsDtoRequest markExamsDtoRequest) {
        markExamsService.markExams(markExamsDtoRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
