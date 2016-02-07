package redis.web.analytics;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.ProductDBManager;

public class PurchasesTodayCommand extends Commands {

	public PurchasesTodayCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		String productName = this.getArgument().getValue("productname");
		Integer purchaseCount = ProductDBManager.singleton.getPurchaseToday(productName);

		return purchaseCount.toString();
	}

}
