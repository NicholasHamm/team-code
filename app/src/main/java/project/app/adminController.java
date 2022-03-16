package project.app;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

public class adminController {

    @PostMapping("/product/{id}")
    // RedirectView to String
    public String editDatset(@PathVariable int id, String title, String description, Float price, Visibility visibility){
        Product item = (Product) Cart.findProductId(id);

        if (Product.isEmpty(title)) {
            item.setTitle(title);
        }

        if (Product.isEmpty(description)) {
            item.setDescription(description);
        }

        if (price != null) {
            item.setPrice(price);
        }

        if (visibility != null) {
            item.setVisibility(visibility);
        }


        return "redirect:/admin/products/edit/" +  item.getId();
    }

    public enum Visibility {
        RELEASED, PREORDER, PRIVATE
    }
}
