package com.aj.TaskRunnerActivity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.aj.TaskRunner.Task;

public class MyTask extends Task {
	
	public MyTask(Bundle params) {
		super(params);
	}
	
	public MyTask(Parcel in) {
		super(in);
	}
	
	public final static Parcelable.Creator<Task> CREATOR = new Parcelable.Creator<Task>() {
    	
        public MyTask createFromParcel(Parcel in) {
            return new MyTask(in);
        }
        public MyTask[] newArray(int size) {
            return new MyTask[size];
        }
    };
    
    @Override
	public void execute(TaskProgressListener progressListener, Task task) {
    	try {
    		int size = 10;
        	StringBuffer buffer = new StringBuffer();
        	for(int i = 0; i < size; i++) {
        		try {
        			Thread.sleep(100);
        			buffer.append("A");
            		progressListener.onProgressUpdate(i, task);
        		} catch(Exception e){
        			Log.e(TAG, "Thread interrupt exception was thrown");
        		}
        	}
        	String data = buffer.toString();
    		getResultBundle().putString("my_result", data);
    	} catch (Exception e) {
    		
    	}
	}
}