package com.example.demo.controller;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.Task;
import com.example.demo.model.TaskRepository;

@RestController
/*
 * Controller annotation can be used to create web application
 * RestController is a combination of Controller+ResponseBody
 * which can be used to send any kind of response.
 */
public class TaskController
{
	@Autowired
	TaskRepository taskrep;
	/*	@GetMapping-To retreive data from a database
			@PostMapping-To insert data into a database
			@PutMapping-To update data within a database
			@DeleteMapping=to delete data from a database
	 */

	@GetMapping("/tasks")
	public List<Task> getTask()
	{
		//return taskrep.findByOrderByTaskTitleDesc();
		return taskrep.findByOrderByTaskTitleDesc();
	}
	@GetMapping("/tasks/{taskid}")
	public Task getTask(@PathVariable int taskid)
	{
		Optional<Task> tobj=taskrep.findById(taskid);
		Task retreiveobj=null;
		if(tobj.isPresent())
		{
			retreiveobj=tobj.get();
		}
		return retreiveobj;	
	}

	@PostMapping("/insertTask")
	public Task saveTask(@RequestBody Task taskobj)
	{
		Task t=taskrep.save(taskobj);
		return t;
	}

	@PutMapping("/updateTask")
	public Task updateTask(@RequestBody Task obj)
	{
		int taskid=obj.getTaskid();
		Optional<Task> updatetask=taskrep.findById(taskid);
		Task update=null;
		if(updatetask.isPresent())
		{
			update=updatetask.get();
			update.setTaskid(obj.getTaskid());
			update.setAssignedTo(obj.getAssignedTo());
			update.setDuration(obj.getDuration());
			update.setTaskTitle(obj.getTaskTitle());
			taskrep.save(update);
		}
		return update;
	}
	@RequestMapping("/index2")  // http://localhost:8083/index2
	public String getPage() {
		return "default";      // default.html
	}
	@RequestMapping("/taskData") // http://localhost:8083/taskData
	public ModelAndView getTaskData() {
		Task task = new Task();
		task.setTaskid(111);
		task.setAssignedTo("Bah");
		task.setDuration(90);
		task.setTaskTitle("CSS");

		return new ModelAndView(
				"TaskData", // TaskData.jsp
				"taskdata",   // : {taskdata} within the jsp page
				task);      // main message within jsp page   
	}
}
