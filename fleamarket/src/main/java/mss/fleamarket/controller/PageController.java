package mss.fleamarket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/join")
    public String join() {
        return "join";
    }

    @GetMapping("/itemList")
    public String itemList() {
        return "itemList";
    }

    @GetMapping("/myPage")
    public String myPage() {
        return "myPage";
    }
}
