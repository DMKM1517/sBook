package redis.web.products;

import redis.web.Commands;
import redis.web.util.AnalyticsDBManager;
import redis.web.util.Argument;
import redis.web.util.ProductDBManager;

public class UpdateTagCommand extends Commands {

	public UpdateTagCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		String sessionid = this.getArgument().getValue("sessionid");
		String productname = this.getArgument().getValue("productname");
		String details = this.getArgument().getValue("details");
		String actionType = this.getArgument().getValue("action");

		switch (actionType.toLowerCase()) {
		case "browse":
			if (productname != null & ProductDBManager.singleton.keyExist(productname)) {
				AnalyticsDBManager.singleton.updateRatingInTag(productname, 1);
				AnalyticsDBManager.singleton.updateProductVisit(sessionid, productname);
			}
			break;
		case "buy":
			System.out.println("Buying the products in the shopping cart !! ");
			String[] products = details.split(",");
			for (String product : products) {
				if (product != null & !product.trim().equals("")) {
					AnalyticsDBManager.singleton.updateRatingInTag(product+"@products", 10);
					AnalyticsDBManager.singleton.updateProductPurchase(sessionid, product);
				}
			}

			break;
		default:
			System.out.println("The URL cannot be acted uppon  ");
			break;
		}

		return "";
	}

}
