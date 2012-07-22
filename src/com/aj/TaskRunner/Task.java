package com.aj.TaskRunner;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * <p>Base class for all tasks.</p>
 * <div>All concrete classes that extend this class have to do several things to make this work.</div><br />
 * <strong>1) A child class must create 2 constructors and call super in both constructors.</strong>
 * <pre>  
 * {@code
 * public MyTask(Bundle params) {
		super(params);
	}
	
	public MyTask(Parcel in) {
		super(in);
	}
 * }
 * </pre>
 * <strong>2) A child class must implement Parcelable.Creator<Task> as a public CREATOR field that generates instances of your Parcelable class from a Parcel. </strong>
 * <pre> 
 * {@code
 * public final static Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
    	
        public MyTask createFromParcel(Parcel in) {
            return new MyTask(in);
        }
        public MyTask[] newArray(int size) {
            return new MyTask[size];
        }
    };
 * }
 * </pre>
 *<strong>3) Implement <i>public abstract void execute(TaskProgressListener progressListener, Task task)</i> method with the actual code that has to be executed by this task.</strong>
 *<pre>
	public void execute(TaskProgressListener progressListener, Task task) {
		//Do some lengthy task here....
	}
 * </pre>
 */

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
