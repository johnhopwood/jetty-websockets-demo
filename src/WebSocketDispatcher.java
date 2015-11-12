import java.util.logging.Logger;

import org.eclipse.jetty.websocket.servlet.ServletUpgradeRequest;
import org.eclipse.jetty.websocket.servlet.ServletUpgradeResponse;
import org.eclipse.jetty.websocket.servlet.WebSocketCreator;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

@SuppressWarnings("serial")
public class WebSocketDispatcher extends WebSocketServlet implements WebSocketCreator {

	private static final Logger _logger = Logger.getLogger(WebSocketDispatcher.class.getName());
	
	@Override
	public void configure(WebSocketServletFactory factory) {
        factory.setCreator(this);
	}
	
	@Override
	public Object createWebSocket(ServletUpgradeRequest req, ServletUpgradeResponse resp) {
   
		//Check ServletUpgradeRequest here, return null to terminate connection
		
		if (req.hasSubProtocol(WebSocketReflect._protocol)){
			
			resp.setAcceptedSubProtocol(WebSocketReflect._protocol);
			return new WebSocketReflect();
		}
		
		if (req.hasSubProtocol(WebSocketReverseReflect._protocol)){
			
			resp.setAcceptedSubProtocol(WebSocketReverseReflect._protocol);
			return new WebSocketReverseReflect();
		}

		if (req.hasSubProtocol(WebSocketClock._protocol)){
			
			resp.setAcceptedSubProtocol(WebSocketClock._protocol);
			return new WebSocketClock();
		}
		
		_logger.info("Unrecognized protocol");
		
		return null;
	}
}
