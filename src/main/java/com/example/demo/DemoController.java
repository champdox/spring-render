package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
//@RequestMapping("/calculator")
public class DemoController {
    @Autowired
    private DemoService demoService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("notif", "No numbers to add");
        return "result";
    }

    @PostMapping("/webhook1")
    public ResponseEntity<String> receiveMessage(String payload) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/add/{num1}/{num2}")
    public String add(@PathVariable Integer num1, @PathVariable Integer num2, Model model) {

        int result = demoService.add(num1, num2);
        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("result", result);

        return "result";

    }
    @GetMapping(value = "/webhook", produces = "text/plain")
    public ResponseEntity<String> verifyWebhook(@RequestParam("hub.mode") String mode,
                                                @RequestParam("hub.verify_token") String verifyToken,
                                                @RequestParam("hub.challenge") String challenge) {
        String VERIFY_TOKEN = "your_verify_token"; // Your verify token

        if ("subscribe".equals(mode) && VERIFY_TOKEN.equals(verifyToken)) {
            return ResponseEntity.ok(challenge);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }


//    @GetMapping("/add")
//    public String addNumbers(
//            @RequestParam(defaultValue = "0") int num1,
//            @RequestParam(defaultValue = "0") int num2,
//            Model model
//    ) {
//        int result = num1 + num2;
//        model.addAttribute("num1", num1);
//        model.addAttribute("num2", num2);
//        model.addAttribute("result", result);
//        return "addition-result";
//    }

}
