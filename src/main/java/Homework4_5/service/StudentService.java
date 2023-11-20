package Homework4_5.service;

import Homework4_5.model.Student;
import Homework4_5.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final static Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    Object monitorObjectSynch = new Object();

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(Student student){
        logger.info("Was invoked method for create student");
    return studentRepository.save(student);
    }

    public Student get(long id) {
        logger.info("Was invoked method for getting student by id = {}", id);
        return studentRepository.findById(id).orElse(null);
    }
    public Student remove(long id) {
        logger.info("Was invoked method for removing student by id = {}", id);
        var entity = studentRepository.findById(id).orElse(null);
        if (entity != null) {
            studentRepository.delete(entity);
        }
        return entity;
    }
    public Student update(Student student) {
        logger.info("Was invoked method for change student ");
        return studentRepository.findById(student.getId())
                .map(entity -> studentRepository.save(student))
                .orElse(null);
    }

    public Collection<Student> filterByAge(int age) {
        logger.info("The method of searching for a students by age was called ");
        return studentRepository.findByAge(age);
    }
    public Collection<Student> filterByAgeBetween(int min, int max) {
        logger.info("A method was called to search for students by age from minimum to maximum ");
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public List <Student> returnAllStudents() {
        logger.info("The method for calling all students from the database was called ");
        return studentRepository.findAll();
    }

    public Collection<Student> returnByFaculty(long id) {
        logger.info("The method for calling all students belonging to one faculty was called id = {} ", id);
        return  studentRepository.findStudentsByFaculty_Id( id);
    }

    public Integer totalCountOfStudents() {
        logger.info("Method that displays the number of all students in the database ");
        return studentRepository.totalCountOfStudents();
    }

    public double averageAgeOfStudents() {
        logger.info("Method showing the average age of all students ");
        return studentRepository.averageAgeOfStudents();
    }

    public Collection<Student> lastStudents(int count) {
        logger.info("Method showing the number of students since the last ");
        return studentRepository.lastStudents(count);
    }

    public Collection<String> getStudentNameStartsA() {
      return   studentRepository.findAll().stream()
                .map(e -> e.getName().toUpperCase())
                .filter(e -> e.startsWith("А"))
                .sorted()
                .peek(e -> System.out.println(" 2 => " + e))
                .collect(Collectors.toList());
    }

    public double averageAgeStudentsInSchoolWithStreams() {
        return studentRepository.findAll()
                .stream()
                .mapToInt(Student::getAge)
                .average().orElse(0);
    }
    public void printNonSync (){
           var students = studentRepository.findAll();
           showsStudentNames(students, 0,2 );

     Thread thread1 = new Thread(() ->
              showsStudentNames(students,2,2 ));

     Thread thread2 = new Thread(() ->
              showsStudentNames(students,4,2));

     thread1.start();
     thread2.start();
    }

    public void printSync (){
        var students = studentRepository.findAll();
        showsSynchronizedStudentNames(students, 0,2);

        Thread thread1 = new Thread(() ->
                showsSynchronizedStudentNames(students,2,2));

        Thread thread2 = new Thread(() ->
                showsSynchronizedStudentNames(students,4,2));

        thread1.start();
        thread2.start();
    }
    private synchronized void showsSynchronizedStudentNames(List<Student> students,long retreat, long skipQuantity){
       synchronized (monitorObjectSynch){
            showsStudentNames(students, retreat, skipQuantity);
        }
    }

    private void showsStudentNames (List<Student> students, long retreat, long skipQuantity){
        Long thredNumber = Thread.currentThread().getId();
        students.stream()
                .skip(retreat)
                .limit(skipQuantity)
                .map(Student::getName)
                .peek((s)-> System.out.println( "поток => " + thredNumber))
                .forEach(System.out::println);
    }
}

