package com.imooc.proxy;

public class Client {

	/**
	 * ≤‚ ‘¿‡
	 */
	public static void main(String[] args) {
		try {
			Car car = new Car();
			InvocationHandler invocationHandler = new TimeHandler(car);
			Moveable move = (Moveable) Proxy.newProxyInstance(Moveable.class,invocationHandler);
			move.move();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
