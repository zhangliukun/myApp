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
public class GuideView extends View{

    private static final PorterDuff.Mode MODE = PorterDuff.Mode.DST_OUT;

    private Context mContext;
    private Paint mPaint;
    private PorterDuffXfermode porterDuffXfermode;

    private int mScreenW,mScreenH;


    public GuideView(Context context) {
        this(context,null);
    }

    public GuideView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        porterDuffXfermode = new PorterDuffXfermode(MODE);

        calculatePosition();
    }

    private void calculatePosition() {
        mScreenW = BaseUtil.getScreenWidth(mContext);
        mScreenH = BaseUtil.getScreenHeight(mContext);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int sc = canvas.saveLayer(0,0,mScreenW,mScreenH,null,Canvas.ALL_SAVE_FLAG);

        canvas.drawColor(ContextCompat.getColor(mContext, R.color.guide_bg_color));

        mPaint.setXfermode(porterDuffXfermode);

        canvas.drawRect(100,100,500,500,mPaint);

        mPaint.setXfermode(null);

        canvas.restoreToCount(sc);



    }
}
