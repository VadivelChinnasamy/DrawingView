package com.canvasdrawing;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.canvasdrawing.CommonFiles.CanvasView;
import com.canvasdrawing.CommonFiles.Util;

/**
 *
 * Author :  VadivelChinnasamy
 * Title : Drawing view
 * */

public class HomeActivity extends Activity {

    private CanvasView mCanvasView;
    private Animation animAlpha;
    private TextView textView;
    private Spinner spinner;
    private String mBrushSize[] = {"5", "8", "12", "15", "18", "21", "24", "27", "30"};
    private ArrayAdapter<String> itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mCanvasView = findViewById(R.id.view_draw);
        textView = findViewById(R.id.textView);
        spinner = findViewById(R.id.spinner);
        itemsAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mBrushSize);
        spinner.setAdapter(itemsAdapter);

        animAlpha = AnimationUtils.loadAnimation(HomeActivity.this,
                R.anim.anim_alpha);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setVisibility(View.GONE);
            }
        }, 2000);


    }

    /**
     * Undo the drawing which is drawing on the screen
     */
    public void OnUndo(View v) {
        v.setAnimation(animAlpha);
        mCanvasView.onClickUndo();

    }

    /**
     * Redo the drawing which is drawing on the screen
     */
    public void OnRedo(View v) {
        v.setAnimation(animAlpha);
        mCanvasView.onClickRedo();
    }

    /**
     * Erase the drawing which is drawing on the screen
     */
    public void OnEraser(View v) {

        v.setAnimation(animAlpha);
        Toast.makeText(this, "Eraser Enabled and Drawing marker disabled", Toast.LENGTH_SHORT).show();
        mCanvasView.setErase(true);

    }

    /**
     * Brush - Enable to drawing on the screen abd disabled a eraser option
     */
    public void OnBrush(View v) {
        v.setAnimation(animAlpha);

        Toast.makeText(this, "Drawing disabled and Eraser marker enabled", Toast.LENGTH_SHORT).show();
        mCanvasView.setErase(false);
        Util.mDraw_disable = true;
    }

    /***
     * Brush size - You can increase your brush size which you like
     * */
    public void OnBrushSize(View v) {
        v.setAnimation(animAlpha);
        spinner.setVisibility(View.VISIBLE);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, final int i, long l) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        spinner.setVisibility(View.GONE);
                        float size = Float.parseFloat(mBrushSize[i]);
                        mCanvasView.setBrushSize(size);
                    }
                }, 4000);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    /**
     * EraseAll - If you wanna to erase all use this button
     */
    public void OnEraseAll(View v) {
        v.setAnimation(animAlpha);
        android.app.AlertDialog.Builder saveDialog = new android.app.AlertDialog.Builder(this);
        saveDialog.setMessage("Are you sure, You want to erase all?");
        saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mCanvasView.eraseAll();

            }
        });
        saveDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        saveDialog.show();


    }


    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

}
