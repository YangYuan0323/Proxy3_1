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

		//定义文件的路径，取当前应用所在的路径然后放在bin 目录下，方便我们来进行编译
		String filename = System.getProperty("user.dir")+"/bin/com/imooc/proxy/$Proxy0.java";
		//FileUtils可以对文件快速的进行读写
		File file = new File(filename);
		FileUtils.writeStringToFile(file, str);
		
		//拿到编译器
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		//文件管理者
		StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);
		//获取文件
		Iterable units  = fileManager.getJavaFileObjects(filename);
		//编译任务
		CompilationTask compilationTask = compiler.getTask(null, fileManager, null, null, null, units);
		//进行编译
		compilationTask.call();
		//关闭文件管理器
		fileManager.close();
		
		//load到内存
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
		Class clazz = classLoader.loadClass("com.imooc.proxy.$Proxy0");
		 Constructor constructor = clazz.getConstructor(InvocationHandler.class);
		return constructor.newInstance(invocationHandler);
	}

}
