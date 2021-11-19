package com.kh.ajax.movie.model.vo;

public class Movie {

	private String movieNm;
	private int rank;
	private int audiAcc;
	public Movie() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Movie(String movieNm, int rank, int audiAcc) {
		super();
		this.movieNm = movieNm;
		this.rank = rank;
		this.audiAcc = audiAcc;
	}
	public String getMovieNm() {
		return movieNm;
	}
	public void setMovieNm(String movieNm) {
		this.movieNm = movieNm;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public int getAudiAcc() {
		return audiAcc;
	}
	public void setAudiAcc(int audiAcc) {
		this.audiAcc = audiAcc;
	}
	
	
	
	
}
