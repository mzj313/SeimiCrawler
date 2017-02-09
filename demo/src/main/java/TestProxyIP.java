import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

public class TestProxyIP {
	public static void main(String[] args) {
		try {
			String hostname = "124.88.67.52";
			int port = 843;
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(hostname, port));
			URL url = new URL("http://www.baidu.com");
			HttpURLConnection uc = (HttpURLConnection)url.openConnection(proxy);
			uc.connect();
			System.out.println(uc.getResponseMessage());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
