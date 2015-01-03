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

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.google.common.collect.Sets;


public class DbTemplateResolver extends TemplateResolver {

	private final static String PREFIX = "db:";
	
	@Autowired
	TemplateRepository templateRepo;
	
	
	public DbTemplateResolver() {
        setResourceResolver(new DbResourceResolver());
        setResolvablePatterns(Sets.newHashSet(PREFIX + "*"));
        setCharacterEncoding("UTF-8");
    }
	
	@Override
	public void setPrefix(String prefix) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public void setSuffix(String suffix) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	protected String computeResourceName(TemplateProcessingParameters templateProcessingParameters) {
		String templateName = templateProcessingParameters.getTemplateName();
		return templateName.substring(PREFIX.length());
	}
	
	private class DbResourceResolver implements IResourceResolver {

		@Override
		public InputStream getResourceAsStream(TemplateProcessingParameters templateProcessingParameters, String resourceName) {
			
            Template template = templateRepo.findOne(Long.valueOf(resourceName));
            if (template != null) {
                return new ByteArrayInputStream(template.getContent().getBytes());
            }

            return null;
		}
		
		@Override
		public String getName() {
			return "dbResourceResolver";
		}
	}
}
