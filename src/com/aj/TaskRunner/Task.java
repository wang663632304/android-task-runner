package com.aj.TaskRunner;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public abstract class Task implements Parcelable {
	
	public static final String TAG = Task.class.getName();

	
	private Bundle bundle;
	
	public abstract void execute(TaskProgressListener progressListener);
	
	public interface TaskProgressListener {
		public void onProgressUpdate(int progress);
	}
	
	public Task() {
		bundle = new Bundle();
	}
	
	public Task(Parcel in) {
        bundle = in.readBundle();
    }
	
	public void setBundle(Bundle bundle) {
		this.bundle = bundle;
	}
	
	public Bundle getBundle() {
		return bundle;
	}

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeBundle(bundle);
    }
}
