package redis.web.sessions;

import redis.web.Commands;
import redis.web.util.AnalyticsDBManager;
import redis.web.util.Argument;
import redis.web.util.ProductDBManager;

public class BrowseCommand extends Commands {

	public BrowseCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		String productname = this.getArgument().getValue("browse");
		if (ProductDBManager.singleton.keyExist(productname)) {
			AnalyticsDBManager.singleton.updateBrowsingHistory(this.getArgument().getValue("sessionid"), productname);

			return ProductDBManager.singleton.getProductInfo(productname);
		} else {
			return "";
		}

	}

}
