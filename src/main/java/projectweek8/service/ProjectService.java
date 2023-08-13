package projectweek8.service;

import projectweek8.entity.Project;
import projectweek8.dao.ProjectDao;




public class ProjectService {

    private ProjectDao projectDao = new ProjectDao();
    
    public Project addProject(Project project) {
        return projectDao.insertProject(project);
    }
}
