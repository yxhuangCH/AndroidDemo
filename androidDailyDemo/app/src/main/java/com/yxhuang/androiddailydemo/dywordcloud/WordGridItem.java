package com.yxhuang.androiddailydemo.dywordcloud;

import android.graphics.Rect;

/**
 * Created by yxhuang
 * Date: 2021/1/19
 * Description:
 */
public class WordGridItem {
    private Rect gridRect;
    private int textColor;
    private int textSize;

    private int inPadding;

    private int row;
    private int column;



    public WordGridItem(Rect gridRect, int textColor, int textSize,
                        int padding, int row, int column) {
        this.gridRect = gridRect;
        this.textColor = textColor;
        this.textSize = textSize;
        this.inPadding = padding;
        this.row = row;
        this.column = column;
    }

    public Rect getGridRect() {
        return gridRect;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getPadding() {
        return inPadding;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

}
