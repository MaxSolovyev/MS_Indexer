package com.app.indexstorage.rest;

import com.app.indexstorage.dto.DocInfo;
import com.app.indexstorage.model.IndexDoc;
import com.app.indexstorage.repository.IndexDocRepository;
import com.app.indexstorage.service.IndexCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@RestController
public class IndexCrudController {
    private IndexDocRepository indexDocRepository;
    private IndexCrudService indexCrudService;

    @Autowired
    public IndexCrudController(IndexDocRepository indexDocRepository, IndexCrudService indexCrudService) {
        this.indexDocRepository = indexDocRepository;
        this.indexCrudService = indexCrudService;
    }

    @RequestMapping(value = "/updateindex", method = RequestMethod.POST)
//    ResponseEntity<List<IndexDoc>> getReferences(){
    ResponseEntity<List<IndexDoc>> getReferences(@ModelAttribute(name="docinfo") DocInfo docInfo){

        DocInfo docInfo1 = new DocInfo(2L, "Vitos hello");

        String content = docInfo1.content;
        String[] words = content.split(" ");

        List<IndexDoc> indexDocs = indexCrudService.getIndexDocByWords(Arrays.asList(words));

        List<String> wordsUpdated = new ArrayList<>();
        for (IndexDoc indexDoc : indexDocs) {
            List<Long> references = indexDoc.references;
            wordsUpdated.add(indexDoc.word);
            if (!references.contains(docInfo.id)) {
                references.add(docInfo.id);
                references.sort(Comparator.naturalOrder());

                indexDoc.references = references;

                indexDocRepository.save(indexDoc);
            }
        }

        for (String word : words) {
            if (!wordsUpdated.contains(word)) {
                indexDocRepository.save(new IndexDoc(word, Collections.singletonList(docInfo.id)));
            }
        }








// The name of the file to open.
//        String fileName = "temp.txt";
//
//        // This will reference one line at a time
//        String line = null;
//
//        try {
//            // FileReader reads text files in the default encoding.
//            FileReader fileReader =
//                    new FileReader(fileName);
//
//            // Always wrap FileReader in BufferedReader.
//            BufferedReader bufferedReader =
//                    new BufferedReader(fileReader);
//
//            while((line = bufferedReader.readLine()) != null) {
//                System.out.println(line);
//            }
//
//            // Always close files.
//            bufferedReader.close();
//        }
//        catch(FileNotFoundException ex) {
//            System.out.println(
//                    "Unable to open file '" +
//                            fileName + "'");
//        }
//        catch(IOException ex) {
//            System.out.println(
//                    "Error reading file '"
//                            + fileName + "'");
//            // Or we could just do this:
//            // ex.printStackTrace();
//        }



//        List<Long> references = new ArrayList<>();
//        references.add(1L);
//        references.add(2L);
//        IndexDoc indexDoc = new IndexDoc(word, references);
//
//        indexDocRepository.save(indexDoc);
//
//        List<IndexDoc> findReferences = indexDocRepository.findByWord("hello");
        return ResponseEntity.ok(indexDocs);
    }
}