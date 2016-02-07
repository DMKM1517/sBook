package redis.web.sessions;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

import redis.web.Commands;
import redis.web.util.AnalyticsDBManager;
import redis.web.util.Argument;
import redis.web.util.ProductDBManager;
import redis.web.util.UserDBManager;

public class LoginCommand extends Commands {

	private String name;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LoginCommand() {
		super(null);
	}
	
	public LoginCommand(Argument argument) {
		super(argument);
	}

	public LoginCommand(String name, String password) {
		super(new Argument("name=" + name + ":password=" + password));


	}

	@Override
	public String execute() {
		
		if(this.getArgument() == null)
			this.setArgument(new Argument("name=" + name + ":password=" + password));
		
		name = this.getArgument().getValue("name");
		password = this.getArgument().getValue("password");
		Gson gson = new Gson();
		Map<String, String> ret = new HashMap<String, String>();
		
		if (UserDBManager.singleton.doesUserExist(name)) {
			if (UserDBManager.singleton.getUserPassword(name).equals(password)
					& UserDBManager.singleton.getUserSessionId(name).equals("null")) {
				String sessionID = ProductDBManager.getRandomSessionID();
				UserDBManager.singleton.login(sessionID, name);

				Map<String, String> map = new HashMap<String, String>();
				map.put("sessionID", sessionID);
				UserDBManager.singleton.setRegistrationMap(name, map);
				System.out.println("login map : " + map);

				AnalyticsDBManager.singleton.registerInSessionTracker(sessionID);

				ret.put("session", sessionID);

			} else if (UserDBManager.singleton.getUserPassword(name).equals(password)
					& !UserDBManager.singleton.getUserSessionId(name).equals("null")) {
				ret.put("error", "relogin");
			} else {
				ret.put("error", "password");
			}
		} else {
			ret.put("error", "register");
		}
		return gson.toJson(ret);

	}
}
