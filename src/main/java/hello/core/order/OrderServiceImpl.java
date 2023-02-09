package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderServiceImpl implements OrderService {

    /**
     * 인터페이스에만 의존하므로 DIP 잘 지키게 설계함
     */
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;

    @Autowired
    public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        this.memberRepository = memberRepository;
        this.discountPolicy = discountPolicy;
    }

    //private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    //DiscountPolicy 인터페이스만 의존하는게 아니라 구체 클래스까지 의존하는 문제점 발생(OCP 위반)
   // private DiscountPolicy discountPolicy; DIP는 지켰지만 null에 점찍으니 NullPointerException 발생
    
    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId); //회원정보 조회
        int discountPrice = discountPolicy.discount(member, itemPrice); //할인정책에 member을 넘김

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }
    //테스트용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }
}
