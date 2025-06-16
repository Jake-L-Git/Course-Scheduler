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

public class ScheduleQueries {
    private static Connection connection;
    private static PreparedStatement addEntry;
    private static PreparedStatement querySchedule;
    private static PreparedStatement dropCourse;
    private static PreparedStatement updateCourse;
    private static ResultSet resultSet;
    
    public static void addScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        try
        {
            addEntry = connection.prepareStatement("insert into app.schedule (Semester, CourseCode, StudentID, Status, Timestamp) values (?, ?, ?, ?, ?)");
            addEntry.setString(1, entry.getSemester());
            addEntry.setString(2, entry.getCourseCode());
            addEntry.setString(3, entry.getStudentID());
            addEntry.setString(4, entry.getStatus());
            addEntry.setTimestamp(5, entry.getTimestamp());
            addEntry.executeUpdate();
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
    }
    
    public static ArrayList<ScheduleEntry> getScheduleByStudent(String semester, String studentID)
    {
        connection = DBConnection.getConnection();
        resultSet = null;
        ArrayList<ScheduleEntry> schedule = null;
        try
        {
            querySchedule = connection.prepareStatement("select * from app.schedule where semester = (?) and studentID = (?) order by status, courseCode");
            querySchedule.setString(1, semester);
            querySchedule.setString(2, studentID);
            resultSet = querySchedule.executeQuery();
            schedule = new ArrayList();
            
            while(resultSet.next())
            {
                schedule.add(new ScheduleEntry(
                        resultSet.getString("Semester"),
                        resultSet.getString("CourseCode"),
                        resultSet.getString("StudentID"),
                        resultSet.getString("Status"),
                        resultSet.getTimestamp("Timestamp")
                ));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
       
        return schedule;
    }
    
    public static int getScheduledStudentCount(String currentSemester, String courseCode)
    {
        connection = DBConnection.getConnection();
        resultSet = null;
        int count = 0;
        try
        {
            querySchedule = connection.prepareStatement("select count(studentID) from app.schedule where semester = ? and courseCode = ?");
            querySchedule.setString(1, currentSemester);
            querySchedule.setString(2, courseCode);
            //querySchedule.setString(3, "Scheduled");
            resultSet = querySchedule.executeQuery();
            
            while(resultSet.next())
            {
                count = resultSet.getInt(1);
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
       
        return count;
    }
    
    public static void dropStudentScheduleByCourse(String semester, String studentID, String courseCode)
    {
        connection = DBConnection.getConnection();
        //resultSet = null;
        try
        {
            dropCourse = connection.prepareStatement("delete from app.schedule where semester = (?) and studentID = (?) and courseCode = (?)");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, studentID);
            dropCourse.setString(3, courseCode);
            dropCourse.executeUpdate();
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
       
    }
    
    
    public static ArrayList<ScheduleEntry> getWaitlistedStudentsByClass(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        resultSet = null;
        ArrayList<ScheduleEntry> scheduleEntries = null;
        
        try
        {
            querySchedule = connection.prepareStatement("select * from app.schedule where semester = (?) and courseCode = (?) and status = 'waitlisted' order by timestamp ");
            querySchedule.setString(1, semester);
            querySchedule.setString(2, courseCode);
            resultSet = querySchedule.executeQuery();
            scheduleEntries = new ArrayList();
            
            while(resultSet.next())
            {
                scheduleEntries.add(new ScheduleEntry(
                        resultSet.getString("Semester"),
                        resultSet.getString("CourseCode"),
                        resultSet.getString("StudentID"),
                        resultSet.getString("Status"),
                        resultSet.getTimestamp("Timestamp")
                ));
            }
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
       
        return scheduleEntries;
       
    }
    
    
    
    public static void updateScheduleEntry(ScheduleEntry entry)
    {
        connection = DBConnection.getConnection();
        String studentID = entry.getStudentID();
        //resultSet = null;
        try
        {
            updateCourse = connection.prepareStatement("update app.schedule set status = 'scheduled' where studentid = (?)");
            updateCourse.setString(1, studentID);
            updateCourse.executeUpdate();
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
       
    }
    
    
    /*
    public static void dropScheduleByCourse(String semester, String courseCode)
    {
        connection = DBConnection.getConnection();
        //resultSet = null;
        try
        {
            dropCourse = connection.prepareStatement("delete from app.schedule where semester = (?) and studentID = (?) and courseCode = (?)");
            dropCourse.setString(1, semester);
            dropCourse.setString(2, studentID);
            dropCourse.setString(3, courseCode);
            dropCourse.executeUpdate();
            
            
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
       
    }
    */

}
