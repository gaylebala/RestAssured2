import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import pojo.Data;
import pojo.LoginRequest;
import pojo.LoginResponse;
import pojo.OrderResponse;
import pojo.ViewOrder;
import pojo.orderDetail;
import pojo.orders;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

public class EcommerceApiTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		LoginRequest login = new LoginRequest();
		login.setUserEmail("balavel@gmail.com");
		login.setUserPassword("!228Vishnuarcotva");

		RequestSpecification reqlogin = given().log().all().spec(req).body(login);

		LoginResponse loginresponse = reqlogin.when().post("api/ecom/auth/login").then().log().all().extract()
				.response().as(LoginResponse.class);
		String token = loginresponse.getToken();
		System.out.println(loginresponse.getToken());
		System.out.println(loginresponse.getUserId());
		String userId = loginresponse.getUserId();

		// Add product

		RequestSpecification addproductBasereq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();

		RequestSpecification reqaddproduct = given().spec(addproductBasereq).param("productName", "laptop")
				.param("productAddedBy", userId).param("productCategory", "fashion")
				.param("productSubCategory", "shirts").param("productPrice", "11500")
				.param("productDescription", "adidas original").param("productFor", "women").multiPart("productImage",
						new File("C://Users//BALA//Pictures//Screenshots//Screenshot 2024-09-11 084214.png"));

		String addproductResponse = reqaddproduct.when().post("api/ecom/product/add-product").then().log().all()
				.extract().response().asString();

		JsonPath js = new JsonPath(addproductResponse);
		String ProductId = js.get("productId");
		System.out.println(ProductId);

		// create order

		RequestSpecification createorderBasereq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addHeader("Authorization", token).build();
		orderDetail orderdetail = new orderDetail();
		orderdetail.setCountry("India");
		orderdetail.setProductOrderId(ProductId);

		List<orderDetail> orderdetaillist = new ArrayList<orderDetail>();
		orderdetaillist.add(orderdetail);
		orders orders = new orders();
		orders.setOrders(orderdetaillist);
		RequestSpecification reqcreateorder = given().spec(createorderBasereq).body(orders);

		/**
		 * OrderResponse orderresponse =
		 * reqcreateorder.when().post("api/ecom/order/create-order").then().log().all()
		 * .extract().response().as(OrderResponse.class);
		 * 
		 * ArrayList<String> ordersList = orderresponse.getOrders();
		 * 
		 * for (int i = 0; i < ordersList.size(); i++) {
		 * 
		 * System.out.println(ordersList.get(i));
		 * 
		 * } String orderId = ordersList.get(0);
		 **/

		String createresponse = reqcreateorder.when().post("api/ecom/order/create-order").then().log().all().extract()
				.response().asString();

		JsonPath js2 = new JsonPath(createresponse);

		String orderId1 = js2.getString("orders[0]");

		System.out.println(orderId1);

		// View Order Details

		RequestSpecification vieworderdetails = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addHeader("Authorization", token).build();

		RequestSpecification vieworderreq = given().log().all().spec(vieworderdetails).queryParam("id", orderId1);

		ViewOrder vieworder = vieworderreq.when().get("api/ecom/order/get-orders-details").then().log().all().extract()
				.response().as(ViewOrder.class);

		System.out.println(vieworder.getMessage());
		System.out.println(vieworder.getData().getCountry());

		// Delete Product

		RequestSpecification deleteproductBasereq = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON)
				.addHeader("Authorization", token).build();

		RequestSpecification deleteproductreq = given().log().all().spec(deleteproductBasereq).pathParam("ProductId",
				ProductId);

		String responsedeleteProduct = deleteproductreq.when().delete("api/ecom/product/delete-product/{ProductId}")
				.then().log().all().extract().response().asString();

		JsonPath js1 = new JsonPath(responsedeleteProduct);
		js1.get("message");
		Assert.assertEquals("Product Deleted Successfully", js1.get("message"));

		// Delete order

		RequestSpecification deleteorderBasereq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addHeader("Authorization", token).build();

		RequestSpecification deleteorderreq = given().log().all().spec(deleteorderBasereq).pathParam("orders[0]",
				orderId1);

		/** There is a bug in passing string value to the path parameter **/

		String deleteOrderRes = deleteorderreq.when().delete("/api/ecom/order/delete-order/{orders[0]}").then()

				.extract().response().asString();

		System.out.println(deleteOrderRes);

		JsonPath js4 = new JsonPath(deleteOrderRes);
		js4.get("message");
		Assert.assertEquals("Orders Deleted Successfully", js4.get("message"));

	}
}
