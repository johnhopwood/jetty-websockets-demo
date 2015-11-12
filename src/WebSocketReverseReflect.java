import java.util.logging.Logger;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;


@WebSocket
public class WebSocketReverseReflect{

	private static final Logger _logger = Logger.getLogger(WebSocketReverseReflect.class.getName());
	
	public final static String _protocol = "reverse";
	public final static Long _idleTimeout = 10000L;	
	private Session _session = null;

	@OnWebSocketConnect
    public void onOpen(Session session){
		
		_logger.info("remoteAddress=" + session.getRemoteAddress().getHostString());
		
		_session=session;
		_session.setIdleTimeout(_idleTimeout);
    }
    
	@OnWebSocketMessage
    public void onMessage(String msg){
		
		_logger.info("msg=" + msg);
		
		send(new StringBuffer(msg).reverse().toString());
    }

    @OnWebSocketClose
    public void onClose(int code, String message){
    	
    	_logger.info("code=" + code + " message=" + message);
    }

    //close() will also invoke onClose above
    public void close(){
    	_session.close();
    }
    
    public void send(String msg){
    	if(_session != null && _session.isOpen())
    		_session.getRemote().sendStringByFuture(msg);
    }
}
