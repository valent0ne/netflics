
package it.univaq.disim.netflics.vault;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getMovieRequest complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getMovieRequest"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="imdb_id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getMovieRequest", propOrder = {
    "imdbId"
})
public class GetMovieRequest {

    @XmlElement(name = "imdb_id", required = true)
    protected String imdbId;

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

}
