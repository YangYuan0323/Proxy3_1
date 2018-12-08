package com.imooc.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TimeHandler implements InvocationHandler {
	
	private Object target;//���������
	

	public TimeHandler(Object target) {
		super();
		this.target = target;
	}


	/**
	 * object:�������
	 * method������
	 */
	@Override
	public void invoke(Object object, Method method) {
		try {
			long startTime = System.currentTimeMillis();
			System.out.println("������ʼ��ʻ...");
			method.invoke(target);
			long endTime = System.currentTimeMillis();
			System.out.println("����������ʻ...������ʻʱ�䣺"+(endTime-startTime)+"���룡");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
