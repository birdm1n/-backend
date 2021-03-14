package com.daema.web;

import com.daema.api.response.enums.ResponseCodeEnum;
import com.daema.api.response.exception.ProcessErrorException;
import com.daema.api.response.io.CommonResponse;
import com.daema.api.response.service.ResponseService;
import com.daema.dto.BoardDto;
import com.daema.dto.request.BoardRequestDto;
import com.daema.dto.response.BoardResponseDto;
import com.daema.service.BoardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Sample API", tags = "Sample API")
@RestController
@RequestMapping("/v1/api/sample")
public class BoardController {

    private final BoardService boardService;

    private final ResponseService responseService;

    public BoardController(BoardService boardService, ResponseService responseService) {
        this.boardService = boardService;
        this.responseService = responseService;
    }

    @ApiOperation(value = "게시판 조회", notes = "게시판을 목록으로 조회합니다")
    @GetMapping("")
    public ResponseEntity<CommonResponse<BoardResponseDto>> getBoardList(BoardRequestDto requestDto) {
        try{
            // 단순 응답
            return responseService.ok(boardService.getBoardList());

            // 조회 object 상태에 따라 성공, 실패 판단하여 응답
            //return responseService.getResponseMessageAsRetrieveResult(boardService.getBoardList(), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
        }catch (Exception e){
            e.printStackTrace();
            return responseService.exception(e.getMessage());
        }
    }

    /**
     * 실패 처리 case1
     * 비즈니스 오류와 런타임 오류를 구분하여 응답값 전달
     */
    @ApiOperation(value = "게시물 등록", notes = "신규 게시물을 등록합니다")
    @PostMapping("")
    public ResponseEntity<CommonResponse<Void>> insertBoard(@ApiParam(value = "게시판 domain", required = true) @RequestBody BoardDto boardDto) {
        try {
            // 단순 응답
            // return responseService.ok(boardService.saveBoard(boardDto));

            // service 에서 리턴받은 CUD 결과에 따라 성공, 실패 판단하여 응답
            return responseService.getResponseMessageAsCUD(boardService.saveBoard(boardDto));
        }catch (Exception e){
            e.printStackTrace();
            //예외처리 case1
            //return responseService.exception(e.getMessage());

            //예외처리 case2
            throw new ProcessErrorException(e.getMessage());
        }
    }

    /**
     * 실패 처리 case2
     * 서비스에서 예외처리
     * 런타임 오류는 무시하고 사용자가 정의한 실패 응답값 전달
     */
    @ApiOperation(value = "게시물 수정", notes = "특정 게시물의 내용을 변경합니다")
    @PatchMapping("")
    public ResponseEntity<CommonResponse<Void>> updateBoard(@ApiParam(value = "게시판 domain", required = true) @RequestBody BoardDto boardDto) {
        return responseService.getResponseMessageAsCUD(boardService.updateBoard(boardDto));
    }

    @ApiOperation(value = "게시물 상세", notes = "특정 게시물을 조회합니다")
    @GetMapping("/{boardNo}")
    public ResponseEntity<CommonResponse<BoardDto>> getBoardDetail(@ApiParam(value = "게시글 번호", required = true) @PathVariable(value = "boardNo") String boardNo) {
        try{
            return responseService.getResponseMessageAsRetrieveResult(boardService.getBoardDetail(boardNo), ResponseCodeEnum.NODATA.getResultCode(), ResponseCodeEnum.NODATA.getResultMsg());
        }catch (Exception e){
            e.printStackTrace();
            return responseService.exception(e.getMessage());
        }
    }

    @ApiOperation(value = "게시물 삭제", notes = "특정 게시물을 삭제합니다")
    @DeleteMapping("/{boardNo}")
    public ResponseEntity<CommonResponse<Void>> deleteBoard(@ApiParam(value = "게시글 번호", required = true) @PathVariable(value = "boardNo") String boardNo) {
        return responseService.getResponseMessageAsCUD(boardService.deleteBoard(boardNo));
    }
}