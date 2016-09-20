package zalezone.view.guide;

/**
 * Created by zale on 16/9/19.
 */
public class GuideModel {
    private float x;
    private float y;
    private float visibleWidth;
    private int visibleType;
    private int imageResource;
    private int marginLeft;
    private int marginTop;
    private int margingRight;
    private int margingBottom;

    public GuideModel(float x, float y, float visibleWidth, int visibleType, int imageResource,
                      int marginLeft, int marginTop, int margingRight, int margingBottom) {
        this.x = x;
        this.y = y;
        this.visibleWidth = visibleWidth;
        this.visibleType = visibleType;
        this.imageResource = imageResource;
        this.marginLeft = marginLeft;
        this.marginTop = marginTop;
        this.margingRight = margingRight;
        this.margingBottom = margingBottom;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getVisibleWidth() {
        return visibleWidth;
    }

    public void setVisibleWidth(float visibleWidth) {
        this.visibleWidth = visibleWidth;
    }

    public int getVisibleType() {
        return visibleType;
    }

    public void setVisibleType(int visibleType) {
        this.visibleType = visibleType;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public int getMarginLeft() {
        return marginLeft;
    }

    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }

    public int getMarginTop() {
        return marginTop;
    }

    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }

    public int getMargingRight() {
        return margingRight;
    }

    public void setMargingRight(int margingRight) {
        this.margingRight = margingRight;
    }

    public int getMargingBottom() {
        return margingBottom;
    }

    public void setMargingBottom(int margingBottom) {
        this.margingBottom = margingBottom;
    }
}
