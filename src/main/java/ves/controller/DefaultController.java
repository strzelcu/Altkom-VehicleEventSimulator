package ves.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Klasa DefaultController jest odpowiedzialna za wyświetlenie strony
 * startowej w przypadku wpisania nieznanego adresu w przeglądarce
 */
@Controller
public class DefaultController {

    @RequestMapping
    public String mainMenuHandler() {
        return "home";
    }

}
