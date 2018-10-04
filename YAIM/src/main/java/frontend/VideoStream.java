package frontend;

import java.io.FileInputStream;

class VideoStream {

    FileInputStream fis; //video file
    int frame_nb; //current frame nb

    public VideoStream(String filename) throws Exception {

        //init variables
        fis = new FileInputStream(filename);
        frame_nb = 0;
    }

    public int getnextframe(byte[] frame) throws Exception {
        int length = 0;
        String length_string;
        byte[] frame_length = new byte[5];

        //read current frame length
        fis.read(frame_length, 0, 5);

        //transform frame_length to integer
        length_string = new String(frame_length);
        try {
            length = Integer.parseInt(length_string);
        } catch (Exception e) {
            return -1;
        }

        return (fis.read(frame, 0, length));
    }
}