package org.zerock.ex3.Controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.ex3.dto.SampleDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/sample")
@Log4j2
public class SampleController {

    @GetMapping("/ex1")
    public void ex1() {
        log.info("ex1.....................");
    }

    @GetMapping({"/ex2"}) // 중괄호 {}를 사용하는 이유는 경로(path)를 배열 형태로 지정하여 여러 개의 경로를 한 번에 처리하기 위함 ex) @GetMapping({"/ex2", "/example2"})
    public void exModel(Model model) {
        List<SampleDTO> list = IntStream.rangeClosed(1,20).asLongStream().mapToObj(i -> {
            // IntStream.rangeClosed(1,20)는 1부터 20까지의 숫자를 생성하는 스트림을 생성
            // .asLongStream()은 이를 long 형식의 스트림으로 변환
            // .mapToObj(i -> { ... })는 각 숫자를 SampleDTO 객체로 매핑
            // SampleDTO는 빌더 패턴을 사용하여 생성되며, sno, first, last, regTime 필드를 설정
           SampleDTO dto = SampleDTO.builder()
                   .sno(i)
                   .first("First.." + i)
                   .last("Last.." + i)
                   .regTime(LocalDateTime.now())
                   .build();
           return dto;
        }).collect(Collectors.toList());
        // .collect(Collectors.toList())는 이전 단계에서 생성된 SampleDTO 객체들을 리스트로 수집하는 부분입니다. 즉, 스트림에서 생성된 모든 SampleDTO 객체들을 하나의 리스트로 모아줍니다.
        model.addAttribute("list",list);
    }

    @GetMapping({"/exInline"})
    public String exInline(RedirectAttributes redirectAttributes) {
        log.info("exInline.........");

        SampleDTO dto = SampleDTO.builder()
                .sno(100L)
                .first("First..100")
                .last("Last...100")
                .regTime(LocalDateTime.now())
                .build();
        redirectAttributes.addFlashAttribute("result","success");
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sample/ex3";
    }

    @GetMapping("/ex3")
    public void ex3(){
        log.info("ex3");
    }
}
