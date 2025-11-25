package edu.ismagi.hadoop;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class HDFSWrite {
    public static void main(String[] args) throws IOException {
        if(args.length < 2) {
            System.out.println("Usage: <chemin_fichier> <contenu>");
            System.exit(1);
        }

        Configuration conf = new Configuration();
        FileSystem fs = FileSystem.get(conf);
        Path file = new Path(args[0]);

        if(!fs.exists(file)) {
            FSDataOutputStream out = fs.create(file);
            out.writeUTF(args[1]);
            out.close();
        } else {
            System.out.println("File already exists");
        }

        fs.close();
    }
}

