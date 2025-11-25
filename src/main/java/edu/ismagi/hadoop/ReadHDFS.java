package edu.ismagi.hadoop;

import java.io.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class ReadHDFS {
    public static void main(String[] args) throws IOException {
        if(args.length < 1) {
            System.out.println("Usage: <chemin_fichier>");
            System.exit(1);
        }

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path file = new Path(args[0]);
        if(!fs.exists(file)) {
            System.out.println("File does not exist");
            System.exit(1);
        }

        FSDataInputStream in = fs.open(file);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = br.readLine()) != null) System.out.println(line);
        in.close();
        fs.close();
    }
}

