public class CrawlerTest
{
    /**
     * This is our test class. It calls the crawler implementation.
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        Crawler crawlObj = new Crawler();
        crawlObj.search("http://www.rit.edu/");
    }
}