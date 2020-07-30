package restlet_api.resources;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.restlet.data.Form;
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
	    String result = "hellu";
	    String parm = getContext().getParameters().getFirstValue("type");
	    return result;
	}

	@Get
	public String getGet() {
		//Form queryParams = getRequest().getResourceRef().getQueryAsForm();
		//String parm = queryParams.getFirstValue("type");
		String type = getRequest().getResourceRef().getQueryAsForm().getFirstValue("type");
	    return "Param: " + '\n';
	}
}
