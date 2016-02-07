package redis.web.sessions;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.UserDBManager;

public class LogoutCommand extends Commands {

	public LogoutCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		String sessionid = this.getArgument().getValue("sessionid");
		if (UserDBManager.singleton.expireSession(sessionid)) {
			return "[1]";
		} else {
			return "[0]";
		}

	}

}
