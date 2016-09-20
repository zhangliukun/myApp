package zalezone.view.guide;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import zalezone.surfaceview.R;
import zalezone.zframework.util.BaseUtil;

/**
 * Created by zale on 16/9/19.
 */
public class GuideViewLayout extends RelativeLayout{

    private List<GuideModel> mGuideModelList = new ArrayList<>();
    private float mHalfScreenW, mHalfScreenH;

    private GuideMask guideMask;
    private View mView;
    private Context mContext;

    private OnGuideFinishedListener onGuideFinishedListener;

    public GuideViewLayout(Context context) {
        this(context,null);
    }

    public GuideViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initUI();
    }

    public void addGuideOperation(List<GuideModel> guideModelList){
        mGuideModelList.addAll(guideModelList);
    }

    public void addGuideOperation(GuideModel guideModel){
        mGuideModelList.add(guideModel);
    }

    public interface OnGuideFinishedListener{
        public void guideFinished();
    }


    private void initUI() {
        mView = View.inflate(mContext, R.layout.fra_guide_layout,this);
        guideMask = (GuideMask) mView.findViewById(R.id.guide_view);
        mHalfScreenW = BaseUtil.getScreenWidth(mContext)/2;
        mHalfScreenH = BaseUtil.getScreenHeight(mContext)/2;
    }

    public void startGuide(OnGuideFinishedListener onGuideFinishedListener){
        if (mGuideModelList.size()>0){
            GuideModel guideModel = mGuideModelList.get(0);
            addGuideImage(guideModel);
            this.onGuideFinishedListener = onGuideFinishedListener;
        }
    }

    private void addGuideImage(GuideModel guideModel){
        final ImageView guide = new ImageView(mContext);
        guide.setImageResource(guideModel.getImageResource());
        ((RelativeLayout)mView).addView(guide, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        calculatePosition(guide,guideModel);
        guide.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((RelativeLayout)mView).removeView(guide);
                mGuideModelList.remove(0);
                if (mGuideModelList.size()>0){
                    GuideModel guideModel = mGuideModelList.get(0);
                    addGuideImage(guideModel);
                }else {
                    onGuideFinishedListener.guideFinished();
                }
            }
        });

        guideMask.showGuide(guideModel);
    }

    //只需要两个位置信息
    private void calculatePosition(ImageView guide,GuideModel guideModel){
        LayoutParams lp = (LayoutParams) guide.getLayoutParams();
        if (guideModel.getMarginLeft()!=-1){
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            lp.leftMargin = guideModel.getMarginLeft();
        }
        if (guideModel.getMarginTop()!=-1){
            lp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            lp.topMargin = guideModel.getMarginTop();
        }
        if (guideModel.getMargingRight()!=-1){
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            lp.rightMargin = guideModel.getMargingRight();
        }
        if (guideModel.getMargingBottom()!=-1){
            lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lp.bottomMargin = guideModel.getMargingBottom();
        }
        guide.setLayoutParams(lp);
    }

}
