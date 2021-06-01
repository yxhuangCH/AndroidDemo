package com.yxhuang.androiddailydemo.view;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.OffsetEdgeTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.TriangleEdgeTreatment;
import com.yxhuang.androiddailydemo.R;

/**
 * Created by yxhuang
 * Date: 2021/6/1
 * Description:
 *  参考 https://howtodoandroid.com/shapeableimageview-material-components-android/
 *
 */
public class ShapeableImageViewActivity extends AppCompatActivity {

    private TextView mTvTips;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shape_able_activity_layout);
        mTvTips = findViewById(R.id.tv_tips);
        setTvTips();
    }

    private void setTvTips(){
        /**
         * CornerTreatment
         *
         * RoundedCornerTreatment 圆角方案
         * CutCornerTreatment 折角方案
         *
         *
         * EdgeTreatment
         *
         * TriangleEdgeTreatment 三角形
         * MarkerEdgeTreatment 类似于地图的兴趣点
         * OffsetEdgeTreatment 位移
         * BottomAppBarTopEdgeTreatment 应该是中间带有悬浮按钮的效果
         *
         * 作者：菲尼克斯大大
         * 链接：https://juejin.cn/post/6893361764399628296
         * 来源：掘金
         * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
         */
        ShapeAppearanceModel model = ShapeAppearanceModel.builder()
                .setAllCornerSizes(6f)
                .setBottomEdge(new OffsetEdgeTreatment(new TriangleEdgeTreatment(20f, false), 90f))
                .build();
        MaterialShapeDrawable drawable = new MaterialShapeDrawable(model);
        drawable.setTint(Color.parseColor("#FA4B05"));
        drawable.setPaintStyle(Paint.Style.FILL);


        mTvTips.setBackground(drawable);

    }
}
