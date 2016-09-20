package zalezone.view.guide;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import zalezone.surfaceview.R;
import zalezone.zframework.util.BaseUtil;

/**
 * Created by zale on 16/9/18.
 */
public class GuideMask extends View {

    public static final int TYPE_GUIDE_CIRCLE = 0;

    private static final PorterDuff.Mode MODE = PorterDuff.Mode.CLEAR;


    private Context mContext;
    private Paint mPaint;
    private PorterDuffXfermode porterDuffXfermode;

    private int mScreenW, mScreenH;

    private GuideModel mGuideMode;


    public GuideMask(Context context) {
        this(context, null);
    }

    public GuideMask(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        porterDuffXfermode = new PorterDuffXfermode(MODE);

        calculateScreen();
    }

    public void showGuide(GuideModel guideModel) {
        this.mGuideMode = guideModel;
        invalidate();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mGuideMode != null) {
            int sc = canvas.saveLayer(0, 0, mScreenW, mScreenH, null, Canvas.ALL_SAVE_FLAG);

            canvas.drawColor(ContextCompat.getColor(mContext, R.color.guide_bg_color));

            mPaint.setXfermode(porterDuffXfermode);

            canvas.drawCircle(mGuideMode.getX(), mGuideMode.getY(), mGuideMode.getVisibleWidth() / 2, mPaint);

            mPaint.setXfermode(null);

            canvas.restoreToCount(sc);
        }

    }

    private void calculateScreen() {
        mScreenW = BaseUtil.getScreenWidth(mContext);
        mScreenH = BaseUtil.getScreenHeight(mContext);
    }


}
