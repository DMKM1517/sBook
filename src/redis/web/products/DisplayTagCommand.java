package redis.web.products;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.ProductDBManager;

public class DisplayTagCommand extends Commands {

	public DisplayTagCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {

		String tagName = this.getArgument().getValue("tagname");
		String details = ProductDBManager.singleton.getTagValues(tagName);
		return details;
	}

}
