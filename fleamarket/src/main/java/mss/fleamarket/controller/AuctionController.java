package mss.fleamarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mss.fleamarket.domain.Comment;
import mss.fleamarket.domain.Item;
import mss.fleamarket.domain.Member;
import mss.fleamarket.domain.MemberItem;
import mss.fleamarket.domain.status.ItemStatus;
import mss.fleamarket.dto.auction.AuctionUpdate;
import mss.fleamarket.dto.auction.BidRequest;
import mss.fleamarket.dto.auction.TimeUpdate;
import mss.fleamarket.service.comment.CommentService;
import mss.fleamarket.service.item.ItemService;
import mss.fleamarket.service.member.MemberService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuctionController {

    private final SimpMessagingTemplate messagingTemplate;
    private final ItemService itemService;
    private final MemberService memberService;
    private final CommentService commentService;

    @Scheduled(fixedRate = 1000)
    public void sendRemainingTime() {
        List<Item> items = itemService.getItemsByStatus(ItemStatus.SALE);

        for (Item item : items) {
            long remainingSeconds = calculateTimeRemaining(item);

            if (remainingSeconds < 0) {
                item.soldOut();
                itemService.modifyItem(item);
                remainingSeconds = 0; // 경매 시간이 이미 끝났을 경우
            }

            long hours = remainingSeconds / 3600;
            long minutes = (remainingSeconds % 3600) / 60;
            long seconds = remainingSeconds % 60;

            // 남은 시간을 "HH:mm:ss" 형식으로 포맷
            String timeRemaining = String.format("%02d:%02d:%02d", hours, minutes, seconds);

            TimeUpdate timeUpdate = new TimeUpdate(timeRemaining);

            messagingTemplate.convertAndSend("/topic/auction/" + item.getId(), timeUpdate);
        }
    }
    
    @MessageMapping("/bid")
    public void bid(BidRequest bidRequest) {
        Member member = memberService.getMember(bidRequest.getMemberId());

        Item item = itemService.getItem(bidRequest.getItemId());
        boolean isSuccess = item.bid(member, bidRequest.getBidAmount());

        if (isSuccess) {
            itemService.modifyItem(item);
            String message = member.getName() +"님이 " + bidRequest.getBidAmount() + "원을 입찰하였습니다.";
            commentService.sendMessage(new Comment(item, message));
            MemberItem memberItem = itemService.getMemberItem(member, item);
            if (memberItem == null) {
                MemberItem newMemberItem = new MemberItem(member, item, bidRequest.getBidAmount());
                itemService.bid(newMemberItem);
            } else {
                memberItem.setPrice(bidRequest.getBidAmount());
                itemService.bid(memberItem);
            }

            AuctionUpdate auctionUpdate = new AuctionUpdate();
            auctionUpdate.setHighestBid(bidRequest.getBidAmount());
            auctionUpdate.setMessage(message);
            auctionUpdate.setParticipantCount(itemService.getParticipantCount(item));

            messagingTemplate.convertAndSend("/topic/auction/" + item.getId(), auctionUpdate);
        }
    }

    private long calculateTimeRemaining(Item item) {
        // 경매 시작 시간과 현재 시간을 비교하여 남은 시간 계산
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime auctionEndTime = item.getCreatedAt().plusHours(24); // 경매 종료 시간
        return Duration.between(now, auctionEndTime).getSeconds();
    }
}
