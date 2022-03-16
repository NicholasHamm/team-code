package project.app;

import javafx.scene.canvas.GraphicsContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

// import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CheckoutController {

    // @Autowired
    private CheckoutRepository checkoutRepository;

    @GetMapping("/checkout")
    public String showCheckout(Checkout checkout) {
        return "checkout";
    }

    @GetMapping("/backtocart")
    public String showBackToCart(Checkout checkout) {
        return "index";
    }

//    @PostMapping("/checkout")
//    public String addCheckout(@Valid Checkout checkout, BindingResult result, Model model){
//        if (result.hasErrors()){
//            return "checkout";
//        }
//
//        checkoutRepository.save(checkout);
//        return "/payment";
//    }

    @GetMapping("/index")
    public String showAllCheckout(Model model){
        model.addAttribute("checkout", checkoutRepository.findAll());
        return "index";
    }

//    @GetMapping("/edit/{id}")
//    public String showUpdateCheckout(@PathVariable("id") long id, Model model) {
//        Checkout checkout = checkoutRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//
//        model.addAttribute("checkout", checkout);
//        return "update-checkout";
//    }
//
//    @PostMapping("/update/{id}")
//    public String updateUser(@PathVariable("id") long id, @Valid Checkout checkout,
//                             BindingResult result, Model model) {
//        if (result.hasErrors()) {
//            checkout.setId(id);
//            return "update-checkout";
//        }
//
//        checkoutRepository.save(checkout);
//        return "redirect:/payment";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteCheckout(@PathVariable("id") long id, Model model) {
//        Checkout checkout = checkoutRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
//        checkoutRepository.delete(checkout);
//        return "redirect:/index";
//    }



//    @GetMapping("/checkout")
//    public String showCheckout(Model model) {
//        Checkout checkout = new Checkout();
//        model.addAttribute("details", checkout);
//        return "checkout.html";
//    }
//
//    @PostMapping("/checkout")
//    public String submitCheckout(@ModelAttribute("details") Checkout details){
//        System.out.println(details);
//        Map<String, Object> map = new HashMap<>(1);
//
//        if (details.getFname() == null || details.getLname() == null) {
//            return "checkout.html";
//        } else {
//            map.put("data", details);
//        }
//
//        return "payment.html";
//    }
}

