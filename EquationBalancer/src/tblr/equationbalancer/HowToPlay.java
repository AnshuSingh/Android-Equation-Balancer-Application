package tblr.equationbalancer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

public class HowToPlay extends Activity {

	int clickCtr = 0;
	TextView centerText, xButtonText, statusText, hintEquationText, hintEquation;
	TextView centerHeader, xButtonHeader, statusHeader, hintEquationHeader;
	Button status;
	GridLayout leftGrid,rightGrid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.how_to_play);
		centerText = (TextView)findViewById(R.id.center_text);
		xButtonText = (TextView)findViewById(R.id.xButton_text);
		statusText = (TextView)findViewById(R.id.readyToSolve_text);
		hintEquationText = (TextView)findViewById(R.id.hintEquation_text);
		centerHeader = (TextView)findViewById(R.id.centerHeader);
		xButtonHeader = (TextView)findViewById(R.id.xButton_header);
		statusHeader = (TextView)findViewById(R.id.readyToSolve_header);
		hintEquationHeader = (TextView)findViewById(R.id.hintEquation_header);
		hintEquation = (TextView)findViewById(R.id.hintEquation);
		status = (Button)findViewById(R.id.status);
		leftGrid = (GridLayout)findViewById(R.id.left_grid);
		rightGrid = (GridLayout)findViewById(R.id.right_grid);
	}
	
	public void goHome(View view){
		Intent home = new Intent(this, Home.class);
		finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    	startActivity(home);
	}
	
	public void showNext(View view){
	
		switch(view.getId())
		{
			case R.id.center_cont:
				hideCenterHelp();
				if (clickCtr == 0)
					showEquationHelp();
				if (clickCtr == 1)
					showXButtonHelp();
				clickCtr++;
				break;
			case R.id.equation_cont:
				hideEquationHelp();
				showBalancerHelp();
				break;
			case R.id.xButton_cont:
				hideXButtonHelp();
				if (clickCtr == 2){
					showReadyToSolveGridContents();
					status.setBackgroundResource(R.drawable.readytosolve_enabled2x);
					showStatusHelp(view);
				}
				if (clickCtr == 4){
					hintEquation.setText("4x + 3 = 5x - 2");
					showHintEquationHelp();
				}
				clickCtr++;
				break;
			case R.id.balancer_cont:
				hideBalancerHelp();
				showCenterHelp();
				centerHeader.setText("Build the Equation");
				centerText.setText("\nLet's begin by building the equation shown above.");
				break;
			case R.id.readyToSolve_cont:
				hideStatusHelp();
				if (clickCtr == 3){
					showXButtonHelp();
					status.setBackgroundResource(R.drawable.simplify_disabled2x);
					xButtonHeader.setText("Simplify the Equation");
					xButtonText.setText("You must now simplify the equation by adding or subtracting numbers or X\'s from each side of the scale");
					
				}
				if (clickCtr == 6){
					showSolvedGridContents();
					hintEquation.setText("x = 5"); 
					showHintEquationHelp();
					status.setBackgroundResource(R.drawable.simplify_disabled2x);
					hintEquationHeader.setText("Congratulations");
					hintEquationText.setText("When you finish solving, you should have solved the equation for X!");
				}
				clickCtr++;
				break;
			case R.id.hintEquation_cont:
				hideHintEquationHelp();
				if (clickCtr == 5){
					showSimplifyingGridContents();
					status.setBackgroundResource(R.drawable.simplify2x);
					showStatusHelp(view);
					statusHeader.setText("Simplify");
					statusText.setText("Once you've balanced each side of the scale, \"Simplify\" will light up. Press \"Simplify\" to simplify your equation.");
				}
				if (clickCtr == 7){
					showDeleteHelp();
				}
				clickCtr++;
				break;
			case R.id.home_cont:
				hideHomeHelp();
				showCenterHelp();
				centerHeader.setText("That's it!");
				centerText.setText("\nThat\'s all there is to it! \nAre you ready to take on the challenge? \n \nGood luck!");
				findViewById(R.id.center_cont).setVisibility(View.INVISIBLE);
				break;
			case R.id.delete_cont:
				hideDeleteHelp();
				showHomeHelp();
				break;
			default:
				throw new RuntimeException("Unknow button ID");
		}
		
	}
	private void showSolvedGridContents() {
		leftGrid.setVisibility(View.VISIBLE);
		rightGrid.setVisibility(View.VISIBLE);
		TextView text = (TextView)findViewById(R.id.left_bottom_num);
		text.setText("5");
		text.setVisibility(View.VISIBLE);
		findViewById(R.id.right_greenX14).setVisibility(View.VISIBLE);
		
		findViewById(R.id.left_greenX10).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_greenX11).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_greenX12).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_greenX13).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_up_num).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_greenX10).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_greenX11).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_greenX12).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_greenX13).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_up_num).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_redX00).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_redX01).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_redX02).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_redX03).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_redX00).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_redX01).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_redX02).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_redX03).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_bottom_num).setVisibility(View.INVISIBLE);
		
	}

	private void showSimplifyingGridContents() {
		findViewById(R.id.left_redX00).setVisibility(View.VISIBLE);
		findViewById(R.id.left_redX01).setVisibility(View.VISIBLE);
		findViewById(R.id.left_redX02).setVisibility(View.VISIBLE);
		findViewById(R.id.left_redX03).setVisibility(View.VISIBLE);
		findViewById(R.id.left_up_num).setVisibility(View.VISIBLE);
		findViewById(R.id.right_redX00).setVisibility(View.VISIBLE);
		findViewById(R.id.right_redX01).setVisibility(View.VISIBLE);
		findViewById(R.id.right_redX02).setVisibility(View.VISIBLE);
		findViewById(R.id.right_redX03).setVisibility(View.VISIBLE);
		findViewById(R.id.right_up_num).setVisibility(View.VISIBLE);
		
	}

	void showCenterHelp(){
		 findViewById(R.id.centerHeader).setVisibility(View.VISIBLE);
	     findViewById(R.id.center_cont).setVisibility(View.VISIBLE);
	     findViewById(R.id.center_done).setVisibility(View.VISIBLE);
	     findViewById(R.id.center_help).setVisibility(View.VISIBLE);
	     findViewById(R.id.center_text).setVisibility(View.VISIBLE);
	}
	void hideCenterHelp(){
		 findViewById(R.id.centerHeader).setVisibility(View.INVISIBLE);
	     findViewById(R.id.center_cont).setVisibility(View.INVISIBLE);
	     findViewById(R.id.center_done).setVisibility(View.INVISIBLE);
	     findViewById(R.id.center_help).setVisibility(View.INVISIBLE);
	     findViewById(R.id.center_text).setVisibility(View.INVISIBLE);
	}
	
	void hideEquationHelp(){
		 findViewById(R.id.equation_cont).setVisibility(View.INVISIBLE);
	     findViewById(R.id.equation_done).setVisibility(View.INVISIBLE);
	     findViewById(R.id.equation_help).setVisibility(View.INVISIBLE);
	     findViewById(R.id.equation_text).setVisibility(View.INVISIBLE);
	     findViewById(R.id.equation_header).setVisibility(View.INVISIBLE);
	}
	void showEquationHelp(){
		 findViewById(R.id.equation).setVisibility(View.VISIBLE);
	     findViewById(R.id.equation_cont).setVisibility(View.VISIBLE);
	     findViewById(R.id.equation_done).setVisibility(View.VISIBLE);
	     findViewById(R.id.equation_help).setVisibility(View.VISIBLE);
	     findViewById(R.id.equation_text).setVisibility(View.VISIBLE);
	     findViewById(R.id.equation_header).setVisibility(View.VISIBLE);
	}
	
	void showXButtonHelp(){
		 findViewById(R.id.xButton_cont).setVisibility(View.VISIBLE);
		 findViewById(R.id.xButton_done).setVisibility(View.VISIBLE);
		 findViewById(R.id.xButton_help).setVisibility(View.VISIBLE);
		 findViewById(R.id.xButton_text).setVisibility(View.VISIBLE);
		 findViewById(R.id.xButton_header).setVisibility(View.VISIBLE);
	}
	void hideXButtonHelp(){
		 findViewById(R.id.xButton_cont).setVisibility(View.INVISIBLE);
		 findViewById(R.id.xButton_done).setVisibility(View.INVISIBLE);
		 findViewById(R.id.xButton_help).setVisibility(View.INVISIBLE);
		 findViewById(R.id.xButton_text).setVisibility(View.INVISIBLE);
		 findViewById(R.id.xButton_header).setVisibility(View.INVISIBLE);
	}
	
	void showBalancerHelp(){
		 findViewById(R.id.balancer_cont).setVisibility(View.VISIBLE);
	     findViewById(R.id.balancer_done).setVisibility(View.VISIBLE);
	     findViewById(R.id.balancer_help).setVisibility(View.VISIBLE);
	     findViewById(R.id.balancer_text).setVisibility(View.VISIBLE);
	     findViewById(R.id.balancer_header).setVisibility(View.VISIBLE);
	}
	void hideBalancerHelp(){
		 findViewById(R.id.balancer_cont).setVisibility(View.INVISIBLE);
	     findViewById(R.id.balancer_done).setVisibility(View.INVISIBLE);
	     findViewById(R.id.balancer_help).setVisibility(View.INVISIBLE);
	     findViewById(R.id.balancer_text).setVisibility(View.INVISIBLE);
	     findViewById(R.id.balancer_header).setVisibility(View.INVISIBLE);
	}

	void showStatusHelp(View v){
		 findViewById(R.id.status).setVisibility(View.VISIBLE);
		 findViewById(R.id.readyToSolve_cont).setVisibility(View.VISIBLE);
		 findViewById(R.id.readyToSolve_done).setVisibility(View.VISIBLE);
		 findViewById(R.id.readyToSolve_help).setVisibility(View.VISIBLE);
		 findViewById(R.id.readyToSolve_text).setVisibility(View.VISIBLE);
		 findViewById(R.id.readyToSolve_header).setVisibility(View.VISIBLE);
	}
	void hideStatusHelp(){
		 findViewById(R.id.readyToSolve_cont).setVisibility(View.INVISIBLE);
		 findViewById(R.id.readyToSolve_done).setVisibility(View.INVISIBLE);
		 findViewById(R.id.readyToSolve_help).setVisibility(View.INVISIBLE);
		 findViewById(R.id.readyToSolve_text).setVisibility(View.INVISIBLE);
		 findViewById(R.id.readyToSolve_header).setVisibility(View.INVISIBLE);
		 
	}
	
	void showHintEquationHelp(){
		 findViewById(R.id.hintEquation_cont).setVisibility(View.VISIBLE);
		 findViewById(R.id.hintEquation_done).setVisibility(View.VISIBLE);
		 findViewById(R.id.hintEquation_help).setVisibility(View.VISIBLE);
		 findViewById(R.id.hintEquation_text).setVisibility(View.VISIBLE);
		 findViewById(R.id.hintEquation).setVisibility(View.VISIBLE);
		 findViewById(R.id.hintEquation_header).setVisibility(View.VISIBLE);
	}
	void hideHintEquationHelp(){
		 findViewById(R.id.hintEquation_cont).setVisibility(View.INVISIBLE);
		 findViewById(R.id.hintEquation_done).setVisibility(View.INVISIBLE);
		 findViewById(R.id.hintEquation_help).setVisibility(View.INVISIBLE);
		 findViewById(R.id.hintEquation_text).setVisibility(View.INVISIBLE);
		 findViewById(R.id.hintEquation_header).setVisibility(View.INVISIBLE);
	}

	void showHomeHelp(){
		findViewById(R.id.home_cont).setVisibility(View.VISIBLE);
		findViewById(R.id.home_done).setVisibility(View.VISIBLE);
		findViewById(R.id.home_help).setVisibility(View.VISIBLE);
		findViewById(R.id.home_text).setVisibility(View.VISIBLE);
		findViewById(R.id.home_header).setVisibility(View.VISIBLE);
	}
	void hideHomeHelp(){
		findViewById(R.id.home_cont).setVisibility(View.INVISIBLE);
		findViewById(R.id.home_done).setVisibility(View.INVISIBLE);
		findViewById(R.id.home_help).setVisibility(View.INVISIBLE);
		findViewById(R.id.home_text).setVisibility(View.INVISIBLE);
		findViewById(R.id.home_header).setVisibility(View.INVISIBLE);
	}
	
	void showDeleteHelp(){
		findViewById(R.id.delete_cont).setVisibility(View.VISIBLE);
		findViewById(R.id.delete_done).setVisibility(View.VISIBLE);
		findViewById(R.id.delete_help).setVisibility(View.VISIBLE);
		findViewById(R.id.delete_text).setVisibility(View.VISIBLE);
		findViewById(R.id.delete_header).setVisibility(View.VISIBLE);
	}
	void hideDeleteHelp(){
		findViewById(R.id.delete_cont).setVisibility(View.INVISIBLE);
		findViewById(R.id.delete_done).setVisibility(View.INVISIBLE);
		findViewById(R.id.delete_help).setVisibility(View.INVISIBLE);
		findViewById(R.id.delete_text).setVisibility(View.INVISIBLE);
		findViewById(R.id.delete_header).setVisibility(View.INVISIBLE);
	}
	
	
	void showReadyToSolveGridContents(){
		leftGrid.setVisibility(View.VISIBLE);
		rightGrid.setVisibility(View.VISIBLE);
		findViewById(R.id.left_redX00).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_redX01).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_redX02).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_redX03).setVisibility(View.INVISIBLE);
		findViewById(R.id.left_up_num).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_redX00).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_redX01).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_redX02).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_redX03).setVisibility(View.INVISIBLE);
		findViewById(R.id.right_up_num).setVisibility(View.INVISIBLE);
	}
	

}
