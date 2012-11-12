package BoggleGame;

public class CountDown {
	DisplayOptions menu;
	Boggle home;
	
	public CountDown(DisplayOptions m, Boggle h){
		menu = m;
		home = h;
		timerThread();
	}
	
	public void timerThread() {
	    try {
	      Runnable r=new Runnable() {
	    	  public void run() {
	    		  startTimer();
	    	  }
	      	};
	      	
	      	Thread Timer=new Thread(r,"Timer Thread");
	      	Timer.start();
	    } 
	    catch(Exception exc) { System.out.println(exc); }
	  }

	  public void startTimer() {
		  int threemin = 3 * 60;
		  try {
			  Thread.sleep(3000);
			  home.display.play = true;
			  for(int i=threemin;i>=0;i--){
				  //menu.timer.setText(""+i);
				  String s;
				  int min = i/60;
				  int sec = i%60;
				  if(sec < 10){
					  s = "0"+sec;
				  }
				  else{
					  s = ""+sec;
				  }
				  //System.out.println(min+":"+s);
				  menu.timer.setText(min+":"+s);
				  menu.repaint();
				  Thread.sleep(1000);
			  }
			  home.display.play = false;
		  } 
		  catch(Exception exc) { System.out.println(exc); }
	  }
}
