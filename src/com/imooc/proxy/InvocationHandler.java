package com.imooc.proxy;

import java.lang.reflect.Method;

public interface InvocationHandler {
	/**
	 * �˷���ר��������ĳ������ķ������д���
	 */
	public void invoke(Object object,Method method);

}
