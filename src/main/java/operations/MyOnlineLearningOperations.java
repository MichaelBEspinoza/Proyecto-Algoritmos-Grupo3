package operations;

import domain.Course;
import domain.Lesson;
import domain.Progress;
import domain.User;
import interfaces.MyOnlineLearning;
import structures.lists.ListException;
import structures.trees.TreeException;

import java.util.ArrayList;
import java.util.List;

public class MyOnlineLearningOperations implements MyOnlineLearning {

    private UserOperations userOperations = new UserOperations();
    private LessonOperations lessonOperations = new LessonOperations();

    @Override
    public List<Lesson> accessCourseContent(int userId, int courseId) {
        try {
            User user = userOperations.readUser(userId);
            if (user != null && user.getCourses().contains(new Course(courseId))) {
                return lessonOperations.listLessonsByCourse(courseId);
            }
        } catch (ListException | TreeException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public Progress trackProgress(int userId, int courseId) {
        try {
            User user = userOperations.readUser(userId);
            if (user != null && user.getCourses().contains(new Course(courseId))) {
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
        } catch (ListException | TreeException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Course> listUserCourses(int userId) {
        try {
            User user = userOperations.readUser(userId);
            if (user != null) {
                List<Course> courseList = new ArrayList<>();
                for (int i = 1; i <= user.getCourses().size(); i++) {
                    Course course = (Course) user.getCourses().getNode(i).data;
                    courseList.add(course);
                }
                return courseList;
            }
        } catch (ListException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
