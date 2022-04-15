package assignment8.livelock;

public class Spoon {
	Diner owner;

	public Spoon(Diner d) {
		owner = d;
	}

	public synchronized Diner getOwner() {
		return owner;
	}

	public synchronized void setOwner(Diner d) {
		owner = d;
	}

	public synchronized void use() {
		System.out.printf("%s had dinner!", owner.name);
	}
}
