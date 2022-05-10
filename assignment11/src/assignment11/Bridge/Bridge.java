package assignment11.Bridge;

public abstract class Bridge {

	public abstract void enterCar() throws InterruptedException;

	public abstract void leaveCar();

	public abstract void enterTruck() throws InterruptedException;

	public abstract void leaveTruck();

}
