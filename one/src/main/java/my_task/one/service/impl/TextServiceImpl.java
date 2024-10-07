package my_task.one.service.impl;

import my_task.one.bean.Text;
import my_task.one.dao.TextRepository;
import my_task.one.service.ServiceException;
import my_task.one.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TextServiceImpl implements TextService {


    private final TextRepository textRepository;

    @Autowired
    public TextServiceImpl(TextRepository textRepository) {
        this.textRepository = textRepository;
    }

    @Override
    public Text saveText(Text text) throws ServiceException {
            return textRepository.save(text);
    }

    @Override
    public Optional<Text> findById(Long id) throws ServiceException {
        return textRepository.findById(id);
    }


}
