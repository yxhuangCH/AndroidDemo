package com.yxhuang.androiddailydemo.wordcloud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.yxhuang.androiddailydemo.R;
import com.yxhuang.androiddailydemo.dywordcloud.DyWordCloudView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WordCloudActivity extends AppCompatActivity {

    private WordCloudView mWordCloud;

    private DyWordCloudView mDyWordCloudView;

    private String[] WORDS = {
            "A1Maya", "B2Yashar", "C3Benjamin",
            "D4Maithe Tyrion", "E5Ben", "F6Wouter",
            "G7Shyvana", "H8D", "I9Jon",
            "J10TyrionJoin TESTD", "K11Hodor", "L12Hodor",
            "M12Hodor", "N13r", "R14Hodor"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word_cloud);

        mWordCloud = (WordCloudView) findViewById(R.id.wordcloud);
        mWordCloud.setAdapter(new SampleWordAdapter());

        mDyWordCloudView = findViewById(R.id.dy_word_view);
        mDyWordCloudView.setWords(Arrays.asList(WORDS));
    }
}