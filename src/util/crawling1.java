package util;

import java.io.IOException;
 
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
 
public class crawling1 {
    public static void main(String args[]){
        try {
            // 1. 수집 대상 URL
            String URL = "https://heodolf.tistory.com/18";
            
            // 2. Connection 생성
            Connection conn = Jsoup.connect(URL);
 
            // 3. HTML 파싱.
            Document html = conn.get(); // conn.post();
            
            // 4. HTML 출력
            System.out.println( html.toString() ); 
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
