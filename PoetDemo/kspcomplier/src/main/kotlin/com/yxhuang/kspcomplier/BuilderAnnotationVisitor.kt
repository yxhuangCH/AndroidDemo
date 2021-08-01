package com.yxhuang.kspcomplier

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.Dependencies
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import com.google.devtools.ksp.symbol.Nullability
import java.io.OutputStream
import java.util.logging.Logger

/**
 * Created by yxhuang
 * Date: 2021/7/24
 * Description:
 *    Builder 注解 Visitor
 */
fun OutputStream.appendText(str: String) {
    this.write(str.toByteArray())
}

class BuilderAnnotationVisitor(private val logger:KSPLogger,
                               private val codeGenerator: CodeGenerator) : KSVisitorVoid() {

    companion object{
        private const val TAG = "BuilderAnnotationVisitor_"
    }



    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
        logger.info(TAG + "visitClassDeclaration")
        classDeclaration.primaryConstructor!!.accept(this, data)
    }

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
        logger.info(TAG + "visitFunctionDeclaration")
        val parent = function.parentDeclaration as KSClassDeclaration
        val packageName = parent.containingFile!!.packageName.asString()
        val className = "${parent.simpleName.asString()}Builder"
        logger.info(TAG + "visitFunctionDeclaration packageName " + packageName + " className " + className)

        // 创建新的文件
        val file = codeGenerator.createNewFile(Dependencies(true, function.containingFile!!), packageName, className)

        file.appendText("package $packageName\n\n")
        file.appendText("class $className{\n")
        function.parameters.forEach {
            val name = it.name!!.asString()
            val typeName = StringBuilder(it.type.resolve().declaration.qualifiedName?.asString() ?: "<ERROR>")
            val typeArgs = it.type.element!!.typeArguments
            if (it.type.element!!.typeArguments.isNotEmpty()) {
                typeName.append("<")
                typeName.append(
                    typeArgs.map {
                        val type = it.type?.resolve()
                        "${it.variance.label} ${type?.declaration?.qualifiedName?.asString() ?: "ERROR"}" +
                                if (type?.nullability == Nullability.NULLABLE) "?" else ""
                    }.joinToString(", ")
                )
                typeName.append(">")
            }
            file.appendText("    private var $name: $typeName? = null\n")
            file.appendText("    internal fun with${name.capitalize()}($name: $typeName): $className {\n")
            file.appendText("        this.$name = $name\n")
            file.appendText("        return this\n")
            file.appendText("    }\n\n")
        }
        file.appendText("    internal fun build(): ${parent.qualifiedName!!.asString()} {\n")
        file.appendText("        return ${parent.qualifiedName!!.asString()}(")
        file.appendText(
            function.parameters.map {
                "${it.name!!.asString()}!!"
            }.joinToString(", ")
        )
        file.appendText(")\n")
        file.appendText("    }\n")
        file.appendText("}\n")
        file.close()
    }

}