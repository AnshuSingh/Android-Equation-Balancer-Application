package tblr.equationbalancer.model;

import android.widget.ImageButton;
import android.widget.TextView;

public class GridObject {

	private ImageButton imgBtn;
	private int row;
	private int col;
	private TextView numText;
	private int side;
	private int type;
	
	public GridObject(ImageButton imgBtn, int row, int col, TextView numText,
			int side, int type) {
		super();
		this.setImgBtn(imgBtn);
		this.setRow(row);
		this.setCol(col);
		this.setNumText(numText);
		this.setSide(side);
		this.setType(type);
	}

	public GridObject() {
	}
	public void addTextViewObj(ImageButton imgBtn, int row, int col, TextView numText,
			int side, int type){
		this.setImgBtn(imgBtn);
		this.setRow(row);
		this.setCol(col);
		this.setNumText(numText);
		this.setSide(side);
		this.setType(type);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ImageButton getImgBtn() {
		return imgBtn;
	}

	public void setImgBtn(ImageButton imgBtn) {
		this.imgBtn = imgBtn;
	}

	public TextView getNumText() {
		return numText;
	}

	public void setNumText(TextView numText) {
		this.numText = numText;
	}

	public int getSide() {
		return side;
	}

	public void setSide(int side) {
		this.side = side;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}
	
	
	
}
