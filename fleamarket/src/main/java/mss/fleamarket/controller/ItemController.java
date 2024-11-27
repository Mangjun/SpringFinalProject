package mss.fleamarket.controller;

import lombok.RequiredArgsConstructor;
import mss.fleamarket.config.data.CustomUserDetails;
import mss.fleamarket.domain.Item;
import mss.fleamarket.domain.Member;
import mss.fleamarket.dto.item.ItemDTO;
import mss.fleamarket.dto.item.ItemForm;
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

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final MemberService memberService;
    private final ItemService itemService;
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
        Item item = itemService.getItem(itemId);
        model.addAttribute("isLogin", isLogin());
        model.addAttribute("item", parseDTO(item));
        return "item";
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
}
