package ru.effective.mobile.java.taskmanagementsystem.app.domain.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Getter
@RequiredArgsConstructor
public enum PostSort {

    TITLE_ASC(Sort.by(Sort.Direction.ASC, "title")),
    TITLE_DESC(Sort.by(Sort.Direction.DESC, "title")),
    DESCRIPTION_ASC(Sort.by(Sort.Direction.ASC, "description")),
    DESCRIPTION_DESC(Sort.by(Sort.Direction.DESC, "description")),
    STATUS_ASC(Sort.by(Sort.Direction.ASC, "status")),
    STATUS_DESC(Sort.by(Sort.Direction.DESC, "status")),
    PRIORITY_ASC(Sort.by(Sort.Direction.ASC, "priority")),
    PRIORITY_DESC(Sort.by(Sort.Direction.DESC, "priority"));

    private final Sort sortValue;

}
