package tblr.equationbalancer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class NumKeyPad extends DialogFragment  {
	
	public interface NumKeyPadListener {
        public void onDialogPositiveClick(String selectedValue);
    }
	
	TextView numText;    
	NumKeyPadListener mListener;
	
	@Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (NumKeyPadListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement NumKeyPadListener");
        }
    }



	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		Activity dActivity = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(dActivity);
	    LayoutInflater inflater = dActivity.getLayoutInflater();
	    View _view = inflater.inflate(R.layout.num_keypad, null);
	    NumKeyListener numKeyListner = new NumKeyListener();
	    Button okayButton = (Button) _view.findViewById(R.id.okaybtn);
		 Button cancelButton = (Button) _view.findViewById(R.id.cancel);
		 Button delButton = (Button) _view.findViewById(R.id.delete);
		 
		 numText = (TextView) _view.findViewById(R.id.numText);
			ImageView minus = (ImageView)_view.findViewById(R.id.negative);
			if (getArguments().getBoolean("isNegative"))
				minus.setVisibility(View.VISIBLE);
			else
				minus.setVisibility(View.INVISIBLE);
	    
		 ((Button) _view.findViewById(R.id.one)).setOnClickListener(numKeyListner);
	    ((Button) _view.findViewById(R.id.two)).setOnClickListener(numKeyListner);
	    ((Button) _view.findViewById(R.id.three)).setOnClickListener(numKeyListner);
	    ((Button) _view.findViewById(R.id.four)).setOnClickListener(numKeyListner);
	    ((Button) _view.findViewById(R.id.five)).setOnClickListener(numKeyListner);
	    ((Button) _view.findViewById(R.id.six)).setOnClickListener(numKeyListner);
	    ((Button) _view.findViewById(R.id.seven)).setOnClickListener(numKeyListner);
	    ((Button) _view.findViewById(R.id.eight)).setOnClickListener(numKeyListner);
	    ((Button) _view.findViewById(R.id.nine)).setOnClickListener(numKeyListner);
	    ((Button) _view.findViewById(R.id.zero)).setOnClickListener(numKeyListner);

	    builder.setView(_view);
	  	final AlertDialog keypad = builder.create();
		 
		
		 okayButton.setOnClickListener(new OnClickListener() {
          @Override
          public void onClick(View v) {
        	  String numStr = (String) numText.getText();
			  if (numStr.equals(""))
				  return; // do nothing
	          mListener.onDialogPositiveClick(numStr);
	          keypad.dismiss();
          }

		 });
         
		 cancelButton.setOnClickListener(new OnClickListener() {
	          @Override
	          public void onClick(View v) {
	        	 keypad.cancel();
          }
      });
		
		 delButton.setOnClickListener(new OnClickListener() {
	          @Override
	          public void onClick(View v) {
				numText.setText("");	
			}
      }); 
	   
		return keypad; 
       

	}
	
	

	class NumKeyListener implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			switch(v.getId())
			{
				case R.id.one:
					numText.setText("1");
							break;
				case R.id.two:
					numText.setText("2");
					 		break;
				case R.id.three:
					numText.setText("3");
			 				break;
				case R.id.four:
					numText.setText("4");
					break;
				case R.id.five:
					numText.setText("5");
			 		break;
				case R.id.six:
					numText.setText("6");
	 				break;
				case R.id.seven:
					numText.setText("7");
					break;
				case R.id.eight:
					numText.setText("8");
			 		break;
				case R.id.nine:
					numText.setText("9");
	 				break;
	 			case R.id.zero:
	 				numText.setText("0");
					break;
					
				default:
				throw new RuntimeException("Unknow button ID");
			}
			
		}
	}
}
