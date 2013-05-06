package tblr.equationbalancer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AboutGame extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about_game);
	}
	
	public void goBack(View view){
		Intent home = new Intent(this, Home.class);
		finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    	startActivity(home);
	}

}
