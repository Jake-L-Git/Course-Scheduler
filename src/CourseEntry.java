/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jakelicata
 */
public class CourseEntry {
    private String courseCode;
    private String description;

    public CourseEntry(String courseCode, String description) {
        this.courseCode = courseCode;
        this.description = description;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString(){
        return getCourseCode();
    }
    
    
}
