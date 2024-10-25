package pojo;

import java.util.ArrayList;

public class OrderResponse {

	public ArrayList<String> orders;
	public ArrayList<String> productOrderId;
	public String message;

	/**
	 * @return the orders
	 */
	public ArrayList<String> getOrders() {
		return orders;
	}

	/**
	 * @param orders the orders to set
	 */
	public void setOrders(ArrayList<String> orders) {
		this.orders = orders;
	}

	/**
	 * @return the productOrderId
	 */
	public ArrayList<String> getProductOrderId() {
		return productOrderId;
	}

	/**
	 * @param productOrderId the productOrderId to set
	 */
	public void setProductOrderId(ArrayList<String> productOrderId) {
		this.productOrderId = productOrderId;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
