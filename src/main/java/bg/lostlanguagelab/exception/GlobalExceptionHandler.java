package bg.lostlanguagelab.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntime(RuntimeException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error";
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex, Model model) {
        model.addAttribute("message", "Невалидни данни: " + ex.getMessage());
        return "error";
    }

    @ExceptionHandler(Exception.class)
    public String handleGeneral(Exception ex, Model model) {
        model.addAttribute("message", "Възникна неочаквана грешка.");
        return "error";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String handleNoSuchElement(NoSuchElementException ex, Model model) {
        model.addAttribute("message", "Ресурсът не беше намерен.");
        return "error";
    }

}
