package domain;

import operations.CourseOperations;

public class Lesson {
    private int id; /**Identificador unico de la leccion**/

    private String title;/**Titulo de la leccion**/

    private String content;/**Contenido de la leccion**/

    private int courseId;/**Identificador del curso al que pertenece la leccion**/

    private String course;

    private boolean stateCompleted;

    private int grade;


    public Lesson() {
    }

    public Lesson(int id, String title, String content, String course,int courseId,boolean stateCompleted) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.course = course;
        this.courseId = courseId;
        this.stateCompleted = stateCompleted;
        this.grade = util.Utility.getRandom(99);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public boolean isStateCompleted() {
        return stateCompleted;
    }

    public void setStateCompleted(boolean stateCompleted) {
        this.stateCompleted = stateCompleted;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
