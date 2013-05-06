package tblr.equationbalancer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class Splash extends Activity{
	 private static long SLEEP_TIME = 2;    // Sleep for some time

	 @Override
	 protected void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);

	  this.requestWindowFeature(Window.FEATURE_NO_TITLE);    // Removes title bar
	  this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,     WindowManager.LayoutParams.FLAG_FULLSCREEN);    // Removes notification bar

	  setContentView(R.layout.splash_screen);
	  Log.d("tblr.equationbalancer","Equation Balancer Started.");
	  // Start timer and launch main activity
	  IntentLauncher launcher = new IntentLauncher();
	  launcher.start();
	}

	 private class IntentLauncher extends Thread {
	  @Override
	  /**
	   * Sleep for some time and than start new activity.
	   */
	  public void run() {
	     try {
	        // Sleeping
	        Thread.sleep(SLEEP_TIME*1000);
	     } catch (Exception e) {
	        System.out.println(e.getMessage());
	     }

	     // Start main activity
	     Intent intent = new Intent(Splash.this, Home.class);
	     Splash.this.startActivity(intent);
	     overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	     Splash.this.finish();
	  }
	}
}
