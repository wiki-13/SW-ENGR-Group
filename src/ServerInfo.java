/*Set server informations*/

public class ServerInfo {
	private String IPAddress;
	private int portaddress;
    protected ServerInfo(String ipid,int pid)
    {
    	this.IPAddress = ipid;
    	this.portaddress = pid;
    }
    public String getIP()
    {
    	return IPAddress;
    }
    public int getport()
    {
    	return portaddress;
    }
}
