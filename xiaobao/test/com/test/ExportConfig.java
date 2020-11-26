package com.test;

import java.net.URL;
import java.nio.file.Paths;

import io.github.swagger2markup.Language;
import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;

public class ExportConfig {
	 public static void generateAsciiDocs() throws Exception {
	        //    输出Ascii格式
	        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
	                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
	                .withOutputLanguage(Language.ZH)
	                .withPathsGroupedBy(GroupBy.TAGS)
	                .withGeneratedExamples()
	                .withoutInlineSchema()
	                .build();
	        
	        Swagger2MarkupConverter.from(new URL("http://153.37.217.112:60003/xiaobao/v2/api-docs"))
	                .withConfig(config)
	                .build()
	                .toFolder(Paths.get("src/docs/asciidoc/generated/all"));
	    }
	 
	 public static void main(String[] args) throws Exception {
		 generateAsciiDocs();
	}
}
