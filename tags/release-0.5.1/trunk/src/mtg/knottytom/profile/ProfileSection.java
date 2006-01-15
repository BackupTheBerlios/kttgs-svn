package mtg.knottytom.profile;

// import mtg.knottytom.profile.ProfileParser;

/**
 * Description of the Class
 * 
 * @author tom
 * @created 3. August 2005
 */
public class ProfileSection {
	private String type = "road";
	private int dist = 0;
	private int height = 0;
	private boolean isCrossPoint = true;
	private String longitude = "";
	private String latitude = "";

	public ProfileSection() {
		// System.out.println("ProfileSection()");
	}

	/**
	 * Constructor for the ProfileSection object
	 * 
	 * @param t
	 *            Type
	 * @param d
	 *            Distance
	 * @param h
	 *            Height
	 */
	public ProfileSection(String t, int d, int h, boolean icp) {
		type = t;
		dist = d;
		height = h;
		isCrossPoint = icp;
	}

	/**
	 * Returns the value of type.
	 * 
	 * @return The type value
	 */
	public String getType() {
		return type;
	}

	/**
	 * Sets the value of type.
	 * 
	 * @param type
	 *            The value to assign type.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Returns the value of dist.
	 * 
	 * @return The dist value
	 */
	public int getDist() {
		return dist;
	}

	/**
	 * Sets the value of dist.
	 * 
	 * @param dist
	 *            The value to assign dist.
	 */
	public void setDist(int dist) {
		this.dist = dist;
	}

	public void setDistance(String dist) {
		// System.out.println("public void setDistance(int dist)");
		// System.out.println("Int: " + dist);
		setDist(Integer.parseInt(dist));
	}

	/**
	 * Returns the value of height.
	 * 
	 * @return The height value
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Sets the value of height.
	 * 
	 * @param height
	 *            The value to assign height.
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	public boolean isCrossPoint() {
		return isCrossPoint;
	}

	public void setCrossPoint(boolean isCrossPoint) {
		// System.out.println("icp: " + isCrossPoint);
		this.isCrossPoint = isCrossPoint;
	}
	
	public void setNoCrossPoint() {
		this.isCrossPoint = false;
	}

	/**
	 * @return Returns the latitude.
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude The latitude to set.
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return Returns the longitude.
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude The longitude to set.
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

}
