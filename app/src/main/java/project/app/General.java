package project.app;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class General {

    @RequestMapping(value = "/search")
    public String search(){
        return "search";
    }
    @GetMapping("/search")
    public String search(Principal principal, Model model) {
        List<Product> products = new ArrayList<>();
        model.addAttribute("product", filterVisiblity(principal, products));
        return "search";
    }
    private List<Product> filterVisiblity(Principal principal, List<Product> product) {
        return product.stream().collect(Collectors.toList());
    }
}

