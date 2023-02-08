package hello.core.discount;

import hello.core.AppConfig;
import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RateDiscountPolicyTest  {

    RateDiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Test
    @DisplayName("VIP는 10% 할인이 적용되어야 한다")
    void vip_o() {
        //given
        Member member = new Member(1L, "memberVIP", Grade.VIP);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(1000);
    }

    @Test
    @DisplayName("VIP가 아니면 할인이 적용되지 않아야 한다")
    void vip_x(){
        //given
        Member member = new Member(2L, "memberBASIC", Grade.Basic);
        //when
        int discount = discountPolicy.discount(member, 10000);
        //then
        assertThat(discount).isEqualTo(0);
    }

    public static class ApplicationContextBasicFindTest {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);

        @Test
        @DisplayName("빈 이름으로 조회")
        void findBeanByName() {
            MemberService memberService = ac.getBean("memberService", MemberService.class);
            System.out.println("memberService = " + memberService);
            System.out.println("memberService.getClass() = " + memberService.getClass());
            assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
        }

        @Test
        @DisplayName("이름 없이 타입으로만 조회")
        void findBeanByType() {
            MemberService memberService = ac.getBean(MemberService.class);
            assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
        }

        @Test
        @DisplayName("구체 타입으로 조회")
        void findBeanByName2() {
            MemberServiceImpl memberService = ac.getBean("memberService", MemberServiceImpl.class);
            //꼭 인터페이스 아니고 구체화로 적어도 가능은 하나 유연성이 떨어짐
            assertThat(memberService).isInstanceOf(MemberServiceImpl.class);
        }

       /* @Test
        @DisplayName("빈 이름으로 조회X")
        void findBeanByNameX() {
//            ac.getBean("xxxxx", MemberService.class);
            MemberService xxxxx = ac.getBean("xxxxx", MemberService.class);
            assertThrows(NoSuchBeanDefinitionException.class,
                    () -> ac.getBean("xxxxx", MemberService.class)); //오른쪽 식을 실행하면 왼쪽 예외가 발생
        }*/
    }
}