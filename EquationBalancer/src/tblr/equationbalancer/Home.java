package tblr.equationbalancer;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class Home extends Activity {
	
	ImageButton playGame, aboutGame, howTo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		playGame = (ImageButton)this.findViewById(R.id.btn_play);
		aboutGame = (ImageButton)this.findViewById(R.id.btn_about);
		howTo = (ImageButton)this.findViewById(R.id.btn_howto);
		
	}

	 public void aboutScreen(View view)
    {    	
    	Intent aboutGame = new Intent(this, AboutGame.class);
    	startActivityForResult(aboutGame, 1);
    }
	 
	 public void startGame(View view)
	    {    	
	    	Intent mainGame = new Intent(this, MainGame.class);
	    	startActivityForResult(mainGame, 1);
	    }
	 
	 public void howToPlay(View view)
	    {    	
	    	Intent how2Play = new Intent(this, HowToPlay.class);
	    	startActivityForResult(how2Play, 1);
	    }
	 
	 
	
}
