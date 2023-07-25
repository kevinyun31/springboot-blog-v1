package shop.mtcoding.blog.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Controller
public class HelloController {

    @GetMapping("/test")
    public String test(Model model) {
        model.addAttribute("username", "ssar");
        return "test";
    }

    @GetMapping("/array")
    public String test2(Model model) {
        List<String> list = new ArrayList<>();
        list.add("바나나");
        list.add("딸기");
        model.addAttribute("list", list);
        return "array";
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    class User {
        private int id;
        private String username;
    }

    @GetMapping("/users")
    public String users(Model model) {
        List<User> list = new ArrayList<>();
        list.add(new User(1, "ssar"));
        list.add(new User(2, "cos"));
        model.addAttribute("list", list);
        model.addAttribute("list", true);
        return "users";
    }

} 

