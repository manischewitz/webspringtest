package servlets;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
public class MainPageServlet extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, Object> pageVars = createPageVariablesMap(request);
		
		pageVars.put("Message", "");

		
		response.getWriter().println(PageLoader.instance().getPage("index.html", pageVars));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
		
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Map<String, Object> pageVars = createPageVariablesMap(request);

        String message = request.getParameter("Message");

        response.setContentType("text/html;charset=utf-8");

        if (message == null || message.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        } else {
            response.setStatus(HttpServletResponse.SC_OK);
        }
        pageVars.put("Message", message == null ? "Hallo" : message);

        response.getWriter().println(PageLoader.instance().getPage("index.html", pageVars));
		
	}
	
	private static Map<String, Object> createPageVariablesMap(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("Method", request.getMethod());
		map.put("URL", request.getRequestURL().toString());
		map.put("sessionID", request.getSession().getId());
		map.put("parameters", request.getParameterMap().toString());
		return map;
	}
}
