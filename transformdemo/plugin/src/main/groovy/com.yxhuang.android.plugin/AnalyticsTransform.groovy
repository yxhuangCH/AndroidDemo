package com.yxhuang.android.plugin

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import org.apache.commons.codec.digest.DigestUtils
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

class AnalyticsTransform extends Transform {

    private static Project mProject

    AnalyticsTransform(Project project) {
        mProject = project
    }

    @Override
    String getName() { // Task 名称
        return "YxhuangAnalyticsTrack"
    }

    /**
     *  TransformManager.CONTENT_CLASS 处理 java 的 class 文件
     *  TransformManager.CONTENT_RESOURCES 处理 java 的资源
     *
     * @return
     */
    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {  // 要处理的数据类型
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() { // 作用域
        return TransformManager.SCOPE_FULL_PROJECT   // 整个项目
    }

    @Override
    boolean isIncremental() { // 是否为增量编译
        return true
    }

    @Override
    void transform(Context context, Collection<TransformInput> inputs, Collection<TransformInput>
            referencedInputs, TransformOutputProvider outputProvider, boolean isIncremental) throws
            IOException, TransformException, InterruptedException {
        println("#######################")
        println("yxhuang.transfrom")

        inputs.forEach{TransformInput input ->
            //  遍历目录
            input.directoryInputs.forEach{ DirectoryInput directoryInput ->
                // 获取 output 目录
                def dest = outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes,
                                                            directoryInput.scopes, Format.DIRECTORY)
                // 将 input 的目录复制到 output 指定目录
                FileUtils.copyDirectory(directoryInput.file, dest)
            }

            // 遍历 jar
            input.jarInputs.equals{ JarInput jarInput ->
                // 重新命名输出文件
                def jarName = jarInput.name
                def md5Name = DigestUtils.md5Hex(jarInput.file.getAbsolutePath())
                if (jarName.endsWith(".jar")){
                    jarName = jarName.substring(0, jarName.length() -1)
                }
                File copyJarFile = jarInput.file;

                // 生成输出路径
                def dest = outputProvider.getContentLocation(jarName + md5Name,
                        jarInput.contentTypes, jarInput.scopes, Format.JAR)
                // 将 input 的目录复制到 output 指定目录
                FileUtils.copyFile(copyJarFile, dest)
            }
        }

    }

}