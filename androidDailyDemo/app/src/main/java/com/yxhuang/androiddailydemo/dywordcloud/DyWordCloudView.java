package com.yxhuang.androiddailydemo.dywordcloud;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yxhuang
 * Date: 2021/1/18
 * Description: 词云
 */
public class DyWordCloudView extends View {

    private static final String TAG = "DyWordCloudView";

    private static final int ROW = 3;
    private static final int COLUMN = 5;

    private static int mWordCloudViewWidth = 0;

    private static Rect[][] mGridRect = new Rect[ROW][COLUMN];
    private List<WordGridItem> mGridItemList = new ArrayList<>(ROW * COLUMN);

    public DyWordCloudView(Context context) {
        super(context);
    }

    public DyWordCloudView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DyWordCloudView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    private List<String> mWordTexts = new ArrayList<>();
    private List<DyWord> mDyWords = new ArrayList<>();

    public void setWords(List<String> words) {
        mWordTexts.clear();
        mWordTexts.addAll(words);
        placeWords();
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWordCloudViewWidth = getWidth();
        if (mWordCloudViewWidth > 0) {
            diviseView();
            resortDiviseView();
            placeWords();
            invalidate();
        }
    }

    // 分割成表格
    private void diviseView() {
        int width = getWidth();
        int height = getHeight();

        if (width == 0 || height == 0 || mWordTexts == null) {
            return;
        }
        Log.i(TAG, "diviseView width " + width + " height " + height);

        Rect rect = new Rect();
        this.getGlobalVisibleRect(rect);
        Log.i(TAG, "diviseView Rect " + rect.toShortString());

        int left = rect.left;
        int top = rect.top;
        int right = rect.right;
        int bottom = rect.bottom;

        int divideHeight = (bottom - top) / ROW;
        int divideWidth = (right - left) / COLUMN;

        for (int i = 0; i < ROW; i++) { // 行j
            for (int j = 0; j < COLUMN; j++) { // 列
                Rect tempRect = new Rect();
                tempRect.left = divideWidth * j;
                tempRect.top = divideHeight * i;
                tempRect.right = divideWidth * (j + 1);
                tempRect.bottom = divideHeight * (i + 1);
                mGridRect[i][j] = tempRect;
            }
        }
        printGridRect();
    }

    private void placeWords() {
        int width = getWidth();
        int height = getHeight();

        if (width == 0 || height == 0 || mWordTexts == null) {
            return;
        }

        for (int i = 0; i < mWordTexts.size(); i++) {
            if (i > mGridItemList.size() - 1) {
                break;
            }
            placeWord(mWordTexts.get(i), i);
        }
    }

    private void placeWord(String word, int index) {
        WordGridItem gridItem = mGridItemList.get(index);
        DyWord dyWord = new DyWord(word, gridItem, 20);
        mDyWords.add(dyWord);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mDyWords != null) {
            for (DyWord word : mDyWords) {
                word.draw(canvas);
            }
        }
    }

    private void printGridRect() {
        for (int i = 0; i < ROW; i++) { // 行
            for (int j = 0; j < COLUMN; j++) { // 列
                Rect rect = mGridRect[i][j];
                Log.i(TAG, "printGridRect rect  [" + i + "," + j + "] " + rect.toShortString());
            }
        }
    }

    private void resortDiviseView() {
        mGridItemList.clear();

        int whiteColor = Color.parseColor("#FFFFFF");
        int white60Color = Color.parseColor("#9aFFFFFF");
        int white33Color = Color.parseColor("#59FFFFFF");

        // 第三列
        mGridItemList.add(new WordGridItem(mGridRect[1][2], whiteColor, 60, 20,1, 2)); // 中间
        mGridItemList.add(new WordGridItem(mGridRect[0][2], whiteColor, 50, 16, 0, 2));
        mGridItemList.add(new WordGridItem(mGridRect[2][2], whiteColor, 40, 10, 2, 2));

        // 第二列
        mGridItemList.add(new WordGridItem(mGridRect[1][1], white60Color, 50, 10, 1, 1));
        mGridItemList.add(new WordGridItem(mGridRect[0][1], white60Color, 50, 16, 0, 1));
        mGridItemList.add(new WordGridItem(mGridRect[2][1], white60Color, 40, 10, 2, 1));

        // 第四列
        mGridItemList.add(new WordGridItem(mGridRect[1][3], white60Color, 50, 7, 1, 3));
        mGridItemList.add(new WordGridItem(mGridRect[0][3], white60Color, 50, 7, 0, 3));
        mGridItemList.add(new WordGridItem(mGridRect[2][3], white60Color, 40, 6, 2, 3));

        // 第一列
        mGridItemList.add(new WordGridItem(mGridRect[1][0], white33Color, 45, 6, 1, 0));
        mGridItemList.add(new WordGridItem(mGridRect[0][0], white33Color, 38, 5, 0, 0));
        mGridItemList.add(new WordGridItem(mGridRect[2][0], white33Color, 38, 5, 2, 0));

        // 第五列
        mGridItemList.add(new WordGridItem(mGridRect[1][4], white33Color, 40, 6, 1, 4));
        mGridItemList.add(new WordGridItem(mGridRect[0][4], white33Color, 38, 5, 0, 4));
        mGridItemList.add(new WordGridItem(mGridRect[2][4], white33Color, 38, 5, 2, 4));
    }

    public static class DyWord {

        private String text;
        private Paint paint;
        private WordGridItem gridItem;

        private float width;
        private float height;
        private int padding;

        private Paint bgPaint;

        public DyWord(String text, WordGridItem gridItem, int padding) {
            this.text = text;
            this.gridItem = gridItem;
            this.padding = padding;

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextAlign(Paint.Align.CENTER);

            paint.setColor(gridItem.getTextColor());
            paint.setTextSize(gridItem.getTextSize());

            width = paint.measureText(text) + gridItem.getPadding() * 2;
            height = paint.getFontMetrics().bottom - paint.getFontMetrics().top;


            bgPaint = new Paint();
            bgPaint.setStyle(Paint.Style.FILL);//充满
            bgPaint.setColor(Color.parseColor("#1E1F3A"));
            bgPaint.setAntiAlias(true);// 设置画笔的锯齿效果

        }


        protected void draw(Canvas canvas) {
            Rect rect = gridItem.getGridRect();
            float x = (rect.right - rect.left) / 2 + rect.left;
            float y = (rect.bottom - rect.top) / 2 + rect.top;

            Log.i(TAG, "draw x " + x + " y " + y + " text " + text);
            Paint.FontMetrics fm = paint.getFontMetrics();
            if (gridItem.getColumn() < 2) { // 左边
                x = translateHorizontalLeftX(gridItem);
            } else if (gridItem.getColumn() > 2) { // 右边
                x = translateHorizontalRightX(gridItem);
            }
            if (isOutHorizontal(x)) {
                return;
            }

            float dy  = Math.abs(paint.ascent() + paint.descent()) / 2;

            // 绘制背景
            RectF bgRect = new RectF();
            bgRect.left = x - width / 2;
            bgRect.right = x + width / 2;
            bgRect.top = y - dy - height / 2;
            bgRect.bottom = y - dy  + height / 2;

            canvas.drawRoundRect(bgRect, 40, 40, bgPaint);
            canvas.save();

            canvas.drawText(text, x, y, paint);
            canvas.restore();

            resetCurrentItemRect(gridItem, x, y);
        }

        // 是否超出水平
        private boolean isOutHorizontal(float x) {
            float left = x - width / 2;
            float right = x + width / 2;
            return left < 0 || right > mWordCloudViewWidth;
        }

        // 获取 x 左边的位置
        private float translateHorizontalLeftX(WordGridItem gridItem) {
            int currentRow = gridItem.getRow();  //当前行
            int currentColumn = gridItem.getColumn(); // 当前列

            int rightRow = currentRow;
            int rightColumn = currentColumn + 1;  // 右边列

            Rect rect = mGridRect[rightRow][rightColumn];
            int rightRectLeft = rect.left;
            float x = rightRectLeft - this.padding - this.width / 2;
            return x;
        }

        // 获取 x 右边的位置
        private float translateHorizontalRightX(WordGridItem gridItem) {
            int currentRow = gridItem.getRow();  //当前行
            int currentColumn = gridItem.getColumn(); // 当前列

            int leftRow = currentRow;
            int leftColumn = currentColumn - 1;  // 左边列

            Rect rect = mGridRect[leftRow][leftColumn];
            int leftRectRight = rect.right;
            float x = leftRectRight + this.padding + this.width / 2;
            return x;
        }

        private void resetCurrentItemRect(WordGridItem gridItem, float x, float y) {
            Rect rect = gridItem.getGridRect();
            rect.left = (int) (x - width / 2);
            rect.right = (int) (x + width / 2);
            rect.top = (int) (y - height / 2);
            rect.bottom = (int) (y + height / 2);

            mGridRect[gridItem.getRow()][gridItem.getColumn()] = rect;
        }
    }


}

