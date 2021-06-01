package com.yxhuang.androiddailydemo.multiapp

import android.os.Build
import android.os.Bundle
import android.os.Process
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.yxhuang.androiddailydemo.R
import java.io.File
import java.io.FileDescriptor
import java.io.FileOutputStream
import java.lang.reflect.Field
import java.nio.file.Files
import java.nio.file.Paths


/**
 * Created by yxhuang
 * Date: 2021/4/19
 * Description:
 *
 *   多开检测
 */
class MultipleActivity : AppCompatActivity() {

    companion object{
        private const val TAG = "MultipleActivity_"

    }

    /**
     * 用户级多开 Native
     */
//    external fun isDualAppNative(str: String):Boolean

    private lateinit var mTvShow:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_multiple_activity)

        mTvShow = findViewById(R.id.tv_show)

        val stringBuffer = StringBuffer()
        val sb = StringBuilder()
        sb.append("系统级多开              :")
        sb.append(if (isSysDualApp()) "阳性 +" else "阴性")
        sb.append("\n")
        stringBuffer.append(sb.toString())
        val sb2 = StringBuilder()
        sb2.append("用户级多开 Native :")
//        sb2.append(if (isDualAppNative(applicationInfo.dataDir)) "阳性 +" else "阴性")
        sb2.append("\n")
        stringBuffer.append(sb2.toString())
        val sb3 = StringBuilder()
        sb3.append("用户级多开 Java    :")
        sb3.append(if (isDualApp(applicationInfo.dataDir)) "阳性 +" else "阴性")
        sb3.append("\n")
        stringBuffer.append(sb3.toString())
        if (Build.VERSION.SDK_INT >= 26) {
            val sb4 = StringBuilder()
            sb4.append("用户级多开 EX        :")
            sb4.append(if (isDualAppEx(applicationInfo.dataDir)) "阳性 +" else "阴性")
            sb4.append("\n")
            stringBuffer.append(sb4.toString())
        } else {
            stringBuffer.append("用户级多开 EX        :Java 版本公开实现不支持 8.0 以下系统\n")
        }
        mTvShow.setText(stringBuffer.toString())
    }


    /**
     * 是否系统双开
     * @return
     */
    private fun isSysDualApp(): Boolean {
        return Process.myUid() / 100000 != 0
    }

    /**
     * 用户级多开 Java
     * @param str
     * @return
     */
    private fun isDualApp(str: String): Boolean {
        return File(str + File.separator.toString() + "..").canRead()
    }

    /* access modifiers changed from: package-private */
    @RequiresApi(api = 26)
    fun isDualAppEx(str: String): Boolean {
        return try {
            val fd: FileDescriptor = FileOutputStream(str + File.separator + "wtf_jack").fd
            val declaredField: Field = fd.javaClass.getDeclaredField("descriptor")
            declaredField.isAccessible = true
            val absolutePath: String = Files.readSymbolicLink(Paths.get(String.format("/proc/self/fd/%d",
                    *arrayOf<Any>(Integer.valueOf((declaredField.get(fd) as Int).toInt()))),
                    *arrayOfNulls<String>(0))).toFile().absolutePath
            val substring = absolutePath.substring(absolutePath.lastIndexOf(File.separator))
            if (substring != File.separator + "wtf_jack") {
                return true
            }
            val replace = absolutePath.replace("wtf_jack", "..")
            val str2: String = TAG
            Log.d(str2, "isDualAppEx: $replace")
            File(replace).canRead()
        } catch (e: Exception) {
            e.printStackTrace()
            true
        }
    }
}