package com.aj.TaskRunner;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class Task implements Parcelable {
	
	public static final String TAG = Task.class.getName();
	
	public static final String EXTRA_KEY_TASK = Task.class.getName() + "task";

	
	private Bundle result;
	private Bundle params;
	private String id;
	
	public abstract void execute(TaskProgressListener progressListener, Task task);
	
	public interface TaskProgressListener {
		public void onProgressUpdate(int progress, Task task);
	}
	
	public Task(Bundle params) {
		this.result = new Bundle();
		this.params = params;
	}
	
	public Task(Parcel in) {
        this.result = in.readBundle();
        this.params = in.readBundle();
        this.id = in.readString();
    }
	
	public void setResultBundle(Bundle result) {
		this.result = result;
	}
	
	public Bundle getResultBundle() {
		return this.result;
	}
	
	public Bundle getParams() {
		return params;
	}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeBundle(result);
        out.writeBundle(params);
        out.writeString(id);
    }
    
    protected void setId(String id) {
    	this.id = id;
    }
    
    protected String getId() {
    	return this.id;
    }
}
