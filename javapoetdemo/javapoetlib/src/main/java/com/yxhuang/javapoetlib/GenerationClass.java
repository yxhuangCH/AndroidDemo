package com.yxhuang.javapoetlib;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;

/**
 * Created by yxhuang
 * Date: 2018/9/6
 * Description:
 */
public class GenerationClass {

    private static final String PACK_NAME = "com.yxhuang.temp";

    public GenerationClass() {
    }


    public JavaFile generateHelloWorldClass(){

        /*
         public final class HelloWorld {
            public static void main(String[] args) {
                System.out.println("Hello World!");
              }
           }
         */
        // 生成 main 方法
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC) // 添加标识符 public static
                .returns(void.class) // 返回类型
                .addParameter(String[].class, "args") // 方法的参数
                .addStatement("$T.out.println($S)", System.class, "Hello World!")  // 方法里面的语句声明
                .build();

        // 构建 HelloWorld 类
        TypeSpec hellWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        // 生成 java 文件
        JavaFile javaFile = JavaFile.builder(PACK_NAME, hellWorld).build();

        return javaFile;

    }

    public JavaFile controlFlow(){

        MethodSpec main = MethodSpec.methodBuilder("main")
                .addStatement("int total = 0")
                .beginControlFlow("for (int i = 0; i < 10; i++)")
                .addStatement("total +=i")
                .endControlFlow()
                .build();

        // 构建 HelloWorld 类
        TypeSpec hellWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        // 生成 java 文件
        JavaFile javaFile = JavaFile.builder(PACK_NAME, hellWorld).build();


        return javaFile;
    }


    /**
     *
     * @return
     */
    public JavaFile forLiterals(){

        // 构建 HelloWorld 类
        TypeSpec hellWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(computeRange("multiply10to20", 10, 20, "*"))
                .build();

        // 生成 java 文件
        JavaFile javaFile = JavaFile.builder(PACK_NAME, hellWorld).build();

        return javaFile;

    }

    private MethodSpec computeRange(String name, int from, int to,String op){
        return MethodSpec.methodBuilder(name)
                .returns(int.class)
                .addStatement("int result = 1")
                .beginControlFlow("for (int i = " + from + "; i < " + to + "; i++)")
                .addStatement("result = result " + op + " i")
                .endControlFlow()
                .addStatement("return result")
                .build();
    }


}
