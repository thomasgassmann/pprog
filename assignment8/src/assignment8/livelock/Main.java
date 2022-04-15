package assignment8.livelock;

public class Main {

	public static void main(String[] args) {
		final Diner husband = new Diner("Bob");
		final Diner wife = new Diner("Alice");

		final Spoon s = new Spoon(husband);

		new Thread(new Runnable() {
			public void run() {
				husband.eatWith(s, wife);
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				wife.eatWith(s, husband);
			}
		}).start();
	}

}
