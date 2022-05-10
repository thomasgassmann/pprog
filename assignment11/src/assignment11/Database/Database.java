package assignment11.Database;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;

public abstract class Database {

	protected int MAX_USERS = 10;
	protected Set<User> activeUsers = new HashSet<>();
	
	public abstract void login(User u) throws InterruptedException;

	public abstract void logout(User u) throws InterruptedException;

	public abstract void backup() throws InterruptedException, BrokenBarrierException;
	
}
