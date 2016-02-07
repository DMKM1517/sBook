package redis.web.products;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.ProductDBManager;

public class DisplayCommand extends Commands {

	public DisplayCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		String display = ProductDBManager.singleton.getProductInfo(this.getArgument().getValue("name"));
		return display;
	}

}
