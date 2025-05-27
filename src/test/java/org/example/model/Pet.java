package org.example.model;

import lombok.Data;
import java.util.List;


@Data
public class Pet {
    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private String status;

    @Data
    public static class Category {
        private Long id;
        private String name;
    }

    @Data
    public static class Tag {
        private Long id;
        private String name;
    }
}