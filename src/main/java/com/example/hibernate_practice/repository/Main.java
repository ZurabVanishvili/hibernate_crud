package com.example.hibernate_practice.repository;

import com.example.hibernate_practice.model.Course;
import com.example.hibernate_practice.model.Instructor;
import com.example.hibernate_practice.model.InstructorDetail;
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
//        addCourse("Java Course for intermediates2");

        //get instructor courses
//        getInstructorCourses();

        //delete course
//        deleteCourse(1);
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

    private static void addCourse(String  title){
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            //begin the transaction
            session.beginTransaction();

            //retrieve instructor object
            Instructor instructor = (Instructor) session.get(Instructor.class,13);


            //create course objects and insert into database
            Course course = new Course(title);
            Course course2 = new Course("Spring Boot course2");

            instructor.add(course);
            instructor.add(course2);

            //save the courses
            session.save(course2);
            session.save(course);

            //commit
            session.getTransaction().commit();
        }finally {
            factory.close();
        }
    }

    private static void getInstructorCourses(){
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();

        try {
            //begin the transaction
            session.beginTransaction();

            Instructor instructor =
                    (Instructor) session.get(Instructor.class,11);

            System.out.println("Instructor: " + instructor);

            System.out.println("Courses: " + instructor.getCourses());

            //commit
            session.getTransaction().commit();
        }finally {
            factory.close();
        }
    }

    private static void deleteCourse(int id){
        SessionFactory factory = getSessionFactory();
        Session session = factory.getCurrentSession();
        try {
            session.beginTransaction();

            Course course = (Course) session.get(Course.class,id);
            System.out.println("Deleting course with ID : "+id);

            session.delete(course);

            session.getTransaction().commit();

        }finally {
            factory.close();
        }
    }

    private static SessionFactory getSessionFactory() {

        return new Configuration().configure("" +
                        "hibernate.cfg.xml").addAnnotatedClass(Instructor.class).
                addAnnotatedClass(InstructorDetail.class).addAnnotatedClass(Course.class).buildSessionFactory();


    }


}
