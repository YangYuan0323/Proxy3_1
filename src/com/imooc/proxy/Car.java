package com.imooc.proxy;

import java.util.Random;
/**
 * 普通的方式
 * @author yangyuan
 *
 */
public class Car implements Moveable {

	@Override
	public void move() {
		//实现开车
		try {
			Thread.sleep(new Random().nextInt(1000));
			System.out.println("汽车行驶中....");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
