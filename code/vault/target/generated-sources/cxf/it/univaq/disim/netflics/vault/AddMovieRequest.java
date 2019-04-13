
package it.univaq.disim.netflics.vault;

import javax.activation.DataHandler;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for addMovieRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="addMovieRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="imdb_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="movie" type="{http://www.w3.org/2001/XMLSchema}byte"/&gt;
 *         &lt;element name="token" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "addMovieRequest", propOrder = {
    "imdbId",
    "movie",
    "token"
})
public class AddMovieRequest {

    @XmlElement(name = "imdb_id", required = true)
    protected String imdbId;
    protected DataHandler movie;
    @XmlElement(required = true)
    protected String token;

    /**
     * Gets the value of the imdbId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImdbId() {
        return imdbId;
    }

    /**
     * Sets the value of the imdbId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImdbId(String value) {
        this.imdbId = value;
    }

    /**
     * Gets the value of the movie property.
     * 
     */
    public DataHandler getMovie() {
        return movie;
    }

    /**
     * Sets the value of the movie property.
     * 
     */
    public void setMovie(DataHandler value) {
        this.movie = value;
    }

    /**
     * Gets the value of the token property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets the value of the token property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setToken(String value) {
        this.token = value;
    }

}
