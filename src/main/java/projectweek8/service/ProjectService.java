package projectweek8.service;

import projectweek8.entity.Project;

import java.util.List;
import java.util.NoSuchElementException;

import projectweek8.dao.ProjectDao;




public class ProjectService {

    private ProjectDao projectDao = new ProjectDao();
    
    public Project addProject(Project project) {
        return projectDao.insertProject(project);
    }

        public List<Project> fetchAllProjects() {
           
            return projectDao.fetchAllProjects(); 
        
	}
        public Project fetchProjectById(Integer projectId) {
        	return projectDao.fetchProjectById(projectId).orElseThrow(() -> new NoSuchElementException(
        			"Project with ID=" + projectId + " does not exist."));
        	
        }
}

