package checkers;

public class Main {

	

	public static void main(String[] args) throws InterruptedException {
		/*Tworze obiekt aplikacji i uruchamiam w oddzielnym w�tku tak �e funkcja main 
		 * mo�e si� wykonywa� r�wnolegle*/
		App app=new App();
	    Thread appThread=new Thread(app);
	    appThread.start();
	    System.out.println("hej");
	}

}
