package my_task.one.controller;

import jakarta.servlet.http.HttpSession;
import my_task.one.bean.Lang;
import my_task.one.bean.Status;
import my_task.one.bean.Text;
import my_task.one.service.ServiceException;
import my_task.one.service.TextService;
import my_task.one.service.YandexSpellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Optional;

@RestController
public class Starter {

    private final TextService textService;
    private final YandexSpellerService yandexSpellerService;

    @Autowired
    public Starter(TextService textService, YandexSpellerService yandexSpellerService){
    this.textService = textService;
    this.yandexSpellerService = yandexSpellerService;

}

    @RequestMapping("/addNewText")
    public RedirectView addNewText(@RequestParam("Textarea") String textInput, @RequestParam("dropdown") Lang lang, @RequestParam(value = "id", required = false) Long id){
        if (id != null){
            return new RedirectView("/showTextById?id=" + id);
        } else {
            Text text = new Text();
            text.setValue(textInput);
            text.setLang(lang);
            text.setStatus(Status.NEW);
            try {
                textService.saveText(text);
            } catch (ServiceException e) {
                throw new RuntimeException(e);
            }
            return new RedirectView("/fixTheText?id=" + text.getId());
        }
    }

    @RequestMapping("/fixTheText")
    public RedirectView fixTheText(@RequestParam("id") Long id, HttpSession session){
        Text text = null;
        try {
            Optional optText = textService.findById(id);
            if (optText.isPresent()){
                text = (Text) optText.get();
                text.setStatus(Status.IN_PROCESS);
                String fixedText = yandexSpellerService.checkText(text.getValue(), text.getLang());
                text.setFixedValue(fixedText);
                text.setStatus(Status.COMPLETE);
                textService.saveText(text);
                session.setAttribute("fixedText", text);
            }

        } catch (ServiceException e) {
            text = new Text();
            text.setStatus(Status.ERROR);
            session.setAttribute("error", e);
            return new RedirectView("/");
        }
        return new RedirectView("/");
    }

    @RequestMapping("/showTextById")
    public RedirectView showTextById(@RequestParam("id") Long id, HttpSession session){
        try {
            Optional optText = textService.findById(id);
            if (optText.isPresent()){
                Text text = (Text) optText.get();
                if (text.getStatus().equals(Status.COMPLETE)){
                    session.setAttribute("fixedText", text);
                } else if (text.getStatus().equals(Status.IN_PROCESS)){
                    session.setAttribute("inProcess", text);
                }
            }
        } catch (ServiceException e) {
            session.setAttribute("error", e);
            return new RedirectView("/");
        }

        return new RedirectView("/");
    }

}
