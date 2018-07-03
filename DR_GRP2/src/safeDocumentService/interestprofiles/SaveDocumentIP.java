package safeDocumentService.interestprofiles;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.media.MediaHttpUploader;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.InputStreamContent;

import eventprocessing.agent.interestprofile.AbstractInterestProfile;
import eventprocessing.event.AbstractEvent;
import eventprocessing.utils.factory.AbstractFactory;
import eventprocessing.utils.factory.FactoryProducer;
import eventprocessing.utils.factory.FactoryValues;
import eventprocessing.utils.factory.LoggerFactory;
import semanticService.interestprofiles.SemanticChunksIP;

public class SaveDocumentIP extends AbstractInterestProfile{

	@Override
	protected void doOnReceive(AbstractEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	/** Authorizes the installed application to access user's protected data. */
	 private static Credential authorize() throws Exception {
	   
	   File mediaFile = new File("/tmp/Test.jpg");
	   InputStreamContent mediaContent =
	       new InputStreamContent("image/jpeg",
	           new BufferedInputStream(new FileInputStream(mediaFile)));
	   mediaContent.setLength(mediaFile.length());

	   MediaHttpUploader uploader = new MediaHttpUploader(mediaContent, transport, httpRequestInitializer);
	   uploader.setProgressListener(new CustomProgressListener());
	   HttpResponse response = uploader.upload(requestUrl);
	   if (!response.isSuccessStatusCode()) {
	     throw GoogleJsonResponseException(jsonFactory, response);
	   }
	}
	  
	  
}
