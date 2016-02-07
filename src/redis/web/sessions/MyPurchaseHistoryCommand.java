package redis.web.sessions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import redis.web.Commands;
import redis.web.util.AnalyticsDBManager;
import redis.web.util.Argument;

public class MyPurchaseHistoryCommand extends Commands {

	public MyPurchaseHistoryCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		Gson gson = new Gson();
		String sessionid = this.getArgument().getValue("sessionid");
		List<String> purchasehistory = AnalyticsDBManager.singleton.getMyPurchaseHistory(sessionid);
		Map<String, String> purchaseh = new HashMap<String, String>();
		for(String prod : purchasehistory){
			String[] prodDate = prod.split(" on ");
			purchaseh.put(prodDate[0],prodDate[1]);
		}
		return gson.toJson(purchaseh);
	}

}
