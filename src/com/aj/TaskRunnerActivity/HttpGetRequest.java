package com.aj.TaskRunnerActivity;

import java.io.ByteArrayOutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import android.net.http.AndroidHttpClient;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

import com.aj.TaskRunner.Task;

public class HttpGetRequest extends Task {
	
	public HttpGetRequest(Bundle params) {
		super(params);
	}
	
	public HttpGetRequest(Parcel in) {
		super(in);
	}

	@Override
	public void execute(TaskProgressListener progressListener, Task task) {
		String result = null;
		AndroidHttpClient httpClient = null;
		ByteArrayOutputStream out = null;
		try {
			httpClient = AndroidHttpClient.newInstance("My User Agent");
			HttpGet getRequest = new HttpGet(getParams().getString("url"));
			HttpResponse response = httpClient.execute(getRequest);
			out = new ByteArrayOutputStream();
	        response.getEntity().writeTo(out);
	        result = out.toString();
		} catch (Exception e) {
			result = "Error: Could not make http get request." + e.toString();
		} finally {
			try {
				out.close();
			} catch (Exception e) {}
			try {
				httpClient.close();
			} catch (Exception e){}
			
			getResultBundle().putString("my_result", result);
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
