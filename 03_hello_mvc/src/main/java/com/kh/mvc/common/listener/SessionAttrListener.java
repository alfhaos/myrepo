package com.kh.mvc.common.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import com.kh.mvc.member.model.vo.Member;

/**
 * Application Lifecycle Listener implementation class SessionAttrListener
 *
 *
 *	밑의 @WebListener 이거없으면 web.xml에 따로 등록해야함
 */
@WebListener
public class SessionAttrListener implements HttpSessionAttributeListener {

    /**
     * Default constructor. 
     */
    public SessionAttrListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionAttributeListener#attributeAdded(HttpSessionBindingEvent)
     */
    public void attributeAdded(HttpSessionBindingEvent se)  { 
       
    	HttpSession session = se.getSession();
    	
    	String name = se.getName();
        Object value =  se.getValue();
        System.out.println("attributeAdded : " + name + "=" + value);
        
        if(value instanceof Member) {
        	Member loginMember = (Member) value;
        	System.out.println(loginMember.getMemberName() + "님이 로그인했습니다.");
        }
    }

	/**
     * @see HttpSessionAttributeListener#attributeRemoved(HttpSessionBindingEvent)
     */
    public void attributeRemoved(HttpSessionBindingEvent se)  { 
    	String name = se.getName();
        Object value =  se.getValue();
        System.out.println("attributeRemoved : " + name + "=" + value);
        
        if(value instanceof Member) {
        	Member loginMember = (Member) value;
        	System.out.println(loginMember.getMemberName() + "님이 로그아웃했습니다.");
        }
    }

	/**
     * @see HttpSessionAttributeListener#attributeReplaced(HttpSessionBindingEvent)
     */
    public void attributeReplaced(HttpSessionBindingEvent se)  { 
         
    }
	
}
