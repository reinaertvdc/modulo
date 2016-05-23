package be.lambdaware.controllers;

import be.lambdaware.repos.ClassRepo;
import be.lambdaware.repos.TaskRepo;
import be.lambdaware.repos.TaskScoreRepo;
import be.lambdaware.repos.UserRepo;
import be.lambdaware.enums.UserRole;
import be.lambdaware.models.Clazz;
import be.lambdaware.models.Task;
import be.lambdaware.models.TaskScore;
import be.lambdaware.models.User;
import be.lambdaware.response.Responses;
import be.lambdaware.security.APIAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by MichielVM on 4/05/2016.
 */

@RestController
@RequestMapping("/task")
@CrossOrigin
public class TaskController {

    @Autowired
    TaskRepo taskRepo;
    @Autowired
    TaskScoreRepo taskScoreRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ClassRepo classRepo;

    @Autowired
    APIAuthentication authentication;

    // ===================================================================================
    // GET methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        Task task = taskRepo.findById(id);

        if (task == null) return Responses.TASK_NOT_FOUND;
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @RequestMapping(value = "/teacher/{teacher}", method = RequestMethod.GET)
    public ResponseEntity<?> getAllTeachedTasks(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long teacher) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        // get all PAV classes for this teacher
        User user = userRepo.findById(teacher);

        if (user == null) return Responses.USER_NOT_FOUND;
        if (user.getRole() != UserRole.TEACHER) return Responses.USER_NOT_TEACHER;

        List<Clazz> classes = user.getTeachedClasses();
        if (classes.size() == 0) return Responses.USER_NO_TEACHING;

        // get tasks associated to each clazz
        List<Task> teachedTasks = new ArrayList<Task>();
        for (Clazz clazz : classes) {
            teachedTasks.addAll(taskRepo.findAllByClazz(clazz));
        }
        teachedTasks.sort(new DateComparator());

        return new ResponseEntity<>(teachedTasks, HttpStatus.OK);
    }

    @RequestMapping(value = "/scores/{taskId}", method = RequestMethod.GET)
    public ResponseEntity<?> getTaskScores(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long taskId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        Task task = taskRepo.findById(taskId);
        if (task == null) return Responses.TASK_NOT_FOUND;

        List<TaskScore> scores = taskScoreRepo.findAllByTask(task);
        scores.sort(new NameComparator());

        return new ResponseEntity<>(scores, HttpStatus.OK);
    }

    @RequestMapping(value = "/download/{taskScoreId}", method = RequestMethod.GET)
    public ResponseEntity<?> downloadFile(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, HttpServletResponse response, @PathVariable long taskScoreId) throws IOException {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!(authentication.isTeacher() || authentication.isStudent() || authentication.isParent())) return Responses.UNAUTHORIZED;

        TaskScore score = taskScoreRepo.findById(taskScoreId);
        if (score == null) return Responses.TASKSCORE_NOT_FOUND;

        File file = new File("uploads/" + score.getId());  // the way the file is stored on server
        if (!file.exists())
            return Responses.FILE_NOT_DOWNLOADED;

        String mimeType = URLConnection.guessContentTypeFromName(score.getFileName());
        if (mimeType == null) mimeType = "application/octet-stream";

        response.setContentType(mimeType);
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "filename=\"" + score.getFileName() + "\"");

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
        return Responses.FILE_DOWNLOADED;
    }

    @RequestMapping(value = "/downloadAll/{taskId}", method = RequestMethod.GET)
    public ResponseEntity<?> downloadFilesForTask(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, HttpServletResponse response, @PathVariable long taskId) throws IOException {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        Task task = taskRepo.findById(taskId);
        if (task == null) return Responses.TASK_NOT_FOUND;

        // create the zipfile
        File zipFile = new File(task.getName() + ".zip");
        FileOutputStream fos = new FileOutputStream(zipFile);
        ZipOutputStream zos = new ZipOutputStream(fos);
        List<TaskScore> scores = taskScoreRepo.findAllByTask(task);
        for (TaskScore score : scores) {
            if (score.getFileName() != null)
                addToZipFile(score, zos);
        }
        zos.close();
        fos.close();

        // put the zip in response
        response.setContentType("application/zip");
        response.setContentLength((int) zipFile.length());
        response.setHeader("Content-Disposition", "filename=\"" + zipFile.getName() + "\"");
        InputStream inputStream = new BufferedInputStream(new FileInputStream(zipFile));
        FileCopyUtils.copy(inputStream, response.getOutputStream());

        zipFile.delete();         // remove the temp zipfile
        return Responses.FILE_DOWNLOADED;
    }


    // ===================================================================================
    // POST methods
    // ===================================================================================

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<?> createTask(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody Task task) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        try {
            taskRepo.save(task);
        } catch (Exception e) {
            System.out.println("bad request");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        taskRepo.flush();

        Clazz clazz = classRepo.findById(task.getClazz().getId());  // manually get clazz in order to get all students

        for (User student : clazz.getStudents()) {
            TaskScore score = new TaskScore();
            score.setGradedDate(task.getDeadline());
            score.setUser(student);
            score.setTask(task);
            taskScoreRepo.saveAndFlush(score);
        }

        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @RequestMapping(value = "/scores", method = RequestMethod.POST)
    public ResponseEntity<?> createScores(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody ArrayList<TaskScore> scores) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        // only update 'score' and 'remarks'
        for (TaskScore score : scores) {
            TaskScore origScore = taskScoreRepo.findById(score.getId());
            origScore.setScore(score.getScore());
            origScore.setRemarks(score.getRemarks());
            taskScoreRepo.saveAndFlush(origScore);
        }

        return new ResponseEntity<>(scores, HttpStatus.OK);
    }


    @RequestMapping(value = "/upload/{taskScoreId}", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestParam MultipartFile file, @PathVariable long taskScoreId) {
        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isStudent()) return Responses.UNAUTHORIZED;

        TaskScore score = taskScoreRepo.findById(taskScoreId);
        if (score == null) return Responses.TASKSCORE_NOT_FOUND;

        // only allow upload if before deadline
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date date = new java.sql.Date(utilDate.getTime());
        if(date.compareTo(score.getTask().getDeadline()) > 0)
            return Responses.FILE_NOT_UPLOADED;

        if (!file.isEmpty()) {
            try {
                // save file
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File("uploads/" + score.getId())));
                stream.write(bytes);
                stream.close();

                score.setFileName(file.getOriginalFilename());  // save original filename
                taskScoreRepo.saveAndFlush(score);
                return Responses.FILE_UPLOADED;
            } catch (Exception e) {
                return Responses.FILE_NOT_UPLOADED;
            }
        } else {
            return Responses.FILE_NOT_UPLOADED;
        }
    }

    // ===================================================================================
    // PUT methods
    // ===================================================================================

    @Transactional
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestBody Task task) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        Task oldTask = taskRepo.findById(task.getId());

        // if class changed, then delete old TaskScores and create new empty ones
        if (oldTask.getClazz().getId() != task.getClazz().getId()) {
            taskScoreRepo.removeByTask(oldTask);  // remove old scores

            // create scores for new class
            Clazz clazz = classRepo.findById(task.getClazz().getId());
            for (User student : clazz.getStudents()) {
                TaskScore score = new TaskScore();
                score.setGradedDate(task.getDeadline());
                score.setUser(student);
                score.setTask(task);
                taskScoreRepo.saveAndFlush(score);
            }
        }
        // update graded date
        else {
            List<TaskScore> taskScores = taskScoreRepo.findAllByTask(oldTask);
            for (TaskScore score : taskScores) {
                score.setGradedDate(task.getDeadline());
            }
            taskScoreRepo.save(taskScores);
            taskScoreRepo.flush();
        }

        // update actual task
        taskRepo.saveAndFlush(task);
        return new ResponseEntity<>(task, HttpStatus.OK);
    }


    @RequestMapping(value = "/score/{taskScoreId}/reset", method = RequestMethod.PUT)
    public ResponseEntity<?> resetUpload(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long taskScoreId) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isStudent()) return Responses.UNAUTHORIZED;

        TaskScore score = taskScoreRepo.findById(taskScoreId);
        if (score == null) return Responses.TASKSCORE_NOT_FOUND;

        // only allow reset if before deadline
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date date = new java.sql.Date(utilDate.getTime());
        if(date.compareTo(score.getTask().getDeadline()) > 0)
            return Responses.TASK_UPLOAD_NOT_RESET;

        score.setFileName(null);
        taskScoreRepo.saveAndFlush(score);

        return Responses.TASK_UPLOAD_RESET;
    }


    // ===================================================================================
    // DELETE methods
    // ===================================================================================

    @RequestMapping(value = "/id/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @PathVariable long id) {

        if (auth.equals("empty")) return Responses.AUTH_HEADER_EMPTY;
        if (!authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;
        if (!authentication.isTeacher()) return Responses.UNAUTHORIZED;

        Task task = taskRepo.findById(id);
        if (task == null) return Responses.TASK_NOT_FOUND;

        // delete all associated scores
        taskScoreRepo.delete(task.getTaskScores());

        taskRepo.delete(id);
        return Responses.TASK_DELETED;
    }


    // ===================================================================================
    // some helpers
    // ===================================================================================

    // Take care of sorting on date
    public class DateComparator implements Comparator<Task> {
        public int compare(Task t1, Task t2) {
            return t1.getDeadline().compareTo(t2.getDeadline());
        }
    }

    // Take care of sorting on <firstname, lastname>
    public class NameComparator implements Comparator<TaskScore> {
        public int compare(TaskScore s1, TaskScore s2) {
            if (s1.getUser().getFirstName().compareTo(s2.getUser().getFirstName()) != 0)
                return s1.getUser().getFirstName().compareTo(s2.getUser().getFirstName());
            else
                return s1.getUser().getLastName().compareTo(s2.getUser().getLastName());
        }
    }

    // add a file to a zipfile
    // source: http://www.avajava.com/tutorials/lessons/how-can-i-create-a-zip-file-from-a-set-of-files.html
    public static void addToZipFile(TaskScore score, ZipOutputStream zos) throws IOException {
        FileInputStream fis = new FileInputStream(new File("uploads/" + score.getId()));
        ZipEntry zipEntry = new ZipEntry(score.getFileName());
        zos.putNextEntry(zipEntry);

        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zos.write(bytes, 0, length);
        }

        zos.closeEntry();
        fis.close();
    }
}



