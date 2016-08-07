import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;

public class Test {

	public static void main(String[] args) {

		Test test = new Test();
		try {
			test.x("/newfile.php");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void x(String myResource) throws IOException {

		URL url = this.getClass().getResource(myResource);
		
		String fileName;
		
		if (url.getProtocol().equals("file")) {
			fileName = url.getFile();
		} else if (url.getProtocol().equals("jar")) {
			JarURLConnection jarUrl = (JarURLConnection) url.openConnection();
			fileName = jarUrl.getJarFile().getName();
		} else {
			throw new IllegalArgumentException("Not a file");
		}
		File file = new File(fileName);
		System.out.println(file.getName());
	}
}
