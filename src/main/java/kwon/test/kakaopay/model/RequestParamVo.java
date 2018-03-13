package kwon.test.kakaopay.model;


public class RequestParamVo extends RootVo {
	
	private String email;
	private PageVo page;


	private long txtime = System.currentTimeMillis();



	public String getEmail() {
		return email;
	}

	public PageVo getPage() {
		return page;
	}

	public void setPage(PageVo page) {
		this.page = page;
	}

	public void setTxtime(long txtime) {
		this.txtime = txtime;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public long getTxtime() {
		return txtime;
	}
}
