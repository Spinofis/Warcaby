package checkers;

public class Main {

	

	public static void main(String[] args) throws InterruptedException {
		/*Tworze obiekt aplikacji i uruchamiam w oddzielnym w¹tku tak ¿e funkcja main 
		 * mo¿e siê wykonywaæ równolegle*/
		App app=new App();
	    Thread appThread=new Thread(app);
	    appThread.start();
	    System.out.println("hej");
	}

}
