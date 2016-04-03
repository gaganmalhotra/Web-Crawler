import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CrawlerUtil {
	//Creating a fake user_agent to connect to the web page.
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 8.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";
	private List<String> links = new LinkedList<String>();
	private Document htmlDocument;

	/**
	 * 
	 * This method makes an HTTP request, gathers all the link and save the html
	 * document into a text file
	 * 
	 * @param url
	 *            - The URL to visit
	 * @return returns true if the crawl was successful
	 */
	public boolean crawl(String url) {
		try {
			Connection conn = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDOM = conn.get();
			this.htmlDocument = htmlDOM;
			if (conn.response().statusCode() == 200) // 200 is Status OK
			{
				System.out.println("\n Received web page at " + url);
			}
			if (!conn.response().contentType().contains("text/html")) {
				System.out.println("Failure-- No HTML found");
				return false;
			}

			// this method call saves the html document into a text file
			this.saveToFile(url, htmlDOM);

			Elements linksOnPage = htmlDOM.select("a[href]");
			for (Element link : linksOnPage) {
				this.links.add(link.absUrl("href"));
			}
			return true;
		} catch (IOException ioe) {
			return false;
		}
	}

	/**
	 * This method saves the html document to a text file giving the name as url
	 * string.
	 * 
	 * @param url
	 * @param htmlDocument
	 */
	private void saveToFile(String url, Document htmlDocument) {
		Pattern p = Pattern.compile("(http|https)://(.*)");
		Matcher m = p.matcher(url);
		m.find();
		try {
			File file = new File("/Users/gagan/Documents/Projects/IBM Watson University Info/"
					+ m.group(2).replace('/', '_') + ".txt");
			FileOutputStream fop = new FileOutputStream(file);

			// if file doesn't exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes
			byte[] contentInBytes = htmlDocument.text().getBytes();

			fop.write(contentInBytes);
			fop.flush();
			fop.close();
		}

		catch (IllegalStateException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<String> getLinks() {
		return this.links;
	}

}