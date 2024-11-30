package mss.fleamarket.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mss.fleamarket.config.data.CustomUserDetails;
import mss.fleamarket.domain.Item;
import mss.fleamarket.domain.Member;
import mss.fleamarket.dto.item.ItemDTO;
import mss.fleamarket.dto.item.ItemForm;
import mss.fleamarket.dto.member.MemberForm;
import mss.fleamarket.service.item.ItemService;
import mss.fleamarket.service.member.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PageController {

    private final ItemService itemService;
    private final MemberService memberService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("isLogin", isLogin());

        List<Item> items = itemService.getItems();
        List<ItemDTO> itemDTOS = new ArrayList<>();

        for (Item item : items) {
            itemDTOS.add(parseDTO(item));
        }

        model.addAttribute("items", itemDTOS);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("memberForm", new MemberForm());
        return "join";
    }

    @GetMapping("/item/add")
    public String addItem(Model model) {
        if (!isLogin()) {
            return "redirect:/";
        }
        model.addAttribute("isLogin", isLogin());
        model.addAttribute("itemForm", new ItemForm());
        return "itemAdd";
    }

    @GetMapping("/myPage")
    public String myPage(Model model) {
        if (!isLogin()) {
            return "redirect:/";
        }
        CustomUserDetails userDetails = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = userDetails.getEmail();
        Member member = memberService.getMemberByEmail(email);

        List<Item> registerItems = itemService.getItemsByMember(member);
        List<ItemDTO> registerItemDTOs = new ArrayList<>();
        List<Item> participatedItems = itemService.getItemsByAuction(member);
        List<ItemDTO> participatedItemDTOs = new ArrayList<>();

        for (Item item : registerItems) {
            registerItemDTOs.add(parseDTO(item));
        }

        for (Item item : participatedItems) {
            ItemDTO itemDTO = parseDTO(item);
            log.info("{}", itemDTO);
            participatedItemDTOs.add(itemDTO);
        }

        model.addAttribute("registerItems", registerItemDTOs);
        model.addAttribute("participatedItems", participatedItemDTOs);
        model.addAttribute("isLogin", isLogin());
        return "myPage";
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
        itemDTO.setPrice(item.getPrice());
        return itemDTO;
    }
}
