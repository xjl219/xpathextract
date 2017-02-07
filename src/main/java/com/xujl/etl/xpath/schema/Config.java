package com.xujl.etl.xpath.schema;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.script.Bindings;

import org.springframework.test.web.servlet.ResultHandler;

import com.xujl.etl.xpath.extractor.Extractor;
import com.xujl.etl.xpath.kit.Context;
import com.xujl.etl.xpath.kit.Properties;
import com.xujl.etl.xpath.kit.Seed;

public class Config {

    private static final Logger logger = Logger.getLogger(Config.class.getName());

    public Config() {
        seeds = new Seeds();
        extractors = new Extractors();
        filters = new Filters();
        pages = new Pages();
        params = new Properties();
    }

    private Seeds seeds;
    private Extractors extractors;
    private Filters filters;
    private Pages pages;
    private Properties params;
    private String script;
    private ScriptBindings bindings;
    private ResultHandler resultHandler;

    public interface ScriptBindings {
        void config(Bindings bindings, Context ctx);
    }

    public static class Seeds {
        private List<Seed> seeds;

        public Seeds() {
            this.seeds = new ArrayList<>();
        }

        public Seeds add(Seed seed) {
            this.seeds.add(seed);
            logger.info("添加种子: " + seed);
            return this;
        }

        public Seeds add(String name, String url) {
            return this.add(new Seed(name, url));
        }

        public Seeds add(String url) {
            return this.add(new Seed(url));
        }

        public List<Seed> all() {
            return this.seeds;
        }
    }


    public static class Extractors {
        private Map<String, Class<Extractor>> extractors;

        public Extractors() {
            extractors = new HashMap<>();
        }

        public Extractors register(String name, Class<Extractor> extractor) {
            this.extractors.put(name, extractor);
            logger.info("注册解析器类: " + name + ", " + extractor.getName());
            return this;
        }

        public Map<String, Class<Extractor>> all() {
            return this.extractors;
        }
    }

    public static class Filters {
        private Map<String, Field.ValueFilter> filters;

        public Filters() {
            filters = new HashMap<>();
        }

        public Filters register(String name, Field.ValueFilter ft) {
            this.filters.put(name, ft);
            logger.info("注册过滤器: " + name + ", " + ft);
            return this;
        }

        public Map<String, Field.ValueFilter> all() {
            return this.filters;
        }
    }

    public static class Pages {
        private List<Page> pages;

        public Pages() {
            this.pages = new ArrayList<>();
        }

        public Pages add(Page page) {
            this.pages.add(page);
            logger.info("添加页面配置: " + page);
            return this;
        }

        public List<Page> all() {
            return this.pages;
        }
    }

    public interface Builder {
        Config build() throws Exception;
    }

    public Config addSeed(String url) {
        return this.addSeed(new Seed(url));
    }

    public Config addSeed(String name, String url) {
        return this.addSeed(new Seed(name, url));
    }

    public Config addSeed(Seed seed) {
        seeds.add(seed);
        return this;
    }


    public Config registerExtractor(String name, Class<Extractor> extractor) {
        extractors.register(name, extractor);
        return this;
    }

    public Config registerFilter(String name, Field.ValueFilter filter) {
        filters.register(name, filter);
        return this;
    }

    public ScriptBindings getScriptBindings() {
        return this.bindings;
    }

    public Config addPage(Page page) {
        pages.add(page);
        return this;
    }

    public Config set(String paramName, Object value) {
        this.params.put(paramName, value);
        return this;
    }

    public Seeds getSeeds() {
        return seeds;
    }


    public Extractors getExtractors() {
        return extractors;
    }

    public Filters getFilters() {
        return filters;
    }

    public Pages getPages() {
        return pages;
    }

    public Properties getParams() {
        return params;
    }

    public Config setScriptBindings(ScriptBindings bindings) {
        this.bindings = bindings;
        return this;
    }

    public Config setScript(String script) {
        this.script = script;
        return this;
    }

    public String getScript() {
        return this.script;
    }

    public Config setResultHandler(ResultHandler resultHandler) {
        this.resultHandler = resultHandler;
        return this;
    }

    public ResultHandler getResultHandler() {
        return this.resultHandler;
    }

}
