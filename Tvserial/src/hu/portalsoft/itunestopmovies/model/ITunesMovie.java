package hu.portalsoft.itunestopmovies.model;

import java.net.URL;

/**
 * Data class for iTunes movie
 * @author sagi
 *
 */
public class ITunesMovie {

	/**
	 * Id of the movie
	 */
	Long id;
	
	/**
	 * Movie title
	 */
	String title;
	
	/**
	 * Copyright notice
	 */
	String copyright;
	
	/**
	 * Movie small image
	 */
	URL smallImageUrl;
	
	/**
	 * Movie image
	 */
	URL imageUrl;
	
	/**
	 * Movie summary
	 */
	String summary;
	
	URL privewUrl;
	
	/**
	 * Get movie id
	 * @return Id of the movie
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Set movie id
	 * @param id Id of the move
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Get movie title
	 * @return Movie title value or null if no title found for movie
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set movie title
	 * @param title The title of the movie
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Get movie copyright notice or null if no copyright notice found for movie
	 * @return Movie copyright notice
	 */
	public String getCopyright() {
		return copyright;
	}

	/**
	 * Set movie copyright notice
	 * @param copyright The copyright notice of the movie
	 */
	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	/**
	 * Get movie small image URL
	 * @return Movie small image URL or null if no small image found for movie
	 */
	public URL getSmallImageUrl() {
		return smallImageUrl;
	}

	/**
	 * Set movie small image URL
	 * @param smallImageUrl The small image URL of the movie
	 */
	public void setSmallImageUrl(URL smallImageUrl) {
		this.smallImageUrl = smallImageUrl;
	}

	/**
	 * Get movie image URL
	 * @return Movie image URL or null if no image found for movie
	 */
	public URL getImageUrl() {
		return imageUrl;
	}

	/**
	 * Set movie image URL
	 * @param imageUrl The image URL of the movie
	 */
	public void setImageUrl(URL imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * Get movie summary
	 * @return The summary text of the movie
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Set movie summary
	 * @param summary The summary of the movie
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}

	/**
	 * @return the privewUrl
	 */
	public URL getPrivewUrl() {
		return privewUrl;
	}

	/**
	 * @param privewUrl the privewUrl to set
	 */
	public void setPrivewUrl(URL privewUrl) {
		this.privewUrl = privewUrl;
	}
	
}
