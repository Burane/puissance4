import java.io.IOException;

@SuppressWarnings("unchecked")
public class test {
	public static void main(String[] args) throws InterruptedException {


		String bb1 = "hello world";
		String bb2 = "HELLO WORLD";

		System.out.println(bb1);
		Thread.sleep(250);
		cls();
		System.out.println(bb2);
		System.out.println("------------------");


	}

	public static void cls() {
		try {
			if (System.getProperty("os.name").contains("Windows"))
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			else
				Runtime.getRuntime().exec("clear");
		} catch (InterruptedException | IOException ex) {
		}
	}

}
