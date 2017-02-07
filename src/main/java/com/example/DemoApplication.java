package com.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.batch.item.ItemReader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.xujl.etl.biz.IacExtractor;
import com.xujl.etl.css.etler.CssSelectorExtracter;
import com.xujl.etl.css.etler.MultiHashMapExtractor;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run("beijing.xml", args);
		IacExtractor bean = context.getBean(IacExtractor.class);
		bean.extract();
	}
}
