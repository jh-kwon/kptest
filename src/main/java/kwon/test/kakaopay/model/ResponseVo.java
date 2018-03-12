package kwon.test.kakaopay.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class ResponseVo implements Serializable{
	
	private boolean success;
	private Object data; // responsedata
	private PageVo page;
//	private Map<String, Object> ext; // for extra data
	
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public PageVo getPage() {
		return page;
	}
	public void setPage(PageVo page) {
		this.page = page;
	}
	
//	public Map<String, Object> getExt() {
//		return ext;
//	}
//	public void setExt(Map<String, Object> ext) {
//		this.ext = ext;
//	}
//	
//	@JsonIgnore
//	public void addExtraData(String key, Object val) {
//		if(this.ext == null) {
//			this.ext = new HashMap();
//		}
//		
//		this.ext.put(key, val);
//	}
}
