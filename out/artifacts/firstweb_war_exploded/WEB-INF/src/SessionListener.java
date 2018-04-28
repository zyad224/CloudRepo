import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent arg0) {
        System.out.println("sessionCreated - add one session into counter");
        System.out.println("session ID: " + arg0.getSession().getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent arg0) {
        System.out.println("sessionDestroyed - deduct one session from counter");
        System.out.println("session ID: " + arg0.getSession().getId());
        SessionTableUtil.deleteUserInSessionTable(arg0.getSession().getId().toString());
    }
}
