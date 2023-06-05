package com.example.siren.web.file;

import com.example.siren.domain.file.Board;
import com.example.siren.domain.file.BoardService;
import com.example.siren.domain.member.Member;
import com.example.siren.domain.member.MemberUpdateDto;
import com.example.siren.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
public class FileUpload {
    private final BoardService boardService;
    private final MemberService memberService;

    @PostMapping("/upload")
    public ResponseEntity<?> createBoard(
            @Validated @RequestPart(value = "files") List<MultipartFile> files,Long id) throws Exception {
        log.info("받은값들 files = {},id={}",files,id);
        Optional<Board> boardCheck = boardService.findBoard(id);
        if(boardCheck.isEmpty()){
            Board board = Board.builder().build();
            board.setId(id);
            boardService.addBoard(board, files,id);
        }else{
            boardService.update(files,id);
        }
        MemberUpdateDto updateDto = new MemberUpdateDto(id,id);
        memberService.updateProfile(updateDto);

        return ResponseEntity.ok().build();
    }

 /*   @ResponseBody
    @GetMapping("/download")
    public ResponseEntity<UrlResource> downloadImage(@RequestParam long id) throws MalformedURLException {
        Board board = boardService.findBoard(id).orElseThrow(RuntimeException::new);
        String imgPath = board.getStoredFileName();
        UrlResource resource = new UrlResource("file:"+imgPath);

        String encodedUploadFileName = UriUtils.encode("2.png", StandardCharsets.UTF_8);
        String contentDisposition = "attachment; filename=\""+encodedUploadFileName+"\"";

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition).body(resource);
    }*/
 @GetMapping("/download")
 public ResponseEntity<Resource> display(@RequestParam long id) throws IOException {
     Optional<Member> member = memberService.findById(id);
     log.info("id={},member.Profile={}",id,member.get().getProfile());
     Long profile = member.get().getProfile();
     Board board = boardService.findBoard(profile).orElseThrow(RuntimeException::new);
     String imgPath = board.getStoredFileName();
     Resource resource = new FileSystemResource("C:\\Users\\hk177\\come-capstone23-pool\\"+imgPath);

     if(!resource.exists())
         return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
     HttpHeaders headers = new HttpHeaders();
     Path filePath = null;

     try{
         filePath = Paths.get("C:\\Users\\hk177\\come-capstone23-pool\\"+imgPath);
         headers.add("Content-Type", Files.probeContentType(filePath));
     }catch (IOException e){
         e.printStackTrace();
     }
     return new ResponseEntity<Resource>(resource,headers,HttpStatus.OK);
 }
}
