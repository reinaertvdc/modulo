/*
 * Created by Lambdaware as part of the course "Software Engineering" @ Hasselt University.
 */

package be.lambdaware.controllers;

import be.lambdaware.doc.MethodObject;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * {@link DocController} is the controller that is mapped to "/doc" and generates a raw form of documentation with all
 * available endpoints as a JSON response.
 *
 * @author Hendrik Lievens - 1130921
 */
@RestController
@RequestMapping("/doc")
@CrossOrigin
public class DocController {

    private static Logger log = Logger.getLogger(DocController.class);

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> generateDoc() {

        HashMap<String, List<MethodObject>> mappings = new HashMap<>();
        fillHashMapWithMappings("AuthController", mappings);
        fillHashMapWithMappings("ClassController", mappings);
        fillHashMapWithMappings("CertificateController", mappings);
        fillHashMapWithMappings("DocController", mappings);
        fillHashMapWithMappings("UploadController", mappings);
        fillHashMapWithMappings("UserController", mappings);
        return new ResponseEntity<>(mappings, HttpStatus.OK);
    }

    private void fillHashMapWithMappings(String controllerName, HashMap<String, List<MethodObject>> mappings) {
        try {
            ArrayList<MethodObject> methods = new ArrayList<>();
            Class c = Class.forName("be.lambdaware.controllers." + controllerName);
            Annotation classAnnotation = c.getAnnotation(RequestMapping.class);
            RequestMapping superMapping = (RequestMapping) classAnnotation;
            for (Method method : c.getMethods()) {
                Annotation annotation = method.getAnnotation(RequestMapping.class);
                RequestMapping requestMapping = (RequestMapping) annotation;
                if (requestMapping != null) {
                    String value = requestMapping.value()[0];
                    String httpMethod = requestMapping.method()[0].name();
                    MethodObject methodObject = new MethodObject(httpMethod, value);
                    methods.add(methodObject);
                }
            }
            mappings.put(superMapping.value()[0], methods);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
