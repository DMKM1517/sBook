package redis.web.sessions;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.UserDBManager;

public class RegistrationCommand extends Commands {

	public RegistrationCommand(Argument argument) {
		super(argument);
	}

	public String execute() {
		String name = this.getArgument().getValue("name");

		if (!UserDBManager.singleton.doesUserExist(name)) {
			UserDBManager.singleton.createUser(this.getArgument().getAttributes());

		} else {
			return "";
		}

		return name;
	}

}
