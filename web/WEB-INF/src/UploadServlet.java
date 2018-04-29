import javax.servlet.http.HttpServlet;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
        maxFileSize=1024*1024*10,      // 10MB
        maxRequestSize=1024*1024*50)
public class UploadServlet extends HttpServlet {

    private static final String SAVE_DIR = "uploadedFiles";

    public UploadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length()-1);
            }
        }
        return "";
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        // Get absolute path of this running web application
        String appPath = req.getServletContext().getRealPath("");
        // Create path to the directory to save uploaded file
        String savePath = appPath + File.separator + SAVE_DIR;

        System.out.println(appPath);
        System.out.println(savePath);


        // Create the save directory if it does not exist
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists())
            fileSaveDir.mkdir();
        for (Part part : req.getParts()) {
            String fileName = extractFileName(part);
            part.write(savePath + File.separator + fileName);
        }
        PrintWriter out=res.getWriter();

        out.print("<script language='javascript'>alert('File Uploaded Successfully!');window.location.href='login.jsp';</script>");
        // Add more code here to generate HTML response
        // to say that upload was completed successfully
    }

}
