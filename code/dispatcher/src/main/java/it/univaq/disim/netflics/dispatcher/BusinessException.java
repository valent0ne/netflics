package it.univaq.disim.netflics.dispatcher;

import javax.ws.rs.core.Response;

public class BusinessException extends RuntimeException {

	public BusinessException() {
		super();
	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public Response restResponseHandler(){

        Response.Status s = Response.Status.INTERNAL_SERVER_ERROR;

        String statusCode = this.getMessage().substring(0,3);
        String message = this.getMessage().substring(4);

        switch(statusCode){
            case "401":
                s = Response.Status.UNAUTHORIZED;
                break;
            case "400":
                s = Response.Status.BAD_REQUEST;
                break;
            case "404":
                s = Response.Status.NOT_FOUND;
                break;
            case "503":
                s = Response.Status.SERVICE_UNAVAILABLE;
                break;
            default:
                message = this.getMessage();
                break;
        }

        return Response.status(s).entity(message).build();
    }
	
	

}
