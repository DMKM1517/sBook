package redis.web.products;

import redis.web.Commands;
import redis.web.util.AnalyticsDBManager;
import redis.web.util.Argument;

public class TagHistoryCommand extends Commands {

	public TagHistoryCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		String tagname = this.getArgument().getValue("tagname");
		String tagHistory = AnalyticsDBManager.singleton.getTagHistory(tagname);
		return tagHistory;
	}

}
