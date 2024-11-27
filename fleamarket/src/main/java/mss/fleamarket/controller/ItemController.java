package mss.fleamarket.controller;

import lombok.RequiredArgsConstructor;
import mss.fleamarket.config.data.CustomUserDetails;
import mss.fleamarket.domain.Comment;
import mss.fleamarket.domain.Item;
import mss.fleamarket.domain.Member;
import mss.fleamarket.dto.item.ItemDTO;
import mss.fleamarket.dto.item.ItemForm;
import mss.fleamarket.dto.member.MemberDTO;
import mss.fleamarket.service.comment.CommentService;
import mss.fleamarket.service.item.ItemService;
import mss.fleamarket.service.member.MemberService;
import mss.fleamarket.service.upload.PhotoUploadService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final MemberService memberService;
    private final ItemService itemService;
    private final CommentService commentService;
    private final PhotoUploadService photoUploadService;

    @PostMapping("/item/add")
    public String addItem(@RequestParam("title") String title,
                          @RequestParam("description") String description,
                          @RequestParam("price") Integer price,
                          @RequestParam("photo") MultipartFile photo,
                          Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getEmail();
        Member member = memberService.getMemberByEmail(email);

        try {
            // 파일 업로드
            String photoPath = photoUploadService.upload(photo);
            Item item = new Item(title, photoPath, description, price, member);
            itemService.registerItem(item);
        } catch (IOException e) {
            model.addAttribute("error", "파일 업로드 실패");
            return "itemAdd";
        }

        return "redirect:/";
    }

    @GetMapping("/item/{itemId}")
    public String item(@PathVariable Long itemId, Model model) {
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getEmail();
        Member member = memberService.getMemberByEmail(email);

        Item item = itemService.getItem(itemId);
        int participantCount = itemService.getParticipantCount(item);

        long remainingSeconds = calculateTimeRemaining(item);

        if (remainingSeconds < 0) {
            remainingSeconds = 0; // 경매 시간이 이미 끝났을 경우
        }

        long hours = remainingSeconds / 3600;
        long minutes = (remainingSeconds % 3600) / 60;
        long seconds = remainingSeconds % 60;

        // 남은 시간을 "HH:mm:ss" 형식으로 포맷
        String timeRemaining = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        List<Comment> comments = commentService.getComments(item);
        List<String> messages = new ArrayList<>();

        for (Comment comment : comments) {
            messages.add(comment.getMessage());
        }

        model.addAttribute("isLogin", isLogin());
        model.addAttribute("item", parseDTO(item));
        model.addAttribute("member", parseDTO(member));
        model.addAttribute("highestBid", item.getPrice());
        model.addAttribute("participantCount", participantCount);
        model.addAttribute("messages", messages);
        model.addAttribute("timeRemaining", timeRemaining);
        return "item";
    }

    private long calculateTimeRemaining(Item item) {
        // 경매 시작 시간과 현재 시간을 비교하여 남은 시간 계산
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime auctionEndTime = item.getCreatedAt().plusHours(24); // 경매 종료 시간
        return Duration.between(now, auctionEndTime).getSeconds();
    }

    private boolean isLogin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // 인증 객체가 없거나 익명의 사용자라면 로그인되지 않은 상태
        return authentication != null && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String && authentication.getPrincipal().equals("anonymousUser"));
    }

    private ItemDTO parseDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setTitle(item.getTitle());
        itemDTO.setPhoto(item.getPhoto());
        itemDTO.setDescription(item.getDescription());
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }

    private MemberDTO parseDTO(Member member) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(member.getId());
        memberDTO.setEmail(member.getEmail());
        memberDTO.setName(member.getName());
        return memberDTO;
    }
}
