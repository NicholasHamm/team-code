package project.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path="/User")
public class UserController {
	@Autowired
	private UsersRepository userRepository;
	
	@Autowired
	private DatasetRepository datasetRepository;
	
	@Autowired
	private UserOrderRepository orderRepository; 
	
	@GetMapping(path="/all")
    public @ResponseBody Iterable<Users> getAllUsers() {
		userRepository.findAll();
        return userRepository.findAll();
    }
	
	//Useful for adding users
	@GetMapping(path="/addnew/{id}/{name}/{email}")
	public @ResponseBody String addUser(@PathVariable int id,
										@PathVariable String name,
										@PathVariable String email) {
		Users u = new Users(id, name, email);
		userRepository.save(u);
		
		return "Added user " + name;
	}
	
	//G0: View the datasets currently on offer (as a user)
	@GetMapping(path="/{id}/show")
    public @ResponseBody String showTable(@PathVariable int id) {
		Users u = userRepository.getById(id);
		
    	String head = "<head>\r\n"
    			+ "<style>\r\n"
    			+ "table {\r\n"
    			+ "  font-family: arial, sans-serif;\r\n"
    			+ "  border-collapse: collapse;\r\n"
    			+ "  width: 100%;\r\n"
    			+ "}\r\n"
    			+ "\r\n"
    			+ "td, th {\r\n"
    			+ "  border: 1px solid #dddddd;\r\n"
    			+ "  text-align: left;\r\n"
    			+ "  padding: 8px;\r\n"
    			+ "}\r\n"
    			+ "\r\n"
    			+ "tr:nth-child(even) {\r\n"
    			+ "  background-color: #dddddd;\r\n"
    			+ "}\r\n"
    			+ "</style>\r\n"
    			+ "</head>";
    	String table = "<table>"
    			+ "\n\t<tr>"
    			+ "\n\t\t<th>ID</td>"
    			+ "\n\t\t<th>Price per Data Point</td>"
    			+ "\n\t\t<th>Number of Data Points</td>"
    			+ "\n\t\t<th></th>"
    			+ "\n\t<tr>";
    	
    	for(Dataset d : datasetRepository.findAll()) {
    		String priceString = String.format("%.2f", d.getPricePerDataPoint());
    		
    		if(!d.isHidden()) {
	    		table += "	<tr>\r\n"
	    				+ "    <td>" + d.getId() + "</td>\r\n"
	    				+ "    <td>$" + priceString + "</td>\r\n"
	    				+ "    <td>" + d.getNumberOfDataPoints() + "</td>\r\n"
	    				+ "	   <td><a href=\"/User/" + u.getId() + "/show/" + d.getId() + " \">\r\n"
	    				+ "   <button>Go to page</button>\r\n"
	    				+ "</a></td>"
	    				+ "  </tr>";
    		}
    	}
    	
    	table += "\n</table>";
    	return head + table;
    }
    
	//G1: View details about a particular dataset (as a user)
	//Button to add datasets to cart only appears for user with login
    @GetMapping("/{userId}/show/{datasetId}")
    public @ResponseBody String datasetPage(@PathVariable int userId,
    										@PathVariable int datasetId) {
    		Dataset d = datasetRepository.getById(datasetId);
    		
    		if(d.isHidden()) return "<h2>Data from this dataset is unavailable</h2>"; 
    		
    		int count = d.getNumberOfDataPoints();
    		float price = d.getPricePerDataPoint();
    		String priceString = String.format("%.2f", price);
    		String desc = d.getDescription();
    		String name = d.getName();
    		
    		String head = "<head>\r\n"
        			+ "<style>\r\n"
        			+ "table {\r\n"
        			+ "  font-family: arial, sans-serif;\r\n"
        			+ "  border-collapse: collapse;\r\n"
        			+ "  width: 100%;\r\n"
        			+ "}\r\n"
        			+ "\r\n"
        			+ "td, th {\r\n"
        			+ "  border: 1px solid #dddddd;\r\n"
        			+ "  text-align: left;\r\n"
        			+ "  padding: 8px;\r\n"
        			+ "}\r\n"
        			+ "\r\n"
        			+ "tr:nth-child(even) {\r\n"
        			+ "  background-color: #dddddd;\r\n"
        			+ "}\r\n"
        			+ "</style>\r\n"
        			+ "</head>";
    		
    		String html = "<body><h2>" + name + "</h2>"
    				+ "<h3>" + desc + "</h3>"
    				+ "<table>"
    				+ "<tr>"
    				+ "<th>Total number of Data Points Available</th>"
    				+ "<th>Price per Data Point</th>"
    				+ "</tr>"
    				+ "<tr>"
    				+ "<td>" + count + "</td>"
    				+ "<td>" + priceString + "</td>"
    				+ "</tr>"
    				+ "</table>";
    		
    		//Button and input with javascript function to add dataset to cart
    		String button = "<p>Add from row <input type=\"number\" id=\"thing1\" min=\"1\" max=\"" + count + "\"> to row <input type=\"number\" id=\"thing2\" min=\"1\" max=\"" + count + "\"> to your cart.</p>"
    					+ "<button id=\"func\">Add</button>"
    					+ "<script type=\"text/javascript\">"
    					+ "document.getElementById(\"func\").onclick = function(){addPoints()};"
    					+ "function addPoints() {"
    					+ "	var row1 = document.getElementById('thing1').value;"
    					+ "	var row2 = document.getElementById('thing2').value;"
    					+ "	window.location = '" + datasetId + "/' + row1 + '/' + row2;"
    					+ "}"
    					+ "</script>";
    		
    		return head + html + button;
    }
    
    //G2: add a set of datapoints to a shopping cart
    //A user can add a certain section of rows
    @GetMapping("/{userId}/show/{datasetId}/{row1}/{row2}")
    public @ResponseBody String add(@PathVariable int userId, @PathVariable int datasetId, @PathVariable int row1, @PathVariable int row2) {
    	Users u = userRepository.getById(userId);
    	Dataset d = datasetRepository.getById(datasetId);
    	
    	int count = Math.abs(row1 - row2) + 1;
    	
    	int cartId = (int) (orderRepository.count() + 1);
    	UserOrder co = new UserOrder(cartId, userId, datasetId, row1, row2, count, d.getPricePerDataPoint(), false);
    	orderRepository.save(co);
    	
    	return "<h2>" + count + " were added to " + u.getName() + "'s shopping cart.</h2>"
    			+ "<a href = /User/" + userId + "/show/><button>Back to browse</button></a>";
    }
    
    //G2 continued: Basic implementation of user-specific shopping cart
    @GetMapping("/{userId}/shoppingcart")
    public @ResponseBody String viewShoppingCart(@PathVariable int userId) {		
    	String head = "<head>"
    			+ "<style>"
    			+ "table {"
    			+ "  font-family: arial, sans-serif;"
    			+ "  border-collapse: collapse;"
    			+ "  width: 100%;"
    			+ "}"
    			+ "td, th {"
    			+ "  border: 1px solid #dddddd;"
    			+ "  text-align: left;"
    			+ "  padding: 8px;"
    			+ "}"
    			+ "tr:nth-child(even) {"
    			+ "  background-color: #dddddd;"
    			+ "}"
    			+ "</style>"
    			+ "</head>";
    	String table = "<table>"
    			+ "<tr>"
    			+ "<th>ID</th>"
    			+ "<th>Dataset</th>"
    			+ "<th>From Row</th>"
    			+ "<th>To Row</th>"
    			+ "<th>Number of Data Points</th>"
    			+ "<th>Price</th>"
    			+ "<tr>";
    	
    	for (UserOrder uo : orderRepository.findAll()) {
    		Dataset d = datasetRepository.getById(uo.getDataset());
    		
    		String price = String.format("%.2f", uo.getPrice());
    		
    		if(uo.getUser() == userId && !uo.isBought()) {
	    		table += "	<tr>\r\n"
	    				+ "    <td>" + uo.getId() + "</td>"
	    				+ "    <td>" + d.getName() + "</td>"
	    				+ "	   <td>" + uo.getFromRow() + "</td>"
	    				+ "	   <td>" + uo.getToRow() + "</td>"
	    				+ "    <td>" + uo.getCount() + "</td>"
	    				+ "    <td>$" + price + "</td>"
	    				+ "	   <td><button>Buy</button></td>"
	    				+ "  </tr>";
    		}
    	}
    	
    	table += "\n</table>";
    	return head + table;
    }
    
    //Buying feature for testing purposes
    @GetMapping("/{userId}/buy/{userOrderId}")
    public @ResponseBody String buy(@PathVariable int userId, @PathVariable int userOrderId){
    	UserOrder uo = orderRepository.getById(userOrderId);
    	
    	if(userId == uo.getUser()) {
	    	Users u = userRepository.getById(userId);
	    	Dataset d = datasetRepository.getById(uo.getDataset());
	    	
	    	uo.setBought(true);
	        orderRepository.save(uo);
	    	
	    	String price = String.format("%.2f", uo.getPrice()); 
	    	
	    	return "<h2>" + u.getName() + " just bought " + uo.getCount() + " points from " + d.getName() + " for $" + price + "!</h2>"
	    			+ "<a href=\"/User/" + u.getId() + "/show\"><button>Back</button></a>";
    	}
    	else {
    		return "<h2>That order is not in your shopping cart!</h2>";
    	}
    }
}
