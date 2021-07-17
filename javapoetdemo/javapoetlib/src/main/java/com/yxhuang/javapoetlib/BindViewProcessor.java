package com.yxhuang.javapoetlib;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.yxhuang.annotationlib.BindView;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;

/**
 * Created by yxhuang
 * Date: 2018/9/6
 * Description:
 */

@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    private Filer mFiler;
    private Elements mElementUtils;
    private Messager mMessager;

    private GenerationClass mGenerationClass;

    private static final String PACK_NAME = "com.yxhuang.temp";

    private Map<String, ClassCreatorFactory> mClassCreatorFactoryMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();

        mGenerationClass = new GenerationClass();

    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(BindView.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.print("MyProcessor process");

        // 得到所有的注解ElementKind
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for (Element element : elements){
            VariableElement variableElement = (VariableElement) element;
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            String fullClassName = classElement.getQualifiedName().toString();
            ClassCreatorFactory proxy = mClassCreatorFactoryMap.get(fullClassName);
            if (proxy == null){
                proxy = new ClassCreatorFactory(mElementUtils, classElement);
                mClassCreatorFactoryMap.put(fullClassName, proxy);
            }
            BindView bindViewAnnotation = variableElement.getAnnotation(BindView.class);
            int id = bindViewAnnotation.value();
            proxy.putElement(id, variableElement);
        }

        // 创建 java 文件
        for (String key : mClassCreatorFactoryMap.keySet()){
            ClassCreatorFactory proxyInfo = mClassCreatorFactoryMap.get(key);
            try {
                JavaFile javaFile = JavaFile.builder(proxyInfo.getPackageName(),
                        proxyInfo.generateJavaCodeWithJavapoet()).build();
                javaFile.writeTo(processingEnv.getFiler());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
