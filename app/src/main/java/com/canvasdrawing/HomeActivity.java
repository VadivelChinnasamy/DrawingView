package com.canvasdrawing;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.canvasdrawing.CommonFiles.CanvasView;
import com.canvasdrawing.CommonFiles.Util;

public class HomeActivity extends Activity {

    private CanvasView mCanvasView;
    private Animation animAlpha;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mCanvasView = findViewById(R.id.view_draw);
        textView=findViewById(R.id.textView);

        animAlpha = AnimationUtils.loadAnimation(HomeActivity.this,
                R.anim.anim_alpha);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.GONE);
            }
        },2000);

    }

    /**
     * Undo the drawing which is drawing on the screen
     * */
    public void OnUndo(View v) {
        v.setAnimation(animAlpha);
        mCanvasView.onClickUndo();

    }
    /**
     * Redo the drawing which is drawing on the screen
     * */
    public void OnRedo(View v) {
        v.setAnimation(animAlpha);
        mCanvasView.onClickRedo();
    }
    /**
     * Erase the drawing which is drawing on the screen
     * */
    public void OnEraser(View v) {

        v.setAnimation(animAlpha);
        Toast.makeText(this, "Eraser Enabled and Drawing marker disabled", Toast.LENGTH_SHORT).show();
        mCanvasView.setErase(true);

    }
    /**
     * Brush - Enable to drawing on the screen abd disabled a eraser option
     * */
    public void OnBrush(View v) {
        v.setAnimation(animAlpha);
        Toast.makeText(this, "Drawing disabled and Eraser marker enabled", Toast.LENGTH_SHORT).show();
        mCanvasView.setErase(false);
        Util.mDraw_disable = true;
    }
}
