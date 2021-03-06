package util;

import java.io.IOException;
import java.util.Iterator;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class crawling_eng {
//크롤러
	public static void main(String[] args) {

		// Jsoup를 이용해서 https://endic.naver.com/?sLn=kr 크롤링

		String url = "https://endic.naver.com/?sLn=kr"; // 크롤링할 url지정
		Document doc = null; // Document에는 페이지의 전체 소스가 저장된다

		try {

			doc = Jsoup.connect(url).get(); //웹 페이지의 GET데이터 정보 가져오기

		} catch (IOException e) {

			e.printStackTrace();

		}

		// select를 이용하여 원하는 태그를 선택한다. select는 원하는 값을 가져오기 위한 중요한 기능이다.
		// ==>원하는 값들이 들어있는 덩어리를 가져온다
		Elements element = doc.select("ul.component_today_word");
		
		System.out.println(element);
		System.out.println("============================================================");

		// Iterator을 사용하여 하나씩 값 가져오기
		// 덩어리안에서 필요한부분만 선택하여 가져올 수 있다.
		Iterator<Element> ie1 = element.select("a.word_link").iterator(); //영단어
 
		Iterator<Element> ie2 = element.select("div.txt_trans").iterator(); //뜻

		while (ie1.hasNext()) {

			System.out.println(ie1.next().text() + "\t" +ie2.next().text());

		}

		System.out.println("============================================================");

	}
}
