package com.test;

import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;




public class Swagger2Markup { 
	private static String[] restKeys = new String[]{"ui","api"};//"ui"
//指定adoc文件生成路径
private static Path outputDirectory;

//通过配置文件生成swagger2markup的参数
public Swagger2MarkupConfig config;

public Swagger2Markup(String Json) throws Exception{
    //读取配置文件
    Configuration configuration = new Configurations().properties("swagger.properties")    ;//
    configuration.addProperty("", "");
    config = new Swagger2MarkupConfigBuilder(configuration).build();
    if(Json.startsWith("http")){
        //获取远程json数据
        createAdocFile(new URL(Json));
    }else{
        //获取本地json数据
        createAdocFile(Paths.get(Json));
    }
}
/**
 * 通过url生成adoc文件
 */
public void createAdocFile(URL remoteSwaggerFile){
    Swagger2MarkupConverter.from(remoteSwaggerFile)
            .withConfig(config)
            .build()
            .toFolder(outputDirectory);
}
/**
 * 通过json文件生成adoc文件
 */
public void createAdocFile(Path localSwaggerFile){
    Swagger2MarkupConverter.from(localSwaggerFile)
            .withConfig(config)
            .build()
            .toFolder(outputDirectory);
}

public static void main(String[] args) throws Exception{
    //循环生成json对应的acdoc
   // for(String key:restKeys){
        outputDirectory = Paths.get("target/asciidoc/generated/");
        //指定本地json文件路径
        //new Swagger2Markup("xxx-web/target/swagger/xxx-"+key+"-v1.json");
        new Swagger2Markup("http://153.37.217.112:60003/xiaobao/v2/api-docs");
    //}

    //指定远程json文件路径
    //  new Swagger2Markup("http://petstore.swagger.io/v2/swagger.json");

}
}
