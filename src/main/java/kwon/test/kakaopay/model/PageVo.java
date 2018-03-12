package kwon.test.kakaopay.model;

import java.io.Serializable;

public class PageVo implements Serializable{
	private long rowno;
	private int rowcnt;
	
	
	public long getRowno() {
		return rowno;
	}
	public void setRowno(long rowno) {
		this.rowno = rowno;
	}
	public int getRowcnt() {
		return rowcnt;
	}
	public void setRowcnt(int rowcnt) {
		this.rowcnt = rowcnt;
	}
}
