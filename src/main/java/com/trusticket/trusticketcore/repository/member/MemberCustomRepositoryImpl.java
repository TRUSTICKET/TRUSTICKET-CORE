package com.trusticket.trusticketcore.repository.member;

import com.trusticket.trusticketcore.domain.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import static com.trusticket.trusticketcore.domain.QMember.member;


@Repository
@AllArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;




}
