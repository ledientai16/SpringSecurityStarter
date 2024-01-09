package org.idk.springsecurity.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DemoController {
    @GetMapping("/")
    public String showHome() {
        return "home";
    }

    @GetMapping("/leader")
    public String showLeaderPage() {
        return "leader";
    }

    @GetMapping("/system")
    public String ShowSystemPage() {
        return "system";
    }
    @GetMapping("/access-denied")
    public String showAccessDeniedPage() {
        return "access-denied";
    }
}
