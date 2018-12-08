package com.imooc.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

import com.imooc.proxy.Car;
import com.imooc.proxy.Moveable;

public class Test {

	/**
	 * JDK��̬���������
	 */
	public static void main(String[] args) {
		Car car = new Car();
		InvocationHandler h = new TimeHandler(car);
		Class<?> cls = car.getClass();
		/**
		 * loader  �������
		 * interfaces  ʵ�ֽӿ�
		 * h InvocationHandler
		 * 
		 * ��̬����ʵ��˼·
		 * ʵ�ֹ��ܣ�ͨ��proxy��newProxyInstance���ش������
		 * 1.����һ��Դ�루��̬��������
		 * 2.����Դ��(JDK Compile API),�����µ��ࣨ�����ࣩ
		 * 3.�������load���ڴ浱�У�����һ���µĶ��󣨴������
		 * 4.return�������
		 */
		Moveable m = (Moveable)Proxy.newProxyInstance(cls.getClassLoader(),cls.getInterfaces(), h);
		System.out.println("���������֣�"+m.getClass().getName());
		m.move();
	}

}
