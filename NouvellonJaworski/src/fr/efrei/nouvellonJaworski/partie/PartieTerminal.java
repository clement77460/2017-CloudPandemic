package fr.efrei.nouvellonJaworski.partie;



import fr.efrei.nouvellonJaworski.controller.frame.FrameController;
import fr.efrei.nouvellonJaworski.model.abstractmodel.TableModelPandemic;
import fr.efrei.nouvellonJaworski.networking.ClientTerminal;
import fr.efrei.nouvellonJaworski.vue.frame.PandemicFrame;
import fr.efrei.paumier.shared.orders.OrderType;

public class PartieTerminal{
	private ClientTerminal cityBorder;
	private PandemicFrame vue;
	private TableModelPandemic tableModel;
	private Thread thread;
	
	public PartieTerminal(int population,String ip,int port) {
		
		this.cityBorder=new ClientTerminal(ip,port);
		this.tableModel=new TableModelPandemic();
		
		this.cityBorder.setTableModelPandemic(this.tableModel);
		
		thread=new Thread( (Runnable) this.cityBorder);
		thread.start();
		
		
		vue=new PandemicFrame(this.tableModel,this);
		vue.addWindowListener(new FrameController(this.thread,this.cityBorder));
        vue.setVisible(true);
	}

	public void launchOrder(String destinataire, OrderType orderType) {
		cityBorder.sendClientMessage(destinataire,orderType);
		
	}
	

}
