package zalezone.surfaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.Random;

/**
 * Created by zale on 16/7/11.
 */
public class FireworkView extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder holder;
    private MyThread myThread;

    private double mScreenWidth;


    public FireworkView(Context context) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
        myThread = new MyThread(holder);

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        myThread.isRun = true;
        myThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        myThread.isRun = false;
    }

    

    class MyThread extends Thread{
        private SurfaceHolder holder;
        public boolean isRun;
        Random random =new Random();
        public MyThread(SurfaceHolder holder){
            this.holder = holder;
            isRun = true;
        }

        @Override
        public void run() {
            int x = 0,y=0;
            mScreenWidth = getWidth();
            while (isRun){
                Canvas c = null;
                try {
                    synchronized (holder){
                        c= holder.lockCanvas();
                        c.drawColor(Color.BLACK);
                        Paint p = new Paint();
                        p.setAntiAlias(true);
                        p.setStyle(Paint.Style.STROKE);
                        p.setColor(Color.WHITE);
                        c.drawLine(x,y,x+300,y+300,p);
                        x++;
                        y++;
                        if (x>mScreenWidth){
                            x -= mScreenWidth;
                            y-= mScreenWidth;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (c!=null){
                        holder.unlockCanvasAndPost(c);
                    }
                }

            }
        }
    }

}
