/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Tomasz Kaczmarzyk
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.kaczmarzyk.blog.thdbview;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.google.common.collect.Sets;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class Application {

	@Autowired
	TemplateRepository templateRepo;
	
	@PostConstruct
	public void initializeSampleData() {
		templateRepo.save(
				new Template("<strong th:inline=\"text\">Hello, [[${name}]]! This view fragment was loaded from the DB. How cool is it!?</strong>"));
	}
	
	@Bean
    public TemplateResolver springThymeleafTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setPrefix("classpath:/templates/");
        resolver.setSuffix(".html");
        resolver.setOrder(1);
        return resolver;
    }

    @Bean
    public DbTemplateResolver dbTemplateResolver() {
    	DbTemplateResolver resolver = new DbTemplateResolver();
        resolver.setOrder(2);
        return resolver;
    }

    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setTemplateResolvers(Sets.newHashSet(springThymeleafTemplateResolver(), dbTemplateResolver()));
        return engine;
    }
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
