
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Web Crawler Class to extract, parse and save the required web pages into text
 * file.
 * 
 * @author gagan
 *
 */
public class Crawler {
	// The maximum number of web pages to visit
	private static final int MAX_PAGES = 1000;
	// Set containing the url of already visited pages
	private Set<String> visitedPages = new HashSet<String>();
	// set containing url to be visited
	private List<String> toBeVisited = new LinkedList<String>();

	/**
	 * Our main launch point for the Crawler functionality. It creates
	 * crawlerUtil internally to parse and save the html content to text file
	 * 
	 * @param url
	 *            - The starting point of the Crawler
	 */
	public void search(String url) {
		while (this.visitedPages.size() < MAX_PAGES) {
			String currentUrl;
			CrawlerUtil util = new CrawlerUtil();
			if (this.toBeVisited.isEmpty()) {
				currentUrl = url;
				this.visitedPages.add(url);
			} else {
				currentUrl = this.nextUrl();
			}
			util.crawl(currentUrl);
			this.toBeVisited.addAll(util.getLinks());
		}
	}

	/**
	 * Returns the next url to visit. Also checks if the url has been previously
	 * visited.
	 * 
	 * @return
	 */
	private String nextUrl() {
		String nextLink;
		do {
			nextLink = this.toBeVisited.remove(0);
		} while (this.visitedPages.contains(nextLink));
		this.visitedPages.add(nextLink);
		return nextLink;
	}
}