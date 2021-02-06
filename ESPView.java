package p042ru.geokar2006.modmenu;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Process;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/* renamed from: ru.geokar2006.modmenu.ESPView */
public class ESPView extends View implements Runnable {
    public static int FPS = 30;
    Paint mFilledPaint;
    Paint mStrokePaint;
    Paint mTextPaint;
    Thread mThread;
    long sleepTime = ((long) (1000 / FPS));

    public ESPView(Context context) {
        super(context, (AttributeSet) null, 0);
        InitializePaints();
        setFocusableInTouchMode(false);
        setBackgroundColor(0);
        Thread thread = new Thread(this);
        this.mThread = thread;
        thread.start();
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (canvas != null && getVisibility() == 0) {
            ClearCanvas(canvas);
            FloatingModMenuService.DrawOn(this, canvas);
        }
    }

    public void run() {
        Process.setThreadPriority(10);
        while (this.mThread.isAlive() && !this.mThread.isInterrupted()) {
            try {
                long t1 = System.currentTimeMillis();
                postInvalidate();
                Thread.sleep(Math.max(Math.min(0, this.sleepTime - (System.currentTimeMillis() - t1)), this.sleepTime));
            } catch (InterruptedException it) {
                Log.e("OverlayThread", it.getMessage());
            }
        }
    }

    public void InitializePaints() {
        Paint paint = new Paint();
        this.mStrokePaint = paint;
        paint.setStyle(Paint.Style.STROKE);
        this.mStrokePaint.setAntiAlias(true);
        this.mStrokePaint.setColor(Color.rgb(0, 0, 0));
        Paint paint2 = new Paint();
        this.mFilledPaint = paint2;
        paint2.setStyle(Paint.Style.FILL);
        this.mFilledPaint.setAntiAlias(true);
        this.mFilledPaint.setColor(Color.rgb(0, 0, 0));
        Paint paint3 = new Paint();
        this.mTextPaint = paint3;
        paint3.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mTextPaint.setAntiAlias(true);
        this.mTextPaint.setColor(Color.rgb(0, 0, 0));
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mTextPaint.setStrokeWidth(1.1f);
    }

    public void ClearCanvas(Canvas cvs) {
        cvs.drawColor(0, PorterDuff.Mode.CLEAR);
    }

    public void DrawLine(Canvas cvs, int a, int r, int g, int b, float lineWidth, float fromX, float fromY, float toX, float toY) {
        this.mStrokePaint.setColor(Color.rgb(r, g, b));
        int i = a;
        this.mStrokePaint.setAlpha(a);
        this.mStrokePaint.setStrokeWidth(lineWidth);
        cvs.drawLine(fromX, fromY, toX, toY, this.mStrokePaint);
    }

    public void DrawText(Canvas cvs, int a, int r, int g, int b, String txt, float posX, float posY, float size) {
        this.mTextPaint.setColor(Color.rgb(r, g, b));
        this.mTextPaint.setAlpha(a);
        if (getRight() > 1920 || getBottom() > 1920) {
            this.mTextPaint.setTextSize(4.0f + size);
        } else if (getRight() == 1920 || getBottom() == 1920) {
            this.mTextPaint.setTextSize(2.0f + size);
        } else {
            this.mTextPaint.setTextSize(size);
        }
        cvs.drawText(txt, posX, posY, this.mTextPaint);
    }

    public void DrawCircle(Canvas cvs, int a, int r, int g, int b, float stroke, float posX, float posY, float radius) {
        this.mStrokePaint.setColor(Color.rgb(r, g, b));
        this.mStrokePaint.setAlpha(a);
        this.mStrokePaint.setStrokeWidth(stroke);
        cvs.drawCircle(posX, posY, radius, this.mStrokePaint);
    }

    public void DrawFilledCircle(Canvas cvs, int a, int r, int g, int b, float posX, float posY, float radius) {
        this.mFilledPaint.setColor(Color.rgb(r, g, b));
        this.mFilledPaint.setAlpha(a);
        cvs.drawCircle(posX, posY, radius, this.mFilledPaint);
    }

    public void DrawRect(Canvas cvs, int a, int r, int g, int b, int stroke, float x, float y, float width, float height) {
        this.mStrokePaint.setStrokeWidth((float) stroke);
        this.mStrokePaint.setColor(Color.rgb(r, g, b));
        int i = a;
        this.mStrokePaint.setAlpha(a);
        cvs.drawRect(x, y, x + width, y + height, this.mStrokePaint);
    }

    public void DrawFilledRect(Canvas cvs, int a, int r, int g, int b, float x, float y, float width, float height) {
        this.mFilledPaint.setColor(Color.rgb(r, g, b));
        int i = a;
        this.mFilledPaint.setAlpha(a);
        cvs.drawRect(x, y, x + width, y + height, this.mFilledPaint);
    }
}
