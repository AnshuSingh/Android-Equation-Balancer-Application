package tblr.equationbalancer.model;

import java.util.Random;

import android.util.Log;


public class Equation {

	String [] strConstants = {"+","-","x "," = "," "};
	private int lhsCoefficient;
	private int rhsCoefficient;
	private int lhsConstant;
	private int rhsConstant;
	private int lhsOperator;
	private int rhsOperator;
	public int answer = 0;
	int PLUS = 0;
	int MINUS = 1;
	StringBuilder strEquation;


	public Equation(){
		setLhsOperator(0);
		setRhsOperator(0);
		setLhsConstant(1);
		setRhsConstant(1);
		setLhsCoefficient(0);
		setRhsCoefficient(0);		
		answer = 0;
	}
	
	public void generate(){
		Random generator = new Random();
		setLhsOperator(generator.nextInt(100)%2);
		setRhsOperator(generator.nextInt(100)%2);
		while (getLhsConstant() == getRhsConstant()){
			setLhsConstant(generator.nextInt(9) + 1);
			setRhsConstant(generator.nextInt(9)+1);
		}
		int coefficient = generator.nextInt(5)+1;
		if ((generator.nextInt()%2) != 0 && coefficient<5){
			setLhsCoefficient(coefficient);
			setRhsCoefficient(getLhsCoefficient()+1);
		}
		else{
			setRhsCoefficient(generator.nextInt(5)+1);
			if (getRhsCoefficient() == 5)
				setLhsCoefficient(getRhsCoefficient() - 1);
			else
				setLhsCoefficient(getRhsCoefficient()+1);
		}
		StringBuilder leftX =new StringBuilder(getLhsCoefficient() + strConstants[2]);
		StringBuilder rightX = new StringBuilder(getRhsCoefficient() + strConstants[2]);
		if (getLhsCoefficient() == 1)
			leftX.deleteCharAt(0);
		if (getRhsCoefficient() == 1)
			rightX.deleteCharAt(0);
			
		strEquation =  new StringBuilder(leftX+strConstants[getLhsOperator()]+strConstants[4]+getLhsConstant() + strConstants[3]+rightX+strConstants[getRhsOperator()]+strConstants[4]+getRhsConstant());
		
		Log.d("tblr.equationbalancer.model",strEquation+"");
		calculateAnswer();
			
	}

	public StringBuilder getStrEquation() {
		return strEquation;
	}

	public void setStrEquation(StringBuilder strEquation) {
		this.strEquation = strEquation;
	}

	private void calculateAnswer(){
		if (getLhsCoefficient() < getRhsCoefficient())
		{
			if (getRhsOperator() == PLUS && getLhsOperator() == PLUS)
				answer = getLhsConstant() - getRhsConstant();
			if (getRhsOperator() == PLUS && getLhsOperator() == MINUS)
				answer = -getLhsConstant() - getRhsConstant();
			if (getRhsOperator() == MINUS && getLhsOperator() == PLUS)
				answer = getLhsConstant() + getRhsConstant();
			if (getRhsOperator() == MINUS && getLhsOperator() == MINUS)
				answer = -getLhsConstant() + getRhsConstant();
		}
		else
		{
			if (getLhsOperator() == PLUS && getRhsOperator() == PLUS)
				answer = getRhsConstant() - getLhsConstant();
			if (getLhsOperator() == PLUS && getRhsOperator() == MINUS)
				answer = -getRhsConstant() - getLhsConstant();
			if (getLhsOperator() == MINUS && getRhsOperator() == PLUS)
				answer = getRhsConstant() + getLhsConstant();
			if (getLhsOperator() == MINUS && getRhsOperator() == MINUS)
				answer = -getRhsConstant() + getLhsConstant();
		}
	}
	
	public int getAnswer(){
		return answer;
	}

	public int getLhsCoefficient() {
		return lhsCoefficient;
	}

	public int setLhsCoefficient(int lhsCoefficient) {
		this.lhsCoefficient = lhsCoefficient;
		return lhsCoefficient;
	}

	public int getRhsCoefficient() {
		return rhsCoefficient;
	}

	public int setRhsCoefficient(int rhsCoefficient) {
		this.rhsCoefficient = rhsCoefficient;
		return rhsCoefficient;
	}

	public int getLhsOperator() {
		return lhsOperator;
	}

	public void setLhsOperator(int lhsOperator) {
		this.lhsOperator = lhsOperator;
	}

	public int getLhsConstant() {
		return lhsConstant;
	}

	public void setLhsConstant(int lhsConstant) {
		this.lhsConstant = lhsConstant;
	}

	public int getRhsOperator() {
		return rhsOperator;
	}

	public void setRhsOperator(int rhsOperator) {
		this.rhsOperator = rhsOperator;
	}

	public int getRhsConstant() {
		return rhsConstant;
	}

	public void setRhsConstant(int rhsConstant) {
		this.rhsConstant = rhsConstant;
	}

}
