import java.awt.EventQueue;

public class CtrPrincipal {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmPrincipal window = new FrmPrincipal();
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
