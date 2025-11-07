package com.srk.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.srk.vo.Student;

public class StudentDAO {

    private static final String DB_URL = "jdbc:mysql://database-1.clmwg2gcc2ju.ap-southeast-1.rds.amazonaws.com:3306/studentdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String DB_USERNAME = "studentdb";
    private static final String DB_PASSWORD = "viki12345678";

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found.", e);
        }
    }

    // ✅ Insert new student
    public int saveStudent(Student student) {
        String sql = "INSERT INTO students (name, address, age, qualification, percentage, year_passed) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getAddress());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getQualification());
            ps.setDouble(5, student.getPercentage());
            ps.setString(6, student.getYearPassed());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ✅ Get all students
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("id"));
                s.setName(rs.getString("name"));
                s.setAddress(rs.getString("address"));
                s.setAge(rs.getInt("age"));
                s.setQualification(rs.getString("qualification"));
                s.setPercentage(rs.getDouble("percentage"));
                s.setYearPassed(rs.getString("year_passed"));
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // ✅ Delete student
    public int deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // ✅ Update student
    public int updateStudent(Student student) {
        String sql = "UPDATE students SET name=?, address=?, age=?, qualification=?, percentage=?, year_passed=? WHERE id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, student.getName());
            ps.setString(2, student.getAddress());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getQualification());
            ps.setDouble(5, student.getPercentage());
            ps.setString(6, student.getYearPassed());
            ps.setInt(7, student.getId());

            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
