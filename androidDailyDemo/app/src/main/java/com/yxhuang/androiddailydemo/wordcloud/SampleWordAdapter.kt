package com.yxhuang.androiddailydemo.wordcloud

import android.content.res.Resources


/**
 * Created by Yashar on 03/03/2018.
 */
class SampleWordAdapter : WordAdapter() {

    private val WORDS = arrayOf(
            "A1Maya", "B2Yashar", "C3Benjamin", "D4Maithe", "E5Ben Albu", "F6Wouter", "G7Shyvana",
            "H8Daenerys Targaryen", "I9Jon Snow", "Tyrion Lannister", "Hodor", "Hodor",
            "Hodor", "Hodor", "Hodor", "Ramin", "Bahram", "Seyyed", "Amir hossein",
            "Arashk", "Amin", "Navid", "Ehsan", "Pedram")


    init {
        setMaximumTextSize(Resources.getSystem().displayMetrics.density * 30)
        setMinimumTextSize(Resources.getSystem().displayMetrics.density * 5)
    }

    override fun getCount(): Int {
        return WORDS.size
    }

    override fun getText(position: Int): String {
        return if (position < WORDS.size) {
            WORDS[position]
        } else ""
    }

    override fun getImportance(position: Int): Float {
        val importance = super.getImportance(position)
        return importance * importance
    }
}
