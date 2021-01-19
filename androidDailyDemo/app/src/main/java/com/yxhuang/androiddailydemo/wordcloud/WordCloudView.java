package com.yxhuang.androiddailydemo.wordcloud;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import com.yxhuang.androiddailydemo.R;

import java.util.ArrayList;

/**
 * Created by Yashar on 02/24/2018.
 * https://github.com/yasharpm/WordCloud
 */

public class WordCloudView extends View {

    private static final int ROTATION_TRIES = 7;
    private static final float SPIRAL_PROGRESS_STEP = 0.003f;

    public interface OnWordClickListener {

        void onWordClicked(int position, Word word);

    }

    private SpiralProvider mSpiralProvider = SpiralProvider.DEFAULT_SPIRAL;
    private RotationProvider mRotationProvider = RotationProvider.DEFAULT;

    private WordAdapter mAdapter = null;
    private ArrayList<Word> mWords = null;

    private PointF mHPoint = new PointF();

    private boolean mIsAttached = false;

    private GestureDetector mGestureDetector;

    private OnWordClickListener mOnWordClickListener = null;

    private int mWordBgColor = Color.TRANSPARENT;
    private float mWordBgRadius = 0F;
    private float mWordHorizontalPadding = 0F;

    public WordCloudView(Context context) {
        super(context);
        initialize(context, null, 0, 0);
    }

    public WordCloudView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context, attrs, 0, 0);
    }

    public WordCloudView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WordCloudView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initialize(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initialize(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        mGestureDetector = new GestureDetector(context, mOnGestureListener);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.WordCloudViewTextStyle);
        mWordBgColor = typedArray.getColor(R.styleable.WordCloudViewTextStyle_word_bg_color, Color.TRANSPARENT);
        mWordBgRadius = typedArray.getDimension(R.styleable.WordCloudViewTextStyle_word_bg_radius, 0f);
        mWordHorizontalPadding = typedArray.getDimension(R.styleable.WordCloudViewTextStyle_word_horizontal_padding, 0f);
        typedArray.recycle();
    }

    public void setOnWordClickListener(OnWordClickListener listener) {
        mOnWordClickListener = listener;
    }

    public void setSpiralProvider(SpiralProvider spiralProvider) {
        mSpiralProvider = spiralProvider;

        requestLayout();
    }

    public void setRotationProvider(RotationProvider rotationProvider) {
        mRotationProvider = rotationProvider;

        requestLayout();
    }

    public void setAdapter(WordAdapter adapter) {
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
            mAdapter = null;
        }

        mAdapter = adapter;

        if (mIsAttached) {
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }

        processWords();

        requestLayout();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        mIsAttached = true;

        if (mAdapter != null) {
            mAdapter.registerDataSetObserver(mDataSetObserver);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();

        mIsAttached = false;

        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(mDataSetObserver);
        }
    }

    private DataSetObserver mDataSetObserver = new DataSetObserver() {

        @Override
        public void onChanged() {
            processWords();
        }
    };

    private void processWords() {
        mWords = new ArrayList<>(mAdapter.getCount());
        for (int i = 0; i < mAdapter.getCount(); i++) {
            mWords.add(new Word(i, mAdapter.getText(i), mAdapter.getFont(i), mAdapter.getTextSize(i),
                    mAdapter.getPadding(i), mAdapter.getTextColor(i), mWordBgColor, mWordBgRadius, mWordHorizontalPadding));
        }
        placeWords();
        invalidate();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        if (getWidth() > 0) {
            placeWords();
        }
    }

    private void placeWords() {
        int width = getWidth();
        int height = getHeight();

        if (width == 0 || height == 0 || mWords == null) {
            return;
        }

        for (int i = 0; i < mWords.size(); i++) {
            Word word = mWords.get(i);
            placeWord(word, i, 0);
        }
    }

    private float placeWord(Word word, int index, float t) {
        word.isPlaced = false;

        while (t < 1) {
            mSpiralProvider.getSpiralPoint(t, getWidth(), getHeight(), mHPoint);

            word.x = mHPoint.x;
            word.y = mHPoint.y;

            for (int i = 0; i < ROTATION_TRIES; i++) {
                word.rotation = mRotationProvider.getRotation(index, (float) index / mWords.size());
                word.isMeasured = false;
                boolean success = true;

                for (int wIndex = 0; wIndex < index; wIndex++) {
                    if (word.collides(mWords.get(wIndex))) {
                        success = false;
                        break;
                    }
                }

                if (success) {
                    word.isPlaced = true;
                    return t;
                }
            }

            t += SPIRAL_PROGRESS_STEP;
        }

        return t;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mWords != null) {
            for (Word word : mWords) {
                word.draw(canvas);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    private GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.OnGestureListener() {

        @Override
        public boolean onDown(MotionEvent motionEvent) {
            return mOnWordClickListener != null;
        }

        @Override
        public void onShowPress(MotionEvent motionEvent) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent motionEvent) {
            if (mWords == null) {
                return false;
            }

            float x = motionEvent.getX();
            float y = motionEvent.getY();

            for (Word word : mWords) {
                if (word.contains(x, y)) {
                    if (mOnWordClickListener != null) {
                        mOnWordClickListener.onWordClicked(word.position, word);
                    }

                    return true;
                }
            }

            return true;
        }

        @Override
        public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent motionEvent) {
        }

        @Override
        public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
            return false;
        }

    };

    public static class Word implements Comparable {

        private int position;

        private String text;

        private Paint paint;
        private Paint mBgPaint;

        private float width;
        private float height;

        private float x;
        private float y;
        private float rotation;

        private boolean isMeasured = false;
        private PointF[] points = new PointF[4];

        private boolean isPlaced = false;

        private float[] matPoints = new float[8];


        private int wordBgColor = Color.TRANSPARENT;
        private float wordBgRadius = 0F;

        private float horizontalPadding = 0F;
        private float fixPadding = 20F;


        protected Word(int position, String text, Typeface font, float textSize, float padding, int textColor) {
            this(position, text, font, textSize, padding, textColor, Color.TRANSPARENT, 0, 0);
        }

        protected Word(int position, String text, Typeface font, float textSize, float padding,
                       int textColor, int wordBgColor, float wordBgRadius, float wordHorizontalPadding) {
            this.position = position;
            this.text = text;
            this.horizontalPadding = wordHorizontalPadding;

            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setStyle(Paint.Style.FILL);
            paint.setTextAlign(Paint.Align.CENTER);

            paint.setColor(textColor);
            paint.setTextSize(textSize);

            if (font != null) {
                paint.setTypeface(font);
            }

            this.wordBgColor = wordBgColor;
            this.wordBgRadius = wordBgRadius;
            mBgPaint = new Paint();
            mBgPaint.setAntiAlias(true);
            mBgPaint.setStyle(Paint.Style.FILL_AND_STROKE);
            mBgPaint.setColor(wordBgColor);


            width = paint.measureText(text) + 2 * padding;
            height = paint.getFontMetrics().bottom - paint.getFontMetrics().top + 2 * padding;

            for (int i = 0; i < points.length; i++) {
                points[i] = new PointF();
            }

        }

        protected boolean collides(Word word) {
            if (!isMeasured) {
                measure();
                isMeasured = true;
            }

            if (!word.isMeasured) {
                word.measure();
                word.isMeasured = true;
            }

            return arePolygonsIntersecting(points, word.points);
        }

        protected boolean contains(float x, float y) {
            x -= this.x;
            y -= this.y;

            float innerSize = Math.min(width, height) / 2;

            if (Math.abs(x) < innerSize && Math.abs(y) < innerSize) {
                return true;
            }

            float sin = (float) Math.sin(-rotation / 180 * Math.PI);
            float cos = (float) Math.cos(-rotation / 180 * Math.PI);

            float newX = x * cos - y * sin;
            float newY = x * sin + y * cos;

            if ((Math.abs(newX) < width / 2 && Math.abs(newY) < height / 2)) {
                return true;
            }

            return (Math.abs(newX) < width / 2 && Math.abs(newY) < height / 2);
        }

        private void measure() {
            points[0].set(-width / 2 - horizontalPadding - fixPadding, -height / 2);
            points[1].set(-points[0].x - horizontalPadding - fixPadding, points[0].y);
            points[2].set(points[1].x + horizontalPadding + fixPadding, -points[1].y);
            points[3].set(-points[2].x + horizontalPadding + fixPadding, points[2].y);

            Matrix matrix = new Matrix();
            matrix.setRotate(rotation);

            for (int i = 0; i < 4; i++) {
                matPoints[i * 2] = points[i].x;
                matPoints[i * 2 + 1] = points[i].y;
            }

            matrix.mapPoints(matPoints);

            for (int i = 0; i < 4; i++) {
                points[i].x = x + matPoints[i * 2];
                points[i].y = y + matPoints[i * 2 + 1];
            }
        }

        protected void draw(Canvas canvas) {
            if (!isPlaced) {
                return;
            }

            Paint.FontMetrics fm = paint.getFontMetrics();
            canvas.save();
            canvas.translate(x, y);
            canvas.rotate(rotation);

            // 绘制背景
            float textLength = paint.measureText(text);
            float halfTextLength = textLength / 2;
            float dy = (-fm.top + fm.bottom) / 2;
            canvas.drawRoundRect(new RectF(-halfTextLength - horizontalPadding, dy,
                    halfTextLength + horizontalPadding, -dy), wordBgRadius, wordBgRadius, mBgPaint);

            canvas.drawText(text, 0, -(fm.ascent + fm.descent) / 2, paint);
            canvas.restore();
        }

        @Override
        public int compareTo(Object o) {
            return (int) ((((Word) o).paint.getTextSize()) - paint.getTextSize());
        }

        public float getX() {
            return x;
        }

        public float getY() {
            return y;
        }

        public String getText() {
            return text;
        }

        public boolean isPlaced() {
            return isPlaced;
        }

    }

    private static PointF[][] polygons = new PointF[2][];

    private static boolean arePolygonsIntersecting(PointF[] a, PointF[] b) {
        polygons[0] = a;
        polygons[1] = b;

        for (int i = 0; i < polygons.length; i++) {

            // for each polygon, look at each edge of the polygon, and determine if it separates
            // the two shapes
            PointF[] polygon = polygons[i];

            for (int i1 = 0; i1 < polygon.length; i1++) {

                // grab 2 vertices to create an edge
                int i2 = (i1 + 1) % polygon.length;
                PointF p1 = polygon[i1];
                PointF p2 = polygon[i2];

                // find the line perpendicular to this edge
                PointF normal = new PointF(p2.y - p1.y, p1.x - p2.x);

                float minA = Float.MAX_VALUE;
                float maxA = Float.MIN_VALUE;

                // for each vertex in the first shape, project it onto the line perpendicular to the edge
                // and keep track of the min and max of these values
                for (int j = 0; j < polygons[0].length; j++) {
                    float projected = normal.x * polygons[0][j].x + normal.y * polygons[0][j].y;
                    if (projected < minA) {
                        minA = projected;
                    }
                    if (projected > maxA) {
                        maxA = projected;
                    }
                }

                // for each vertex in the second shape, project it onto the line perpendicular to the edge
                // and keep track of the min and max of these values
                float minB = Float.MAX_VALUE;
                float maxB = Float.MIN_VALUE;

                for (int j = 0; j < polygons[1].length; j++) {
                    float projected = normal.x * polygons[1][j].x + normal.y * polygons[1][j].y;
                    if (projected < minB) {
                        minB = projected;
                    }
                    if (projected > maxB) {
                        maxB = projected;
                    }
                }

                // if there is no overlap between the projects, the edge we are looking at separates the two
                // polygons, and we know there is no overlap
                if (maxA < minB || maxB < minA) {
                    // polygons don't intersect!
                    return false;
                }
            }
        }
        return true;
    }

}
