package com.daema.core.sample.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(of="boardNo")
@ToString
@Entity
@Table(name="board")
public class Board {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long boardNo;
	
	@Column(length = 200)
	private String title;
	
	@Column(length = 50)
	private String writer;
	
	@Lob
	private String content;

}
