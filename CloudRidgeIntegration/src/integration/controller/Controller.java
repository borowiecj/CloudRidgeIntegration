package integration.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import integration.classes.FaceBikeSQL;
import integration.classes.FaceBike;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try
		{
			FaceBikeSQL crSQL = new FaceBikeSQL();
			
			ArrayList<FaceBike> list = crSQL.ReturnTopTen();
			
			//ArrayList<FaceBike> list = crSQL.FindByName("ab");
			
			//ArrayList<FaceBike> list = crSQL.FindByDepartment("HR");
			
			//ArrayList<FaceBike> list = crSQL.FindByEmail("Moshe_Gorczany@verna.org");
			
			//ArrayList<FaceBike> list = crSQL.FindByID(100);
			
			//Test Update
			crSQL.UpdateName("Bob Jenkins", 0);
			crSQL.UpdateDepartment("HR2", 0);
		
			request.setAttribute("list", list);
			request.getRequestDispatcher("/WEB-INF/ViewFaceBike.jsp").forward(request, response);
			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
