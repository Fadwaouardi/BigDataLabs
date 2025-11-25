package edu.ismagi.hadoop;

import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

public class HadoopFileStatus {

    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: HadoopFileStatus <chemin_fichier> <nom_fichier> <nouveau_nom_fichier>");
            System.exit(1);
        }

        String chemin = args[0];      // chemin du dossier
        String nomFichier = args[1];  // fichier existant
        String nouveauNom = args[2];  // nouveau nom

        Configuration conf = new Configuration();
        FileSystem fs;

        try {
            fs = FileSystem.get(conf);
            Path filepath = new Path(chemin, nomFichier);

            if (!fs.exists(filepath)) {
                System.out.println("File does not exist: " + filepath);
                System.exit(1);
            }

            FileStatus status = fs.getFileStatus(filepath);

            System.out.println("File Name: " + filepath.getName());
            System.out.println("File Size: " + status.getLen() + " bytes");
            System.out.println("File Owner: " + status.getOwner());
            System.out.println("File Permission: " + status.getPermission());
            System.out.println("File Replication: " + status.getReplication());
            System.out.println("File Block Size: " + status.getBlockSize());

            BlockLocation[] blockLocations = fs.getFileBlockLocations(status, 0, status.getLen());
            for (BlockLocation blockLocation : blockLocations) {
                String[] hosts = blockLocation.getHosts();
                System.out.println("Block offset: " + blockLocation.getOffset());
                System.out.println("Block length: " + blockLocation.getLength());
                System.out.print("Block hosts: ");
                for (String host : hosts) {
                    System.out.print(host + " ");
                }
                System.out.println();
            }

            // Renommer le fichier
            fs.rename(filepath, new Path(chemin, nouveauNom));
            System.out.println("File renamed to: " + nouveauNom);

            fs.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
