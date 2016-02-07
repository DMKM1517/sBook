package redis.web.sessions;

import java.util.Map;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.UserDBManager;

public class EditMyDataCommand extends Commands {

	public EditMyDataCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		Map<String, String> editMap = this.getArgument().getAttributes();
		boolean result = UserDBManager.singleton.editRegistrationMap(editMap);
		if (result) {
			return "[1]";
		} else {
			return "[0]";
		}

	}

}
