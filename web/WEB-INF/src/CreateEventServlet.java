

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Collection;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 * Servlet implementation class createEvent
 */
@WebServlet("/CreateEventServlet")
public class CreateEventServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String String = null;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateEventServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        createDynamicPage(request,response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String eventName = request.getParameter("Title");
        String eventPlace=request.getParameter("Place");
        String eventDate=request.getParameter("Date");
        String eventPrice= request.getParameter("Price");

        addEvent2XML(eventName,eventPlace,eventDate,eventPrice);
        doGet(request, response);
    }

    protected void createDynamicPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        String path = "/EventManagement.jsp";
        PrintWriter output = response.getWriter();
        output.println("<!DOCTYPE html>\n" + "<html>\n" + "<body>");
        output.println("<h2>You create an Event !!</h2>");
        output.println("<p>This is a dynamic web page </br> " + "<a href="+path+">Click here to back</a></p>");
        output.println("</body>" + "</html>\n");
    }


    private void addEvent2XML(String eventName, String eventPlace, String eventDate, String eventPrice) {

        String xmlPath  = "C:\\Users\\pegas\\IdeaProjects\\firstweb\\web\\Events.xml";
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        Document document = null;
        Element root = null;

        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.parse(xmlPath);
            root = document.getDocumentElement();

            Collection<Event> events = new ArrayList<>();
            events.add(new Event());

            for (Event event : events) {
                // event elements
                Element newEvent = document.createElement("event");

                Element title = document.createElement("title");
                title.appendChild(document.createTextNode(eventName));
                newEvent.appendChild(title);

                Element place = document.createElement("place");
                place.appendChild(document.createTextNode(eventPlace));
                newEvent.appendChild(place);

                Element date = document.createElement("date");
                date.appendChild(document.createTextNode(eventDate));
                newEvent.appendChild(date);

                Element ePrice = document.createElement("price");
                ePrice.appendChild(document.createTextNode(eventPrice));
                newEvent.appendChild(ePrice);

                root.appendChild(newEvent);
            }

            DOMSource source = new DOMSource(document);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            StreamResult result = new StreamResult(xmlPath);
            transformer.transform(source, result);

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}

class Event{
    String title;
    String place;
    String date;
    String price;

    public String getTitle() { return title; }
    public String getPlace() { return place; }
    public String getDate() { return date; }
    public String getPrice() { return price; }

}