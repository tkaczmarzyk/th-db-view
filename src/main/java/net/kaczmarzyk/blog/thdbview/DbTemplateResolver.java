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
        setResourceResolver(new DbTemplateResourceResolver());
        setResolvablePatterns(Sets.newHashSet(PREFIX + "*"));
        setCharacterEncoding("UTF-8");
    }
	
	private class DbTemplateResourceResolver implements IResourceResolver {

		@Override
		public InputStream getResourceAsStream(TemplateProcessingParameters templateProcessingParameters, String resourceName) {
			
			Long templateId = Long.valueOf(resourceName.substring(PREFIX.length()));
            Template template = templateRepo.findOne(templateId);
            if (template != null) {
                return new ByteArrayInputStream(template.getContent().getBytes());
            }

            return null;
		}
		
		@Override
		public String getName() {
			return "viewTemplateResourceResolver";
		}
	}
}
