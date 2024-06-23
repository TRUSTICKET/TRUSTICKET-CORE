package com.trusticket.trusticketcore.repository.member;

import com.trusticket.trusticketcore.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {


    Member findMemberByMemberId(Long memberId);

    Member findMemberByEmail(String email);



}


