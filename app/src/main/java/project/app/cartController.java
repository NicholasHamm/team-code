package project.app;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

public class cartController {
    @GetMapping("/addSub")
    public String addSub(int id, HttpSession httpsession, String cart) {
        HashMap<Integer, Cart> item = new HashMap<>();
        int quantity = item.get(id).getQuantity();
        Product product = null;
        if (item.size() != 0) {
            httpsession.getAttribute("cart");}
        else {
            item.put(id, new Cart(product.getTitle(), product.getPrice(), quantity++));
        }

        if (quantity == 1) {
            item.remove(id);
            if (item.size() == 0){
                httpsession.removeAttribute("cart");
            } else {
                item.put(id, new Cart(product.getTitle(), product.getPrice(), quantity--));
            }
        }
        return cart;
    }
        @RequestMapping(value = "removeFromCart", method = RequestMethod.DELETE)
        @ResponseStatus(value = HttpStatus.OK)
        public void removeFromCart (@RequestParam int id, HttpSession session){
            HashMap<Integer, Cart> cart = (HashMap<Integer, Cart>) session.getAttribute("cart");
            cart.remove(id);
            if (cart.isEmpty()) {
                session.removeAttribute("cart");
            }

        }
    }



