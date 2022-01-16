package jpossim.util;

import org.jpos.iso.ISOMsg;

public class ISOArray {
	private ISOMsg message;
	private String status;
	private int idNumber;
	
	public ISOMsg getMessage() {
		return message;
	}
	
	public void setMessage(ISOMsg message) {
		this.message = message;
	}
	
	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setIdNumber(int idNumber){
		this.idNumber = idNumber;
	}
	
	public int getIdNumber() {
		return idNumber;
	}
}
