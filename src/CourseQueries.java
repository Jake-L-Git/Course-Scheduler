
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jakelicata
 */

public class CourseQueries {
    private static Connection connection;
    //private static ArrayList<String> faculty = new ArrayList<String>();
    private static PreparedStatement addCourse;
    private static PreparedStatement getCourseList;
    private static PreparedStatement getCourse;
    private static ResultSet resultSet;
    
    
    public static void addCourse(CourseEntry course){
        connection = DBConnection.getConnection();
        try
        {
            addCourse = connection.prepareStatement("insert into app.course (CourseCode, Description) values (?, ?)");
            addCourse.setString(1, course.getCourseCode());
            addCourse.setString(2, course.getDescription());
            addCourse.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    
    public static ArrayList<String> getAllCourseCodes(){
        connection = DBConnection.getConnection();
        ArrayList<String> courses = new ArrayList();
        try
        {
            getCourseList = connection.prepareStatement("select coursecode from app.course order by coursecode");
            resultSet = getCourseList.executeQuery();
            
            while(resultSet.next())
            {
                courses.add(resultSet.getString("CourseCode"));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return courses;
    }
    
    
    public static CourseEntry getCourse(String courseCode)
    {
        connection = DBConnection.getConnection();
        resultSet = null;
        CourseEntry course1 = null;
        
        try
        {
            getCourse = connection.prepareStatement("select CourseCode, Description from app.course where courseCode = (?)");
            getCourse.setString(1, courseCode);
            resultSet = getCourse.executeQuery();
            resultSet.next();
            
            course1 = new CourseEntry(
                resultSet.getString("CourseCode"),
                resultSet.getString("Description")
            );
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        
        return course1;
        
    }
}

