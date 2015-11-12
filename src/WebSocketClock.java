import java.util.logging.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;


@WebSocket
public class WebSocketClock{

	private static final Logger _logger = Logger.getLogger(WebSocketClock.class.getName());
	
	public final static String _protocol = "clock"; 
	public final static Long _idleTimeout = 10000L;
	private volatile boolean _running = true;
	private Session _session = null;
	private Thread _webSocketClockWorker = new Thread(new WebSocketClockWorker(), WebSocketClockWorker.class.getName());


	@OnWebSocketConnect
    public void onOpen(Session session){
		
		_logger.info("remoteAddress=" + session.getRemoteAddress().getHostString());
		
		_session=session;
		_session.setIdleTimeout(_idleTimeout);
		_webSocketClockWorker.start();
    }
    
	@OnWebSocketMessage
    public void onMessage(String msg){
		
		_logger.info("msg=" + msg);
    }

    @OnWebSocketClose
    public void onClose(int code, String message){
    	
    	_logger.info("code=" + code + " message=" + message);
    	_running=false;
    }

    //close() will also invoke onClose above
    public void close(){
    	_session.close();
    }
    
    public void send(String msg){
    	if(_session != null && _session.isOpen())
    		_session.getRemote().sendStringByFuture(msg);
    }
    
	private class WebSocketClockWorker implements Runnable
	{
		
		@Override
		public void run() {
					
			while(_running){
				
				//send system second value 1-60
				send(Long.toString((System.currentTimeMillis()/1000L % 60)+1));
				
				try {
					Thread.sleep(1000L);
				} catch (InterruptedException e) {
					_logger.warning("InterruptedException while sleeping");
				}
			}
			
			_logger.info("exiting");
		}
	}

}
