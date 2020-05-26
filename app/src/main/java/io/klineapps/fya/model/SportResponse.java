package io.klineapps.fya.model;//
//	SportResponse.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import com.google.gson.annotations.SerializedName;


public class SportResponse{

	private static SportResponse instance;

	@SerializedName("sports")
	private Sport[] sports;

	public void setSports(Sport[] sports){
		this.sports = sports;
	}
	public Sport[] getSports(){
		return this.sports;
	}

	public static SportResponse getInstance(){
		if (instance == null){
			instance = new SportResponse();
		}

		return instance;
	}

}