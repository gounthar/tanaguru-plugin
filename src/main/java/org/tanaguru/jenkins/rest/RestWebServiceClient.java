package org.tanaguru.jenkins.rest;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.tanaguru.model.AuditModel;
import org.tanaguru.util.UtilityCall;

/**
 *
 * @author tanaguru
 */
public class RestWebServiceClient {

    public String baseUri = null;
    private Client client = null;
    private WebTarget target = null;

    public RestWebServiceClient(String baseUri, String proxy_uri, String proxy_username, String proxy_password) {
        this.baseUri = baseUri;
        ClientConfig config = new ClientConfig();

//        client.property(ClientProperties.CONNECT_TIMEOUT, 1000);
//        client.property(ClientProperties.READ_TIMEOUT, 1000);

        if(proxy_uri != null && proxy_username != null && proxy_password != null) {
            config.property(ClientProperties.PROXY_URI, proxy_uri);
            config.property(ClientProperties.PROXY_USERNAME, proxy_username);
            config.property(ClientProperties.PROXY_PASSWORD, proxy_password);
        }
        client = ClientBuilder.newClient(config);
        target = client.target(baseUri);
    }

    public void reloadUri(String baseUri) {
        target = null;
        target = client.target(baseUri);
    }

    public String getTestConnection() {
        target = target.path("/test");
        // GET Request from Jersey Client
        Response response = target.request(MediaType.TEXT_PLAIN)
                .get(Response.class);

        System.out.println("Status de la requete :" + response.getStatus());

        String message = "";
        if (response.getStatus() == 200) {
            message = response.readEntity(new GenericType<String>() {
            });
            System.out.println(message);
        }
        return message;
    }

    public void postRequest() {
        reloadUri(baseUri);
        String input = "{\"id\":\"12\",\"idCode\":\"ZF123\", \"url\":\"myurl\",\"type\":\"RestApiJms\",\"state\":\"Initialization\",\"scenario\":\"my scenario\"}";
        target = target.path("/postMessage");

        // POST Request from Jersey Client
        Response response = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(input, MediaType.APPLICATION_JSON), Response.class);

        System.out.println(response);
        if (response.getStatus() == 200) {

            String message = response.readEntity(new GenericType<String>() {
            });
            System.out.println(message);
            System.out.println("post success");
        }
    }

    public String postRequestUsingGson(String scenario) {
        reloadUri(baseUri);
        target = target.path("/audits");
        Gson gson = new Gson();
        AuditModel auditModel = new AuditModel();
        //  auditModel.setUrl(url);
        auditModel.setIdCode(UtilityCall.getCode());
        auditModel.setScenario(scenario);

        String input = gson.toJson(auditModel);

        //POST Request from jersey Client Using GSON
        Response response = (Response) target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(input, MediaType.APPLICATION_JSON), Response.class);

        System.out.println(response);
        if (response.getStatus() == 200) {

            String message = response.readEntity(new GenericType<String>() {
            });
            System.out.println(message);
            System.out.println("post request using Json is Success");
            return message;
        }
        return "Post request using Json is Error";
    }

    public void putRequest() {
        reloadUri(baseUri);
        target = target.path("putMessage");
        String input = "{\"site\":\"www.9threes.com\",\"message\":\"is new domain\"}";

        //PUT Request from Jersey Client Example
        Response response = target.request(MediaType.APPLICATION_JSON)
                .put(Entity.entity(input, MediaType.APPLICATION_JSON), Response.class);

        System.out.println(response);
        if (response.getStatus() == 200) {
            System.out.println("put request using Json is Success");
        }
    }

    public static void main(String args[]) throws Exception {
        try {
            RestWebServiceClient restWebServiceClient = new RestWebServiceClient("http://richemont.tanaguru.com/rest/service", "", "", "");
            restWebServiceClient.getTestConnection();
            //  jerseyClient.postRequest();
            restWebServiceClient.postRequestUsingGson("http://longdesc.fr/");
//                     jerseyClient.putRequest();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
