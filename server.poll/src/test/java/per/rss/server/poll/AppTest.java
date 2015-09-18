package per.rss.server.poll;

import per.rss.server.poll.util.xml.JsoupXMLHandler;
import per.rss.server.poll.util.xml.XMLHandler;

/**
 * Unit test for simple App.
 */
public class AppTest {
	public static void main(String[] args) {
		XMLHandler xmlhander=new JsoupXMLHandler();
		xmlhander.parse("0");
//		xmlhander.parse2("0");
	}
}
