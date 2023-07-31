package hello.hellospring.repository;

import hello.hellospring.domain.Member;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class JpaMemberRepository implements MemberRepository {

    private final EntityManager em;

    public JpaMemberRepository(EntityManager em){
        this.em=em;
    }

    @Override
    public Member save(Member member){
        em.persist(member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id){
        Member member = em.find(Member.class, id);
        return Optional.ofNullable(member);
    }

    @Override
    public Optional<Member> findByName(String name){
       List<Member> result = em.createQuery("select m from Member m where m.name=:name",Member.class) //콜론(:)으로 파라미터 이름과 위치를 지정하고 setParameter로 파라미터를 바인딩 시킨다.
                .setParameter("name",name)
               .getResultList(); //결과가 없으면 빈 리스트 반환, 빈 collection이 반환되기 때문에, nullPoiontException에 대한 걱정은 안 해도 된다.
       return result.stream().findAny();
    }

    @Override
    public List<Member> findAll(){
        List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();
        return result;
    }

}
