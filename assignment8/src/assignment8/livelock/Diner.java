package assignment8.livelock;

public class Diner {
	final String name;
	volatile boolean isHungry;

	public Diner(String n) {
		name = n;
		isHungry = true;
	}

	public String getName() {
		return name;
	}

	public boolean isHungry() {
		return isHungry;
	}

	public void eatWith(Spoon spoon, Diner spouse) {
		while (isHungry) {
			// Don't have the spoon, so wait patiently for other one.
			if (spoon.owner != this) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					continue;
				}
				continue;
			}

			// If loved one is still hungry, insist upon passing the spoon.
			if (spouse.isHungry()) {
				System.out.printf("%s: You go first dear %s!%n", name, spouse.getName());
				spoon.setOwner(spouse);
				continue;
			}

			// Spouse wasn't hungry, so finally eat
			//// Critical region start
			spoon.use();
			//// Critical region end
			isHungry = false;
			System.out.printf("%s: I am full, dear %s!%n", name, spouse.getName());
			spoon.setOwner(spouse);
		}
	}
}
