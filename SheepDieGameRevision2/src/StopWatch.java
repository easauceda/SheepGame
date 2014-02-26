import java.util.Date;

public class StopWatch {
	private Date startTime;

	public void startTiming() {
		startTime = new Date();
	}

	public double stopTiming() {
		Date stopTime = new Date();
		double timediff = (stopTime.getTime() - startTime.getTime());
		//long remainder = timediff%1000;
		timediff = timediff*(.001);
		return timediff;
	}
	
	public long getCurrentTime()
	{
		if (startTime != null){
		Date currentTime = new Date();
		long timediff = (currentTime.getTime() - startTime.getTime());
		
		return timediff;
		}
		else{
			return 0;
		}
	}

	public static void main(String args[]) {
		StopWatch stopwatch = new StopWatch();
		final int Min = 2;
		final int Max = 4;

		stopwatch.startTiming();

		long total = 0;
		for (int i = 0; i < 10; i++) {
			try {
				long rnd = Min + (int) (Math.random() * ((Max - Min) + 1));
				long pause = rnd * 100;
				total += pause;
				System.out.println("pausing: " + pause);
				Thread.sleep(pause);
			} catch (Exception ex) {

			}
		}

		double interval = stopwatch.stopTiming();

		System.out.println("time: " + interval);
		System.out.println("total: " + total);
		System.out.println("overhead: " + (interval - total));
	}
}
