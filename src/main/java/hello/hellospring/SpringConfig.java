package hello.hellospring;

import hello.hellospring.repository.MemberRepository;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    /*@Autowired
    DataSource dataSource;*/

//    private DataSource dataSource;
//
//    @Autowired
//    public SpringConfig(DataSource dataSource){
//        this.dataSource=dataSource;
//    }
    private final MemberRepository memberRepository;

    @Autowired
    public SpringConfig(MemberRepository memberRepository){
        this.memberRepository=memberRepository;
    }
//    @PersistenceContext
//    private EntityManager em;

//    @Autowired
//    public SpringConfig(EntityManager em){
//        this.em=em;
//    }

    @Bean
    public MemberService memberService(){
        return new MemberService(memberRepository);
    }

//    @Bean
//    public TimeTraceAop timeTraceAop(){
//        return new TimeTraceAop();
//    }

//    @Bean
//    public MemberRepository memberRepository(){
//        //return new MemoryMemberRepository();
//        //return new JdbcMemberRepository(dataSource);
//        //return new JdbcTemplateMemberRepository(dataSource);
//        //return new JpaMemberRepository(em);
//    }
}
