package project.app;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserOrder {
	@Id
    private int id;
	
	private int user;
	private int dataset;
	private int fromRow;
	private int toRow;
	private int count;
	private float price;
	private boolean bought;
	
	public UserOrder() {}
	
	public UserOrder(int id, int user, int dataset, int fromRow, int toRow, int count, float price, boolean bought) {
		this.id = id;
		this.user = user;
		this.dataset = dataset;
		this.toRow = toRow;
		this.fromRow = fromRow;
		this.count = count;
		this.price = price*count;
		this.bought = bought;
	}

	public int getToRow() {
		return toRow;
	}

	public void setToRow(int toRow) {
		this.toRow = toRow;
	}

	public int getFromRow() {
		return fromRow;
	}

	public void setFromRow(int fromRow) {
		this.fromRow = fromRow;
	}

	public boolean isBought() {
		return bought;
	}

	public void setBought(boolean bought) {
		this.bought = bought;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUser() {
		return user;
	}

	public void setUser(int user) {
		this.user = user;
	}

	public int getDataset() {
		return dataset;
	}

	public void setDataset(int dataset) {
		this.dataset = dataset;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}
}

