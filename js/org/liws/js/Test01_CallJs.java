package org.liws.js;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Test01_CallJs {

	private static ScriptEngine engine = null;

	/**
	 * 加载js文件
	 * 
	 * @return
	 */
	private static Invocable getInvocable() {
		if (engine == null) {
			ScriptEngineManager manager = new ScriptEngineManager();
			engine = manager.getEngineByName("javascript");
			try (Reader reader1 = new InputStreamReader(
					Test01_CallJs.class.getClassLoader().getResourceAsStream("jsfiles/jsComp1.js"), "UTF-8");
					Reader reader2 = new InputStreamReader(
							Test01_CallJs.class.getClassLoader().getResourceAsStream("jsfiles//jsEntrance.js"),
							"UTF-8");) {
				engine.eval(reader1);
				engine.eval(reader2);
			} catch (IOException | ScriptException e) {
			}
		}
		return (Invocable) engine;
	}

	public static void main(String[] args) throws NoSuchMethodException, ScriptException {
		System.out.println(getInvocable().invokeFunction("func", "aa"));
		// out : aa-func1-func2
	}
}
