package redis.web.analytics;

import redis.web.Commands;
import redis.web.util.AnalyticsDBManager;
import redis.web.util.Argument;

public class VisitTodayCommand extends Commands {

	public VisitTodayCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		String productName = this.getArgument().getValue("productname");
		Integer visitCount = AnalyticsDBManager.singleton.getVisitToday(productName);

		return visitCount.toString();
	}

}
