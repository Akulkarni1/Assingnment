package PlayTech;

import static org.junit.Assert.*;

import org.junit.Test;

public class SplitTest {

	@Test
	public void MainTest() throws Exception {

		UrlState t = new UrlState().split("http://host:8090/path?params");

		assertEquals(t.getScheme(), "http");
		assertEquals(t.getHost(), "host");
		assertEquals(t.getPort(), "8090");
		assertEquals(t.getPath(), "path");
		assertEquals(t.getParameters(), "params");
	}

	@Test
	public void testWithoutPort() throws Exception {
		UrlState t = new UrlState().split("http://host/path?params=test");
		assertEquals(t.getScheme(), "http");
		assertEquals(t.getHost(), "host");
		assertEquals(t.getPath(), "path");
		assertEquals(t.getParameters(), "params=test");

	}

	@Test
	public void testWithoutParams() throws Exception {
		UrlState t = new UrlState().split("http://host/path");
		assertEquals(t.getScheme(), "http");
		assertEquals(t.getHost(), "host");
		assertEquals(t.getPath(), "path");
	}
}
