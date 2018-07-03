package saveDocumentService.interestprofiles;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.text.Document;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Node;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;


import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;

public class SaveDocumentIP extends AbstractInterestProfile{

	@Override
	protected void doOnReceive(AbstractEvent event) {
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = null;
		try {
			transformer = transformerFactory.newTransformer();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc= (Document) event.getPropertyByKey("document").getValue();
		DOMSource source = new DOMSource((Node) event.getPropertyByKey("DOM").getValue());
		StreamResult result = new StreamResult(
				new File("C:\\Users\\USERNAME\\Documents\\Conversation\\Protocol.xml"));
		try {
			transformer.transform(source, result);
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	// In Google Drive speichern über API
		private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
	    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	    private static final String CREDENTIALS_FOLDER = "credentials"; // Directory to store user credentials.

	    /**
	     * Global instance of the scopes required by this quickstart.
	     * If modifying these scopes, delete your previously saved credentials/ folder.
	     */
	    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
	    private static final String CLIENT_SECRET_DIR = "client_secret.json";

	    /**
	     * Creates an authorized Credential object.
	     * @param HTTP_TRANSPORT The network HTTP Transport.
	     * @return An authorized Credential object.
	     * @throws Exception 
	     */
	    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws Exception {
	        // Load client secrets.
	        InputStream in = SaveDocumentIP.class.getResourceAsStream(CLIENT_SECRET_DIR);
	        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

	        // Build flow and trigger user authorization request.
	        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
	                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
	                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
	                .setAccessType("offline")
	                .build();
	        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
	    }

	    public static void main(String... args) throws IOException, GeneralSecurityException {
	       String requestUrl= "https://drive.google.com/drive/folders/1jyTsasf_aeWhw1a_MeBgxRwQ2Tg9T-f2";
	       Date currentTime = new Date(); 
	    	
	    	// Build a new authorized API client service.
	        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	        Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
	                .setApplicationName(APPLICATION_NAME)
	                .build();

	        // Upload Doc
	        File fileMetadata = new File(""+currentTime+"Protocol.xml");
	        java.io.File filePath = new java.io.File("Conversation/"+currentTime+"Protocol.xml");
	        FileContent mediaContent = new FileContent("Protocol", filePath);
	        File file = driveService.files().create(fileMetadata, mediaContent)
	            .setFields("id")
	            .execute();
	    }
		
	  
}
