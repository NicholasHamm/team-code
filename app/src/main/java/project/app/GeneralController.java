package project.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GeneralController {
	@Autowired
	private DatasetRepository datasetRepository;
	
	//G0: View the datasets currently on offer (generally)
	@GetMapping(path="/show")
    public @ResponseBody String showTable() {
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
    			+ "}\r\n"
    			+ "\r\n"
    			+ "tr:nth-child(even) {"
    			+ "  background-color: #dddddd;"
    			+ "}\r\n"
    			+ "</style>\r\n"
    			+ "</head>";
    	String table = "<table>"
    			+ "<tr>"
    			+ "<th>ID</th>"
    			+ "<th>Dataset Name</th>"
    			+ "<th>Price per Data Point</th>"
    			+ "<th>Number of Data Points</th>"
    			+ "<th></th>"
    			+ "<tr>";
    	
    	for(Dataset d : datasetRepository.findAll()) {
    		String priceString = String.format("%.2f", d.getPricePerDataPoint());
    		
    		if(!d.isHidden()) {
	    		table += "	<tr>\r\n"
	    				+ "    <td>" + d.getId() + "</td>\r\n"
	    				+ "    <td>" + d.getName() + "</td>\r\n"
	    				+ "    <td>$" + priceString + "</td>\r\n"
	    				+ "    <td>" + d.getNumberOfDataPoints() + "</td>\r\n"
	    				+ "	   <td><a href=\"/show/" + d.getId() + " \">\r\n"
	    				+ "   <button>Go to page</button>\r\n"
	    				+ "</a></td>"
	    				+ "  </tr>";
    		}
    	}
    	
    	table += "\n</table>";
    	return head + table;
    }
	
	//G1: View details about a particular dataset (generally)
	@GetMapping("/show/{datasetId}")
    public @ResponseBody String datasetPage(@PathVariable int datasetId) {
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
    		
    		String body = "<body><h2>" + name + "</h2>"
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
    				+ "</table></body>";
    		
    		return head + body;
    }
}
