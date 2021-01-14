package com.webcontroller;

import com.jpaspecdomain.Member;
import com.jpaspecservice.MemberService;
import com.jpaspecwebmodel.FilterRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_OK;

@Api(tags = "Member API")
@CrossOrigin
@Controller
@RequestMapping(value = "/members", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class MemberController {
    private MemberService memberService;

    public MemberController(@Lazy MemberService memberService) {
        this.memberService = memberService;
    }

    @ApiOperation(value = "Retrieves member information as filtered by an optional search string or filter request")
    @ApiResponses(value = { @ApiResponse(code = SC_OK, message = "ok") })
    @ResponseBody
    @GetMapping(value = "")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Member>> getAllMembers(@RequestParam(required = false) String searchString, FilterRequest filter) {
        return new ResponseEntity<>(memberService.getMembers(filter, searchString), HttpStatus.OK);
    }
}
