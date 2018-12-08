package com.imooc.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler {
	
	private Object target;//被代理对象
	

	public TimeHandler(Object target) {
		super();
		this.target = target;
	}


	/**
	 * object:代理对象
	 * method：方法
	 */
	@Override
	public void invoke(Object object, Method method) {
		try {
			long startTime = System.currentTimeMillis();
			System.out.println("汽车开始行驶...");
			method.invoke(target);
			long endTime = System.currentTimeMillis();
			System.out.println("汽车结束行驶...汽车行驶时间："+(endTime-startTime)+"毫秒！");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
