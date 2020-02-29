package restlet_api.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.restlet.data.MediaType;
import org.restlet.ext.fileupload.RestletFileUpload;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Status;

import restlet_api.databaseLayer.DatabaseManager;
import restlet_api.utilities.GeneralUtilities;

public class TestResource extends PowerResource{
	@Post
	public String accept(Representation entity) throws Exception {
	    String result = null;
	    if (entity != null) {
	        if (MediaType.MULTIPART_FORM_DATA.equals(entity.getMediaType(), true)) {
	            // 1/ Create a factory for disk-based file items
	            DiskFileItemFactory factory = new DiskFileItemFactory();
	            factory.setSizeThreshold(1000240);

	            // 2/ Create a new file upload handler based on the Restlet
	            // FileUpload extension that will parse Restlet requests and
	            // generates FileItems.
	            RestletFileUpload upload = new RestletFileUpload(factory);

	            // 3/ Request is parsed by the handler which generates a
	            // list of FileItems
	            FileItemIterator fileIterator = upload.getItemIterator(entity);

	            // Process only the uploaded item called "fileToUpload"
	            // and return back
	            boolean found = false;
	            while (fileIterator.hasNext() && !found) {
	                FileItemStream fi = fileIterator.next();
	                if (fi.getFieldName().equals("fileToUpload")) {
	                    found = true;
	                    File theFile = new File("theFile.csv");
	                    FileWriter writer  = new FileWriter("theFile.csv");
	                    // consume the stream immediately, otherwise the stream
	                    // will be closed.
	                    
	                    StringBuilder sb = new StringBuilder();
	                    /*
	                    sb.append(fi.getContentType()).append("\n");
	                    sb.append("file name : ");
	                    sb.append(fi.getName()).append("\n");
	                    */
	                    BufferedReader br = new BufferedReader(
	                            new InputStreamReader(fi.openStream()));
	                    String line = null;
	                    while ((line = br.readLine()) != null) {
	                        sb.append(line);
	                        sb.append('\n');
	                    }
	                    sb.append("\n");
	                    System.out.println(sb.toString());
	                    writer.write(sb.toString());
	                    writer.close();
	                    
	                    GeneralUtilities.executeSystemCommand("./mongoInsertCSV theFile.csv automateTest");
	                    
	                    theFile.delete();
	                    
	                    
	                    result = GeneralUtilities.STATUS_OK + '\n';
	                }
	            }
	        } else {
	            // POST request with no entity.
	            //setStatus();
	        }
	    }
	    return result;
	}

	@Get
	public static synchronized String getGet() {
		return "Time since last update\n" + DatabaseManager.lastQuotaUpdate;
	}
}
