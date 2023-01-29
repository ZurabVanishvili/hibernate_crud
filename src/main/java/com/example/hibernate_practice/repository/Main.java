package com.example.hibernate_practice.repository;

import com.example.hibernate_practice.model.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@SuppressWarnings("all")
public class Main {

    public static void main(String[] args) {

        //create instructor
//        createInstructor("John", "Higgins", "higgins@gmail.com",
//                "https://youtube.com/johnHiggins", "Snooker-Pro");

        //delete instructor
//        deleteInstructor(9);

        //delete instructor details
//        deleteInstructorFromDetails(10);

        //add course
//        addCourse("Java Course for intermediates.pt4");

        //get instructor courses
//        getInstructorCourses(11);

        //delete course
//        deleteCourse(117);

//        createCourseAndReviews(13,"Good Course!","Java Course");

//        getCourseAndReviews(113);

//        deleteCourseAndReviews(113);


        //create course and students at the same time
//        createCourseAndStudents("Kotlin course",
//                "John","Higgins","higgins@gmail.com");

        //add courses for many students
//        addCoursesForMany(2,"Devops course",
//                "Linux course");

        //get courses
//        getCoursesForMany(1);

        //delete students
//        deleteStudent(2);
    }

    private static Instructor createInstructor(String firstName, String lastName, String email, String channel, String hobby) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();

        try {

            //create objects
            Instructor instructor = new Instructor(firstName, lastName, email);
            InstructorDetail detail = new InstructorDetail(channel,
                    hobby);

            //associate the objects
            instructor.setInstructorDetail(detail);

            //begin transaction
            session.beginTransaction();

            //save the instructor
            //this will also save the details object
            //because of CascadeType.ALL
            session.save(instructor);

            //commit transaction
            session.getTransaction().commit();

            return instructor;

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            factory.close();
        }


    }

    private static void deleteInstructor(int id) {

        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        try {

            //begin transaction
            session.beginTransaction();

            //get instructor by primary key
            Instructor instructor = (Instructor) session.get(Instructor.class, id);

            //delete instructor
            session.delete(instructor);
            //commit transaction
            session.getTransaction().commit();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            factory.close();
        }

    }

    private static void deleteInstructorFromDetails(int id) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            //begin transaction
            session.beginTransaction();

            //get detail object
            InstructorDetail detail = (InstructorDetail) session.get(InstructorDetail.class, id);

            //delete instructor detail
            detail.getInstructor().setInstructorDetail(null);
            session.delete(detail);

            //commit transaction
            session.getTransaction().commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            factory.close();
        }

    }

    private static void addCourse(String title) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            //begin the transaction
            session.beginTransaction();

            //retrieve instructor object
            Instructor instructor = (Instructor) session.get(Instructor.class, 13);


            //create course objects and insert into database
            Course course = new Course(title);

            instructor.add(course);

            //save the courses
            session.save(course);

            //commit transaction
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

    private static void getInstructorCourses(int id) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            //begin the transaction
            session.beginTransaction();

            Instructor instructor =
                    (Instructor) session.get(Instructor.class, id);

            System.out.println("Instructor: " + instructor);

            System.out.println("Courses: " + instructor.getCourses());

            //commit transaction
            session.getTransaction().commit();

        } finally {
            factory.close();
        }
    }

    private static void createCourseAndReviews(int id, String review, String courseName) {

        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();

            Course course = new Course(courseName);

            Instructor instructor = (Instructor) session.get(Instructor.class, id);


            course.addReview(new Review(review));


            instructor.add(course);
            session.save(course);

            //commit transaction
            session.getTransaction().commit();
        } finally {
            factory.close();
        }

    }

    private static void getCourseAndReviews(int id) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();

            Course course = (Course) session.get(Course.class, id);

            System.out.println(course);
            System.out.println(course.getReviews());

            //commit transaction
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

    private static void deleteCourse(int id) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();

            Course course = (Course) session.get(Course.class, id);
            System.out.println("Deleting course with ID : " + id);

            session.delete(course);

            session.getTransaction().commit();

        } finally {
            factory.close();
        }
    }

    private static void deleteCourseAndReviews(int id) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();

            Course course = (Course) session.get(Course.class, id);

            session.delete(course);
            //commit transaction
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }


    private static void createCourseAndStudents(String courseName, String firstName, String lastName, String email) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();

            Course course = new Course(courseName);

            System.out.println("Saving the course...");
            session.save(course);

            System.out.println("Save complete.");


            //create the student
            Student student = new Student(firstName, lastName, email);

            //add student to the course
            course.addStudent(student);

            //save student
            session.save(student);

            System.out.println("Student saved.");

            //commit transaction
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

    private static void addCoursesForMany(int id, String courseName, String secondCourse) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();

            //get student from database
            Student student = (Student) session.get(Student.class, id);

            System.out.println("Courses: " + student.getCourses());

            //create more courses
            Course course = new Course(courseName);
            Course course2 = new Course(secondCourse);

            //add student to those courses
            course2.addStudent(student);
            course.addStudent(student);

            //save the courses
            session.save(course);
            session.save(course2);

            //commit transaction
            session.getTransaction().commit();

            System.out.println("Courses: " + student.getCourses());

        } finally {
            factory.close();
        }
    }


    private static void getCoursesForMany(int id) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            session.beginTransaction();

            Student student = (Student) session.get(Student.class, id);


            System.out.println(student);
            System.out.println(student.getCourses());

            //commit transaction
            session.getTransaction().commit();


        } finally {
            factory.close();
        }
    }

    private static void deleteStudent(int id) {
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();

            Student student = (Student) session.get(Student.class, id);

            session.delete(student);
            //commit transaction
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }


    private static SessionFactory getSessionFactory() {

        return new Configuration().configure("" +
                        "hibernate.cfg.xml").addAnnotatedClass(Instructor.class).
                addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Course.class).
                addAnnotatedClass(Review.class).addAnnotatedClass(Student.class).buildSessionFactory();


    }


}
