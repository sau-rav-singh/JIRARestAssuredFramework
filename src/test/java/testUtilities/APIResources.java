package testUtilities;

/**
 * This enum contains the available API resources with their endpoint paths.
 * Each resource is associated with a specific endpoint path.
 */
public enum APIResources {
	/**
	 * Resource for adding a comment to an issue.
	 */
	
	AddComment("rest/api/3/issue/{id}/comment");

	private String resource;

	/**
	 * Constructor for setting the endpoint path of the resource.
	 * @param resource the endpoint path of the resource
	 */
	
	APIResources(String resource) {
		this.resource = resource;
	}

	/**
	 * Getter for the endpoint path of the resource.
	 * @return the endpoint path of the resource
	 */
	
	public String getResource() {
		return resource;
	}
}