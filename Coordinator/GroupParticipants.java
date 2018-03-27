public class GroupParticipants
{
	public int id;
	public int port;
	public String ip;
	public Boolean isDisconnected;
	public GroupParticipants(int id, String ip, int port)
	{
		this.id=id;
		this.port=port;
		this.ip = ip;
		this.isDisconnected=false;
	}
}
