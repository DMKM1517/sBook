package redis.web.analytics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.gson.Gson;

import redis.web.Commands;
import redis.web.util.AnalyticsDBManager;
import redis.web.util.Argument;
import redis.web.util.UserDBManager;

public class MyStatusCommand extends Commands {

	public MyStatusCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		String sessionID = this.getArgument().getValue("sessionid");

		if (UserDBManager.singleton.doesSessionExist(sessionID)) {
			Set<String> browsingHistory = AnalyticsDBManager.singleton.getBrowsingHistory(sessionID);
			List<String> list = new ArrayList<String>(browsingHistory);
			Collections.sort(list, Collections.reverseOrder());
			Set<String> browsingH = new HashSet<String>();
			for(String prod : list){
				browsingH.add(prod.split("@")[0]);
			}
			Gson gson = new Gson();
			return gson.toJson(browsingH);
		} else {
			return "";
		}

	}

}
