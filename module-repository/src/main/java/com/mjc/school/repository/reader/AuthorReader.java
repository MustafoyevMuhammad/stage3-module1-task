package com.mjc.school.repository.reader;

import com.mjc.school.repository.model.AuthorModel;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class AuthorReader {
    private static AuthorReader INSTANCE;

    public static AuthorReader getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AuthorReader();
        }
        return INSTANCE;
    }
    private List<AuthorModel> listOfAuthors = new ArrayList<>();

    public List<AuthorModel> getAuthors() {
        return listOfAuthors;
    }
    public void readAuthorsFromFile() throws IOException {
        BufferedReader in = null;
        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(Files.newInputStream(new File("module-repository/src/main/resources/author.txt").toPath()));
            in = new BufferedReader(isr);
            String line;
            Long id = 1l;
            while ((line = in.readLine()) != null){
                AuthorModel author = new AuthorModel(line, id);
                listOfAuthors.add(author);
                id++;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            if(isr != null)
                isr.close();
            if(in != null)
                in.close();
        }
    }


}
