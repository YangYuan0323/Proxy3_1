package com.imooc.proxy;

import java.lang.reflect.Method;

public interface InvocationHandler {
	/**
	 * 此方法专门用来对某个对象的方法进行处理
	 */
	public void invoke(Object object,Method method);

}
