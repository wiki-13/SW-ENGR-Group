/*Save returned server results*/
class serverresult extends ServerInfo {

	 String result;
	protected serverresult(String ipid, int pid,String result) {
		super(ipid, pid);
		this.result = result;
	}

	 
}
