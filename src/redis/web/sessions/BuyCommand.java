package redis.web.sessions;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.ShoppingCartDBManager;

public class BuyCommand extends Commands {

	public BuyCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		String sessionid = this.getArgument().getValue("sessionid");
		String shoppingdetails = ShoppingCartDBManager.singleton.buyItemsInTheShoppingCart(sessionid);
		return shoppingdetails;
	}

}
