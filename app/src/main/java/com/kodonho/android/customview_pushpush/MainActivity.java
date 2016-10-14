package com.kodonho.android.customview_pushpush;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    FrameLayout ground;
    CustomView cv;
    private int groundUnit = 0;
    private int unit = 0;

    private int player_x = 0;
    private int player_y = 0;

    Button btnUp,btnDown,btnLeft,btnRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dp = getResources().getDisplayMetrics();
        groundUnit = dp.widthPixels;
        unit = groundUnit / 10;

        btnUp = (Button) findViewById(R.id.btnUp);
        btnDown = (Button) findViewById(R.id.btnDown);
        btnLeft = (Button) findViewById(R.id.btnLeft);
        btnRight = (Button) findViewById(R.id.btnRight);

        btnUp.setOnClickListener(this);
        btnDown.setOnClickListener(this);
        btnLeft.setOnClickListener(this);
        btnRight.setOnClickListener(this);

        ground = (FrameLayout) findViewById(R.id.ground);
        cv = new CustomView(this);
        ground.addView(cv);
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnUp: player_y = player_y - unit; break;    // 위로가면 unit 만큼 y좌표 감소
            case R.id.btnDown: player_y = player_y + unit; break;
            case R.id.btnLeft: player_x = player_x - unit; break; // 왼쪽으로 unit만큼 x좌표 감소
            case R.id.btnRight: player_x = player_x + unit; break;
        }
        cv.invalidate();
    }

    class CustomView extends View {
        Paint paint = new Paint();
        public CustomView(Context context) {
            super(context);
        }
        @Override
        protected void onDraw(Canvas canvas) {
            // 운동장 배경 그리기
            paint.setColor(Color.CYAN);
            canvas.drawRect(
                     0, 0
                    ,groundUnit, groundUnit, paint);
            // 플레이어 그리기
            paint.setColor(Color.MAGENTA);
            canvas.drawRect(player_x, player_y,
                     player_x + unit, player_y + unit,
                    paint);
        }
    }
}
