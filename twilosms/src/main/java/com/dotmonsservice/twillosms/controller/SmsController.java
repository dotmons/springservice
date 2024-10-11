package com.dotmonsservice.twillosms.controller;

import com.dotmonsservice.twillosms.service.TwilloResponse;
import com.dotmonsservice.twillosms.service.TwilloSmsService;
import dto.TwilloBody;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1/smstwillo")
public class SmsController {

    TwilloSmsService twilloSmsService;

    @PostMapping
    public TwilloResponse sendSms(@RequestBody TwilloBody twilloBody) {
        String responseStatus = "";
        log.info("Sending SMS to Phone Number {}, {} ", twilloBody.getPhoneNumber(), twilloBody.getMessage());

        try {
            responseStatus = twilloSmsService.sendSmsNow(twilloBody.getPhoneNumber(), twilloBody.getMessage());
            return new TwilloResponse(true, responseStatus);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new TwilloResponse(false, responseStatus);
        }
    }
}
