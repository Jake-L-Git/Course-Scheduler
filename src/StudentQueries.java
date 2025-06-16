/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author jakelicata
 */

public class StudentQueries {
    private static Connection connection;
    private static PreparedStatement queryStudent;
    private static PreparedStatement queryStudent1;
    private static PreparedStatement addStudent;
    private static PreparedStatement dropStudent;
    private static ResultSet resultSet;
    
    public static void addStudent(StudentEntry student)
    {
        connection = DBConnection.getConnection();
        try
        {
            addStudent = connection.prepareStatement("insert into app.student (StudentID, FirstName, LastName) values (?, ?, ?)");
            addStudent.setString(1, student.getStudentID());
            addStudent.setString(2, student.getFirstName());
            addStudent.setString(3, student.getLastName());
            addStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<StudentEntry> getAllStudents()
    {
        connection = DBConnection.getConnection();
        resultSet = null;
        ArrayList<StudentEntry> students = null;
        
        try
        {
            queryStudent = connection.prepareStatement("select * from app.student order by studentID");
            resultSet = queryStudent.executeQuery();
            students = new ArrayList();
            
            while(resultSet.next())
            {
                students.add(new StudentEntry(
                        resultSet.getString("StudentID"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("LastName")
                ));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return students;
        }
    
    
    public static void dropStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        try
        {
            dropStudent = connection.prepareStatement("delete from app.student where studentID = (?)");
            dropStudent.setString(1, studentID);
            dropStudent.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
    
    
    public static StudentEntry getStudent(String studentID)
    {
        connection = DBConnection.getConnection();
        resultSet = null;
        StudentEntry student = null;
        
        try
        {
            queryStudent1 = connection.prepareStatement("select StudentID, FirstName, LastName from app.student where studentID = (?)");
            queryStudent1.setString(1, studentID);
            resultSet = queryStudent1.executeQuery();
            resultSet.next();
            
            student = new StudentEntry(
                resultSet.getString("StudentID"),
                resultSet.getString("FirstName"),
                resultSet.getString("LastName")
            );
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return student;
        
    }

    
}
