package com.example.todoTask.services;

import com.example.todoTask.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.todoTask.repositories.UserRepository;
import com.example.todoTask.models.Task;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TaskService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TaskRepository taskRepository;

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        return task.orElseThrow(() -> new RuntimeException(
                "Task não encontrada! Id: " + id + "Tipo: " + Task.class.getName()
        ));
    }

    @Transactional
    public Task craete(Task obj) {
        obj.setId(null);
        obj = this.taskRepository.save(obj);
        return obj;
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
            throw new RuntimeException("Não foi possivel deletar a tarefa!");
        }
    }
}
