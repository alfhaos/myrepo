package com.kh.mybatis.design.pattern.strategy;


/**
 * Design Pattern
 * - 문재해결을 위한 코드구조를 정리한것.
 * 
 * 전략패턴
 * - GoF의 디자인 패턴 중 행위패턴의 한가지
 * 
 * context가 전략(변경가능성이 있는 코드)을 사용함에 있어 interface규격을 통해 제어하는 것
 * - context : strategy를 사용하는 쪽
 * - interface : strate의 규격
 * - strategy(interface 구현클래스)
 * 
 * 
 * @author gksqu
 *
 */
public class Main {

	public static void main(String[] args) {
		
		Controller controller = new Controller(new KService());
		controller.test();
		
	}

}
