package PlayTech;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlState {
	private String scheme;
	private String host;
	private String path;
	private String port;
	private String parameters;
	private long regexTime;
	private long stateTime;
	private final static String regexPattern = "^(([^:/?#]+):)?(//([^/?#]*))/?([^?#]*)(\\?([^#]*)?)?(#(.*))?";

	public UrlState(String scheme, String host, String path, String port, String parameters, long regexTime,
			long stateTime) {
		this.scheme = scheme;
		this.host = host;
		this.path = path;
		this.port = port;
		this.parameters = parameters;
		this.stateTime = stateTime;
		this.regexTime = regexTime;
	}

	public UrlState() {
		scheme = "";
		host = "";
		path = "";
		port = "";
		parameters = "";
		stateTime = 0;
		regexTime = 0;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public long getRegexTime() {
		return regexTime;
	}

	public void setRegexTime(long regexTime) {
		this.regexTime = regexTime;
	}

	public long getStateTime() {
		return stateTime;
	}

	public void setStateTime(long stateTime) {
		this.stateTime = stateTime;
	}

	@Override
	public String toString() {
		return this.scheme + "\n" + this.host + "\n" + this.port + "\n" + this.path + "\n" + this.parameters + "\n"
				+ "Regex " + this.regexTime + " ms" + "\n" + "State " + this.stateTime + " ms";

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((path == null) ? 0 : path.hashCode());
		result = prime * result + ((port == null) ? 0 : port.hashCode());
		result = prime * result + ((scheme == null) ? 0 : scheme.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UrlState other = (UrlState) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		if (scheme == null) {
			if (other.scheme != null)
				return false;
		} else if (!scheme.equals(other.scheme))
			return false;
		return true;
	}

	public UrlState split(String stringUrl) throws Exception {
		Long smTime = populateSmTime(stringUrl);
		long start = System.nanoTime();
		UrlState regexUrl = null;
		try {
			Pattern pattern = Pattern.compile(regexPattern);
			Matcher matcher = pattern.matcher(stringUrl);
			matcher.find();
			String scheme = matcher.group(2);
			String authority = matcher.group(4);
			String host = authority.contains(":") ? authority.split(":")[0] : authority;
			String path = authority.contains(":") ? authority.split(":")[1] : null;
			String port = matcher.group(5);
			String params = matcher.group(7);
			Long elapsedTime = System.nanoTime() - start;
			regexUrl = new UrlState(scheme, host, port, path, params, elapsedTime / 100000, smTime / 100000);

		} catch (Exception e) {
			throw new Exception("Exception occured while regex parsing operation..." + e);
		}
		return regexUrl;
	}

	private long populateSmTime(String stringUrl) throws Exception {
		try {
			long startTime = System.nanoTime();
			List<String> urlParts = new ArrayList<String>();
			String schema = stringUrl.substring(0, stringUrl.indexOf(":"));
			urlParts.add(schema);
			String afterSchema = stringUrl.substring(stringUrl.indexOf(":") + 3);

			String wholeDomain = afterSchema.substring(0, afterSchema.indexOf("/"));
			String afterDomain = afterSchema.substring(afterSchema.indexOf("/") + 1);

			if (wholeDomain.contains(":")) {
				String domain = wholeDomain.substring(0, wholeDomain.indexOf(":"));
				urlParts.add(domain);
				String port = wholeDomain.substring(wholeDomain.indexOf(":") + 1);
				urlParts.add(port);
			} else {
				urlParts.add(wholeDomain);
			}

			if (afterDomain.contains("?")) {
				String path = afterDomain.substring(0, afterDomain.indexOf("?"));
				urlParts.add(path);
				String params = afterDomain.substring(afterDomain.indexOf("?") + 1);
				urlParts.add(params);
			} else {
				urlParts.add(afterDomain);
			}
			return (System.nanoTime() - startTime);
		} catch (Exception ex) {
			throw new Exception("Url Format Exception");
		}
	}
}
