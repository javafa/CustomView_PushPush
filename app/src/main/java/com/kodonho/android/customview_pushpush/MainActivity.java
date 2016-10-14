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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final static int GROUND_LIMIT = 10;
    FrameLayout ground;
    CustomView cv;

    private int groundUnit = 0;
    private int unit = 0;
    private int player_x = 0;
    private int player_y = 0;

    Button btnUp,btnDown,btnLeft,btnRight;
          // Y, X
    int map[ ][ ] = {
            {0,0,0,0,0,0,0,0,1,0},
            {0,0,1,1,1,1,1,0,1,0},
            {0,0,0,1,0,0,0,0,0,0},
            {0,0,0,0,0,1,1,1,0,0},
            {0,1,1,1,1,0,0,0,0,0},
            {0,0,0,0,0,1,0,0,0,0},
            {0,0,0,0,0,1,0,0,1,0},
            {1,1,1,1,0,0,0,1,1,0},
            {0,0,0,0,0,0,0,0,0,0},
            {0,1,1,0,1,1,1,1,0,0},
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dp = getResources().getDisplayMetrics();
        groundUnit = dp.widthPixels;
        unit = groundUnit / GROUND_LIMIT;

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
            case R.id.btnUp    : player_y = player_y + checkCollision("y",- 1); break;    // 위로가면 unit 만큼 y좌표 감소
            case R.id.btnDown  : player_y = player_y + checkCollision("y",+ 1); break;
            case R.id.btnLeft  : player_x = player_x + checkCollision("x",- 1); break;  // 왼쪽으로 unit만큼 x좌표 감소
            case R.id.btnRight : player_x = player_x + checkCollision("x",+ 1); break;
        }
        cv.invalidate();
    }
    // 충돌검사
    private int checkCollision(String direction, int nextValue){
        // 외곽선 체크
        if(direction.equals("y")){
            // y축에서 다음 이동하는 곳의 좌표가
            // 0보다 작거나, GROUND_LIMIT 즉 canvas 보다 크면 0을 리턴해서 이동하지 않게 한다
            if( (player_y + nextValue) < 0 || (player_y + nextValue) >= GROUND_LIMIT )
                return 0;
        }else{
            // x축도 동일
            if( (player_x + nextValue) < 0 || (player_x + nextValue) >= GROUND_LIMIT )
                return 0;
        }

        // 장애물 체크
        if(direction.equals("y")){
            if(map[player_y+nextValue][player_x] == 1)
                return 0;
        }else{
            if(map[player_y][player_x+nextValue] == 1)
                return 0;
        }

        return nextValue;
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
            // map 세팅된 장애물 그리기
            paint.setColor(Color.BLACK);
            for(int i=0; i<GROUND_LIMIT;i++){
                for(int j=0; j<GROUND_LIMIT;j++) {
                    if(map[i][j] == 1){
                        canvas.drawRect(
                                 j*unit
                                ,i*unit
                                ,j*unit +unit
                                ,i*unit +unit
                                , paint);
                    }
                }
            }

            // 플레이어 그리기
            paint.setColor(Color.MAGENTA);
            canvas.drawRect(
                       player_x*unit
                     , player_y*unit
                     , player_x*unit + unit
                     , player_y*unit + unit
                     , paint);
        }
    }
}
