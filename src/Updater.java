import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.application.Platform;

public class Updater {

	ScheduledExecutorService executor;
	Runnable loop;

	public Updater() {
		executor = Executors.newScheduledThreadPool(1, runnable -> {
			Thread t = new Thread(runnable);
			t.setDaemon(true);
			return t;
		});
		loop = new Runnable() {
			public void run() {
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						MainApp.logic();
					}
				});
			}
		};
	}

	public void startService(long rate) {
		executor.scheduleAtFixedRate(loop, 0, rate, TimeUnit.NANOSECONDS);

	}

	public void changeServiceRate(long rate){
		executor.shutdown();
		startService(rate);
	}
}