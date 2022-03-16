package project.app;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Dataset {
	@Id
	private int id;
	private int numberOfDataPoints;
	private float pricePerDataPoint;
	private boolean hidden = false;
	private String description;
	private String name;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dataset() {}
	
	public Dataset(int id, int points, float price, boolean hidden, String desc, String name) {
		this.id = id;
		this.numberOfDataPoints = points;
		this.pricePerDataPoint = price;
		this.hidden = hidden;
		this.description = desc;
		this.name = name;
	}
	
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumberOfDataPoints() {
        return numberOfDataPoints;
    }

    public void setNumberOfDataPoints(int numberOfDataPoints) {
        this.numberOfDataPoints = numberOfDataPoints;
    }

    public float getPricePerDataPoint() {
        return pricePerDataPoint;
    }

    public void setPricePerDataPoint(float pricePerDataPoint) {
        this.pricePerDataPoint = pricePerDataPoint;
    }

    public boolean isHidden() {
    	return hidden;
    }
    
    public void toggleHidden() {
    	hidden = !hidden;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public void setDescription(String description) {
    	this.description = description;
    }
}
