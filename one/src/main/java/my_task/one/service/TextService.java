package my_task.one.service;

import my_task.one.bean.Text;

import java.util.Optional;

public interface TextService {

    Text saveText(Text text) throws ServiceException;
    Optional findById(Long id) throws ServiceException;
}
