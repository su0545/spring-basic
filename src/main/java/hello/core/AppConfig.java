package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    /*
    @Bean memberService -> new MemoryMemberRepository()
    @Bean orderService -> new MemoryMemberRepository()
    결과적으로 각각 다른 2개의 MemoryMemberRepository 가 생성되면서 싱글톤이 깨지는 것 처럼 보인다. 과연 싱글톤이 꺠질까?
    */

    @Bean
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    public static MemoryMemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    /**
     AppConfig를 통해 orderService를 조회하면 OrderServiceImpl이 반환되고
     거기에 MemoryMemberRepository,FixDiscountPolicy가 들어간다.
     */
    @Bean
    public OrderService orderService(){
        System.out.println("call  AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy(){
        return new RateDiscountPolicy();
//        return new FixDiscountPolicy();
    }
}
