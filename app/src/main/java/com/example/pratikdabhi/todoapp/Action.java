package com.example.pratikdabhi.todoapp;

public class Action {
	private long _id;
	private String action;
	
	public Action(long id, String action){
		this._id = id;
		this.action = action;
	}
	public void setId(long _id){
		this._id = _id;
	}
	public void setAction (String action){
		this.action = action;
	}
	
	public long getId(){
		return _id;
	}
	public String getAction() {
		return action;
	}
	
	public String toString() {
		return action;
	}
}
