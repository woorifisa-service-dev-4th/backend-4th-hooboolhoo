package dev.hooboolhoo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Setter
public class Comment {
    private String writer;	// 댓글 작성자 닉네임
    private String content;	// 댓글 내용
    private boolean choiceType;	// 선택 타입
    private LocalDateTime date;	// 댓글 작성 날짜
}
