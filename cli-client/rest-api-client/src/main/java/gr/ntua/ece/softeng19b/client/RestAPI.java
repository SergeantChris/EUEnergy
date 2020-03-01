package gr.ntua.ece.softeng19b.client;

import gr.ntua.ece.softeng19b.data.model.ATLRecordForSpecificDay;
import gr.ntua.ece.softeng19b.data.model.ATLRecordForSpecificMonth;
import gr.ntua.ece.softeng19b.data.model.ATLRecordForSpecificYear;
import gr.ntua.ece.softeng19b.data.model.User;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONObject;
import org.restlet.Client;
import org.restlet.Request;
import org.restlet.data.Header;
import org.restlet.data.MediaType;
import org.restlet.data.Protocol;
import org.restlet.engine.header.HeaderConstants;
import org.restlet.representation.Representation;
import org.restlet.resource.ClientResource;
import org.restlet.util.Series;

import java.io.*;
import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class RestAPI {

    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    private static final String URL_ENCODED = "application/x-www-form-urlencoded";
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static final String BASE_URL = "/energy/api";
    public static final String CUSTOM_HEADER = "Token";

    static {
        System.setProperty("jdk.internal.httpclient.disableHostnameVerification", "true");
    }

    private final String urlPrefix;
    private final HttpClient client;

    private String token = null; //user is not logged in

    public RestAPI() throws RuntimeException {
        this("localhost", 8765);
    }

    public RestAPI(String host, int port) throws RuntimeException {
        try {
            this.client = newHttpClient();
        }
        catch(NoSuchAlgorithmException | KeyManagementException e) {
            throw new RuntimeException(e.getMessage());
        }
        this.urlPrefix = "https://" + host + ":" + port + BASE_URL;
    }
//----------------------------------URL-------------------------------------------------------
    String urlForActualDataLoad(String areaName, String resolutionCode, LocalDate date, Format format) {
        String encAreaName = URLEncoder.encode(areaName, StandardCharsets.UTF_8);
        String encResCode  = URLEncoder.encode(resolutionCode, StandardCharsets.UTF_8);
        return urlPrefix + "/ActualTotalLoad/" + encAreaName + "/" + encResCode + "/date/" + date.toString() +
                "?format=" + format.name().toLowerCase();
    }
    
    String urlForActualDataLoad(String areaName, String resolutionCode, String dateArg, String date, Format format) {
        String encAreaName = URLEncoder.encode(areaName, StandardCharsets.UTF_8);
        String encResCode  = URLEncoder.encode(resolutionCode, StandardCharsets.UTF_8);
        String encDateArg  = URLEncoder.encode(dateArg, StandardCharsets.UTF_8);
        String encDate     = URLEncoder.encode(date, StandardCharsets.UTF_8);
        
        
        return urlPrefix + "/ActualTotalLoad/" + encAreaName + "/" + encResCode + "/" + encDateArg + "/" + encDate +
                "?format=" + format.name().toLowerCase();
    }

    
    String urlForAggregatedGenerationPreType(String areaName, String productionType,String resolutionCode, String dateArg, String date, Format format) {
        String encAreaName = URLEncoder.encode(areaName, StandardCharsets.UTF_8);
        String encResCode  = URLEncoder.encode(resolutionCode, StandardCharsets.UTF_8);
        String encDateArg  = URLEncoder.encode(dateArg, StandardCharsets.UTF_8);
        String encProdType  = URLEncoder.encode(productionType, StandardCharsets.UTF_8);
        String encDate     = URLEncoder.encode(date, StandardCharsets.UTF_8);
        
        
        return urlPrefix + "/AggregatedGenerationPerType/" + encAreaName  +"/" + encProdType + "/" + encResCode + "/" + encDateArg + "/" + encDate +
                "?format=" + format.name().toLowerCase();
    }
    
    String urlForDayAheadTotalLoadForecast(String areaName, String resolutionCode, String dateArg, String date, Format format) {
        String encAreaName = URLEncoder.encode(areaName, StandardCharsets.UTF_8);
        String encResCode  = URLEncoder.encode(resolutionCode, StandardCharsets.UTF_8);
        String encDateArg  = URLEncoder.encode(dateArg, StandardCharsets.UTF_8);
        String encDate     = URLEncoder.encode(date, StandardCharsets.UTF_8);
        
        
        return urlPrefix + "/DayAheadTotalLoadForecast/" + encAreaName + "/" + encResCode + "/" + encDateArg + "/" + encDate +
                "?format=" + format.name().toLowerCase();
    }
    String urlForActualvsForecast(String areaName, String resolutionCode, String dateArg, String date, Format format) {
        String encAreaName = URLEncoder.encode(areaName, StandardCharsets.UTF_8);
        String encResCode  = URLEncoder.encode(resolutionCode, StandardCharsets.UTF_8);
        String encDateArg  = URLEncoder.encode(dateArg, StandardCharsets.UTF_8);
        String encDate     = URLEncoder.encode(date, StandardCharsets.UTF_8);
        
        
        return urlPrefix + "/ActualvsForecast/" + encAreaName + "/" + encResCode + "/" + encDateArg + "/" + encDate +
                "?format=" + format.name().toLowerCase();
    }
    
    
    
    
    String urlForHealthCheck() {
        return urlPrefix + "/HealthCheck";
    }

    String urlForReset() { return urlPrefix + "/Reset"; }

    String urlForLogin() { return urlPrefix + "/Login"; }

    String urlForLogout() { return urlPrefix + "/Logout"; }

    String urlForAddUser() { return urlPrefix + "/Admin/users"; }

    String urlForUpdateUser(String username) {
        return urlPrefix + "/Admin/users/" + URLEncoder.encode(username, StandardCharsets.UTF_8);
    }

    String urlForGetUser(String username) {
        return urlForUpdateUser(username);
    }

    String urlForImport(String dataSet) {
        return urlPrefix + "/Admin/" + URLEncoder.encode(dataSet, StandardCharsets.UTF_8);
    }
//-----------------------------------------END URL-------------------------------------------------------
//-----------------------------------------Request-------------------------------------------------------    
    private String newPowerRequest(String url,String Method, Map<String, String> params) {
    	
    	Client client = new Client(Protocol.HTTP);
    	ClientResource cr = new ClientResource(url);
    	Request req = cr.getRequest();
    	//req.setMethod();
    	Series<Header> headers = new Series<Header>(Header.class);
    	req.getAttributes().put(HeaderConstants.ATTRIBUTE_HEADERS, headers);
    	
    	for(String key : params.keySet()) {
    		String val = params.get(key);
    		headers.add(key, val);
    	}
    	
    	switch(Method) {
    	case "GET": cr.get(MediaType.APPLICATION_JSON);
    		break;
    	case "POST": cr.post(MediaType.APPLICATION_JSON);
    		break;
    	case "PUT": cr.put(MediaType.APPLICATION_JSON);
    		break;
    	}
    	
    	Representation resp = cr.getResponseEntity();
    	
    	return "Answer:\n" + resp.toString() + '\n';
    }

    private HttpRequest newGetRequest(String url) {
        return newRequest("GET", url, URL_ENCODED, HttpRequest.BodyPublishers.noBody());
    }

    private HttpRequest newPostRequest(String url, String contentType, HttpRequest.BodyPublisher bodyPublisher) {
        return newRequest("POST", url, contentType, bodyPublisher);
    }

    private HttpRequest newPutRequest(String url, String contentType, HttpRequest.BodyPublisher bodyPublisher) {
        return newRequest("PUT", url, contentType, bodyPublisher);
    }

    private HttpRequest newRequest(String method, String url, String contentType, HttpRequest.BodyPublisher bodyPublisher) {
        HttpRequest.Builder builder = HttpRequest.newBuilder();
        if (token != null) {
            builder.header(CUSTOM_HEADER, token);
        }
        return builder.
                method(method, bodyPublisher).
                header(CONTENT_TYPE_HEADER, contentType).
                uri(URI.create(url)).
                build();
    }
//------------------------------------END Request-------------------------------------------------------

    private <T> T sendRequestAndParseResponseBodyAsUTF8Text(Supplier<HttpRequest> requestSupplier,
                                                            Function<Reader, T> bodyProcessor) {
        HttpRequest request = requestSupplier.get();
        try {
            System.out.println("Sending " + request.method() + " to " + request.uri());
            HttpResponse<InputStream> response = client.send(request, HttpResponse.BodyHandlers.ofInputStream());
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                try {
                    if (bodyProcessor != null) {
                        return bodyProcessor.apply(new InputStreamReader(response.body(), StandardCharsets.UTF_8));
                    }
                    else {
                        return null;
                    }
                }
                catch(Exception e) {
                    throw new ResponseProcessingException(e.getMessage(), e);
                }
            }
            else {
                throw new ServerResponseException(statusCode, ClientHelper.readContents(response.body()));
            }
        }
        catch(IOException | InterruptedException e) {
            throw new ConnectionException(e.getMessage(), e);
        }
    }
//---------------------------------------------Functions------------------------------------------------------
    public boolean isLoggedIn() {
        return token != null;
    }

    public String healthCheck() {
        return sendRequestAndParseResponseBodyAsUTF8Text(
            () -> newGetRequest(urlForHealthCheck()),
            ClientHelper::parseJsonStatus
        );
    }

    public String resetDatabase() {
        return sendRequestAndParseResponseBodyAsUTF8Text(
            () -> newPostRequest(urlForReset(), URL_ENCODED, HttpRequest.BodyPublishers.noBody()),
            ClientHelper::parseJsonStatus
        );
    }

    public void login(String username, String password) {
        Map<String, String> formData = new LinkedHashMap<>();
        formData.put("username", username);
        formData.put("password", password);
        token = sendRequestAndParseResponseBodyAsUTF8Text(
            () -> newPostRequest(urlForLogin(), URL_ENCODED, ofUrlEncodedFormData(formData)),
            ClientHelper::parseJsonToken
        );
        System.out.println("successful login, token:" + token);
    }

    public void logout() {
        sendRequestAndParseResponseBodyAsUTF8Text(
            () -> newPostRequest(urlForLogout(), URL_ENCODED, HttpRequest.BodyPublishers.noBody()),
            null
        );
        token = null;
    }
//--------------------------------------END Functions----------------------------------------------------
//--------------------------------------Admin Functions--------------------------------------------------
    
    public User addUser(String username, String email, String password, int quota) {
        Map<String, String> formData = new LinkedHashMap<>();
        formData.put("username", username);
        formData.put("email", email);
        formData.put("password", password);
        formData.put("requestsPerDayQuota", String.valueOf(quota));
        return sendRequestAndParseResponseBodyAsUTF8Text(
            () -> newPostRequest(urlForAddUser(), URL_ENCODED, ofUrlEncodedFormData(formData)),
            ClientHelper::parseJsonUser
        );
    }

    public User updateUser(User updatedUser) {
        //only email and/or quota can be updated
        Map<String, String> formData = new LinkedHashMap<>();
        formData.put("email", updatedUser.getEmail());
        formData.put("requestsPerDayQuota", String.valueOf(updatedUser.getRequestsPerDayQuota()));
        return sendRequestAndParseResponseBodyAsUTF8Text(
            () -> newPutRequest(urlForUpdateUser(updatedUser.getUsername()), URL_ENCODED, ofUrlEncodedFormData(formData)),
            ClientHelper::parseJsonUser
        );
    }

    public User getUser(String username) {
        return sendRequestAndParseResponseBodyAsUTF8Text(
            () -> newGetRequest(urlForGetUser(username)),
            ClientHelper::parseJsonUser
        );
    }

    public ImportResult importFile(String dataSet, Path dataFilePath) throws IOException {
        String boundary = new BigInteger(256, new Random()).toString();
        Map<String, Object> formData = Map.of("file", dataFilePath);
        HttpRequest.BodyPublisher bodyPublisher = ofMultipartFormData(formData, boundary);
        String contentType = MULTIPART_FORM_DATA + ";boundary=" + boundary;
        return sendRequestAndParseResponseBodyAsUTF8Text(
            () -> newPostRequest(urlForImport(dataSet), contentType, bodyPublisher),
            ClientHelper::parseJsonImportResult
        );
    }
//-------------------------------------End Admin Functions------------------------------------------------------- 
    

    public String getActualTotalLoad(String areaName,String resolutionCode,String dateArg, String date, Format format) {
        return newPowerRequest(urlForActualDataLoad(areaName, resolutionCode, dateArg, date, format),"GET", null);
    }
    
    public List<ATLRecordForSpecificDay> getActualTotalLoad(String areaName,
											            String resolutionCode,
											            LocalDate date,
											            Format format) {
    	return sendRequestAndParseResponseBodyAsUTF8Text(
    			() -> newGetRequest(urlForActualDataLoad(areaName, resolutionCode, date, format)),
    			format::consumeActualTotalLoadRecordsForSpecificDay
    			);
    }
    
    public String getAggregatedGenerationPerType(String areaName, String productionType,String resolutionCode, String dateArg,String date, Format format) {
        return newPowerRequest(urlForAggregatedGenerationPreType(areaName, productionType, resolutionCode, dateArg, date, format),"GET",null);
    }
    
    public String getDayAheadTotalLoadForecast(String areaName,String resolutionCode,String dateArg, String date, Format format) {
        return newPowerRequest(urlForDayAheadTotalLoadForecast(areaName, resolutionCode, dateArg, date, format),"GET",null);
    }
    
    public String getActualvsForecast(String areaName,String resolutionCode,String dateArg, String date, Format format) {
        return newPowerRequest(urlForActualvsForecast(areaName, resolutionCode, dateArg, date, format),"GET",null);
    }

    
//------------------------------------------HTTP things------------------------------------------
    //Helper method to create a new http client that can tolerate self-signed or improper ssl certificates
    private static HttpClient newHttpClient() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustAllCerts, new SecureRandom());
        return HttpClient.newBuilder().sslContext(sslContext).build();
    }

    private static TrustManager[] trustAllCerts = new TrustManager[]{
        new X509TrustManager() {
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
            public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
            public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
        }
    };

    private static HttpRequest.BodyPublisher ofUrlEncodedFormData(Map<String, String> data) {
        return HttpRequest.BodyPublishers.ofString(ClientHelper.encode(data));
    }

    private static HttpRequest.BodyPublisher ofMultipartFormData(Map<String, Object> data, String boundary)
            throws IOException {
        var byteArrays = new ArrayList<byte[]>();
        String separator = "--" + boundary + "\r\nContent-Disposition: form-data; name=";
        byte[] separatorBytes = separator.getBytes(StandardCharsets.UTF_8);
        for (Map.Entry<String, Object> entry : data.entrySet()) {
            byteArrays.add(separatorBytes);

            if (entry.getValue() instanceof Path) {
                var path = (Path) entry.getValue();
                String mimeType = Files.probeContentType(path);
                byteArrays.add(("\"" + entry.getKey() + "\"; filename=\"" + path.getFileName()
                        + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n").getBytes(StandardCharsets.UTF_8));
                byteArrays.add(Base64.getMimeEncoder().encode(Files.readAllBytes(path)));
                byteArrays.add("\r\n".getBytes(StandardCharsets.UTF_8));
            } else {
                byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue() + "\r\n")
                        .getBytes(StandardCharsets.UTF_8));
            }
        }
        byteArrays.add(("--" + boundary + "--").getBytes(StandardCharsets.UTF_8));
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }

}