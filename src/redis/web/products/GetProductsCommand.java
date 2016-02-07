package redis.web.products;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.ProductDBManager;

public class GetProductsCommand extends Commands {

	public GetProductsCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		String display = ProductDBManager.singleton.getProducts(this.getArgument().getValue("name"));
		return display;
	}
}
