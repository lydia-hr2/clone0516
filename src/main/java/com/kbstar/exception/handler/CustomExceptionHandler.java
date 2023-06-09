package com.kbstar.exception.handler;

import com.kbstar.exception.ChatException;
import com.kbstar.exception.ContentException;
import com.kbstar.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    @ExceptionHandler({UserException.class})
    public String handleUserException(UserException e, Model model) {
        log.info("=================== error =====================", e);
        model.addAttribute("error", e.getMessage());
        return "exception";
    }

    @ExceptionHandler({ContentException.class})
    public String handleContentException(ContentException e, Model model) {
        log.info("=================== error =====================", e);
        model.addAttribute("error", e.getMessage());
        return "exception";
    }

    @ExceptionHandler({ChatException.class})
    public String handleChatException(ChatException e, Model model) {
        log.info("=================== error =====================", e);
        model.addAttribute("error", e.getMessage());
        return "exception";
    }
}
