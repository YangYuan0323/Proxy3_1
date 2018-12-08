package com.imooc.proxy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import javax.tools.JavaCompiler;
import javax.tools.JavaCompiler.CompilationTask;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import org.apache.commons.io.FileUtils;

public class Proxy {
	@SuppressWarnings("unchecked")
	public static Object newProxyInstance(Class infce,InvocationHandler invocationHandler) throws Exception {
		String rt ="\r\n";
		String methodStr = "";
		for (Method m : infce.getMethods()) {
			methodStr +="	@Override"+rt+
			"	public void "+m.getName()+"() {"+rt+
			
			"try{"+rt+
			"Method md = " +infce.getName() + ".class.getMethod(\""+m.getName()+"\");"+rt+
			"invocationHandler.invoke(this,md);"+rt+
			"}catch(Exception e){e.printStackTrace();}"+rt+
			"	}";

		}
		
		String str=
		"package com.imooc.proxy;"+rt+
		"import java.lang.reflect.Method;"+rt+
		"import com.imooc.proxy.InvocationHandler;"+rt+
		"public class $Proxy0 implements "+infce.getName()+" {"+rt+
		"	public $Proxy0(InvocationHandler invocationHandler) {"+rt+
		"		super();"+rt+
		"		this.invocationHandler = invocationHandler;"+rt+
		"	}"+rt+
		"private InvocationHandler invocationHandler;"+rt+
		methodStr+rt+
		"}";

		//�����ļ���·����ȡ��ǰӦ�����ڵ�·��Ȼ�����bin Ŀ¼�£��������������б���
		String filename = System.getProperty("user.dir")+"/bin/com/imooc/proxy/$Proxy0.java";
		//FileUtils���Զ��ļ����ٵĽ��ж�д
		File file = new File(filename);
		FileUtils.writeStringToFile(file, str);
		
		//�õ�������
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		//�ļ�������
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		//��ȡ�ļ�
		Iterable units  = fileManager.getJavaFileObjects(filename);
		//��������
		CompilationTask compilationTask = compiler.getTask(null, fileManager, null, null, null, units);
		//���б���
		compilationTask.call();
		//�ر��ļ�������
		fileManager.close();
		
		//load���ڴ�
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		Class clazz = classLoader.loadClass("com.imooc.proxy.$Proxy0");
		 Constructor constructor = clazz.getConstructor(InvocationHandler.class);
		return constructor.newInstance(invocationHandler);
	}

}
