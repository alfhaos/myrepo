package com.kh.mvc.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

import com.kh.mvc.board.model.vo.Attachment;
import com.oreilly.servlet.MultipartRequest;

public class MvcUtils {
	
	/**
	 * 비밀번호 암호화 처리 메소드
	 * 
	 * 1. 암호화 처리 MessageDigest
	 * 
	 * 2. 암호화된 이진배열을 문자열로 인코딩 처리 Base64Encoder
	 * 
	 * 
	 */

	public static String getEncryptedPassword(String password) {
		
		// 1. 암호화처리
		byte[] encrypted = null;
		
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			byte[] plain = password.getBytes("utf-8");
			md.update(plain); // md객체에 원본문자열 설정
			encrypted = md.digest(); // 암호화
			
			
		} 
		
		catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			
			
			e.printStackTrace();
		}
		System.out.println(encrypted);
		
		
		// 2. 인코딩 처리
		// 영문자 , 숫자 , +,/ 64개의 문자를 사용(=패딩문자도 사용)
		Encoder encoder = Base64.getEncoder();
		String encryptedPassword = encoder.encodeToString(encrypted);
		System.out.println(encryptedPassword);
		
		
		
		
		return encryptedPassword;
	}

	/**
	 * 
	 * @param cPage
	 * @param numPerPage
	 * @param totalContent
	 * @param url
	 * 
	 * totalPage 전체페이지
	 * pagebarSize 페이지바 크기 5
	 * pageNo 
	 * pageStart - pageEnd
	 * 
	 * 
	 * @return
	 */
	public static String getPagebar(int cPage, int numPerPage, int totalContent, String url) {
		
		StringBuilder pagebar = new StringBuilder();
		url = url + "?cPage="; // pageNo 추가전 상태
		
		final int pagebarSize = 5;
		final int totalPage = (int) Math.ceil((double) totalContent / numPerPage);
		
		final int pageStart = (cPage -1) / pagebarSize * pagebarSize + 1;
		int pageEnd = pageStart + pagebarSize - 1;
		pageEnd = totalPage < pageEnd ? totalPage : pageEnd;
		int pageNo = pageStart;
		
		//[이전]
		if(pageNo == 1) {
			//cPage = 1,2,3,4,5
			
		}
		else {
			pagebar.append("<a href='" + url + (pageNo - 1) + "'>prev</a>\n");
			
		}
		
		
		// pageNo
		while(pageNo <= pageEnd) {
			if(pageNo == cPage) {
				pagebar.append("<span class = 'cPage'>" + cPage + "</span>\n");
				
			}
			else {
				pagebar.append("<a href = '" + url + pageNo + "'>" + pageNo + "</a>\n");
				
			}
			pageNo++;
		}
		
		// [다음]
		if(pageNo > totalPage) {
			
		}
		else {
			pagebar.append("<a href='" + url + pageNo + "'>next</a>\n");
		}
		
		return pagebar.toString();
	}

	public static Attachment makeAttachment(MultipartRequest multipartRequest, String name) {
		Attachment attach = new Attachment();
		String originalFilemane = multipartRequest.getOriginalFileName(name);
		String renamedFilename = multipartRequest.getFilesystemName(name);
		attach.setOriginalFilename(originalFilemane);
		attach.setRenamedFilename(renamedFilename);
		
		return attach;
	}
	
	
	


}
