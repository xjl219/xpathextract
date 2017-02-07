package com.xujl.etl.xpath.kit;

import javax.script.*;

import org.apache.log4j.Logger;

import com.xujl.etl.xpath.schema.Config;
import com.xujl.etl.xpath.schema.Page;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Context {
	
	private final static Logger logger =  Logger.getLogger(Context.class);
	
	private Config conf;
	private ScriptEngine scriptEngine;
	
	public Context(Config conf) {
		final List<Page> pages = conf.getPages().all();
		final Properties params = conf.getParams();
		if (pages.isEmpty()) 
			throw new RuntimeException("少年,请添加一个页面来让蜘蛛侠行动起来!参考：conf.addPage");
		final String engineName = params.getString("scriptEngine", "nashorn");
		scriptEngine = new ScriptEngineManager().getEngineByName(engineName);
		if (scriptEngine == null) 
			throw new RuntimeException("无法获取脚本引擎对象[name="+engineName+"]");
		this.conf = conf;
		// process script
		this.processScript();
		
	
		final int limitOfResult = params.getInt("worker.result.limit", 0);
		
	
		
		
	
	}
	

	
	public Config getConf() {
		return this.conf;
	}
	
	public Properties getParams() {
		return this.conf.getParams();
	}
	

	private void processScript() {
		// 若配置了script脚本，执行它
		final Bindings bindings = new SimpleBindings();
		final Config.ScriptBindings sb = conf.getScriptBindings();
		final String script = conf.getScript();
		if (K.isNotBlank(script)) {
			try {
				sb.config(bindings, this);
				scriptEngine.eval(script, bindings);
			} catch (ScriptException e) {
				throw new RuntimeException("执行脚本错误", e);
			}
		}
		// 设置脚本引擎
		/*conf.getPages().all().forEach(page -> {
			page.getModels().all().forEach(p -> {
				p.getFields().forEach(f -> {
					f.getFilters().stream()
						.filter(ft -> ft instanceof ScriptableFilter)
						.map(ft -> (ScriptableFilter)ft)
						.forEach(ft -> {
							ft.setBindings(bindings);
							ft.setScriptEngine(scriptEngine);
						});
				});
			});
		});*/
	}
	


}
