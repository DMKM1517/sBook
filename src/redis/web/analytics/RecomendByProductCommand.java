package redis.web.analytics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;

import redis.web.Commands;
import redis.web.util.AnalyticsDBManager;
import redis.web.util.Argument;
import redis.web.util.ProductDBManager;

public class RecomendByProductCommand extends Commands {
	int totalrecomendations = 10;

	public RecomendByProductCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		String productname = this.getArgument().getValue("productname");
		Map<String, String> recomendations = new HashMap<String, String>();
		Map<String, Integer> tags = ProductDBManager.singleton.getProductTags(productname);
		int totalweight = 0;
		Set<String> keys = tags.keySet();
		for (String key : keys) {
			totalweight = totalweight + tags.get(key);
		}

		for (String key : keys) {
			int slotfortag = Math.round(totalrecomendations * tags.get(key) / totalweight);
			List<String> productnames = AnalyticsDBManager.singleton.getTopProducts(slotfortag, key);
			for (String product : productnames) {
				if (!product.equals(productname)) {
					recomendations.put(product.split("@")[0], key);
				}
			}
		}

		Gson gson = new Gson();
        return gson.toJson(recomendations);
	}

}
