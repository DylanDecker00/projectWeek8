package projectweek8;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import projectweek8.entity.Project;
import projectweek8.exception.DbException;
import projectweek8.service.ProjectService;



public class Projectweek8App {
	private Scanner scanner = new Scanner(System.in);
			private ProjectService projectService = new ProjectService();
			
			private Project curProject;
			private List<String> operations = List.of(
				    "1) Add a project",
				    "2) List projects",
				    "3) Select a project",
				    "4) Update project details",
				    "5) Delete a project",
				    "6) Delete multiple projects by IDs"
				);


			
			public static void main(String[] args) {
				new Projectweek8App().processUserSelections();
			}

			private void processUserSelections() {
				boolean done = false;
				
				while(!done) {
					try {
						int selection = getUserSelection();
						
						switch(selection) {
						case -1:
							done = exitMenu();
							break;
							
						case 1:
							createProject();
							break;
						case 2:
						    listProjects();
						    break;
						case 3:
							selectProject();
							break;
						case 4:
							updateProjectDetails();
							break;
						case 5:
							deleteProject();
							break;
						case 6:
						    deleteMultipleProjects();
						    break;


							default:
								System.out.println("\n" + selection + " in not a valid selection, try again.");
								break;
						}
					}
					catch(Exception e) {
						System.out.println("\nError: " + e + " Try again.");
						e.printStackTrace();
					}
					}
				
		
			}
			
			private void deleteProject() {
				listProjects();
				Integer projectId = getIntInput("Enter the ID of the project to delete");
				projectService.deleteProject(projectId);
				System.out.println("Project" + projectId + " was deleted successfully.");
				if (Objects.nonNull (curProject) && curProject.getProjectId() .equals (projectId)) {
				curProject = null;
				}
			}
			
				private void updateProjectDetails() {
					if(Objects.isNull(curProject) ) {
						System.out.println("\nPlease select a project.");
						return;
					}
					
					String projectName =
							getStringInput("Enter the project name [" + curProject.getProjectName() + "]");
					
					BigDecimal estimatedHours =
							getDecimalInput("Enter the estimated hours [" + curProject.getEstimatedHours() + "]");
					
					BigDecimal actualHours =
							getDecimalInput("Enter the actual hours + [" + curProject.getActualHours() + "]");
					
					Integer difficulty =
							getIntInput("Enter the project difficulty (1-5) [" + curProject.getDifficulty() + "]");
					
					
					String notes = getStringInput("Enter the project notes [" + curProject.getNotes() + "]");
					
					
					Project project = new Project();
					
					project.setProjectId(curProject.getProjectId());
					project.setProjectName(Objects.isNull(projectName) ? curProject.getProjectName() : projectName);
					
					project.setEstimatedHours (
							Objects.isNull(estimatedHours) ? curProject.getEstimatedHours(): estimatedHours);
					
							project.setActualHours (Objects.isNull(actualHours) ? curProject.getActualHours() : actualHours);
							project.setDifficulty(Objects.isNull(difficulty) ? curProject.getDifficulty(): difficulty);
							project.setNotes (Objects.isNull(notes) ? curProject.getNotes(): notes);
							
							projectService.modifyProjectDetails(project);
							
							curProject = projectService.fetchProjectById(curProject.getProjectId());		
			}

				private void selectProject() {
				                    listProjects();
				                    Integer projectId = getIntInput("Enter a project ID to select a project");
				                    curProject = null;
				                    curProject = projectService.fetchProjectById(projectId);
				
				
			}

				private void createProject() {
				    String projectName = getStringInput("Enter the project name");
				    BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
				    BigDecimal actualHours = getDecimalInput("Enter the actual hours");
				    Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
				    String notes = getStringInput("Enter the project notes");
				    
				    Project project = new Project();
				    project.setProjectName(projectName);
				    project.setEstimatedHours(estimatedHours);
				    project.setActualHours(actualHours);
				    project.setDifficulty(difficulty);
				    project.setNotes(notes);
				    
				    Project dbProject = projectService.addProject(project);
				    
				    System.out.println("You have successfully created project: " + dbProject);
				}				

			private BigDecimal getDecimalInput(String prompt) {
					String input = getStringInput(prompt);
					if(Objects.isNull(input)) {
						return null;
					}
					try {
						return new BigDecimal(input).setScale(2);
					}
					catch(NumberFormatException e) {
						throw new DbException(input + " is not a valid decimal number.");
					}
					}


			private boolean exitMenu() {
				System.out.println("Exiting the menu.");
				
				return true;
			}

			private int getUserSelection() {
				printOperations();
				 Integer input = getIntInput("Enter a menu selection");
				
				return Objects.isNull(input) ? -1 : input;
			}

			private Integer getIntInput(String prompt) {
				String input = getStringInput(prompt);
				
				if(Objects.isNull(input)) {
					return null;
				}
				try {
					return Integer.valueOf(input);
				}
				catch(NumberFormatException e ) {
					throw new DbException(input + " is not a valid number.");
				}
			}
			private String getStringInput(String prompt) {
				System.out.print(prompt + ": ");
				String input = scanner.nextLine();
				return input.isBlank() ? null : input.trim();
			}

			private void printOperations() {
				System.out.println("\nThere are the available selections. Press the Enter Key to exit.");
				operations.forEach(line -> System.out.println(" " + line));
				
				if(Objects.isNull(curProject)) {
					System.out.println("\nYou are not working with a project.");
				}
				else {
					System.out.println("\nYou are working with project: " + curProject);
				}
			
			}
			private void listProjects() {
			    List<Project> projects = projectService.fetchAllProjects();

			    System.out.println("\nProjects:");
			    projects.forEach(project -> System.out.println("  " + project.getProjectId() + ": " + project.getProjectName()));
			}
			private void deleteMultipleProjects() {
			    listProjects();
			    String idsInput = getStringInput("Enter comma-separated IDs of the projects to delete (e.g. 1,3,5)");
			    String[] idStrings = idsInput.split(",");
			    List<Integer> projectIds = new ArrayList<>();

			    // Convert the string IDs to Integer and store them in the projectIds list
			    for (String idString : idStrings) {
			        try {
			            projectIds.add(Integer.valueOf(idString.trim()));
			        } catch (NumberFormatException e) {
			            System.out.println(idString.trim() + " is not a valid number. Skipping...");
			        }
			    }

			    try {
			        // Using the new service method
			        projectService.deleteMultipleProjects(projectIds);

			        // Informing the user of the successful deletion
			        for (Integer projectId : projectIds) {
			            System.out.println("Project " + projectId + " was deleted successfully.");
			            if (Objects.nonNull(curProject) && curProject.getProjectId().equals(projectId)) {
			                curProject = null;
			            }
			        }
			    } catch (DbException e) {
			        System.out.println("Failed to delete some projects. Error: " + e.getMessage());
			    }
			}

}

