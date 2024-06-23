package operations;

import domain.Course;
import domain.Lesson;
import domain.Progress;
import domain.User;
import interfaces.MyOnlineLearning;
import structures.graph.GraphException;
import structures.graph.SinglyLinkedListGraph;
import structures.lists.ListException;
import structures.trees.TreeException;

import java.util.ArrayList;
import java.util.List;

public class MyOnlineLearningOperations implements MyOnlineLearning {

    private UserOperations userOperations = new UserOperations();
    private LessonOperations lessonOperations = new LessonOperations();
    private SinglyLinkedListGraph graph = new SinglyLinkedListGraph();

    public MyOnlineLearningOperations() {
        initializeGraph();
    }

    private void initializeGraph() {
        try {
            List<User> users = userOperations.listUsersArray();
            List<Course> courses = new CourseOperations().listCourse();

            for (User user : users) {
                graph.addVertex(user.getId());
                for (int i = 1; i <= user.getCourses().size(); i++) {
                    Course course = (Course) user.getCourses().getNode(i).data;
                    graph.addVertex(course.getId());
                    graph.addEdge(user.getId(), course.getId());
                }
            }

            for (Course course : courses) {
                graph.addVertex(course.getId());
            }

        } catch (ListException e) {
            e.printStackTrace();
        } catch (GraphException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Lesson> accessCourseContent(int userId, int courseId) {
        try {
            if (graph.containsEdge(userId, courseId)) {
                return lessonOperations.listLessonsByCourse(courseId);
            }
        } catch (GraphException | ListException | TreeException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Progress trackProgress(int userId, int courseId) {
        try {
            if (graph.containsEdge(userId, courseId)) {
                User user = userOperations.readUser(userId);
                if (user != null) {
                    int completedLessons = 0;
                    double grade = 0.0;
                    List<Lesson> lessons = lessonOperations.listLessonsByCourse(courseId);
                    for (Lesson lesson : lessons) {
                        if (user.hasCompletedLesson(lesson.getId())) {
                            completedLessons++;
                            grade += user.getGrade(lesson.getId());
                        }
                    }

                    if (completedLessons > 0) {
                        grade = grade / completedLessons; // Promedio de calificaciones
                    }
                    return new Progress(userId, courseId, completedLessons, grade);
                }
            }
        } catch (GraphException | ListException | TreeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Course> listUserCourses(int userId) {
        try {
            if (graph.containsVertex(userId)) {
                User user = userOperations.readUser(userId);
                if (user != null) {
                    List<Course> courseList = new ArrayList<>();
                    for (int i = 1; i <= user.getCourses().size(); i++) {
                        Course course = (Course) user.getCourses().getNode(i).data;
                        if (graph.containsEdge(userId, course.getId())) {
                            courseList.add(course);
                        }
                    }
                    return courseList;
                }
            }
        } catch (GraphException | ListException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
