package com.yxhuang.javepoet;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;

import javax.lang.model.element.Modifier;

public class JavaPoetClient {

    public static void main(String[] args) {
        try {
            createDemo();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void createDemo() throws IOException {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, Chikii!")
                .build();

        TypeSpec helloword = TypeSpec.classBuilder("HelloWord")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.yxhuang.poet", helloword).build();

        javaFile.writeTo(new File("javapoet/src/main/java/"));
    }
}