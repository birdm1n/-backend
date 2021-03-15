package com.daema.repository;

import com.daema.domain.Board;
import com.daema.domain.QBoard;
import com.daema.domain.StoreMap;
import com.daema.domain.User;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class StoreMapRepositoryImpl extends QuerydslRepositorySupport implements CustomStoreMapRepository {

    public StoreMapRepositoryImpl() {
        super(StoreMap.class);
    }

    @Override
    public Page<Board> getSearchPage(String searchType, String keyword, Pageable pageable) {
        String title = keyword;
        String writer = keyword;
        String content = keyword;

        QBoard board = QBoard.board;

        JPQLQuery<Board> query = from(board);

        if(searchType != null && searchType.length() > 0) {
            if(searchType.equals("t")) {
                query.where(board.title.like("%" + title +"%"));
                query.orderBy(board.boardNo.desc());
            }
            else if(searchType.equals("w")) {
                query.where(board.writer.like("%" + writer +"%"));
                query.orderBy(board.boardNo.desc());
            }
            else if(searchType.equals("c")) {
                query.where(board.content.like("%" + content +"%"));
                query.orderBy(board.boardNo.desc());
            }
            else if(searchType.equals("tc")) {
                query.where(board.title.like("%" + title +"%").or(board.content.like("%" + content +"%")));
                query.orderBy(board.boardNo.desc());
            }
            else if(searchType.equals("cw")) {
                query.where(board.content.like("%" + content +"%").or(board.writer.like("%" + writer +"%")));
                query.orderBy(board.boardNo.desc());
            }
            else if(searchType.equals("tcw")) {
                BooleanBuilder builder = new BooleanBuilder();
                builder.and(board.title.like("%" + title +"%"))
                        .or(board.content.like("%" + content +"%"))
                        .or(board.writer.like("%" + writer +"%"));

                query.where(builder);
                query.orderBy(board.boardNo.desc());
            }
            else {
                query.where(board.boardNo.gt(0L));
                query.orderBy(board.boardNo.desc());
            }
        }
        else {
            query.where(board.boardNo.gt(0L));
            query.orderBy(board.boardNo.desc());
        }

        query.offset(pageable.getOffset());
        query.limit(pageable.getPageSize());

        List<Board> resultList = query.fetch();

        long total = query.fetchCount();

        return new PageImpl<>(resultList, pageable, total);
    }
}
