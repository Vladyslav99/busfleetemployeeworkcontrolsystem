package com.kondratenko.busparkemploeesworkcontrol.controller;

import com.kondratenko.busparkemploeesworkcontrol.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(ControllerConstants.Mapping.BUS_PARK_EMPLOYEES_WORK_CONTROL)
public class PagesController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login-page";
    }

    @GetMapping("/login-failure")
    public String showLoginFailureFragment(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        String errorMessage = null;
        if (session != null) {
            BadCredentialsException ex = (BadCredentialsException) session
                    .getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
            if (ex != null) {
                errorMessage = "page.login.form.erro.wrong-login-or-password";
            }
        }
        model.addAttribute("errorMessage", errorMessage);

        return "login-page";
    }

    @GetMapping("/drivertriplist")
    public String showDriverTripListPage() {
        return "driver-assigned-trip-list";
    }

    @GetMapping("/tripinfo")
    public String showTripInfoPage() {
        return "trip-info-performing";
    }

}
