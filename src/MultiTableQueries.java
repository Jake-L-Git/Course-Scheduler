
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
public class MultiTableQueries {
    private static Connection connection;
    //private static ArrayList<ClassDescription> classDescription = new ArrayList<String>();
    //private static PreparedStatement addClass;
    //private static PreparedStatement getClassList;
    private static PreparedStatement getClassDescription;
    private static PreparedStatement getStudentEntry;
    //private static PreparedStatement getClassSeats;
    private static ResultSet resultSet;

    
    public static ArrayList<ClassDescription> getAllClassDescriptions(String semester){
        connection = DBConnection.getConnection();
        ArrayList<ClassDescription> classDescription = new ArrayList<ClassDescription>();
        try
        {
            getClassDescription = connection.prepareStatement("select cl.courseCode, co.description, cl.seats from app.class cl, app.course co where cl.semester = (?) and cl.courseCode = co.courseCode");
            getClassDescription.setString(1, semester);
            resultSet = getClassDescription.executeQuery();
            
            while(resultSet.next())
            {
                classDescription.add(new ClassDescription(
                        resultSet.getString("CourseCode"),
                        resultSet.getString("Description"),
                        resultSet.getInt("Seats")
                ));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return classDescription;
    }
    
    
    
    public static ArrayList<StudentEntry> getScheduledStudentsByClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentEntry = new ArrayList<StudentEntry>();
        try
        {
            getStudentEntry = connection.prepareStatement("select st.LASTNAME, st.FIRSTNAME, st.STUDENTID from app.schedule sc, app.student st where sc.semester = (?) and sc.courseCode = (?) and sc.status = 'scheduled' and sc.STUDENTID = st.STUDENTID");
            getStudentEntry.setString(1, semester);
            getStudentEntry.setString(2, courseCode);
            resultSet = getStudentEntry.executeQuery();
            
            while(resultSet.next())
            {
                studentEntry.add(new StudentEntry(
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
        return studentEntry;
    }
    
    
    public static ArrayList<StudentEntry> getWaitlistedStudentsByClass(String semester, String courseCode){
        connection = DBConnection.getConnection();
        ArrayList<StudentEntry> studentEntry = new ArrayList<StudentEntry>();
        try
        {
            getStudentEntry = connection.prepareStatement("select st.LASTNAME, st.FIRSTNAME, st.STUDENTID from app.schedule sc, app.student st where sc.semester = (?) and sc.courseCode = (?) and sc.status = 'waitlisted' and sc.STUDENTID = st.STUDENTID");
            getStudentEntry.setString(1, semester);
            getStudentEntry.setString(2, courseCode);
            resultSet = getStudentEntry.executeQuery();
            
            while(resultSet.next())
            {
                studentEntry.add(new StudentEntry(
                        resultSet.getString("LastName"),
                        resultSet.getString("FirstName"),
                        resultSet.getString("StudentID")
                ));
            }
        }
        catch(SQLException sqlException)
        {
            sqlException.printStackTrace();
        }
        return studentEntry;
    }
}
