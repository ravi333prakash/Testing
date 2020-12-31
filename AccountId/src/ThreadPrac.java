import java.time.LocalDate;
import java.time.LocalDateTime;

public class ThreadPrac {

	static boolean  reqd;
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		
		/*Thread th = new Thread();
		th.setName("mrng");
		System.out.println("main th ");
		
		
	th.checkAccess();*/
	
	//if(!reqd)
	//	System.out.println("shgad");
		//sop
		//kad
		int count =1;
		System.out.println("before "+LocalDateTime.now().toString().substring(11));
		System.out.println("-------");
	while(count>0 && getCount()>0) {
		//Thread.sleep(30);	
		count --;
		System.out.println("count "+count+" |"+LocalDateTime.now().toString().substring(11));
	}
	System.out.println("-------");
	System.out.println("BYEEE "+LocalDateTime.now().toString().substring(11));
	}
	public static int getCount() throws InterruptedException {
		Thread.sleep(3000);
		return 1;
	}

}
