package redis.web.sessions;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.ShoppingCartDBManager;

public class ShowMyCartCommand extends Commands {

	public ShowMyCartCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		String sessionid = this.getArgument().getValue("sessionid");
		Map<String, String> productMap = ShoppingCartDBManager.singleton.myCartInfo(sessionid);
		if (!productMap.isEmpty()) {
			Map<String, String> cart = new HashMap<String, String>();
			Set<String> set = productMap.keySet();

			for (String str : set) {
				cart.put(str.split("@")[0], productMap.get(str));
			}
			Gson gson = new Gson();
			return gson.toJson(cart);
		} else {
			return "[0]";
		}
	}

}
