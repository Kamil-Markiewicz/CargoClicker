import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application{
	//Main variables
	private static long cents;
	private static long time;
	private static Gui gui;
	private static Updater loop;
	private static long rate;
	private static boolean sync = false;
	
	//Workers
	private static int worker;
	private static long baseWorker;
	private static long valWorker;
	private static long timeWorker;
	
	//Bags
	private static int bag;
	private static long baseBag;
	private static long valBag;
	private static long timeBag;
	
	public static void main(String[] args){
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		gui = new Gui(primaryStage);
		loop = new Updater();
		initialise();
		loop.startService(100000000);
	}
	
	public static void initialise(){
		worker = 1;
		baseWorker = 4000000000L;
		timeWorker = baseWorker;
		valWorker = 10;
		
		bag = 1;
		baseBag = 8000000000L;
		timeBag = baseBag;
		valBag = 20;
		
		cents = 0;
		rate = 16666666;
		time = System.nanoTime();
	}

	public static void logic(){
		sync = true;
		long lastTime = time;
		time = System.nanoTime();
		long latency = (time - lastTime - rate);
		
		timeWorker = addMonies(latency, timeWorker, baseWorker, valWorker, worker);
		timeBag = addMonies(latency, timeBag, baseBag, valBag, bag);
		
		String moneys = getMonies();
		gui.setMonies(moneys + "\n" + worker + ", " + valWorker + ", " + baseWorker);
		long spent = (System.nanoTime() - time);
		System.out.println(spent + ", " + latency + ": " + cents);
		sync = false;
	}
	
	public static long addMonies(long latency, long timer, long base, long val, long amount){
		timer = timer - (rate + latency);
		if(timer <= 0){
			timer += base;
			if(timer <= 0){
				double overflow = 0;
				overflow = (timer*-1)/base;
				overflow = Math.ceil(overflow);
				cents += (val*amount)*overflow;
			}
			cents += (val*amount);
		}
		return timer;
	}
	
	public static String getMonies(){
		double last = 0;
		String amount = "Monies";
		long moneys = cents;
		if(cents >= 100000){
			moneys = moneys/100;
			if(moneys >= 1000000000000000L){
				moneys = moneys/1000000000000L;
				last = (double)moneys/1000;
				amount = "Quadrillion Monies";
			}
			else if(moneys >= 1000000000000L){
				moneys = moneys/1000000000;
				last = (double)moneys/1000;
				amount = "Tillion Monies";
			}
			else if(moneys >= 1000000000){
				moneys = moneys/1000000;
				last = (double)moneys/1000;
				amount = "Billion Monies";
			}
			else if(moneys >= 1000000){
				moneys = moneys/1000;
				last = (double)moneys/1000;
				amount = "Million Monies";
			}
			else if(moneys >= 1000){
				last = (double)moneys/1000;
				amount = "Thousand Monies";
			}
		}
		else
			last = (double)moneys/100;
		String str = last + " " + amount;
		return str;
	}
	
	public static void sync(){
		while(sync){}
		sync = true;
	}
	
	public static void addWorker(){
		sync();
		worker++;
		sync = false;
	}
	
	public static void speedWorker(){
		sync();
		baseWorker = baseWorker/2;
		timeWorker = timeWorker/2;
		sync = false;
	}
	
	public static void capWorker(){
		sync();
		valWorker = (int)(valWorker*1.2);
		sync = false;
	}
}