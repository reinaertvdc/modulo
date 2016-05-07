package be.lambdaware.controllers;

import be.lambdaware.response.Responses;
import be.lambdaware.response.StringMessage;
import be.lambdaware.security.APIAuthentication;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.nio.charset.Charset;

/**
 * {@link UploadController} is the controller that is mapped to "/upload" and is used to handle uploads.
 *
 * @author Hendrik Lievens - 1130921
 */
@RestController
public class UploadController {

    @Autowired
    APIAuthentication authentication;

    Logger log = Logger.getLogger(getClass());

    // ===================================================================================
    // GET methods
    // ===================================================================================

    //TODO proof of concept method. File uploading works.

    @RequestMapping(value="/upload", method = RequestMethod.POST)
    public ResponseEntity<?> uploadFile(@RequestHeader(name = "X-auth", defaultValue = "empty") String auth, @RequestParam MultipartFile file) {

        if (auth.equals("empty") || !authentication.checkLogin(auth)) return Responses.LOGIN_INVALID;

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream =
                        new BufferedOutputStream(new FileOutputStream(new File("Test File")));
                stream.write(bytes);
                stream.close();
                return StringMessage.asEntity("File uploaded.", HttpStatus.OK);
            } catch (Exception e) {
                return StringMessage.asEntity(e.getMessage(), HttpStatus.BAD_REQUEST);
            }
        } else {
            return StringMessage.asEntity("File was empty.", HttpStatus.BAD_REQUEST);
        }
    }


    @RequestMapping(value="/download", method = RequestMethod.GET)
    public void downloadFile(HttpServletResponse response) throws IOException {
        File file = new File("Test File");

        if(!file.exists()){
            String errorMessage = "Sorry. The file you are looking for does not exist";
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
            outputStream.close();
            return;
        }

        String mimeType= URLConnection.guessContentTypeFromName(file.getName());
        if(mimeType==null) mimeType = "application/octet-stream";

        response.setContentType(mimeType);
        response.setContentLength((int)file.length());
        /* "Content-Disposition : inline" will show viewable types [like images/text/pdf/anything viewable by browser] right on browser
            while others(zip e.g) will be directly downloaded [may provide save as popup, based on your browser setting.]*/
        response.setHeader("Content-Disposition", "inline; filename=\"" + file.getName() +"\"");

        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
        FileCopyUtils.copy(inputStream, response.getOutputStream());
    }

}
