package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.order.Order;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;

public class OrderApp {

    public static void main(String[] args) {

        AppConfig appConfig = new AppConfig();
        MemberService memberService = appConfig.memberService();
        OrderService orderService = appConfig.orderService();

//        MemberService memberService = new MemberServiceImpl(null);
//        OrderService orderService = new OrderServiceImpl(null,null);

        Long memberId = 1L;
        Member member= new Member(memberId, "memberA", Grade.VIP);
        memberService.join(member);//멤버서비스를 통해 메모리객체에 넣어놓기

        Order order = orderService.createOrder(memberId, "itemA", 10000);

        System.out.println("order = " + order); //toString 출력
        System.out.println("order.calculatePrice = " + order.calculatePrice());
    }
}
