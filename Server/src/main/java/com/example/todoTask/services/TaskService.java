package com.example.todoTask.services;

import com.example.todoTask.models.User;
import com.example.todoTask.repositories.TaskRepository;
import com.example.todoTask.services.exceptions.DataBindingViolationException;
import com.example.todoTask.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.todoTask.models.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskRepository taskRepository;

    @Transactional
    public Task create(Task obj) {
        User user = this.userService.findById(obj.getUser().getId());
        obj.setId(null);
        obj.setUser(user);
        return this.taskRepository.save(obj);
    }

    public List<Task> findAllByUserId(Long userId) {
        return this.taskRepository.findByUser_Id(userId);
    }

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new ObjectNotFoundException(
                "Task não encontrada! Id: " + id + ", Tipo: " + Task.class.getName()
        ));
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);
        try {
            this.taskRepository.deleteById(id);
        } catch(Exception e) {
            throw new DataBindingViolationException("Não a possivel excluir pois há entidades relacionadas");
        }
    }
}
