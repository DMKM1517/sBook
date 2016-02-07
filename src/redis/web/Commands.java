package redis.web;

import redis.web.util.Argument;

public abstract class Commands {

	private Argument argument;

	public Commands(Argument argument) {
		this.argument = argument;
	}

	public abstract String execute();

	public Argument getArgument() {
		return argument;
	}
	
	public void setArgument(Argument argument){
		this.argument = argument;
	}

}
