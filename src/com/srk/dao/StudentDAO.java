package com.srk.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.Student;

/*
 * Updated StudentDAO.java
 * - Uses placeholders for DB URL/username/password so you can paste your RDS details.
 * - Uses try-with-resources to safely close JDBC resources.
 * - Provides methods: saveStudent, getAllStudents, deleteStudent, getStudentById, updateStudent
 *
 * IMPORTANT: Replace the DB_URL, DB_USERNAME and DB_PASSWORD placeholders with your RDS info
 * Example:
 *   private static final String DB_URL = "jdbc:mysql://my-rds-endpoint.rds.amazonaws.com:3306/studentdb";
 *   private static final String DB_USERNAME = "myuser";
 *   private static final String DB_PASSWORD = "mypassword";
 */

public class StudentDAO {

    // PLACEHOLDERS - replace these with your RDS / local MySQL values
    private static final String DB_URL = "jdbc:mysql://database-1.clmwg2gcc2ju.ap-southeast-1.rds.amazonaws.com:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USERNAME = "studentdb";
    private static final String DB_PASSWORD = "viki12345678";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";

    // SQL statements
    private static final String INSERT_SQL = "INSERT INTO students (student_name, student_address, student_age, student_qualification, student_percentage, year_passed) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SELECT_ALL_SQL = "SELECT * FROM students ORDER BY id DESC";
    private static final String DELETE_SQL = "DELETE FROM students WHERE id = ?";
    private static final String SELECT_BY_ID_SQL = "SELECT * FROM students WHERE id = ?";
    private static final String UPDATE_SQL = "UPDATE students SET student_name=?, student_address=?, student_age=?, student_qualification=?, student_percentage=?, year_passed=? WHERE id=?";

    // Load JDBC driver once when class loads
    static {
        try {
            Class.forName(DB_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Include mysql-connector in your classpath.");
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    }

    public boolean saveStudent(Student student) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(INSERT_SQL)) {
            ps.setString(1, student.getStudentName());
            ps.setString(2, student.getStudentAddress());
            ps.setInt(3, student.getStudentAge());
            ps.setString(4, student.getStudentQualification());
            ps.setFloat(5, student.getStudentPercentage());
            ps.setInt(6, student.getYearPassed());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_ALL_SQL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setStudentName(rs.getString("student_name"));
                s.setStudentAddress(rs.getString("student_address"));
                s.setStudentAge(rs.getInt("student_age"));
                s.setStudentQualification(rs.getString("student_qualification"));
                s.setStudentPercentage(rs.getFloat("student_percentage"));
                s.setYearPassed(rs.getInt("year_passed"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean deleteStudent(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(DELETE_SQL)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Student getStudentById(int id) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(SELECT_BY_ID_SQL)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Student s = new Student();
                    s.setId(rs.getInt("id"));
                    s.setStudentName(rs.getString("student_name"));
                    s.setStudentAddress(rs.getString("student_address"));
                    s.setStudentAge(rs.getInt("student_age"));
                    s.setStudentQualification(rs.getString("student_qualification"));
                    s.setStudentPercentage(rs.getFloat("student_percentage"));
                    s.setYearPassed(rs.getInt("year_passed"));
                    return s;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateStudent(Student student) {
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(UPDATE_SQL)) {
            ps.setString(1, student.getStudentName());
            ps.setString(2, student.getStudentAddress());
            ps.setInt(3, student.getStudentAge());
            ps.setString(4, student.getStudentQualification());
            ps.setFloat(5, student.getStudentPercentage());
            ps.setInt(6, student.getYearPassed());
            ps.setInt(7, student.getId());
            int rows = ps.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
