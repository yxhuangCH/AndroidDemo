package com.yxhuang.kspcomplier

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.validate

import com.yxhuang.annotation.KBuilder


/**
 * Created by yxhuang
 * Date: 2021/7/21
 * Description:
 */
class KspProcessor : SymbolProcessor {
    companion object {
        private const val TAG = "KspProcessor"
    }

    private lateinit var mLogger: KSPLogger
    private lateinit var mCodeGenerator: CodeGenerator


    override fun init(
        options: Map<String, String>,
        kotlinVersion: KotlinVersion,
        codeGenerator: CodeGenerator,
        logger: KSPLogger
    ) {
        mLogger = logger
        mCodeGenerator = codeGenerator

    }

    override fun process(resolver: Resolver): List<KSAnnotated> {
        val symbols = resolver.getSymbolsWithAnnotation(KBuilder::class.java.name)
        val res = symbols.filter {
            it is KSClassDeclaration && it.validate()
        }
        symbols.filter {
            it is KSClassDeclaration && it.validate()
        }.map {
            it.accept(BuilderAnnotationVisitor(mLogger, mCodeGenerator), Unit)
        }
        return res
    }

    override fun finish() {
        mLogger.info(TAG + "finish")

    }
}