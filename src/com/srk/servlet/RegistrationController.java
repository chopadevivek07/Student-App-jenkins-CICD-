package com.srk.servlet;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.srk.dao.StudentDAO;
import com.srk.vo.Student;

@WebServlet("/register")
public class RegistrationController extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String address = request.getParameter("address");
        int age = Integer.parseInt(request.getParameter("age"));
        String qualification = request.getParameter("qualification");
        double percentage = Double.parseDouble(request.getParameter("percentage"));
        String yearPassed = request.getParameter("year_passed");

        Student student = new Student();
        student.setName(name);
        student.setAddress(address);
        student.setAge(age);
        student.setQualification(qualification);
        student.setPercentage(percentage);
        student.setYearPassed(yearPassed);

        StudentDAO dao = new StudentDAO();
        int status = dao.saveStudent(student);

        if (status > 0) {
            response.sendRedirect("success.jsp");
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}
