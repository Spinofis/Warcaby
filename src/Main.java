import java.io.IOException;

public class Main {

	 
	
	
	public static void main(String[] args) throws InterruptedException, IOException {
		App app=new App();
		
	   Thread appThread=new Thread(app);
	    appThread.start();
	    
	    System.out.println("hej");
	}
    
	
	
	
	
}
