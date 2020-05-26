package io.klineapps.fya.model;//
//	Sport.java
//	Model file generated using JSONExport: https://github.com/Ahmed-Ali/JSONExport

import com.google.gson.annotations.SerializedName;


public class Sport{

	@SerializedName("address")
	private String address;
	@SerializedName("dsSport")
	private String dsSport;
	@SerializedName("idSport")
	private int idSport;
	@SerializedName("image")
	private String image;
	@SerializedName("latitude")
	private String latitude;
	@SerializedName("longitude")
	private String longitude;
	@SerializedName("price")
	private String price;

	public void setAddress(String address){
		this.address = address;
	}
	public String getAddress(){
		return this.address;
	}
	public void setDsSport(String dsSport){
		this.dsSport = dsSport;
	}
	public String getDsSport(){
		return this.dsSport;
	}
	public void setIdSport(int idSport){
		this.idSport = idSport;
	}
	public int getIdSport(){
		return this.idSport;
	}
	public void setImage(String image){
		this.image = image;
	}
	public String getImage(){
		return this.image;
	}
	public void setLatitude(String latitude){
		this.latitude = latitude;
	}
	public String getLatitude(){
		return this.latitude;
	}
	public void setLongitude(String longitude){
		this.longitude = longitude;
	}
	public String getLongitude(){
		return this.longitude;
	}
	public void setPrice(String price){
		this.price = price;
	}
	public String getPrice(){
		return this.price;
	}

}