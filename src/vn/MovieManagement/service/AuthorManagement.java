package vn.MovieManagement.service;

import vn.MovieManagement.model.author;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import com.github.freva.asciitable.AsciiTable;
import com.github.freva.asciitable.Column;
import com.github.freva.asciitable.HorizontalAlign;

public class AuthorManagement implements IAuthorManagement {
    private ArrayList<author> authorManagement;
    private int Size;
    private Map<Integer, Integer> idMapping; 

    public AuthorManagement() {
        authorManagement = new ArrayList<>();
        Size = 0;
        idMapping = new HashMap<>();
    }

    public int Size() {
        return this.authorManagement.size();
    }

    public void add(author Author) {
        authorManagement.add(Author); 
        Size++;
        rebuildMapping();
    }


    public void add(ArrayList<author> author) {
        for(author x : author) {
            authorManagement.add(x);
        }
        Size = authorManagement.size();
        rebuildMapping(); 
    }
    
    public void remove(int index) {
        if (Size == 0 || index < 0 || index >= Size) return; 
        
        authorManagement.remove(index);
        Size--;
        rebuildMapping(); 
    }

    public void clear() {
        authorManagement.clear();
        idMapping.clear();
        Size = 0;
    }

    private void rebuildMapping() {
        idMapping.clear();
        for (int i = 0; i < authorManagement.size(); i++) {
            idMapping.put(authorManagement.get(i).getID(), i + 1); 
        }
    }

   public void print() {
        if (authorManagement == null || authorManagement.isEmpty()) {
            System.out.println("\n[!] DATA DOES NOT EXIST\n");
            return;
        }

        String table = AsciiTable.getTable(AsciiTable.BASIC_ASCII, authorManagement, Arrays.asList(
            new Column().header("STT")
                        .headerAlign(HorizontalAlign.CENTER)
                        .dataAlign(HorizontalAlign.CENTER)
                        .with(a -> String.valueOf(adapterID(a))),

            new Column().header("NAME")
                        .maxWidth(30)
                        .with(author::getName),

            new Column().header("AGE")
                        .headerAlign(HorizontalAlign.CENTER)
                        .dataAlign(HorizontalAlign.CENTER)
                        .with(a -> String.valueOf(a.getAge())),

            new Column().header("COUNTRY")
                        .maxWidth(30)
                        .with(author::getCountry),

            new Column().header("DESCRIPTION")
                        .maxWidth(30)
                        .with(a -> a.getDescription() == null ? "" : a.getDescription())
        ));

        System.out.println(table);
    }

    private int adapterID(author m) {
        return idMapping.getOrDefault(m.getID(), -1);
    }

    public int getIdFromStt(int stt) {
        if (stt < 1 || stt > authorManagement.size()) {
            return -1; 
        }
        int index = stt - 1;
        author m = authorManagement.get(index);
        return m.getID();
    }
}
