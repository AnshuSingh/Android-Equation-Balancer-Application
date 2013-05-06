package tblr.equationbalancer;

import java.util.ArrayList;
import java.lang.Math;
import tblr.equationbalancer.NumKeyPad.NumKeyPadListener;
import tblr.equationbalancer.model.Equation;
import tblr.equationbalancer.model.GridObject;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainGame  extends Activity implements NumKeyPadListener{
   
	private int leftBottomCount, rightBottomCount, leftTopCount, rightTopCount, trackLhsCoeff,trackRhsCoeff;
	final SoundPool soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
	int magicSound,clickSound, deleteSound;
	private final int MAXX = 5;
	private final int PLUS = 0, TOP = 10, LEFT = 3, IMGBUTTON = 0;
	private final int MINUS = 1, BOTTOM  = 20, RIGHT = 5,  TEXT = 1;
	private int lnumcol , rnumcol;
	ArrayList<GridObject> gridObj;
	private static ArrayList<Equation> equationList = new ArrayList<Equation>();
	private static int currEquation;
	int buttonClickedId, numRow, row, lesserSide;
	boolean isNumNegative, isReadyToSolve;
	String mNum;
	TextView rhsConst,lhsConst,lhsOp, rhsOp, lhsCoeff, rhsCoeff;
	TextView equalTo, hintLhsConst,hintRhsConst, hintLhsCoeff, hintRhsCoeff;
	TextView topLeftNumText,bottomLeftNumText, topRightNumText, bottomRightNumText;
	GridLayout left_grid,right_grid;
	GridObject bottomLeftNumObj, topLeftNumObj,bottomRightNumObj, topRightNumObj;
	Equation generatedEquation, solvingEquation, simplifyEquation;
	Button statusBtn;
	int ctrX;
	//Red 680000 Green 005008
	Integer leftDiff , rightDiff;
	private boolean isSimplifyingX, isSimplifyingNum;
	private int rightBottomSum,leftBottomSum, rightTopSum, leftTopSum;
	ViewGroup relLayout;
	View leftRedX,leftGreenX,rightRedX,rightGreenX;
	float blkWidth, blkHeight;
	private int numRemovableCol;
	private boolean lnumInBetween;
	private boolean rnumInBetween;
	private boolean isReadyToSolveClicked;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		magicSound = soundPool.load(this, R.raw.magic, 1);
		setContentView(R.layout.main_game);
		clickSound = soundPool.load(this, R.raw.click, 1);
		deleteSound = soundPool.load(this, R.raw.delete, 1);
		generatedEquation = new Equation();
		generatedEquation.generate();
		equationList.add(generatedEquation);
		currEquation =  equationList.size()-1;
		init();
		rhsOp = (TextView)this.findViewById(R.id.rhsOP);
		lhsOp = (TextView)this.findViewById(R.id.lhsOP);
		rhsConst = (TextView)this.findViewById(R.id.rhsConst);
		lhsConst = (TextView)this.findViewById(R.id.lhsConst);
		lhsCoeff  = (TextView)this.findViewById(R.id.lhsCoeff);
		rhsCoeff  = (TextView)this.findViewById(R.id.rhsCoeff);
		hintLhsCoeff = (TextView)this.findViewById(R.id.hintLhsCoeff);
		hintRhsCoeff  = (TextView)this.findViewById(R.id.hintRhsCoeff);
		hintLhsConst = (TextView)this.findViewById(R.id.hintLhsConst);
		hintRhsConst  = (TextView)this.findViewById(R.id.hintRhsConst);
		equalTo = (TextView)this.findViewById(R.id.equalTo);
		left_grid = (GridLayout)this.findViewById(R.id.left_grid);
		right_grid = (GridLayout)this.findViewById(R.id.right_grid);
		relLayout = (ViewGroup) findViewById(R.id.RelativeLayout1);
		leftRedX = findViewById(R.id.left_redX);
		leftGreenX = findViewById(R.id.left_greenX);
		rightRedX = findViewById(R.id.right_redX);
		rightGreenX = findViewById(R.id.right_greenX);
	}
	
	public void addButtons(View view){
		buttonClickedId = view.getId();
		blkWidth = leftGreenX .getWidth();
		blkHeight = leftGreenX .getHeight();
		soundPool.play(clickSound, 1.0f, 1.0f, 0, 0, 1.0f);
		int position = -1;
		switch(buttonClickedId)
		{
			case R.id.left_greenX:
				if (isReadyToSolve){
					showToast(LEFT+PLUS);
					break;
				}
				position = findNextAvailablePosition(LEFT+PLUS+BOTTOM);
				if (position != -1 )
					addX(LEFT+PLUS+BOTTOM, position);					
				break;
			case R.id.left_redX:
				if (!isReadyToSolve){
					showToast(LEFT+MINUS);
					break;
				}
				if (isReadyToSolve && !isReadyToSolveClicked){
					showToast(IMGBUTTON);
					break;
				}
				position = findNextAvailablePosition(LEFT+PLUS+TOP);
				if (position != -1 && position <= generatedEquation.getLhsCoefficient())
					addX(LEFT+PLUS+TOP, position);		
				break;
			case R.id.right_greenX: 
				if (isReadyToSolve){
					showToast(RIGHT+PLUS);
					break;
				}
				position = findNextAvailablePosition(RIGHT+PLUS+BOTTOM);
				if (position != -1 )
					addX(RIGHT+PLUS+BOTTOM, position);		
				break;
				
			case R.id.right_redX:
				if (!isReadyToSolve){
					showToast(RIGHT+MINUS);
					break;
				}
				if (isReadyToSolve && !isReadyToSolveClicked){
					showToast(IMGBUTTON);
					break;
				}
				position = findNextAvailablePosition(RIGHT+PLUS+TOP);
				if (position != -1 && position <= generatedEquation.getRhsCoefficient())
					addX(RIGHT+PLUS+TOP, position);		
				break;
			case R.id.left_greenNum:
				showNumPad(false);
				break;
			case R.id.left_redNum:
				showNumPad(true);
				break;
			case R.id.right_greenNum:
				showNumPad(false);
				break;
			case R.id.right_redNum:
				showNumPad(true);
				break;
			
			case R.id.status:
				statusBtn.setEnabled(false);
				statusBtn.setBackgroundResource(R.drawable.simplify_disabled2x);
				if (isReadyToSolve){
					row = TOP;
					isReadyToSolveClicked = true;
				}
				if (isSimplifyingX)
					removeX();
				if (isSimplifyingNum)
					solveNumeric();
				checkSolved();
				break;
			default:
			throw new RuntimeException("Unknow button ID");
		}
		
	}
	
	public void goBack(View view){
		soundPool.release();
		Intent home = new Intent(this, Home.class);
		finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    	startActivity(home);
	}
	
	public void showPrevious(View view){
		if (currEquation > 0){
			generatedEquation =  equationList.get(--currEquation);
			clearScreen();
			init();
		}
	}
	
	public void showNext(View view){
		if (currEquation < equationList.size()-1 && currEquation >= 0){
			generatedEquation =  equationList.get(++currEquation);
			clearScreen();
			init();
			
		}
	}
	
	public void goHome(View view){
		soundPool.release();
		Intent home = new Intent(this, Home.class);
		finish();
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    	startActivity(home);
	}
	
	@Override
	public void onDialogPositiveClick(String selectedValue) {
		mNum = selectedValue;
		//equalTo.setText(" = ");
		switch(buttonClickedId){
		case R.id.left_greenNum:
			if (row == TOP)
				leftTopSum += Integer.parseInt(mNum);
			else
				leftBottomSum += Integer.parseInt(mNum);
			setNumPosition(LEFT);
			break;
		case R.id.left_redNum:
			if (row == TOP)
				leftTopSum -= Integer.parseInt(mNum);
			else
				leftBottomSum -= Integer.parseInt(mNum);
			setNumPosition(LEFT);
			break;
		case R.id.right_greenNum:
			if (row == TOP)
				rightTopSum += Integer.parseInt(mNum);
			else
				rightBottomSum += Integer.parseInt(mNum);
			setNumPosition(RIGHT);
			break;
		case R.id.right_redNum:
			if (row == TOP)
				rightTopSum -= Integer.parseInt(mNum);
			else
				rightBottomSum -= Integer.parseInt(mNum);
			setNumPosition(RIGHT);
			break;
		}
		if (!isReadyToSolve){
			checkReadyToSolve();
			return;
		}
	}	
	
	private void init(){
		leftBottomCount = rightBottomCount = leftTopCount = rightTopCount = 0;
		lnumcol = rnumcol = -1;
		row = BOTTOM;
		isNumNegative = isReadyToSolve = isSimplifyingX = isSimplifyingNum = lnumInBetween = false;
		rnumInBetween = isReadyToSolveClicked = false;
		leftTopSum = rightTopSum = leftBottomSum = rightBottomSum = ctrX = 0;
		lesserSide = -1;
		mNum = "";
		leftDiff = rightDiff =  trackLhsCoeff = trackRhsCoeff = 0;
		solvingEquation = new Equation();
		simplifyEquation = new Equation();
		gridObj = new ArrayList<GridObject>();
		TextView equation = (TextView)this.findViewById(R.id.equation);
		equation.setText(generatedEquation.getStrEquation());
		
		statusBtn = (Button)this.findViewById(R.id.status);
		statusBtn.setEnabled(false);
		bottomLeftNumObj = new GridObject();
		topLeftNumObj = new GridObject();
		bottomRightNumObj = new GridObject();
		topRightNumObj = new GridObject();
		
		bottomLeftNumText = getTextView(bottomLeftNumText);
		bottomRightNumText = getTextView(bottomRightNumText);
		topLeftNumText = getTextView(topLeftNumText);
		topRightNumText = getTextView(topRightNumText);
		soundPool.play(magicSound, 1.0f, 1.0f, 0, 0, 1.0f);
	}
	
	private void clearHints()
	{
		String empty = "";
		rhsCoeff.setText(empty);
		lhsCoeff.setText(empty);
		rhsConst.setText(empty);
		lhsConst.setText(empty);
		rhsOp.setText(empty);
		lhsOp.setText(empty);
		equalTo.setText(empty);
		hintLhsCoeff.setText(empty);
		hintRhsCoeff.setText(empty);
		hintLhsConst.setText(empty);
		hintRhsConst.setText(empty);
	}
	
	private void clearScreen(){
		for (int i = gridObj.size()-1; i>=0;i--){
			GridObject gObj = gridObj.get(i);
			if (gObj.getType() == IMGBUTTON)
				((ViewGroup) gObj.getImgBtn().getParent()).removeView(gObj.getImgBtn());
			else 
				((ViewGroup) gObj.getNumText().getParent()).removeView(gObj.getNumText());
		}
		gridObj.clear();
		
		statusBtn.setBackgroundResource(R.drawable.readytosolve_disabled2x);
		statusBtn.setEnabled(false);
		clearHints();
	}
	
	private void checkSolved() {
		int correct = 0;
		if (generatedEquation.getLhsCoefficient() < generatedEquation.getRhsCoefficient()){
			if ( trackLhsCoeff == generatedEquation.getLhsCoefficient())
				correct++;
			if ( trackRhsCoeff == (generatedEquation.getRhsCoefficient()-1))
				correct++;
			if (leftDiff == generatedEquation.getAnswer()) 
				correct++;
		}
		if (generatedEquation.getLhsCoefficient() > generatedEquation.getRhsCoefficient()){
			if (trackLhsCoeff == generatedEquation.getLhsCoefficient()-1)
				correct++;
			if ( trackRhsCoeff == (generatedEquation.getRhsCoefficient()))
				correct++;
			if (rightDiff == generatedEquation.getAnswer()) 
				correct++;
		}
		if (correct == 3){
			clearHints();
			equalTo.setText("x = "+generatedEquation.answer);
			Intent done = new Intent(this, Done.class);
			done.putExtra("equation",(generatedEquation.getStrEquation()).toString());
			Integer answer = generatedEquation.answer;
			String strAns = answer.toString();
			done.putExtra("answer",strAns);
			finish();
			overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
	    	startActivity(done);
		}
	}
	
	private boolean checkMax(int count){
		return (count <= MAXX);
	}
	
	private boolean checkMin(int count){
		return (count > 0);
	}
	
	private int findNextAvailablePosition(int state){
		int position = -1;
		switch (state){
			case (PLUS + LEFT + BOTTOM) :
				if (checkMax(leftBottomCount))
					position = leftBottomCount++;
				break;
			case (PLUS + LEFT + TOP):
				if (checkMax(leftTopCount)){
					if (lnumcol == leftTopCount){
						leftTopCount++;
						position = leftTopCount++;
						lnumInBetween = true;
					}else
						position = leftTopCount++;
				}
				break;
			case (PLUS + RIGHT + BOTTOM):
				if (checkMax(rightBottomCount))
					position = rightBottomCount++;
				break;
			case (PLUS + RIGHT + TOP):
				if (checkMax(rightTopCount)){
					if (rnumcol == rightTopCount){
						rightTopCount++;
						position = rightTopCount++;
						rnumInBetween = true;
					}else
					position = rightTopCount++;
				}
				break;		
			case (MINUS + LEFT + BOTTOM) :
				if (checkMin(leftBottomCount))
					position = leftBottomCount--;
				break;
			case (MINUS + LEFT + TOP):
				if (checkMin(leftTopCount)){
					position = leftTopCount--;
					if (leftTopCount == lnumcol)
						position = leftBottomCount--;
				}
				break;
			case (MINUS + RIGHT + BOTTOM):
				if (checkMin(rightBottomCount))
					position = rightBottomCount--;
				break;
			case (MINUS + RIGHT + TOP):
				if (checkMin(rightTopCount))
					position = rightTopCount--;
				break;
			}
		return position;
	}
	
	private ImageButton getXButton(int color){
		ImageButton dynx2x = new ImageButton(this);
		if (color == PLUS)
			dynx2x.setBackgroundResource(R.drawable.greenx2x);
		else
			dynx2x.setBackgroundResource(R.drawable.redx2x);
		dynx2x.setLayoutParams (new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		dynx2x.setOnLongClickListener(new LongKeyListener(dynx2x, this));
		return dynx2x;
	}
		
	private void addX(int state, int position){
		ObjectAnimator animX = null, animY = null;
		ImageButton btn = null;
		AnimatorSet animSetXY = new AnimatorSet();
		switch (state){
			case (PLUS + LEFT + BOTTOM) :
					btn = getXButton(PLUS);
					animX = ObjectAnimator.ofFloat(btn, "x", leftGreenX.getX(), left_grid.getX()+position*blkWidth);
				    animY = ObjectAnimator.ofFloat(btn, "y", leftGreenX.getY(),left_grid.getY()- blkHeight);
					gridObj.add(new GridObject(btn,BOTTOM,position,null,LEFT,IMGBUTTON));
					updateHints(LEFT+BOTTOM+IMGBUTTON,solvingEquation.setLhsCoefficient(solvingEquation.getLhsCoefficient() + 1));
					 animSetXY.addListener(new MyAnimator(PLUS + BOTTOM));
					break;
			case (PLUS + RIGHT + BOTTOM):
					btn = getXButton(PLUS);
					animX = ObjectAnimator.ofFloat(btn, "x", rightGreenX.getX(), right_grid.getX()+position*blkWidth);
				    animY = ObjectAnimator.ofFloat(btn, "y", rightGreenX.getY(),right_grid.getY()-blkHeight);
				    gridObj.add(new GridObject(btn,BOTTOM,position,null,RIGHT,IMGBUTTON));
				    updateHints( RIGHT+BOTTOM+IMGBUTTON,solvingEquation.setRhsCoefficient(solvingEquation.getRhsCoefficient() + 1));
				    animSetXY.addListener(new MyAnimator(PLUS + BOTTOM));
				break;
			case (PLUS + LEFT + TOP):
				 	btn = getXButton(MINUS);
					animX = ObjectAnimator.ofFloat(btn, "x", leftRedX.getX(), left_grid.getX()+position*blkWidth);
				    animY = ObjectAnimator.ofFloat(btn, "y", leftRedX.getY(),left_grid.getY()- 2*blkHeight);
					gridObj.add(new GridObject(btn,TOP,position,null,LEFT,IMGBUTTON));
					updateHints(LEFT + TOP+IMGBUTTON,simplifyEquation.setLhsCoefficient(simplifyEquation.getLhsCoefficient() + 1));
					trackLhsCoeff++;
					 animSetXY.addListener(new MyAnimator(PLUS + TOP));
					break;
			case (PLUS + RIGHT + TOP):
					btn = getXButton(MINUS);
					animX = ObjectAnimator.ofFloat(btn, "x", rightRedX.getX(), right_grid.getX()+position*blkWidth);
				    animY = ObjectAnimator.ofFloat(btn, "y", rightRedX.getY(),right_grid.getY()-2*blkHeight);
					gridObj.add(new GridObject(btn,TOP,position,null,RIGHT,IMGBUTTON));
					updateHints(RIGHT + TOP+IMGBUTTON,simplifyEquation.setRhsCoefficient(simplifyEquation.getRhsCoefficient() + 1));
					trackRhsCoeff++;
					animSetXY.addListener(new MyAnimator(PLUS + TOP));
					break;
		}
		
	    animSetXY.playTogether(animX, animY);
	    animSetXY.start();
	    relLayout.addView(btn);
	    ((TextView)this.findViewById(R.id.equalTo)).setText(" = ");
	}
	
	private void showToast(int state){
		LayoutInflater inflater = getLayoutInflater();
		View layout = inflater.inflate(R.layout.help_cue, (ViewGroup) findViewById(R.id.addFrame));
		TextView text = (TextView) layout.findViewById(R.id.helpText);
		Toast toast = new Toast(getApplicationContext());
		switch (state){
			case (PLUS+LEFT):
				toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 5,5);
				text.setText("In this equation you should simplify by subtracting X from each side.");
				break;
			case (PLUS+RIGHT):
				toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 200,5);
				text.setText("In this equation you should simplify by subtracting X from each side.");
				break;
			case (MINUS+LEFT):
				toast.setGravity(Gravity.BOTTOM|Gravity.LEFT, 60,5);
				text.setText("In this equation you should simplify by adding X from each side.");
				break;
			
			case (MINUS+RIGHT):
				toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 140,5);
				text.setText("In this equation you should simplify by adding X from each side.");
				break;
			case (IMGBUTTON):
				toast.setGravity(Gravity.TOP|Gravity.RIGHT, 120,70);
				text.setText("Click here to solve the equation.");
				break;
		}
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}
	
	private void showNumPad(Boolean isNegative){
		 Bundle args = new Bundle();
	     args.putBoolean("isNegative",isNegative);
		 DialogFragment numKeyPad = new NumKeyPad();
		 numKeyPad.setArguments(args);
		 numKeyPad.show(getFragmentManager(), "NumKeyPad");
	}
	
	private void checkReadyToSolve(){
		int allTrue = 6;
		int checkCounter = 0;
		if (generatedEquation.getLhsCoefficient() == solvingEquation.getLhsCoefficient())
			checkCounter++;
		if (generatedEquation.getLhsOperator() == solvingEquation.getLhsOperator())
			checkCounter++;
		if (generatedEquation.getLhsConstant() == solvingEquation.getLhsConstant())
			checkCounter++;
		if (generatedEquation.getRhsCoefficient() == solvingEquation.getRhsCoefficient())
			checkCounter++;
		if (generatedEquation.getRhsOperator() == solvingEquation.getRhsOperator())
			checkCounter++;
		if (generatedEquation.getRhsConstant() == solvingEquation.getRhsConstant())
			checkCounter++;
		if (checkCounter == allTrue){
			isReadyToSolve = true;
			disableGridObjects();
			statusBtn.setEnabled(true);
			statusBtn.setBackgroundResource(R.drawable.readytosolve_enabled2x);
		}
		else{ 
			isReadyToSolve = false;
			statusBtn.setEnabled(false);
			statusBtn.setBackgroundResource(R.drawable.readytosolve_disabled2x);
		}
	}
	
	private void disableGridObjects(){
		for (int i = 0; i <gridObj.size(); i++){
			GridObject gObj = gridObj.get(i);
			if (gObj.getType() == IMGBUTTON)
				 gObj.getImgBtn().setEnabled(false);
		}
	}
	
	private boolean isReadyToBeSimplified(){
		if (generatedEquation.getLhsCoefficient() < generatedEquation.getRhsCoefficient()){
			numRemovableCol = simplifyEquation.getLhsCoefficient() ;
			lesserSide = LEFT;
			if (simplifyEquation.getLhsCoefficient() == simplifyEquation.getRhsCoefficient()){
				isSimplifyingX = true;
				statusBtn.setEnabled(true);
				statusBtn.setBackgroundResource(R.drawable.simplify2x);
				disableGridObjects();
			}
		}
		if (generatedEquation.getLhsCoefficient() > generatedEquation.getRhsCoefficient()){
			numRemovableCol = simplifyEquation.getRhsCoefficient();
			lesserSide = RIGHT;
			if (simplifyEquation.getLhsCoefficient() == simplifyEquation.getRhsCoefficient()){
				isSimplifyingX = true;
				statusBtn.setEnabled(true);
				statusBtn.setBackgroundResource(R.drawable.simplify2x);
				disableGridObjects();
			}
		}
		return true;
	}
	
	private void clearIfZero(TextView widget, int value){
		if (value == 0)
			widget.setText("");
	}
	
	private void updateHints(int state, int value){
		switch(state){
		case (LEFT+TOP+TEXT):
			if (value > 0)
				hintLhsConst.setText("+"+value);
			else
				hintLhsConst.setText(""+value);
			hintLhsConst.animate();
			clearIfZero (hintLhsConst,value);
			break;
		case (LEFT+TOP+IMGBUTTON):
			if (value == 1)
				hintLhsCoeff.setText("-x");
			else
				hintLhsCoeff.setText("-"+value+"x");
			hintLhsConst.animate();
			clearIfZero (hintLhsCoeff,value);
			break;
		case (LEFT+BOTTOM+TEXT):
			if (value > 0)
				lhsConst.setText(" + "+value);
			else
				lhsConst.setText(" - "+(-value)+" ");
			lhsConst.animate();
			clearIfZero (lhsConst,value);
			break;
		case (LEFT+BOTTOM+IMGBUTTON):
			if (value == 1)
				lhsCoeff.setText("x");
			else
				lhsCoeff.setText(value+"x");
		lhsCoeff.animate();
			clearIfZero (lhsCoeff,value);
			break;
		case (RIGHT+TOP+TEXT):
			if (value > 0)
				hintRhsConst.setText("+"+value);
			else
				hintRhsConst.setText(value+" ");
		hintRhsConst.animate();
		clearIfZero (hintRhsConst,value);
			break;
		case (RIGHT+TOP+IMGBUTTON):
			if (value == 1)
				hintRhsCoeff.setText("-x");
			else
				hintRhsCoeff.setText("-"+value+"x");
		hintRhsCoeff.animate();	
		clearIfZero (hintRhsCoeff,value);
			break;
		case (RIGHT+BOTTOM+TEXT):
			if (value > 0)
				rhsConst.setText(" + "+value);
			else
				rhsConst.setText(" - "+(-value)+" ");
		rhsConst.animate();	
		clearIfZero (rhsConst,value);
			break;
		case (RIGHT+BOTTOM+IMGBUTTON):
			if (value == 1)
				rhsCoeff.setText("x");
			else
				rhsCoeff.setText(value+"x");
		rhsCoeff.animate();	
		clearIfZero (rhsCoeff,value);
			break;
		}
		
	}
	
	private void animateAndRemove(final GridObject gObj){
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);
		float y = gObj.getImgBtn().getY();
		ObjectAnimator animY = ObjectAnimator.ofFloat(gObj.getImgBtn(), "y", y,(y-20), y+metrics.heightPixels); 
		animY.setDuration(1500);
		animY.start();
	    soundPool.play(deleteSound, 1.0f, 1.0f, 0, 0, 1.0f);
		gridObj.remove(gObj);
	}

	private void removeX(){ 
		isSimplifyingX = false;
		int ctr = -1;
		for (int i = gridObj.size()-1; i>=0;i--){
			GridObject gObj = gridObj.get(i);
			ctr = numRemovableCol;
			switch(gObj.getType()+gObj.getSide()){
			case (LEFT+IMGBUTTON):
				if (lnumInBetween)
					ctr = numRemovableCol +1;
				if (gObj.getCol()< ctr ){
					if (gObj.getRow() == TOP)
						updateHints(LEFT+gObj.getRow()+IMGBUTTON,simplifyEquation.setLhsCoefficient(simplifyEquation.getLhsCoefficient() - 1));
					else
						updateHints(LEFT+gObj.getRow()+IMGBUTTON,solvingEquation.setLhsCoefficient(solvingEquation.getLhsCoefficient() - 1));
					animateAndRemove(gObj);
				}
					
				break;
			case (RIGHT+IMGBUTTON):
				if (rnumInBetween)
					ctr = numRemovableCol +1;
				if (gObj.getCol()< ctr ){
					if (gObj.getRow() == TOP)
						updateHints(RIGHT+gObj.getRow()+IMGBUTTON,simplifyEquation.setRhsCoefficient(simplifyEquation.getRhsCoefficient() - 1));
					else
						updateHints(RIGHT+gObj.getRow()+IMGBUTTON,solvingEquation.setRhsCoefficient(solvingEquation.getRhsCoefficient() - 1));
					animateAndRemove(gObj);
				}
				break;
			}
		}
	}
	
	private TextView setNumText(int sum, TextView textWidget, int position, GridObject gObj, int side, Equation eq){
		float height = -1;
		int num = Math.abs(sum);
		textWidget.setText(num+"");
		if (row == TOP)
			height = left_grid.getY() - 2 * blkHeight;
		else
			height = left_grid.getY() -  blkHeight;
		switch (side){
			case LEFT:
				if (sum < 0){
					textWidget.setTextColor(Color.parseColor("#680000"));
					textWidget.setBackgroundResource(R.drawable.redblk_hd);
					eq.setLhsOperator(MINUS);
				}
				else{
					textWidget.setTextColor(Color.parseColor("#005008"));
					textWidget.setBackgroundResource(R.drawable.grnblock_hd);
					eq.setLhsOperator(PLUS);
				}
				eq.setLhsConstant(num) ;
				if (!gridObj.contains(gObj)){
					if (position != -1 ){
						textWidget.setX(left_grid.getX()+position*blkWidth);
						textWidget.setY(height);
						relLayout.addView(textWidget);
					    gObj.addTextViewObj(null, row,position, textWidget, LEFT, TEXT);
						gridObj.add(gObj);
					}
				 }
				break;
			case RIGHT:
				if (sum < 0){
					textWidget.setTextColor(Color.parseColor("#680000"));
					textWidget.setBackgroundResource(R.drawable.redblk_hd);
					eq.setRhsOperator(MINUS);
				}
				else{
					textWidget.setTextColor(Color.parseColor("#005008"));
					textWidget.setBackgroundResource(R.drawable.grnblock_hd);
					eq.setRhsOperator(PLUS);
				}
				eq.setRhsConstant(num) ;
				if (!gridObj.contains(gObj)){
					if (position != -1 ){
						textWidget.setX(right_grid.getX()+position*blkWidth);
						textWidget.setY(height);
						relLayout.addView(textWidget);
					    gObj.addTextViewObj(null, row,position, textWidget, RIGHT, TEXT);
						gridObj.add(gObj);
					}
				 }
				break;
			}
		updateHints(side+row+TEXT,sum);
		return textWidget;
	}

	private TextView getTextView(TextView textv){
		textv = new TextView(this);
		textv.setTextSize(45);
		textv.setTypeface(null, Typeface.BOLD);
		textv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
		return textv;
	}
	
	private void setNumPosition(int side){
		if (!isReadyToSolve){
			if (side==LEFT){
					lnumcol=findNextAvailablePosition(LEFT+PLUS+BOTTOM);
					updateHints(LEFT+BOTTOM+TEXT, leftBottomSum);
					bottomLeftNumText = setNumText(leftBottomSum,bottomLeftNumText, lnumcol, bottomLeftNumObj, LEFT, solvingEquation );
			}
			else {
				rnumcol=findNextAvailablePosition(RIGHT+PLUS+BOTTOM);
				updateHints(RIGHT+BOTTOM+TEXT, rightBottomSum);
				bottomRightNumText = setNumText(rightBottomSum,bottomRightNumText, rnumcol, bottomRightNumObj, RIGHT, solvingEquation );
			}
		}
		else{
			if (side==LEFT){
					row = TOP;
					updateHints(LEFT+TOP+TEXT, leftTopSum);
					topLeftNumText = setNumText(leftTopSum,topLeftNumText, lnumcol, topLeftNumObj, LEFT, simplifyEquation);
					
			 }
			else {
				row = TOP;
				updateHints(RIGHT+TOP+TEXT, rightTopSum);
				topRightNumText = setNumText(rightTopSum,topRightNumText, rnumcol, topRightNumObj, RIGHT, simplifyEquation );
			 }
		}
		if (leftTopSum == rightTopSum){
			isSimplifyingNum = true;
			statusBtn.setEnabled(true);
			statusBtn.setBackgroundResource(R.drawable.simplify2x);
		}
	}
	
	private void solveNumeric(){
		isSimplifyingNum = false;
		leftDiff= leftTopSum + leftBottomSum;
		if (leftDiff == 0){
			for (int i = gridObj.size()-1; i>=0;i--){
				GridObject gObj = gridObj.get(i);
				if (gObj.getSide() == LEFT && gObj.getType() == TEXT){
					((ViewGroup) relLayout).removeView(bottomLeftNumText);
					((ViewGroup) relLayout).removeView(topLeftNumText);
					((ViewGroup) relLayout).removeView(topRightNumText);
					gridObj.remove(gObj);
				}
			}
		}
		else {
			if (leftDiff < 0){
				bottomLeftNumText.setTextColor(Color.parseColor("#680000"));
				bottomLeftNumText.setBackgroundResource(R.drawable.redblk_hd);
			}
			else {
				bottomLeftNumText.setTextColor(Color.parseColor("#005008"));
				bottomLeftNumText.setBackgroundResource(R.drawable.grnblock_hd);
			}
			Integer num = Math.abs(leftDiff);
			bottomLeftNumText.setText(num.toString());
			//
		}
			
		rightDiff= rightTopSum + rightBottomSum;
		if (rightDiff == 0){
			for (int i = gridObj.size()-1; i>=0;i--){
				GridObject gObj = gridObj.get(i);
				if (gObj.getSide() == RIGHT && gObj.getType() == TEXT){
					((ViewGroup) relLayout).removeView(bottomRightNumText);
					((ViewGroup) relLayout).removeView(topLeftNumText);
					((ViewGroup) relLayout).removeView(topRightNumText);
					gridObj.remove(gObj);
				}
			}
		}
		else {
			if (rightDiff < 0){
				bottomRightNumText.setTextColor(Color.parseColor("#680000"));
				bottomRightNumText.setBackgroundResource(R.drawable.redblk_hd);
			}
			else {
				bottomRightNumText.setTextColor(Color.parseColor("#005008"));
				bottomRightNumText.setBackgroundResource(R.drawable.grnblock_hd);
			}
		}
		Integer num = Math.abs(rightDiff);
		bottomRightNumText.setText(num.toString());
	}
	
	class MyAnimator implements AnimatorListener {
		
		int state;
		MyAnimator(int state){
			this.state = state;
		}

		@Override
		public void onAnimationCancel(Animator animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationEnd(Animator animation) {
			switch(state){
			case (PLUS + BOTTOM):
				checkReadyToSolve();
				break;
			case (PLUS + TOP):
				isReadyToBeSimplified();
			break;
			}
		}

		@Override
		public void onAnimationRepeat(Animator animation) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onAnimationStart(Animator animation) {
			// TODO Auto-generated method stub

		}

	}

	private class LongKeyListener implements View.OnLongClickListener {
		ImageButton btn;
		Context context;
		ImageView del;
		LongKeyListener(ImageButton button, Context contex){
			btn = button;
			this.context = contex;
		}
		
		private void slide(int state, int position){
			ObjectAnimator animX = null;
			View v = null;
			float width = blkWidth;
			float x;
			for (int i = 0; i <gridObj.size(); i++){
				GridObject gObj = gridObj.get(i);
				if ( position < gObj.getCol() && (state == ((gObj.getSide()) + gObj.getRow()))){
					if (gObj.getType() == TEXT){
						switch (gObj.getSide()+gObj.getRow()){
						case (LEFT+ BOTTOM):
							v = gObj.getNumText();
							lnumcol--;
							break;
						case (RIGHT+BOTTOM):
							v = gObj.getNumText();
							rnumcol--;
							break;
						case (LEFT+TOP):
							continue;
						case(RIGHT+TOP):
							continue;
						}
							
					}
					else
						v = gObj.getImgBtn();
					x = v.getX();
					animX = ObjectAnimator.ofFloat(v, "x", x,x - width);
					if (lnumInBetween && position<lnumcol && state == LEFT+TOP && gObj.getCol()>lnumcol){
						animX = ObjectAnimator.ofFloat(v, "x", x,x - 2*width);
						gObj.setCol(gObj.getCol() - 1);
						if ((bottomLeftNumObj.getNumText()).getX() <= (x - 2*width)){
							animX = ObjectAnimator.ofFloat(v, "x", x,x - width);
							gObj.setCol(gObj.getCol() + 1);
						}
						
					}
					if (rnumInBetween && position<rnumcol && state == RIGHT+TOP && gObj.getCol()>rnumcol){
						animX = ObjectAnimator.ofFloat(v, "x", x,x - 2*width);
						gObj.setCol(gObj.getCol() - 1);
						if ((bottomRightNumObj.getNumText()).getX() <= (x - 2*width)){
							animX = ObjectAnimator.ofFloat(v, "x", x,x - width);
							gObj.setCol(gObj.getCol() + 1);
						}
					}
					gObj.setCol(gObj.getCol() - 1);
					animX.setDuration(1000);
					animX.start();
				}
			}
		}
		
		@Override
		public boolean onLongClick(View v) {
			del = new ImageView(context);
			del.setImageResource(R.drawable.presence_offline);
			del.setAlpha(1f);
			del.setX(btn.getX());
			del.setY(btn.getY());
			del.setLayoutParams (new LayoutParams(20,20));
			relLayout.addView(del);
			ObjectAnimator animX, animDel = null;
			float x = btn.getX();
			animX = ObjectAnimator.ofFloat(btn, "x", x,x-2,x,x+2,x);
			animX.addListener(new MyAnimatorListener());
			animX.setRepeatCount(5);
			animDel = ObjectAnimator.ofFloat(del, "x", x,x-2,x,x+2,x);
			animDel.setRepeatCount(5);
			AnimatorSet anim = new AnimatorSet();
			anim.playTogether(animX,animDel);
			anim.start();
			return true;
		}
	
		private class MyAnimatorListener implements AnimatorListener{

			
			@Override
			public void onAnimationCancel(Animator arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationEnd(Animator arg0) {
				// TODO Auto-generated method stub
				soundPool.play(deleteSound, 1.0f, 1.0f, 0, 0, 1.0f);
				int state = -1;
				for (int i = gridObj.size()-1; i >=0; i--){
					GridObject gObj = gridObj.get(i);
					if (gObj.getImgBtn() == btn){
						switch ((gObj.getSide()) + row){
							case (LEFT + TOP):
								updateHints(LEFT + TOP+IMGBUTTON,simplifyEquation.setLhsCoefficient(simplifyEquation
										.getLhsCoefficient() - 1));
								trackLhsCoeff--;
							    findNextAvailablePosition(LEFT+TOP+MINUS);
							    state = LEFT + TOP;
							    isReadyToBeSimplified();
							    break;
							case (LEFT + BOTTOM):
								updateHints(LEFT + BOTTOM+IMGBUTTON,solvingEquation.setLhsCoefficient(solvingEquation
										.getLhsCoefficient() - 1));
							    findNextAvailablePosition(LEFT + BOTTOM+MINUS);
							    state = LEFT + BOTTOM;
							    checkReadyToSolve();
								break;
							case (RIGHT + TOP):
								updateHints(RIGHT + TOP+IMGBUTTON,simplifyEquation.setRhsCoefficient(simplifyEquation
										.getRhsCoefficient() - 1));
								trackRhsCoeff--;
							    findNextAvailablePosition(RIGHT+TOP+MINUS);
							    state = RIGHT + TOP;
							    isReadyToBeSimplified();
								break;
							case (RIGHT + BOTTOM):
								updateHints(RIGHT + BOTTOM+IMGBUTTON,solvingEquation.setRhsCoefficient(solvingEquation
										.getRhsCoefficient() - 1));
							    findNextAvailablePosition(RIGHT+BOTTOM+MINUS);
							    state = RIGHT + BOTTOM;
							    checkReadyToSolve();
								break;
						}
						slide(state,gObj.getCol());
					    relLayout.removeView(btn);
					    relLayout.removeView(del);
					    gridObj.remove(gObj);
					    
					}
				}
				
			}

			@Override
			public void onAnimationRepeat(Animator arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onAnimationStart(Animator arg0) {
				// TODO Auto-generated method stub
				
			}
		}
	}
}
