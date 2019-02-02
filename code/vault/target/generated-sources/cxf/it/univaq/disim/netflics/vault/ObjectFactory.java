
package it.univaq.disim.netflics.vault;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.univaq.disim.netflics.vault package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetMovieRequest_QNAME = new QName("http://it.univaq.disim.netflics/vault", "getMovieRequest");
    private final static QName _AddMovieRequest_QNAME = new QName("http://it.univaq.disim.netflics/vault", "addMovieRequest");
    private final static QName _GetMovieResponse_QNAME = new QName("http://it.univaq.disim.netflics/vault", "getMovieResponse");
    private final static QName _AddMovieResponse_QNAME = new QName("http://it.univaq.disim.netflics/vault", "addMovieResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.univaq.disim.netflics.vault
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetMovieRequest }
     * 
     */
    public GetMovieRequest createGetMovieRequest() {
        return new GetMovieRequest();
    }

    /**
     * Create an instance of {@link AddMovieRequest }
     * 
     */
    public AddMovieRequest createAddMovieRequest() {
        return new AddMovieRequest();
    }

    /**
     * Create an instance of {@link GetMovieResponse }
     * 
     */
    public GetMovieResponse createGetMovieResponse() {
        return new GetMovieResponse();
    }

    /**
     * Create an instance of {@link AddMovieResponse }
     * 
     */
    public AddMovieResponse createAddMovieResponse() {
        return new AddMovieResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMovieRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.univaq.disim.netflics/vault", name = "getMovieRequest")
    public JAXBElement<GetMovieRequest> createGetMovieRequest(GetMovieRequest value) {
        return new JAXBElement<GetMovieRequest>(_GetMovieRequest_QNAME, GetMovieRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMovieRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.univaq.disim.netflics/vault", name = "addMovieRequest")
    public JAXBElement<AddMovieRequest> createAddMovieRequest(AddMovieRequest value) {
        return new JAXBElement<AddMovieRequest>(_AddMovieRequest_QNAME, AddMovieRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetMovieResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.univaq.disim.netflics/vault", name = "getMovieResponse")
    public JAXBElement<GetMovieResponse> createGetMovieResponse(GetMovieResponse value) {
        return new JAXBElement<GetMovieResponse>(_GetMovieResponse_QNAME, GetMovieResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddMovieResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://it.univaq.disim.netflics/vault", name = "addMovieResponse")
    public JAXBElement<AddMovieResponse> createAddMovieResponse(AddMovieResponse value) {
        return new JAXBElement<AddMovieResponse>(_AddMovieResponse_QNAME, AddMovieResponse.class, null, value);
    }

}
