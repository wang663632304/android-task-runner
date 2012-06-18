package com.aj.TaskRunnerTest;

import java.io.ByteArrayOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.os.Parcel;
import android.os.Parcelable;

import com.aj.TaskRunner.Task;

public class HttpGetRequest extends Task {
	
	private String url;
	
	public HttpGetRequest(String url) {
		super();
		this.url = url;
	}
	
	public HttpGetRequest(Parcel in) {
		super(in);
		url = in.readString();
	}
	
	public void writeToParcel(Parcel out, int flags) {
    	super.writeToParcel(out, flags);
        out.writeString(url);
    }

	@Override
	public void execute(TaskProgressListener progressListener) {
		String result = null;
		try {
			AndroidHttpClient httpClient = AndroidHttpClient.newInstance("My User Agent");
			HttpGet getRequest = new HttpGet(url);
			HttpResponse response = httpClient.execute(getRequest);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
	        response.getEntity().writeTo(out);
	        out.close();
	        result = out.toString();
		} catch (Exception e) {
			result = "Error: Could not make http get request." + e.toString();
		} finally {
			getBundle().putString("my_result", result);
		}		
	}
	
	public final static Parcelable.Creator<HttpGetRequest> CREATOR = new Parcelable.Creator<HttpGetRequest>() {
    	
        public HttpGetRequest createFromParcel(Parcel in) {
            return new HttpGetRequest(in);
        }
        public HttpGetRequest[] newArray(int size) {
            return new HttpGetRequest[size];
        }
    };
}
