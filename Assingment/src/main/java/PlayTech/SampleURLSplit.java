package PlayTech;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SampleURLSplit {

	public static void main(String[] args) throws Exception {
		System.out.println(new UrlState().split("http://host:8090/path?params"));

	}

}
