package tom.bennybox;

public class MainComponent {

	public static final int width = 800;
	public static final int height = 600;
	public static final String title = "3DEngine";
	public static final double FRAME_CAP = 5000.0;
	
	private boolean isRunning;
	private Game game;
	
	public MainComponent() {

		RenderUtil.initGraphics();
		isRunning = false;
		game = new Game();
	}
	
	public void start(){
		if(isRunning)
			return;
		
		run();
	}
	
	public void stop(){
		if(!isRunning)
			return;
		
		isRunning = false;
			
	}
	
	private void run(){
		
		isRunning = true;
		
		//both for fps count
		int frames = 0;
		long frameCounter = 0;
		
		
		long lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		while(isRunning){
			
			boolean render = false;
			
			final double frameTime = 1.0 / FRAME_CAP;
			
			long startTime = Time.getTime();
			long passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime / (double) Time.SECOND;
			frameCounter += passedTime;
			
			
			while(unprocessedTime > frameTime){
				//when there is need to update set render to true
				render = true;
				
				unprocessedTime -=frameTime;
				
				if(Window.isCloseRequested())
					stop();
				
				Time.setDelta(frameTime);
				
				Input.update();
				game.input();
				game.update();
				
				
				if(frameCounter >= Time.SECOND){
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
				
			}
			if(render){
				render();
				frames++;
			}else{
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			
		}
		
		cleanUp();
		
	}
	
	private void render(){
		RenderUtil.clearScreen();
		game.render();
		Window.render();
	}
	
	private void cleanUp(){
		Window.dispose();
	}
	
	public static void main(String args[]){
		Window.createWindow(width,height,title);
		
		MainComponent game = new MainComponent();
		game.start();
		
	}
}
