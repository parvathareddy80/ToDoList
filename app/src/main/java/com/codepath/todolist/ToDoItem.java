package com.codepath.todolist;

import java.util.Date;
import java.text.SimpleDateFormat;


public class ToDoItem{
    int Id;
    String Task;
    Date DueDate;
    String Status;

    public ToDoItem(String _task, Date _date, String _status){
        super();
        Task = _task;
        DueDate = _date;
        Status = _status;
    }

    public ToDoItem(String _task, String _status){
        super();
        this.Task = _task;
        this.DueDate = new Date(java.lang.System.currentTimeMillis());
        this.Status = _status;
    }

    public ToDoItem(String _task){
        super();
        this.Task = _task;
        this.DueDate = new Date(java.lang.System.currentTimeMillis());
        this.Status = "TO-DO";
    }

    public ToDoItem(){
        Task = "";
        DueDate = new Date(java.lang.System.currentTimeMillis());
        Status = "TO-DO";
    }

    public String getTask(){
        return Task;
    }

    public Date getDueDate(){
        return DueDate;
    }

    public String getStatus(){
        return Status;
    }

    public int getId() {return Id;}

    public void setId(int _Id){
        Id = _Id;
    }

    public void setTask(String _task){
        Task = _task;

    }

    public void setDueDate(Date _date){
        DueDate = _date;
    }

    public void setStatus(String _status){
        Status = _status;
    }
}
