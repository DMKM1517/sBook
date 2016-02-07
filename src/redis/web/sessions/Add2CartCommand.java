package redis.web.sessions;

import java.util.HashMap;
import java.util.Map;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.ShoppingCartDBManager;
import redis.web.util.UserDBManager;

public class Add2CartCommand extends Commands {

	public Add2CartCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		String result = "did not update the shopping cart";
		String sessionid = this.getArgument().getValue("sessionid");
		String product = this.getArgument().getValue("product");
		String[] productList = product.split(",");
		Map<String, String> productQtyMap = new HashMap<String, String>();

		for (String _product : productList) {

			String[] nameQty = _product.split("@");
			productQtyMap.put(nameQty[0], nameQty[1]);
		}

		if (UserDBManager.singleton.doesSessionExist(sessionid)) {
			result = ShoppingCartDBManager.singleton.addToShoppingCart(sessionid, productQtyMap);
		}
		if(result.equals("OK")){
			return "[1]";
		}
		else{
			return result;
		}
	}

}
