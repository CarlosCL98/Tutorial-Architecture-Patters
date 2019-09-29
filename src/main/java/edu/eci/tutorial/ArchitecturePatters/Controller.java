package edu.eci.tutorial.ArchitecturePatters;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
public class Controller {

    @RequestMapping(value = "/costosServiciosAWS", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPDF() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        String filename = "PreciosServiciosAWS.pdf";
        File pdf = new File(System.getProperty("user.dir") + "/src/main/resources/static/" + filename);
        byte[] pdfBytes = fileToByte(pdf);
        headers.add("content-disposition", "inline; filename=" + filename);
        //headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(pdfBytes, headers, HttpStatus.OK);
        return response;
    }

    /**
     * Convert the file to bytes to send it to the client.
     *
     * @param file represents the file to convert to bytes.
     * @return byte[] : file in form of bytes.
     * @throws IOException : in case that a file could not be found or the output
     * stream presents an error.
     */
    private static byte[] fileToByte(File file) throws IOException {
        byte[] dataByte = new byte[(int) file.length()];
        FileInputStream fileIn = null;
        try {
            fileIn = new FileInputStream(file);
            fileIn.read(dataByte);
        } finally {
            if (fileIn != null) {
                fileIn.close();
            }
        }
        return dataByte;
    }
}
