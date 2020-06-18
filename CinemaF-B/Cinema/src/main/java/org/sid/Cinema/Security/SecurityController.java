package org.sid.Cinema.Security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {
    @GetMapping(value = "/erreur")
    public String erreur(){
        return "erreur";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "Login";
    }
    @GetMapping(value = "/logout")
    public String logout(HttpServletRequest request) throws ServletException {
        request.logout();
        return "redirect:/login";
    }

}
