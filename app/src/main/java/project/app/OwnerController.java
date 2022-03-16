package project.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/owner")
public class OwnerController {
	@Autowired
	private DatasetRepository datasetRepository;
	
	@GetMapping(path="/all")
    public @ResponseBody Iterable<Dataset> getAllDatasets() {
        datasetRepository.findAll();
        return datasetRepository.findAll();
    }
	
	//O1: Owner can add datasets to the marketplace
	//This is where you enter the details 
	@GetMapping(path="/add")
	public @ResponseBody String addDataset() {
		String head = "<head>\r\n"
    			+ "<style>\r\n"
    			+ "table {\r\n"
    			+ "  font-family: arial, sans-serif;\r\n"
    			+ "  border-collapse: collapse;\r\n"
    			+ "  width: 100%;\r\n"
    			+ "}\r\n"
    			+ "\r\n"
    			+ "td, th {"
    			+ "  border: 1px solid #dddddd;"
    			+ "  text-align: left;"
    			+ "  padding: 8px;"
    			+ "}"
    			+ "tr:nth-child(even) {\r\n"
    			+ "  background-color: #dddddd;\r\n"
    			+ "}\r\n"
    			+ "</style>\r\n"
    			+ "</head>";
		
		String body = "<h2>Add a new dataset!</h2>"
				+ "<table>"
				+ "<tr>"
				+ "<th>Name</th>"
				+ "<th>ID</th>"
				+ "<th>Description</th>"
				+ "<th>Number of Data Points</th>"
				+ "<th>Price per Data Point</th>"
				+ "</tr>"
				+ "<tr>"
				+ "<th><input type=\"text\" id=\"name\"></th>"
				+ "<th><input type=\"number\" id=\"id\" min=\"0\"></th>"
				+ "<th><input type=\"text\" id=\"desc\"></th>"
				+ "<th><input type=\"number\" id=\"count\"></th>"
				+ "<th><input type=\"number\" step=\"0.01\" id=\"price\"></th>"
				+ "</tr>"
				+ "</table>"
				+ "<button id=\"func\">Add</button>"
				+ "<script type=\"text/javascript\">"
				+ "	document.getElementById(\"func\").onclick = function(){addDataset()};"
				+ "	function addDataset(){"
				+ "		var name = document.getElementById('name').value;"
				+ "		var id = document.getElementById('id').value;"
				+ "		var desc = document.getElementById('desc').value;"
				+ "		var count = document.getElementById('count').value;"
				+ "		var price = document.getElementById('price').value;"
				+ "		window.location = name + '/' + id + '/' + desc + '/' + count + '/' + price;"
				+ "	}"
				+ "</script>";
		
		return head + body;
	}
	
	//O1: Owner can add datasets to the marketplace
	//This method creates the new dataset and redirects you back to the dataset list
	@GetMapping(path="/add/{name}/{id}/{desc}/{count}/{price}")
	public @ResponseBody String newDataset(@PathVariable String name, @PathVariable int id, 
			@PathVariable String desc, @PathVariable int count, @PathVariable float price) {
		Dataset d = new Dataset(id, count, price, false, desc, name);
		datasetRepository.save(d);
		
		return "<meta http-equiv=\"refresh\" content=\"1; url=/owner/show/\">";
	}
	
	//Shows owner all available datasets
    @GetMapping(path="/show")
    public @ResponseBody String show(){
    	String head = "<head>\r\n"
    			+ "<style>\r\n"
    			+ "table {\r\n"
    			+ "  font-family: arial, sans-serif;\r\n"
    			+ "  border-collapse: collapse;\r\n"
    			+ "  width: 100%;\r\n"
    			+ "}\r\n"
    			+ "\r\n"
    			+ "td, th {"
    			+ "  border: 1px solid #dddddd;"
    			+ "  text-align: left;"
    			+ "  padding: 8px;"
    			+ "}"
    			+ "tr:nth-child(even) {\r\n"
    			+ "  background-color: #dddddd;\r\n"
    			+ "}\r\n"
    			+ "</style>\r\n"
    			+ "</head>";
	    
    	String table = "<table>"
    			+ "\n\t<tr>"
    			+ "\n\t\t<th>ID</td>"
    			+ "\n\t\t<th>Dataset Name</td>"
    			+ "\n\t\t<th>Price per Data Point</td>"
    			+ "\n\t\t<th>Number of Data Points</td>"
    			+ "\n\t<tr>";
    	
    	for(Dataset d : datasetRepository.findAll()) {
    		String priceString = String.format("%.2f", d.getPricePerDataPoint());
    		
    		String hideShow;
    		String colour;
    		
    		if(d.isHidden()) {
    			hideShow = "Show";
    			colour = "red";
    		}
    		else {
    			hideShow = "Hide";
    			colour = "green";
    		}
    		
    		table += "	<tr>\r\n"
    				+ "    <td>" + d.getId() + "</td>\r\n"
    				+ "    <td>" + d.getName() + "</td>\r\n"
    				+ "    <td>$" + priceString + "</td>\r\n"
    				+ "    <td>" + d.getNumberOfDataPoints() + "</td>\r\n"
    				+ "	   <td><a href=\"/owner/show/toggle?id=" + d.getId() + "\">"
    				+ "	   <button style=\"background-color:" + colour + "\">" + hideShow + "</button></a>"
    				+ "  </tr>";
    	}
    	
    	table += "\n</table>";
	    
	String button = "<a href=\"/owner/add\"><button>Add</button></a>";
    	return head + table + button;
    }
    
    //O2: Hide datasets to make them unavailable to customers
    @GetMapping(path="/show/toggle")
    public @ResponseBody String toggleHideDataset(int id) {
    	Dataset d = datasetRepository.getById(id);
    	d.toggleHidden();
    	datasetRepository.save(d);
    	
    	return "<meta http-equiv=\"refresh\" content=\"1; url=/owner/show/\">";
    }
}
