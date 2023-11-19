package Homework4_5.controller;
import Homework4_5.model.Faculty;
import Homework4_5.model.Student;
import Homework4_5.service.FacultyService;
import Homework4_5.service.StudentService;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService service;
    private final FacultyService facultyService;

    public StudentController(StudentService service, FacultyService facultyService) {
        this.service = service;
        this.facultyService = facultyService;
    }

    @PostMapping
    public Student add(@RequestBody Student student) {
        return service.add(student);
    }

    @GetMapping("/{id}")
    public Student get(@PathVariable long id) {
        return service.get(id);
    }

    @PutMapping
    public Student update(@RequestBody Student student) {
        return service.update(student);
    }

    @DeleteMapping("/{id}")
    public Student remove(@PathVariable long id) {
        return service.remove(id);
    }

    @GetMapping("/{studentId}/faculty")
    public Faculty facultyByStudent(@PathVariable long studentId) {
        return service.get(studentId).getFaculty();
    }

    @GetMapping("/byAge")
    public Collection<Student> byAge(@RequestParam int age) {
        return service.filterByAge(age);
    }

    @GetMapping("/byAgeBetween")
    public Collection<Student> byAgeBetween(@RequestParam int min, @RequestParam int max) {
        return service.filterByAgeBetween(min, max);
    }
    @GetMapping("/byAll")
    public List <Student> byAll () {
        return service.returnAllStudents();
    }
    @GetMapping("/byFaculty/{id}")
    public Collection<Student> byFaculty (@PathVariable long id) {
        return service.returnByFaculty(id);
    }

    @GetMapping("/totalCount")
    public int totalCountOfStudents() {
        return service.totalCountOfStudents();
    }

    @GetMapping("/averageAge")
    public double averageAgeOfStudents() {
        return service.averageAgeOfStudents();
    }
    @GetMapping("/lastStudents/{count}")
    public Collection<Student> lastStudents(@PathVariable int count) {
        return service.lastStudents(count);
    }    @GetMapping("/studentsFirstLetterA")
    public Collection<String> getStudentNameStartsA () {
        return service.getStudentNameStartsA();
    }
    @GetMapping("/averageAgeStudentWithStreams")
    public double getAverageAgeStudentsInSchoolWithStreams() {
        return service.averageAgeStudentsInSchoolWithStreams();
    }
}

