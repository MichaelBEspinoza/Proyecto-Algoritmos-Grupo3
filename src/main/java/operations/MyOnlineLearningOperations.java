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


    @Override
    public List<Lesson> accessCourseContent(int userId, int courseId) {
        return null;
    }

    @Override
    public Progress trackProgress(int userId, int courseId) {
        return null;
    }

    @Override
    public List<Course> listUserCourses(int userId) {
        return null;
    }
}
