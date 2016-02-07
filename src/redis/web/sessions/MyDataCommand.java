package redis.web.sessions;

import java.util.Map;

import com.google.gson.Gson;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.UserDBManager;

public class MyDataCommand extends Commands {

	public MyDataCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		String sessionid = this.getArgument().getValue("sessionid");

		String name = UserDBManager.singleton.getUserName(sessionid);
		Map<String, String> map = UserDBManager.singleton.getRegistrationMap(name);
		map.remove("sessionID");
		Gson gson = new Gson();
		return gson.toJson(map);
	}

}
