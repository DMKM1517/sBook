package redis.web.products;

import redis.web.Commands;
import redis.web.util.Argument;
import redis.web.util.ProductDBManager;

public class GetTagsCommand extends Commands {

	public GetTagsCommand(Argument argument) {
		super(argument);
	}

	@Override
	public String execute() {
		String display = ProductDBManager.singleton.getTags(this.getArgument().getValue("name"));
		return display;
	}
}
