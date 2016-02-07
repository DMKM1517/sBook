package redis.web.products;

import java.util.Map;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.ProductDBManager;

public class CommissionProductCommand extends Commands {

	public CommissionProductCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		Map<String, String> productAttributes = this.getArgument().getAttributes();
		boolean commisioning_result = ProductDBManager.singleton.commisionProduct(productAttributes);
		boolean tagging_result = ProductDBManager.singleton.enterTagEntries(productAttributes.get("name"),
				productAttributes.get("tags"));
		if (commisioning_result & tagging_result) {
			return "[1]";
		} else {
			return "[0]";
		}
	}

}
