package tblr.equationbalancer;

import tblr.equationbalancer.fireworks.FireworkLayout;
import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class Done extends Activity {
	final SoundPool endSoundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	int winSound;
	FireworkLayout surfaceView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		winSound = endSoundPool.load(this, R.raw.winloop, 1);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.end_screen);
		endSoundPool.play(winSound, 1.0f, 1.0f, 0, -1, 1.0f);
        Intent intent = getIntent();
		String equation = intent.getStringExtra("equation"); 
		String answer = intent.getStringExtra("answer"); 
		TextView txtEquation = (TextView)findViewById(R.id.endscreen_equation);
		TextView txtAnswer = (TextView)findViewById(R.id.solvedX);
		txtEquation.setText(equation);
		txtAnswer.append(answer);
		surfaceView = (FireworkLayout)findViewById(R.id.fireworkSurface);
		surfaceView.setZOrderOnTop(true);    // necessary
        SurfaceHolder holder =  surfaceView.getHolder();
        holder.setFormat(PixelFormat.TRANSLUCENT);
	}
	
	public void goBack(View view){
		endSoundPool.release();
		surfaceView.stopFireworks();
		Intent mainGame = new Intent(this, MainGame.class);endSoundPool.release();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
    	startActivity(mainGame);
		
	}
	
	public void goHome(View view){
		endSoundPool.release();
		surfaceView.stopFireworks();
		Intent home = new Intent(this, Home.class);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		finish();
    	startActivity(home);
	}
	

}
