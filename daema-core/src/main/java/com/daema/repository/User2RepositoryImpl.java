package com.daema.repository;

import com.daema.domain.*;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class User2RepositoryImpl extends QuerydslRepositorySupport implements CustomUser2Repository {

    public User2RepositoryImpl() {
        super(User2.class);
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

    @Override
    public List<User2> findByUser(long storeId, OrderSpecifier orderSpecifier) {
        QUser2 user2 = QUser2.user2;

        JPQLQuery<User2> query = from(user2);

        query.where(
                user2.storeId.eq(storeId)
                .and(user2.userStatus.eq("6"))
        );

        query.orderBy(orderSpecifier);

        return query.fetch();
    }
}
